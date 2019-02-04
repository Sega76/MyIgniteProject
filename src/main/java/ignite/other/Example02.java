package ignite.other;

import ignite.runnable.RunTX;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.configuration.*;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.transactions.Transaction;

import static org.apache.ignite.transactions.TransactionConcurrency.OPTIMISTIC;
import static org.apache.ignite.transactions.TransactionConcurrency.PESSIMISTIC;
import static org.apache.ignite.transactions.TransactionIsolation.READ_COMMITTED;
import static org.apache.ignite.transactions.TransactionIsolation.REPEATABLE_READ;
import static org.apache.ignite.transactions.TransactionIsolation.SERIALIZABLE;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Example02
 */
public class Example02 {
    /**
     * Cache name.
     */
    private static final String CACHE_NAME = "TestCache";

    /**
     * Total number of entries to use in the example.
     */
    private static int ENTRIES_COUNT = 10;

    public static void main(String[] args) throws IgniteException, InterruptedException {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start(new IgniteConfiguration()
                .setDeploymentMode(DeploymentMode.PRIVATE)
                .setPeerClassLoadingEnabled(true)
                .setBinaryConfiguration(new BinaryConfiguration()
                        .setCompactFooter(true))
                .setCommunicationSpi(new TcpCommunicationSpi()
                        .setLocalAddress("localhost"))
                        .setTransactionConfiguration(new TransactionConfiguration()
                                .setDefaultTxTimeout(5_000L))
                .setDiscoverySpi(new TcpDiscoverySpi()
                        .setIpFinder(new TcpDiscoveryVmIpFinder()
                                .setAddresses(Arrays.asList("127.0.0.1:47500..47509")
                                )
                        )
                ))) {

            CacheConfiguration<Integer, Account> cfg = new CacheConfiguration<>(CACHE_NAME);
            cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

            try (IgniteCache<Integer, Account> cache = ignite.getOrCreateCache(cfg)) {
                cache.size(CachePeekMode.ALL);
                
                // Initializing the cache.
                for (int i = 1; i <= ENTRIES_COUNT; i++)
                    cache.put(i, new Account(i, 100));


                System.out.println("Accounts before transfers");
                System.out.println();
                printAccounts(cache);
                printTotalBalance(cache);

                IgniteRunnable run1 = new MyIgniteRunnable(cache, 1);
                IgniteRunnable run2 = new MyIgniteRunnable(cache, 2);

                List<IgniteRunnable> arr = Arrays.asList(run1, run2);
//                ignite.compute().run(new MyIgniteRunnable(cache,1));
                ignite.compute().run(arr);

//                Thread th1 = new Thread(new MyIgniteRunnable(cache,1));
//                Thread th2 = new Thread(new MyIgniteRunnable(cache,2));


//                th1.start();
//                th2.start();

                // Waiting for the completion.
//                th1.join();
//                th2.join();

                System.out.println();
                System.out.println("Accounts after transfers");
                printAccounts(cache);
                printTotalBalance(cache);
            }
        }
    }

    private static void printAccounts(IgniteCache<Integer, Account> cache) {
        for (Integer acctId = 1; acctId <= ENTRIES_COUNT; acctId++)
            System.out.println("[" + acctId + "] = " + cache.get(acctId));
    }

    private static void printTotalBalance(IgniteCache<Integer, Account> cache) throws IgniteException {
        int totalBalance = 0;

        for (int acctId = 1; acctId <= ENTRIES_COUNT; acctId++) {
            Account account = cache.get(acctId);
            totalBalance += account.balance;
        }

        System.out.println("Total Balance: " + totalBalance);
    }
}
