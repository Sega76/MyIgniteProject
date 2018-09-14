package ignite.runnable;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.time.LocalDateTime;


public class RunPrint implements IgniteRunnable {
    private String cacheName;
    private Long size = 1000L;
    private Long latency = 0L;


    @IgniteInstanceResource
    private Ignite ignite;

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + " " + this.getClass().toString() + " START");
        IgniteCache<Long, Long> cache = ignite.getOrCreateCache(cacheName);

        for (Long i = 0L; i < size; i++) {
            System.out.println(cache.get(1L));
//            assert (1L==cache.get(1L))|(size==cache.get(1L));
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(LocalDateTime.now() + " " + this.getClass().toString() + " END");
    }

    public RunPrint(String cacheName) {
        this.cacheName = cacheName;
    }

    public RunPrint(String cacheName, Long size, Long latency) {
        this.cacheName = cacheName;
        this.size = size;
        this.latency = latency;
    }
}

