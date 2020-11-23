package fix.router;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import fix.router.routing.Table;
import fix.router.sockets.ExtendedExecutor;
import fix.router.sockets.SocketServer;


public class Router {
    private static final Logger LOGGER = Logger.getLogger( "Router" );
    public static final Table routingTable = new Table();
    // public static final ExecutorService executor = Executors.newFixedThreadPool(10000000);
    public static final ExecutorService executor = new ExtendedExecutor(10000000);

    public static void main( String[] args ) {

        LOGGER.info("Router Application Start");

        // Start socket server thread
        executor.submit(new SocketServer(5000));


        // Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        //     @Override
        //     public void uncaughtException(Thread t, Throwable e) {
        //         System.out.println("Caught " + e);
        //     }
        // });

        // MessageHandler message = new ValidationProcessor(
        //     new RoutingProcessor(null)
        // );

        // Message message = new Message("35=3|49=SellSide|56=BuySide|34=3|52=20190606-11:01:58.231|45=3|58=Invalid Resend Request: BeginSeqNo (5) is greater than expected (2)|10=174|");

        // System.out.print(message.get(10));


        
        // System.out.println(message.process("35=Login|1=Name Of whatever|asd|asd|asd|10=123|"));


        // Start the socket maintainer thread

        // public String getCurrentLocalDateTimeStamp() {
        //     return LocalDateTime.now()
        //        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        // }
        // System.out.println("8=FIX.4.2|9=58|35=0|49=BuySide|56=SellSide|34=4|52=20190605-12:19:52.060|10=165|".split("|"));
        // new FIXMessage("8=FIX.4.2|9=58|35=0|49=BuySide|56=SellSide|34=4|52=20190605-12:19:52.060|10=165|");

        //System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss.SSS")));

        // System.exit(0);

        // 52 = 20190605-12:19:52.060

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.exit(1);
        }
    }
}
