package com.fixme.market;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;


import com.fixme.commons.messaging.*;
import com.fixme.commons.orders.Order;
import com.fixme.market.markets.CryptoMarket;
import com.fixme.market.markets.Instrument;

public class App {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [\u001b[35;1mMARKET\u001b[0m] [%4$-7s] %5$s %n");
    }

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static CryptoMarket market = new CryptoMarket();
    public static final Logger log = Logger.getLogger( "Market" );

    public static void main( String[] args ) throws Exception {

        log.info(String.format("Market is starting up"));

        try {
            socket = new Socket("localhost", 5001);
        } catch (Exception e) {
            log.severe(String.format("Market cannot start, router may be unavailable [%s]", e.getMessage()));
            socket.close();
            System.exit(1);
        }

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Take initial message from router and set this markets ID
        receiveAndSetLogonId();
        sendMarketName();
        listInstruments();

        Order order = null;
        while (true) {
            try {

                log.info("Waiting for requests");
                String inputLine = in.readLine();

                if (inputLine == null) {
                    log.severe("Connection to router lost");
                    break;
                }

                log.info(String.format("Received :: %s", inputLine));

                Message initialMessage = MessageStaticFactory.fromRawString(inputLine);
                Message message = validateOrderInMessage(initialMessage);

                if (message.valid == false) {
                    log.severe(String.format("Sending :: Rejection :: %s", message.toString()));
                    sendMessage(message);
                    break;
                }

                order = new Order(message);

                switch (message.get("35")) {

                    case "B":
                        log.info(String.format("Processing Buy Order"));
                        if (market.instrumenByCode(message.get("I")) == null)
                            throw new Exception("Cannot find instrument");
                        tryBuyOrder(order);
                        break;
                
                    case "S":
                        log.info(String.format("Processing Sell Order"));
                        if (market.instrumenByCode(message.get("I")) == null)
                            throw new Exception("Cannot find instrument");
                        trySellOrder(order);
                        break;

                    default:
                        throw new Exception("Not a recognised order type");
                }

            } catch (NumberFormatException e) {
                log.severe("Rejection :: Message contains invalid number format");
                sendMessage(MessageStaticFactory.rejectOrder(order, "Message contains invalid number format"));
            } catch (Exception e) {
                e.printStackTrace();
                log.severe(String.format("Rejection :: %s", e.getMessage()));
                sendMessage(MessageStaticFactory.rejectOrder(order, e.getMessage()));
            } 
        }
        in.close();
        out.close();
        socket.close();
        log.info(String.format("Market has been shutdown"));
    }

    public static void sendMessage(Message message) {
        out.println(message.toString());
    }

    public static Message validateOrderInMessage(Message order) {
        String market =     order.get("M");
        String instrument = order.get("I");
        String amount =     order.get("A");
        String price =      order.get("P");
        String clientId =   order.get("109");

        if (market.length() < 1)        return MessageStaticFactory.failResponse("Market has not been specified");
        if (instrument.length() < 1)    return MessageStaticFactory.failResponse("Instrument has not been specified");
        if (amount.length() < 1)        return MessageStaticFactory.failResponse("Amount has not been specified");
        if (price.length() < 1)         return MessageStaticFactory.failResponse("Price has not been specified");
        if (clientId.length() < 1)      return MessageStaticFactory.failResponse("Client ID has not been specified");
        return order;
    }

    private static void trySellOrder(Order order) {
        Instrument instrument = market.instrumenByCode(order.instrument);

        Integer requestedSellAmount = Integer.parseInt(order.amount);
        Double requestedSellPrice = Double.parseDouble(order.price);

        if (!order.valid) {
            sendMessage(MessageStaticFactory.rejectOrder(order, String.format("Invalid Order", instrument.name)));
            return;
        }

        if (requestedSellPrice <= instrument.maxSellPrice) {
            instrument.availableUnits += requestedSellAmount;
            sendMessage(MessageStaticFactory.acceptOrder(order));
        } else {
            log.warning(String.format("Rejected : Sell price for %s is too low", instrument.name));
            sendMessage(MessageStaticFactory.rejectOrder(order, String.format("Cannot sell %s at this price", instrument.name)));
        }        
    }

    public static void tryBuyOrder(Order order) {
        Instrument instrument = market.instrumenByCode(order.instrument);

        Integer requestedBuyAmount = Integer.parseInt(order.amount);
        Double requestedBuyPrice = Double.parseDouble(order.price);

        if (!order.valid) {
            sendMessage(MessageStaticFactory.rejectOrder(order, String.format("Invalid Order", instrument.name)));
            return;
        }

        if (requestedBuyPrice >= instrument.minBuyPrice) {
            if (requestedBuyAmount <= instrument.availableUnits) {
                instrument.availableUnits -= requestedBuyAmount;
                sendMessage(MessageStaticFactory.acceptOrder(order));
            } else {
                log.warning(String.format("Rejected : Not enough %s units to complete order", instrument.name));
                sendMessage(MessageStaticFactory.rejectOrder(order, String.format("Not enough units to complete order", instrument.name)));
            }
        } else {
            log.warning(String.format("Rejected : Buy price for %s is too low", instrument.name));
            sendMessage(MessageStaticFactory.rejectOrder(order, String.format("Cannot buy %s at this price", instrument.name)));
        }
    }

    private static void receiveAndSetLogonId() throws IOException {

        Message init = MessageStaticFactory.fromRawString(in.readLine());
        market.id = init.get("109"); 
        log.info(String.format("Market added to routing table ID=%s", market.id));
    }

    private static void sendMarketName() throws IOException {

        Message name = MessageStaticFactory.identifyMarket(market.id, market.name);
        out.println(name);
        log.info(String.format("Sent market name [%s] to router", market.name));
    }

    private static void listInstruments() {
        for (Instrument i : market.instruments) {
            log.info(String.format("%-20s[%-5s] => Units = %-4d : minBuy=%-20.2f : maxSell=%-20.2f", i.name, i.code, i.availableUnits, i.minBuyPrice, i.maxSellPrice));
        }
    }
}
