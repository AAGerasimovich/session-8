
import java.util.concurrent.locks.Lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class Test1 extends AbstractTest {
    private static final AtomicInteger val = new AtomicInteger();


    public static void main(String... args) throws InterruptedException {

        for (int i = 0; i < 10_000; i++) {
            assert size() == 0;

            Thread th1 = th();
            Thread th2 = th();
            Thread th3 = th();

            th1.start();
            th2.start();
            th3.start();

            th1.join();
            th2.join();
            th3.join();

            assert size() == 3;
            assert containsKey(1);
            assert containsKey(2);
            assert containsKey(3);
            assert contains(th1);
            assert contains(th2);
            assert contains(th3);

            clear();

            System.out.println("Checked " + i);
        }
    }

    private static Thread th() {
        return new Thread(() -> {
        put(val.incrementAndGet());
        if (val.get()==3){
            val.compareAndSet(3,0);
        }


        });
    }

}
