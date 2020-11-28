package com.fixme.broker;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.fixme.commons.messaging.Message;
import com.fixme.commons.messaging.MessageStaticFactory;
import com.fixme.commons.orders.Order;
import com.fixme.commons.orders.OrderType;

public class App {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [\u001b[36;1mBROKER\u001b[0m] [%4$-7s] %5$s %n");
    }

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String id;
    
    private static final Logger log = Logger.getLogger( "Broker" );
    private static Scanner userInput = new Scanner(System.in);

    public static void main( String[] args ) throws Exception {
        log.info(String.format("Broker is starting up"));
        
        System.out.println(System.getProperty("java.class.path"));

        try {
            socket = new Socket("localhost", 5000);
        } catch (Exception e) {
            log.severe(String.format("Broker cannot start, router may be unavailable [%s]", e.getMessage()));
            System.exit(1);
        }
        
        new Message();

        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Get logon Message
        String initLine = in.readLine();
        log.info(String.format("Received :: %s", initLine));
        Message initialMessage = MessageStaticFactory.fromRawString(initLine);

        if (initialMessage.get("35").equalsIgnoreCase("L")) {
            App.id = initialMessage.get("109"); 
            log.info(String.format("Broker added to routing table ID=%s", App.id));
        }

        System.out.println("\nIt is the future, all monies are now crypto.. Good luck.");
        System.out.println("Press any key to continue.");
        userInput.nextLine();
        
        while (true) {

            OrderType type = selectOrderType();
            String instrument = getInstrument();
            Integer amount = getAmount();
            Double price = getPrice(type);

            Order order = new Order("Crypto", instrument, amount, price, App.id);
            Message message = MessageStaticFactory.orderMessage(order, type);

            out.println(message.toString());
            out.flush();

            handleRouterReply();
        }
    }

    private static void cls() {
        if (!System.getProperty("os.name").startsWith("Win")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    private static String getInstrument() {
        
        cls();

        ArrayList<String> instruments = new ArrayList<String>();
        instruments.add("ETH,Ethereum");
        instruments.add("XRP,Ripple");
        instruments.add("LTC,Litecoin");
        instruments.add("USDT,Tether");
        instruments.add("BCH,Bitcoin Cash");
        instruments.add("LIBRA,Libra");
        instruments.add("XMR,Monero");
        instruments.add("EOS,EOS");
        instruments.add("BNB,Binance Coin");
        instruments.add("ETH,Ethereum");

        Integer count = 1;
        for (String s : instruments) {
            String name = s.split(",")[0];
            String code = s.split(",")[1];
            System.out.println(String.format("% 2d) %-7s %s", count, name, code));
            count++;
        }
        
        while (true) {
            try {
                System.out.print("\nSelect an instrument from 1 to 10 : ");
                Integer answer = Integer.parseInt(userInput.nextLine());
                if (answer > 0 && answer < 11) {
                    return instruments.get(answer - 1).split(",")[0];
                }
            } catch (Exception e) {
                continue;     
            }
        }
    }

    private static Integer getAmount() {
        cls();
        while (true) {
            try {
                System.out.print("How many units would you like to purchase? : ");
                return Integer.parseInt(userInput.nextLine());
            } catch (Exception e) {
                System.out.println("Must be an integer..");
            }
        }
    }

    private static OrderType selectOrderType() {
        cls();

        System.out.println("Would you like to buy or sell?");
        
        while (true) {
            System.out.println("1) Buy");
            System.out.println("2) Sell");
            String answer = userInput.nextLine();
            if (answer.equalsIgnoreCase("1")) {
                return OrderType.BUY;
            } else if (answer.equalsIgnoreCase("2")) {
                return OrderType.SELL; 
            }
            System.out.println("Would [you like to buy or sell?");
            System.out.println("Must be either 1 or 2");
        }
    }

    private static Double getPrice(OrderType type) {
        cls();

        String prompt;

        if (type.equals(OrderType.BUY)) {
            prompt = "How much would you like to spend for each unit? : ";
        } else {
            prompt = "How much would you like to sell each unit for? : ";
        }

        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(userInput.nextLine());
            } catch (Exception e) {
                System.out.println("Must be a number. e.g. 20.01 ");
            }
        }
    }

    private static void handleRouterReply() {
        try {
            System.out.println("We wait for input...");
            String inputLine = in.readLine();

            // This is not handled at all, because lazy.
            // Use this to create a message then do stuff with it
            userInput.nextLine();

            MessageStaticFactory.fromRawString(inputLine);

            if (inputLine == null) {
                log.severe("Connection to router lost");
                System.exit(1);
            }

        } catch (Exception e) {
            // Dont know if this needs to be here?
        }
    }
}
