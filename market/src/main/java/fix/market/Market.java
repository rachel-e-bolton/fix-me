package fix.market;

import java.io.*;
import java.net.Socket;

import fix.market.financial.FinancialMarket;

public class Market 
{
    public static void main( String[] args ) throws Exception {
        
        //establish socket connection to server
        Socket socket = new Socket("localhost", 5000);
        //write to socket using ObjectOutputStream
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        
        // if(i==4)oos.writeObject("exit");
        // else oos.writeObject(""+i);
        //read the server response message
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        out.println("35=3|58=Error: Part of message is malformed at asd|10=143|");
        out.flush();

        String inputLine = in.readLine();
        // while ((inputLine = in.readLine()) != null) {
        // }
        System.out.println(inputLine);

        in.close();
        out.close();


        FinancialMarket jse = new FinancialMarket("JSE");

        jse.instruments










    }

    public String connectToRouter() {


        return "";
    }
}
