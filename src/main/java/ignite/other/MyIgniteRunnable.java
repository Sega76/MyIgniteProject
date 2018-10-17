package ignite.other;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.transactions.Transaction;

import java.util.Random;

import static org.apache.ignite.transactions.TransactionConcurrency.OPTIMISTIC;
import static org.apache.ignite.transactions.TransactionConcurrency.PESSIMISTIC;
import static org.apache.ignite.transactions.TransactionIsolation.READ_COMMITTED;
import static org.apache.ignite.transactions.TransactionIsolation.REPEATABLE_READ;
import static org.apache.ignite.transactions.TransactionIsolation.SERIALIZABLE;

public class MyIgniteRunnable implements IgniteRunnable {
    private IgniteCache<Integer, Account> cache;
    private Integer clientId;
    private int ENTRIES_COUNT = 10;
    @IgniteInstanceResource
    private Ignite ignite;


    public MyIgniteRunnable (IgniteCache<Integer, Account> cache, Integer clientId) {
        this.cache = cache;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println("iteration number "+i);
            transferMoney(this.cache, this.ignite, clientId);
//            this.notify();
        }
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max (" + max + ") must be greater than min (" + min + ")");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    private int getRandomAmount(int maxAmount) {
        return getRandomNumberInRange(0, maxAmount);
    }


    private void transferMoney(IgniteCache<Integer, Account> cache, Ignite ignite, Integer clientId) {
        try (Transaction tx = ignite.transactions().txStart(OPTIMISTIC, SERIALIZABLE)) {
            System.out.println("start TX on client "+clientId);
            int fromAccountId = getRandomNumberInRange(1, ENTRIES_COUNT);
            int toAccountId =  getRandomNumberInRange(1, ENTRIES_COUNT);

            while (fromAccountId == toAccountId) {
                toAccountId = getRandomNumberInRange(1, ENTRIES_COUNT);
            }

            Account fromAccount = cache.get(fromAccountId);
            Account toAccount = cache.get(toAccountId);

            int amount = getRandomAmount(fromAccount.balance);

            if (amount < 1) {
                System.out.println("amount "+amount);
                // No money
                return;
            }

            int fromAccountBalanceBeforeTransfer = fromAccount.balance;
            int toAccountBalanceBeforeTransfer = toAccount.balance;

            // Withdraw from account
            fromAccount.withdraw(amount);

            // Deposit into account.
            toAccount.deposit(amount);

            int fromAccountBalanceAfterTransfer = fromAccount.balance;
            int toAccountBalanceAfterTransfer = toAccount.balance;

            // Store updated accounts in cache.
            cache.put(fromAccountId, fromAccount);
            cache.put(toAccountId, toAccount);

            String message = "Client " + clientId + " transfers $" + amount + " from account " + fromAccountId + " to account " + toAccountId + "\n" +
                    "Client " + clientId + " account " + fromAccountId + " balance before transfer is $" + fromAccountBalanceBeforeTransfer + "\n" +
                    "Client " + clientId + " account " + toAccountId + " balance before transfer is $" + toAccountBalanceBeforeTransfer + "\n" +
                    "Client " + clientId + " account " + fromAccountId + " balance after transfer is $" + fromAccountBalanceAfterTransfer + "\n" +
                    "Client " + clientId + " account " + toAccountId + " balance after transfer is $" + toAccountBalanceAfterTransfer + "\n";
            System.out.println(message);
            tx.commit();
        }
    }


}