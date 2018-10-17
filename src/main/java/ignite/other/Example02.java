package ignite.other;

import ignite.runnable.RunTX;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
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
    /** Cache name. */
    private static final String CACHE_NAME = "TestCache";

    /** Total number of entries to use in the example. */
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
                .setDiscoverySpi(new TcpDiscoverySpi()
                        .setIpFinder(new TcpDiscoveryVmIpFinder()
                                .setAddresses(Arrays.asList("127.0.0.1:47500..47509")
                                )
                        )
                ))) {

            CacheConfiguration<Integer, Account> cfg = new CacheConfiguration<>(CACHE_NAME);
            cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);

            try (IgniteCache<Integer, Account> cache = ignite.getOrCreateCache(cfg)) {
                // Initializing the cache.
                for (int i = 1; i <= ENTRIES_COUNT; i++)
                    cache.put(i, new Account(i, 100));

                System.out.println("Accounts before transfers");
                System.out.println();
                printAccounts(cache);
                printTotalBalance(cache);

                IgniteRunnable run1 = new MyIgniteRunnable(cache,1);
                IgniteRunnable run2 = new MyIgniteRunnable(cache,2);

                List<IgniteRunnable> arr = Arrays.asList(run1,run2);
//                ignite.compute().run(new MyIgniteRunnable(cache,1));
                ignite.compute().run(arr);
                Thread.sleep(10_000L);

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

//    private static int getRandomNumberInRange(int min, int max) {
//
//        if (min > max) {
//            throw new IllegalArgumentException("max (" + max + ") must be greater than min (" + min + ")");
//        }
//
//        Random r = new Random();
//        return r.nextInt((max - min) + 1) + min;
//    }
//
//    protected synchronized static int getRandomAmount(int maxAmount) {
//        return getRandomNumberInRange(0, maxAmount);
//    }

//    private static void transferMoney(IgniteCache<Integer, Account> cache, Ignite ignite, Integer clientId) {
//        try (Transaction tx = ignite.transactions().txStart(PESSIMISTIC, SERIALIZABLE)) {
//            int fromAccountId = getRandomNumberInRange(1, ENTRIES_COUNT);
//            int toAccountId = getRandomNumberInRange(1, ENTRIES_COUNT);
//
//            while (fromAccountId == toAccountId) {
//                toAccountId = getRandomNumberInRange(1, ENTRIES_COUNT);
//            }
//
//            Account fromAccount = cache.get(fromAccountId);
//            Account toAccount = cache.get(toAccountId);
//
//            int amount = getRandomAmount(fromAccount.balance);
//            if (amount < 1) {
//                // No money
//                return;
//            }
//
//            int fromAccountBalanceBeforeTransfer = fromAccount.balance;
//            int toAccountBalanceBeforeTransfer = toAccount.balance;
//
//            // Withdraw from account
//            fromAccount.withdraw(amount);
//
//            // Deposit into account.
//            toAccount.deposit(amount);
//
//            int fromAccountBalanceAfterTransfer = fromAccount.balance;
//            int toAccountBalanceAfterTransfer = toAccount.balance;
//
//            // Store updated accounts in cache.
//            cache.put(fromAccountId, fromAccount);
//            cache.put(toAccountId, toAccount);
//
//            String message = "Client " + clientId + " transfers $" + amount + " from account " + fromAccountId + " to account " + toAccountId + "\n" +
//                    "Client " + clientId + " account " + fromAccountId + " balance before transfer is $" + fromAccountBalanceBeforeTransfer + "\n" +
//                    "Client " + clientId + " account " + toAccountId + " balance before transfer is $" + toAccountBalanceBeforeTransfer + "\n" +
//                    "Client " + clientId + " account " + fromAccountId + " balance after transfer is $" + fromAccountBalanceAfterTransfer + "\n" +
//                    "Client " + clientId + " account " + toAccountId + " balance after transfer is $" + toAccountBalanceAfterTransfer + "\n";
//            System.out.println(message);
//            tx.commit();
//        }
//    }

    private  static void printAccounts(IgniteCache<Integer, Account> cache) {
        for (int acctId = 1; acctId <= ENTRIES_COUNT; acctId++)
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
