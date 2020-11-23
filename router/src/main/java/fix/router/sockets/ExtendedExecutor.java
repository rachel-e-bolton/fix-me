package fix.router.sockets;

import java.util.concurrent.*;

public class ExtendedExecutor extends ThreadPoolExecutor {

	public ExtendedExecutor(int corePoolSize) {
				super(  2, // core threads
                corePoolSize, // max threads
                10, // timeout
                TimeUnit.SECONDS, // timeout units

                new LinkedBlockingQueue<Runnable>() // work queue

        );
    }

    protected void afterExecute(Runnable r, Throwable t) {

		super.afterExecute(r, t);
		
		System.out.println("CLOSING THIS THREAD BECAUSE OF UNCAUGHT");

        if (t == null && r instanceof Future<?>) {

            try {

                Future<?> future = (Future<?>) r;

                if (future.isDone()) {

                    future.get();

                }

            } catch (CancellationException ce) {

                t = ce;

            } catch (ExecutionException ee) {

                t = ee.getCause();

            } catch (InterruptedException ie) {

                Thread.currentThread().interrupt();

            }

        }

        if (t != null) {

            System.out.println(t);

        }

    }

}