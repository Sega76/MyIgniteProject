package ignite.callable;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

public class CallTXPessReadComm implements IgniteCallable {
    @IgniteInstanceResource
    private Ignite ignite;

    @Override
    public Object call() throws Exception {
        IgniteCache<Long, Long> cache = ignite.getOrCreateCache("Test_Transactional_Replicated");
        try (Transaction t = ignite.transactions().txStart(TransactionConcurrency.PESSIMISTIC, TransactionIsolation.READ_COMMITTED)) {
            for (Long i = 0L; i < 1000; i++) {
                cache.put(1L, cache.get(1l) + 1L);

            }

            t.commit();

            return cache.get(1l);
        }
    }
}
