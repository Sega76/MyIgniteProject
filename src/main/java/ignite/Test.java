package ignite;

import ignite.runnable.RunPrint;
import ignite.runnable.RunTX;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
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
                        )))) {

            IgniteRunnable runtx = new RunTX.Builder("Test_Transactional_Replicated")
                    .latency(100L)
                    .size(100L)
                    .transactionConcurrency(TransactionConcurrency.OPTIMISTIC)
                    .transactionIsolation(TransactionIsolation.READ_COMMITTED)
                    .build();

            IgniteRunnable runPrint = new RunPrint("Test_Transactional_Replicated",100L,1000L);

            List <IgniteRunnable> arr = Arrays.asList(runtx,runPrint);

            createCache(ignite,"Test_Transactional_Replicated");
            ignite.compute().run(arr);
Thread.sleep(30000);
            System.out.println(checkCache(ignite, "Test_Transactional_Replicated"));
        }
    }

    public static void createCache(Ignite ignite, String cacheName) {
        IgniteCache<Long, Long> cache = ignite.getOrCreateCache(
                new CacheConfiguration<Long, Long>()
                        .setName(cacheName)
                        .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
                        .setCacheMode(CacheMode.REPLICATED));

        cache.put(1L, 0L);
        System.out.println(cache.get(1L));
    }

    public static Long checkCache(Ignite ignite, String cacheName){
        IgniteCache<Long, Long> cache = ignite.getOrCreateCache(cacheName);
        return cache.get(1L);
    }
}
