package ignite;

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.BaselineNode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.events.DiscoveryEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.internal.IgniteEx;
import org.apache.ignite.internal.processors.timeout.GridTimeoutObjectAdapter;

public class BaselineWatcher {
    /** Ignite. */
    private final IgniteEx ignite;

    /** BLT change delay millis. */
    private final long bltChangeDelayMillis;

    /**
     * @param ignite Ignite.
     */
    public BaselineWatcher(Ignite ignite, long bltChangeDelayMillis) {
        this.ignite = (IgniteEx)ignite;
        this.bltChangeDelayMillis = bltChangeDelayMillis;
    }

    /**
     *
     */
    public void start() {
        ignite.events().localListen(event -> {
            DiscoveryEvent e = (DiscoveryEvent)event;

            Set<Object> aliveSrvNodes = e.topologyNodes().stream()
                    .filter(n -> !n.isClient())
                    .map(ClusterNode::consistentId)
                    .collect(Collectors.toSet());

            Set<Object> baseline = ignite.cluster().currentBaselineTopology().stream()
                    .map(BaselineNode::consistentId)
                    .collect(Collectors.toSet());

            final long topVer = e.topologyVersion();

            if (!aliveSrvNodes.equals(baseline))
                ignite.context().timeout().addTimeoutObject(new GridTimeoutObjectAdapter(bltChangeDelayMillis) {
                    @Override public void onTimeout() {
                        if (ignite.cluster().topologyVersion() == topVer)
                            ignite.cluster().setBaselineTopology(topVer);
                    }
                });

            return true;
        }, EventType.EVT_NODE_FAILED, EventType.EVT_NODE_LEFT, EventType.EVT_NODE_JOINED);
    }
}