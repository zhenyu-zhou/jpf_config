package myhub;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
/* import net.floodlightcontroller.core.IFloodlightProviderService.Role;
import net.floodlightcontroller.core.internal.Controller;
import net.floodlightcontroller.debugcounter.IDebugCounterService;
import net.floodlightcontroller.debugcounter.IDebugCounterService.CounterException;
import net.floodlightcontroller.threadpool.IThreadPoolService;
import net.floodlightcontroller.util.OrderedCollection;

import org.jboss.netty.channel.Channel;
import org.openflow.protocol.OFFeaturesReply;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPortStatus;
import org.openflow.protocol.OFStatisticsReply;
import org.openflow.protocol.OFStatisticsRequest;
import org.openflow.protocol.statistics.OFDescriptionStatistics;
import org.openflow.protocol.statistics.OFStatistics; */

public interface IOFSwitch {
    // Attribute keys
    public static final String SWITCH_DESCRIPTION_FUTURE = "DescriptionFuture";
    public static final String SWITCH_SUPPORTS_NX_ROLE = "supportsNxRole";
    public static final String SWITCH_IS_CORE_SWITCH = "isCoreSwitch";
    public static final String PROP_FASTWILDCARDS = "FastWildcards";
    public static final String PROP_REQUIRES_L3_MATCH = "requiresL3Match";
    public static final String PROP_SUPPORTS_OFPP_TABLE = "supportsOfppTable";
    public static final String PROP_SUPPORTS_OFPP_FLOOD = "supportsOfppFlood";
    public static final String PROP_SUPPORTS_NETMASK_TBL = "supportsNetmaskTbl";

    public enum OFPortType {
        NORMAL("normal"),         // normal port (default)
        TUNNEL("tunnel"),         // tunnel port
        UPLINK("uplink"),         // uplink port (on a virtual switch)
        MANAGEMENT("management"), // for in-band management
        TUNNEL_LOOPBACK("tunnel-loopback");

        private final String value;
        OFPortType(String v) {
            value = v;
        }

        @Override
        public String toString() {
            return value;
        }

        public static OFPortType fromString(String str) {
            for (OFPortType m : OFPortType.values()) {
                if (m.value.equals(str)) {
                    return m;
                }
            }
            return OFPortType.NORMAL;
        }
    }

    /* public static class PortChangeEvent {
        public final ImmutablePort port;
        public final PortChangeType type;
        public PortChangeEvent(ImmutablePort port,
                               PortChangeType type) {
            this.port = port;
            this.type = type;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((port == null) ? 0 : port.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            PortChangeEvent other = (PortChangeEvent) obj;
            if (port == null) {
                if (other.port != null) return false;
            } else if (!port.equals(other.port)) return false;
            if (type != other.type) return false;
            return true;
        }
        @Override
        public String toString() {
            return "[" + type + " " + port.toBriefString() + "]";
        }
    }
    public enum PortChangeType {
        ADD, OTHER_UPDATE, DELETE, UP, DOWN,
    }
    public void setFloodlightProvider(Controller controller);
    public void setThreadPoolService(IThreadPoolService threadPool);
    public void setDebugCounterService(IDebugCounterService debugCounters)
            throws CounterException;
    public void setChannel(Channel channel);
    public boolean inputThrottled(OFMessage ofm);
    boolean isOverloaded();
    public void writeThrottled(OFMessage msg, FloodlightContext cntx) throws IOException;
    void writeThrottled(List<OFMessage> msglist, FloodlightContext bc)
            throws IOException;
    public void write(OFMessage m, FloodlightContext bc) throws IOException;
    public void write(List<OFMessage> msglist, FloodlightContext bc) throws IOException;
    public void disconnectOutputStream();
    public int getBuffers();
    public int getActions();
    public int getCapabilities();
    public byte getTables();
    public OFDescriptionStatistics getDescriptionStatistics();
    public void setFeaturesReply(OFFeaturesReply featuresReply);
    public Collection<ImmutablePort> getEnabledPorts();
    public Collection<Short> getEnabledPortNumbers();
    public ImmutablePort getPort(short portNumber);
    public ImmutablePort getPort(String portName);
    public OrderedCollection<PortChangeEvent> processOFPortStatus(OFPortStatus ps);
    public Collection<ImmutablePort> getPorts();
    public boolean portEnabled(short portNumber);
    public boolean portEnabled(String portName);
    public OrderedCollection<PortChangeEvent>
            comparePorts(Collection<ImmutablePort> ports);
    public OrderedCollection<PortChangeEvent>
            setPorts(Collection<ImmutablePort> ports);
    public long getId();
    public String getStringId();
    public SocketAddress getInetAddress();
    public Map<Object, Object> getAttributes();
    public Date getConnectedSince();
    public int getNextTransactionId();
    public Future<List<OFStatistics>> queryStatistics(OFStatisticsRequest request)
            throws IOException;
    public Future<OFFeaturesReply> querySwitchFeaturesReply()
            throws IOException;
    void deliverOFFeaturesReply(OFMessage reply);
    public void cancelFeaturesReply(int transactionId);
    public boolean isConnected();
    public boolean isActive();
    public void setConnected(boolean connected);
    public Role getHARole();
    public void setHARole(Role role);
    public void deliverStatisticsReply(OFStatisticsReply reply);
    public void cancelStatisticsReply(int transactionId);
    public void cancelAllStatisticsReplies();
    boolean hasAttribute(String name);
    Object getAttribute(String name);
    boolean attributeEquals(String name, Object other);
    void setAttribute(String name, Object value);
    Object removeAttribute(String name);
    public void clearAllFlowMods();
    public boolean updateBroadcastCache(Long entry, Short port);
    public Map<Short, Long> getPortBroadcastHits();
    public void sendStatsQuery(OFStatisticsRequest request, int xid,
                            IOFMessageListener caller) throws IOException;
    public void flush();
    public void setSwitchProperties(OFDescriptionStatistics description);
    public OFPortType getPortType(short port_num);
    public boolean isFastPort(short port_num);
    public boolean isWriteThrottleEnabled();
    public void setTableFull(boolean isFull);
    public void setAccessFlowPriority(short prio);
    public void setCoreFlowPriority(short prio);
    public short getAccessFlowPriority();
    public short getCoreFlowPriority();
    public void startDriverHandshake();
    public boolean isDriverHandshakeComplete();
    public void processDriverHandshakeMessage(OFMessage m); */
}
