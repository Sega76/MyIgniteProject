package other;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        A a = new A("a");
        B b = new B("b");
        System.out.println(b.getClass().equals(A.class));
        System.out.println();

        long start = System.currentTimeMillis();
        Thread.sleep(10_000L);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        long time = TimeUnit.SECONDS.convert(finish-start,TimeUnit.MILLISECONDS);
        System.out.println(time+"s");

    }
}
