import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.processors.query.h2.twostep.GridReduceQueryExecutor;
import org.apache.ignite.internal.util.GridSpinBusyLock;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import stepik.oop.TextAnalyzer;
import stepik.oop.impl.NegativeTextAnalyzer;
import stepik.oop.impl.SpamAnalyzer;

import javax.sound.midi.Soundbank;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
//        new GridReduceQueryExecutor(new AtomicLong(18L),new GridSpinBusyLock()).query(null,)

        IgniteConfiguration ignCfg = new IgniteConfiguration()
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
                );

        try (Ignite ignite = Ignition.start()){
            System.out.println("Start");
        }

//
//        TextAnalyzer nt = new NegativeTextAnalyzer();
//        SpamAnalyzer sp = (SpamAnalyzer)nt;
//        System.out.println(sp.getName());

    }

}
