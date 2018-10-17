package ignite.runnable;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;
import java.time.LocalDateTime;

public class RunTX implements IgniteRunnable {
    private String cacheName;
    private Long size;
    private TransactionIsolation transactionIsolation;
    private TransactionConcurrency transactionConcurrency;
    private Long latency;

    @IgniteInstanceResource
    private Ignite ignite;

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + " " + this.getClass().toString() + " START");
        IgniteCache<Long, Long> cache = ignite.getOrCreateCache(cacheName);
        try (Transaction t = ignite.transactions().txStart(transactionConcurrency, transactionIsolation)) {
            for (Long i = 0L; i < size; i++) {
                cache.put(1L, cache.get(1L) + 1L);
                Thread.sleep(latency);
            }
            t.commit();
            System.out.println(cache.get(1L));
            System.out.println(LocalDateTime.now() + " " + this.getClass().toString() + " END");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        private final String cacheName;
        //default values
        private Long size = 1000L;
        private TransactionIsolation transactionIsolation = TransactionIsolation.REPEATABLE_READ;
        private TransactionConcurrency transactionConcurrency = TransactionConcurrency.PESSIMISTIC;
        private Long latency = 0L;


        public Builder(String cacheName) {
            this.cacheName = cacheName;
        }

        public Builder size(Long val) {
            this.size = val;
            return this;
        }

        public Builder transactionIsolation(TransactionIsolation val) {
            this.transactionIsolation = val;
            return this;
        }

        public Builder latency(Long millis) {
            this.latency = millis;
            return this;
        }

        public Builder transactionConcurrency(TransactionConcurrency val) {
            this.transactionConcurrency = val;
            return this;
        }

        public RunTX build() {
            return new RunTX(this);
        }
    }

    private RunTX(Builder builder) {
        cacheName = builder.cacheName;
        size = builder.size;
        transactionIsolation = builder.transactionIsolation;
        transactionConcurrency = builder.transactionConcurrency;
        latency = builder.latency;
    }

}
