/**
*    Copyright 2012, Big Switch Networks, Inc.
*    Originally created by David Erickson, Stanford University
*
*    Licensed under the Apache License, Version 2.0 (the "License"); you may
*    not use this file except in compliance with the License. You may obtain
*    a copy of the License at
*
*         http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
*    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
*    License for the specific language governing permissions and limitations
*    under the License.
**/

package myhub;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* import net.floodlightcontroller.core.IFloodlightProviderService.Role;
import net.floodlightcontroller.core.annotations.LogMessageDoc;
import net.floodlightcontroller.core.annotations.LogMessageDocs;
import net.floodlightcontroller.core.internal.Controller;
import net.floodlightcontroller.core.internal.OFFeaturesReplyFuture;
import net.floodlightcontroller.core.internal.OFStatisticsFuture;
import net.floodlightcontroller.core.util.AppCookie;
import net.floodlightcontroller.core.web.serializers.DPIDSerializer;
import net.floodlightcontroller.debugcounter.IDebugCounter;
import net.floodlightcontroller.debugcounter.IDebugCounterService;
import net.floodlightcontroller.debugcounter.IDebugCounterService.CounterException;
import net.floodlightcontroller.debugcounter.IDebugCounterService.CounterType;
import net.floodlightcontroller.debugcounter.NullDebugCounter;
import net.floodlightcontroller.devicemanager.SwitchPort;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.routing.ForwardingBase;
import net.floodlightcontroller.threadpool.IThreadPoolService;
import net.floodlightcontroller.util.LinkedHashSetWrapper;
import net.floodlightcontroller.util.MACAddress;
import net.floodlightcontroller.util.OrderedCollection;
import net.floodlightcontroller.util.TimedCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.jboss.netty.channel.Channel;
import org.openflow.protocol.OFFeaturesReply;
import org.openflow.protocol.OFFlowMod;
import org.openflow.protocol.OFMatch;
import org.openflow.protocol.OFMessage;
import org.openflow.protocol.OFPacketIn;
import org.openflow.protocol.OFPortStatus;
import org.openflow.protocol.OFPortStatus.OFPortReason;
import org.openflow.protocol.OFPort;
import org.openflow.protocol.OFStatisticsReply;
import org.openflow.protocol.OFStatisticsRequest;
import org.openflow.protocol.OFType;
import org.openflow.protocol.statistics.OFDescriptionStatistics;
import org.openflow.protocol.statistics.OFStatistics;
import org.openflow.protocol.statistics.OFStatisticsType;
import org.openflow.protocol.statistics.OFTableStatistics;
import org.openflow.util.HexString;
import org.openflow.util.U16;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

public abstract class OFSwitchBase implements IOFSwitch {
// public class OFSwitchBase implements IOFSwitch {
    // TODO: should we really do logging in the class or should we throw
    // exception that can then be handled by callers?
    /* protected static final Logger log = LoggerFactory.getLogger(OFSwitchBase.class);

    protected ConcurrentMap<Object, Object> attributes;
    protected IFloodlightProviderService floodlightProvider;
    protected IThreadPoolService threadPool;
    protected IDebugCounterService debugCounters;
    // FIXME: Don't use java.util.Date
    protected volatile Date connectedSince;

    protected int capabilities;
    protected int buffers;
    protected int actions;
    protected byte tables;
    protected long datapathId;
    protected String stringId;

    protected short accessFlowPriority;
    protected short coreFlowPriority;

    private boolean startDriverHandshakeCalled = false;
    protected Channel channel;

    private final AtomicInteger transactionIdSource;
    private final Map<Integer,OFStatisticsFuture> statsFutureMap;
    private final Map<Integer, IOFMessageListener> iofMsgListenersMap;
    private final Map<Integer,OFFeaturesReplyFuture> featuresFutureMap;
    private volatile boolean connected;
    private volatile Role role;
    private final TimedCache<Long> timedCache;
    private final ConcurrentMap<Short, AtomicLong> portBroadcastCacheHitMap;

    private final PortManager portManager;

    private boolean writeThrottleEnabled = false;
    protected boolean packetInThrottleEnabled = false;
    private int packetInRateThresholdHigh =
            Integer.parseInt(System.getProperty("input_threshold", "1000"));
    private int packetInRateThresholdLow = 1;
    private int packetInRatePerMacThreshold = 50;
    private int packetInRatePerPortThreshold = 100;
    private long messageCount = 0;
    private long messageCountUniqueOFMatch = 0;
    private long lastMessageTime;
    private int currentRate = 0;
    private TimedCache<OFMatch> ofMatchCache;
    private TimedCache<Long> macCache;
    private TimedCache<Long> macBlockedCache;
    private TimedCache<Short> portCache;
    private TimedCache<Short> portBlockedCache;
    private boolean flowTableFull = false;

    protected OFDescriptionStatistics description;

    private boolean debugCountersRegistered;
    @SuppressWarnings("unused")
    private IDebugCounter ctrSwitch, ctrSwitchPktin, ctrSwitchWrite;
    private IDebugCounter ctrSwitchPktinDrops, ctrSwitchWriteDrops;

    private static final String PACKAGE = OFSwitchBase.class.getPackage().getName();


    protected final static ThreadLocal<Map<IOFSwitch,List<OFMessage>>> local_msg_buffer =
            new ThreadLocal<Map<IOFSwitch,List<OFMessage>>>() {
        @Override
        protected Map<IOFSwitch,List<OFMessage>> initialValue() {
            return new HashMap<IOFSwitch,List<OFMessage>>();
        }
    };

    public static final int OFSWITCH_APP_ID = 5;
    static {
        AppCookie.registerApp(OFSwitchBase.OFSWITCH_APP_ID, "switch");
    }

    public OFSwitchBase() {
        this.stringId = null;
        this.attributes = new ConcurrentHashMap<Object, Object>();
        this.connectedSince = null;
        this.transactionIdSource = new AtomicInteger();
        this.connected = false;
        this.statsFutureMap = new ConcurrentHashMap<Integer,OFStatisticsFuture>();
        this.featuresFutureMap = new ConcurrentHashMap<Integer,OFFeaturesReplyFuture>();
        this.iofMsgListenersMap = new ConcurrentHashMap<Integer,IOFMessageListener>();
        this.role = null;
        this.timedCache = new TimedCache<Long>(100, 5*1000 );  // 5 seconds interval
        this.portBroadcastCacheHitMap = new ConcurrentHashMap<Short, AtomicLong>();
        this.description = new OFDescriptionStatistics();
        this.lastMessageTime = System.currentTimeMillis();

        this.portManager = new PortManager();

        // Defaults properties for an ideal switch
        this.setAttribute(PROP_FASTWILDCARDS, OFMatch.OFPFW_ALL);
        this.setAttribute(PROP_SUPPORTS_OFPP_FLOOD, Boolean.valueOf(true));
        this.setAttribute(PROP_SUPPORTS_OFPP_TABLE, Boolean.valueOf(true));
        if (packetInRateThresholdHigh == 0) {
            packetInRateThresholdHigh = Integer.MAX_VALUE;
        } else {
            packetInRateThresholdLow = packetInRateThresholdHigh / 2;
        }
    }

    protected class PortManager {
        private final ReentrantReadWriteLock lock;
        private List<ImmutablePort> portList;
        private List<ImmutablePort> enabledPortList;
        private List<Short> enabledPortNumbers;
        private Map<Short,ImmutablePort> portsByNumber;
        private Map<String,ImmutablePort> portsByName;

        public PortManager() {
            this.lock = new ReentrantReadWriteLock();
            this.portList = Collections.emptyList();
            this.enabledPortList = Collections.emptyList();
            this.enabledPortNumbers = Collections.emptyList();
            this.portsByName = Collections.emptyMap();
            this.portsByNumber = Collections.emptyMap();
        }

        private void updatePortsWithNewPortsByNumber(
                Map<Short,ImmutablePort> newPortsByNumber) {
            if (!lock.writeLock().isHeldByCurrentThread()) {
                throw new IllegalStateException("Method called without " +
                                                "holding writeLock");
            }
            Map<String,ImmutablePort> newPortsByName =
                    new HashMap<String, ImmutablePort>();
            List<ImmutablePort> newPortList =
                    new ArrayList<ImmutablePort>();
            List<ImmutablePort> newEnabledPortList =
                    new ArrayList<ImmutablePort>();
            List<Short> newEnabledPortNumbers = new ArrayList<Short>();

            for(ImmutablePort p: newPortsByNumber.values()) {
                newPortList.add(p);
                newPortsByName.put(p.getName().toLowerCase(), p);
                if (p.isEnabled()) {
                    newEnabledPortList.add(p);
                    newEnabledPortNumbers.add(p.getPortNumber());
                }
            }
            portsByName = Collections.unmodifiableMap(newPortsByName);
            portsByNumber =
                    Collections.unmodifiableMap(newPortsByNumber);
            enabledPortList =
                    Collections.unmodifiableList(newEnabledPortList);
            enabledPortNumbers =
                    Collections.unmodifiableList(newEnabledPortNumbers);
            portList = Collections.unmodifiableList(newPortList);
        }

        private OrderedCollection<PortChangeEvent>
                handlePortStatusDelete(ImmutablePort delPort) {
            lock.writeLock().lock();
            OrderedCollection<PortChangeEvent> events =
                    new LinkedHashSetWrapper<PortChangeEvent>();
            try {
                Map<Short,ImmutablePort> newPortByNumber =
                        new HashMap<Short, ImmutablePort>(portsByNumber);
                ImmutablePort prevPort =
                        portsByNumber.get(delPort.getPortNumber());
                if (prevPort == null) {
                    // so such port. Do we have a port with the name?
                    prevPort = portsByName.get(delPort.getName());
                    if (prevPort != null) {
                        newPortByNumber.remove(prevPort.getPortNumber());
                        events.add(new PortChangeEvent(prevPort,
                                                       PortChangeType.DELETE));
                    }
                } else if (prevPort.getName().equals(delPort.getName())) {
                    // port exists with consistent name-number mapping
                    newPortByNumber.remove(delPort.getPortNumber());
                    events.add(new PortChangeEvent(delPort,
                                                   PortChangeType.DELETE));
                } else {
                    // port with same number exists but its name differs. This
                    // is weird. The best we can do is to delete the existing
                    // port(s) that have delPort's name and number.
                    newPortByNumber.remove(delPort.getPortNumber());
                    events.add(new PortChangeEvent(prevPort,
                                                   PortChangeType.DELETE));
                    // is there another port that has delPort's name?
                    prevPort = portsByName.get(delPort.getName().toLowerCase());
                    if (prevPort != null) {
                        newPortByNumber.remove(prevPort.getPortNumber());
                        events.add(new PortChangeEvent(prevPort,
                                                       PortChangeType.DELETE));
                    }
                }
                updatePortsWithNewPortsByNumber(newPortByNumber);
                return events;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public OrderedCollection<PortChangeEvent> handlePortStatusMessage(OFPortStatus ps) {
            if (ps == null) {
                throw new NullPointerException("OFPortStatus message must " +
                                               "not be null");
            }
            lock.writeLock().lock();
            try {
                ImmutablePort port =
                        ImmutablePort.fromOFPhysicalPort(ps.getDesc());
                OFPortReason reason = OFPortReason.fromReasonCode(ps.getReason());
                if (reason == null) {
                    throw new IllegalArgumentException("Unknown PortStatus " +
                            "reason code " + ps.getReason());
                }

                if (log.isDebugEnabled()) {
                    log.debug("Handling OFPortStatus: {} for {}",
                              reason, port.toBriefString());
                }

                if (reason == OFPortReason.OFPPR_DELETE)
                        return handlePortStatusDelete(port);

                Map<Short,ImmutablePort> newPortByNumber =
                    new HashMap<Short, ImmutablePort>(portsByNumber);
                OrderedCollection<PortChangeEvent> events = getSinglePortChanges(port);
                for (PortChangeEvent e: events) {
                    switch(e.type) {
                        case DELETE:
                            newPortByNumber.remove(e.port.getPortNumber());
                            break;
                        case ADD:
                            if (reason != OFPortReason.OFPPR_ADD) {
                                // weird case
                            }
                            // fall through
                        case DOWN:
                        case OTHER_UPDATE:
                        case UP:
                            // update or add the port in the map
                            newPortByNumber.put(e.port.getPortNumber(), e.port);
                            break;
                    }
                }
                updatePortsWithNewPortsByNumber(newPortByNumber);
                return events;
            } finally {
                lock.writeLock().unlock();
            }

        }

        public OrderedCollection<PortChangeEvent>
                getSinglePortChanges(ImmutablePort newPort) {
            lock.readLock().lock();
            try {
                OrderedCollection<PortChangeEvent> events =
                        new LinkedHashSetWrapper<PortChangeEvent>();
                // Check if we have a port by the same number in our
                // old map.
                ImmutablePort prevPort =
                        portsByNumber.get(newPort.getPortNumber());
                if (newPort.equals(prevPort)) {
                    // nothing has changed
                    return events;
                }

                if (prevPort != null &&
                        prevPort.getName().equals(newPort.getName())) {
                    if (prevPort.isEnabled() && !newPort.isEnabled()) {
                        events.add(new PortChangeEvent(newPort,
                                                       PortChangeType.DOWN));
                    } else if (!prevPort.isEnabled() && newPort.isEnabled()) {
                        events.add(new PortChangeEvent(newPort,
                                                       PortChangeType.UP));
                    } else {
                        events.add(new PortChangeEvent(newPort,
                                   PortChangeType.OTHER_UPDATE));
                    }
                    return events;
                }

                if (prevPort != null) {
                    events.add(new PortChangeEvent(prevPort,
                                                   PortChangeType.DELETE));
                }

                prevPort = portsByName.get(newPort.getName().toLowerCase());
                if (prevPort != null) {
                    events.add(new PortChangeEvent(prevPort,
                                                   PortChangeType.DELETE));
                }
                events.add(new PortChangeEvent(newPort, PortChangeType.ADD));
                return events;
            } finally {
                lock.readLock().unlock();
            }
        }

        public OrderedCollection<PortChangeEvent>
                comparePorts(Collection<ImmutablePort> newPorts) {
            return compareAndUpdatePorts(newPorts, false);
        }

        public OrderedCollection<PortChangeEvent>
                updatePorts(Collection<ImmutablePort> newPorts) {
            return compareAndUpdatePorts(newPorts, true);
        }

        private OrderedCollection<PortChangeEvent> compareAndUpdatePorts(
                Collection<ImmutablePort> newPorts,
                boolean doUpdate) {
            if (newPorts == null) {
                throw new NullPointerException("newPortsList must not be null");
            }
            lock.writeLock().lock();
            try {
                OrderedCollection<PortChangeEvent> events =
                        new LinkedHashSetWrapper<PortChangeEvent>();

                Map<Short,ImmutablePort> newPortsByNumber =
                        new HashMap<Short, ImmutablePort>();
                Map<String,ImmutablePort> newPortsByName =
                        new HashMap<String, ImmutablePort>();
                List<ImmutablePort> newEnabledPortList =
                        new ArrayList<ImmutablePort>();
                List<Short> newEnabledPortNumbers =
                        new ArrayList<Short>();
                List<ImmutablePort> newPortsList =
                        new ArrayList<ImmutablePort>(newPorts);

                for (ImmutablePort p: newPortsList) {
                    if (p == null) {
                        throw new NullPointerException("portList must not " +
                                "contain null values");
                    }

                    ImmutablePort duplicatePort;
                    duplicatePort = newPortsByNumber.put(p.getPortNumber(), p);
                    if (duplicatePort != null) {
                        String msg = String.format("Cannot have two ports " +
                                "with the same number: %s <-> %s",
                                p.toBriefString(),
                                duplicatePort.toBriefString());
                        throw new IllegalArgumentException(msg);
                    }
                    duplicatePort =
                            newPortsByName.put(p.getName().toLowerCase(), p);
                    if (duplicatePort != null) {
                        String msg = String.format("Cannot have two ports " +
                                "with the same name: %s <-> %s",
                                p.toBriefString(),
                                duplicatePort.toBriefString());
                        throw new IllegalArgumentException(msg);
                    }
                    if (p.isEnabled()) {
                        newEnabledPortList.add(p);
                        newEnabledPortNumbers.add(p.getPortNumber());
                    }

                    // get changes
                    events.addAll(getSinglePortChanges(p));
                }

                for (ImmutablePort oldPort: this.portList) {
                    if (!newPortsByNumber.containsKey(oldPort.getPortNumber())) {
                        PortChangeEvent ev =
                                new PortChangeEvent(oldPort,
                                                    PortChangeType.DELETE);
                        events.add(ev);
                    }
                }

                if (doUpdate) {
                    portsByName = Collections.unmodifiableMap(newPortsByName);
                    portsByNumber =
                            Collections.unmodifiableMap(newPortsByNumber);
                    enabledPortList =
                            Collections.unmodifiableList(newEnabledPortList);
                    enabledPortNumbers =
                            Collections.unmodifiableList(newEnabledPortNumbers);
                    portList = Collections.unmodifiableList(newPortsList);
                }
                return events;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public ImmutablePort getPort(String name) {
            if (name == null) {
                throw new NullPointerException("Port name must not be null");
            }
            lock.readLock().lock();
            try {
                return portsByName.get(name.toLowerCase());
            } finally {
                lock.readLock().unlock();
            }
        }

        public ImmutablePort getPort(Short portNumber) {
            lock.readLock().lock();
            try {
                return portsByNumber.get(portNumber);
            } finally {
                lock.readLock().unlock();
            }
        }

        public List<ImmutablePort> getPorts() {
            lock.readLock().lock();
            try {
                return portList;
            } finally {
                lock.readLock().unlock();
            }
        }

        public List<ImmutablePort> getEnabledPorts() {
            lock.readLock().lock();
            try {
                return enabledPortList;
            } finally {
                lock.readLock().unlock();
            }
        }

        public List<Short> getEnabledPortNumbers() {
            lock.readLock().lock();
            try {
                return enabledPortNumbers;
            } finally {
                lock.readLock().unlock();
            }
        }
    }


    @Override
    public boolean attributeEquals(String name, Object other) {
        Object attr = this.attributes.get(name);
        if (attr == null)
            return false;
        return attr.equals(other);
    }


    @Override
    public Object getAttribute(String name) {
        // returns null if key doesn't exist
        return this.attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
        return;
    }

    @Override
    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    @Override
    @JsonIgnore
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    // For driver subclass to set throttling
    protected void enableWriteThrottle(boolean enable) {
        this.writeThrottleEnabled = enable;
    }

    @Override
    public boolean isWriteThrottleEnabled() {
        return this.writeThrottleEnabled;
    }

    @Override
    @LogMessageDocs({
        @LogMessageDoc(level="WARN",
                message="Drop throttled OF message to switch {switch}",
                explanation="The controller is sending more messages" +
                "than the switch can handle. Some messages are dropped" +
                "to prevent switch outage",
                recommendation=LogMessageDoc.REPORT_CONTROLLER_BUG)
    })
    public void writeThrottled(OFMessage m, FloodlightContext bc)
            throws IOException {
        if (channel == null || !isConnected())
            return;
        if (!writeThrottleEnabled || channel.isWritable()) {
            write(m, bc);
        } else {
            // Let logback duplicate filtering take care of excessive logs
            ctrSwitchWriteDrops.updateCounterNoFlush();
            log.warn("Drop throttled OF message to switch {}", this);
        }
    }

    @Override
    public void writeThrottled(List<OFMessage> msglist, FloodlightContext bc)
            throws IOException {
        if (!writeThrottleEnabled || channel.isWritable()) {
            write(msglist, bc);
        } else {
            // Let logback duplicate filtering take care of excessive logs
            ctrSwitchWriteDrops.updateCounterNoFlush(msglist.size());
            log.warn("Drop throttled OF messages to switch {}", this);
        }
    }

    @Override
    public void write(OFMessage m, FloodlightContext bc) {
        if (channel == null || !isConnected())
            return;
            //throws IOException {
        Map<IOFSwitch,List<OFMessage>> msg_buffer_map = local_msg_buffer.get();
        List<OFMessage> msg_buffer = msg_buffer_map.get(this);
        if (msg_buffer == null) {
            msg_buffer = new ArrayList<OFMessage>();
            msg_buffer_map.put(this, msg_buffer);
        }

        this.floodlightProvider.handleOutgoingMessage(this, m, bc);
        msg_buffer.add(m);

        if ((msg_buffer.size() >= Controller.BATCH_MAX_SIZE) ||
            ((m.getType() != OFType.PACKET_OUT) && (m.getType() != OFType.FLOW_MOD))) {
            this.write(msg_buffer);
            msg_buffer.clear();
        }
    }
    @Override
    @LogMessageDoc(level="WARN",
                   message="Sending OF message that modifies switch " +
                           "state while in the slave role: {switch}",
                   explanation="An application has sent a message to a switch " +
                           "that is not valid when the switch is in a slave role",
                   recommendation=LogMessageDoc.REPORT_CONTROLLER_BUG)
    public void write(List<OFMessage> msglist,
                      FloodlightContext bc) {
        if (channel == null || !isConnected())
            return;
        for (OFMessage m : msglist) {
            if (role == Role.SLAVE) {
                switch (m.getType()) {
                    case PACKET_OUT:
                    case FLOW_MOD:
                    case PORT_MOD:
                        log.warn("Sending OF message that modifies switch " +
                                 "state while in the slave role: {}",
                                 m.getType().name());
                        break;
                    default:
                        break;
                }
            }
            this.floodlightProvider.handleOutgoingMessage(this, m, bc);
        }
        this.write(msglist);
    }

    protected void write(List<OFMessage> msglist) {
        if (channel == null || !isConnected())
            return;
        this.channel.write(msglist);
    }

    @Override
    public void disconnectOutputStream() {
        if (channel == null)
            return;
        channel.close();
    }

    @Override
    @JsonIgnore
    public void setFeaturesReply(OFFeaturesReply featuresReply) {
        if (stringId == null) {
            List<ImmutablePort> immutablePorts = ImmutablePort
                    .immutablePortListOf(featuresReply.getPorts());
            portManager.updatePorts(immutablePorts);
        }
        this.datapathId = featuresReply.getDatapathId();
        this.stringId = HexString.toHexString(featuresReply.getDatapathId());
        this.capabilities = featuresReply.getCapabilities();
        this.buffers = featuresReply.getBuffers();
        this.actions = featuresReply.getActions();
        this.tables = featuresReply.getTables();
}

    @Override
    @JsonIgnore
    public Collection<ImmutablePort> getEnabledPorts() {
        return portManager.getEnabledPorts();
    }

    @Override
    @JsonIgnore
    public Collection<Short> getEnabledPortNumbers() {
        return portManager.getEnabledPortNumbers();
    }

    @Override
    public ImmutablePort getPort(short portNumber) {
        return portManager.getPort(portNumber);
    }

    @Override
    public ImmutablePort getPort(String portName) {
        return portManager.getPort(portName);
    }

    @Override
    @JsonIgnore
    public OrderedCollection<PortChangeEvent>
            processOFPortStatus(OFPortStatus ps) {
        return portManager.handlePortStatusMessage(ps);
    }

    @Override
    @JsonProperty("ports")
    public Collection<ImmutablePort> getPorts() {
        return portManager.getPorts();
    }

    @Override
    public OrderedCollection<PortChangeEvent>
            comparePorts(Collection<ImmutablePort> ports) {
        return portManager.comparePorts(ports);
    }

    @Override
    @JsonIgnore
    public OrderedCollection<PortChangeEvent>
            setPorts(Collection<ImmutablePort> ports) {
        return portManager.updatePorts(ports);
    }

    @Override
    public boolean portEnabled(short portNumber) {
        ImmutablePort p = portManager.getPort(portNumber);
        if (p == null) return false;
        return p.isEnabled();
    }

    @Override
    public boolean portEnabled(String portName) {
        ImmutablePort p = portManager.getPort(portName);
        if (p == null) return false;
        return p.isEnabled();
    }

    @Override
    @JsonSerialize(using=DPIDSerializer.class)
    @JsonProperty("dpid")
    public long getId() {
        if (this.stringId == null)
            throw new RuntimeException("Features reply has not yet been set");
        return this.datapathId;
    }

    @JsonIgnore
    @Override
    public String getStringId() {
        return stringId;
    }

    @Override
    public String toString() {
        String channelString =
                (channel != null) ? channel.getRemoteAddress().toString() :
                                    "?";
        return "OFSwitchBase [" + channelString + " DPID[" + ((stringId != null) ? stringId : "?") + "]]";
    }

    @Override
    public ConcurrentMap<Object, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Date getConnectedSince() {
        return connectedSince;
    }

    @JsonIgnore
    @Override
    public int getNextTransactionId() {
        return this.transactionIdSource.incrementAndGet();
    }

    @Override
    public void sendStatsQuery(OFStatisticsRequest request, int xid,
                                IOFMessageListener caller) throws IOException {
        request.setXid(xid);
        this.iofMsgListenersMap.put(xid, caller);
        List<OFMessage> msglist = new ArrayList<OFMessage>(1);
        msglist.add(request);
        this.write(msglist);
        return;
    }

    @Override
    public Future<List<OFStatistics>> queryStatistics(OFStatisticsRequest request) throws IOException {
        request.setXid(getNextTransactionId());
        OFStatisticsFuture future = new OFStatisticsFuture(threadPool, this, request.getXid());
        this.statsFutureMap.put(request.getXid(), future);
        List<OFMessage> msglist = new ArrayList<OFMessage>(1);
        msglist.add(request);
        this.write(msglist);
        return future;
    }

    @Override
    public void deliverStatisticsReply(OFStatisticsReply reply) {
        checkForTableStats(reply);
        OFStatisticsFuture future = this.statsFutureMap.get(reply.getXid());
        if (future != null) {
            future.deliverFuture(this, reply);
            // The future will ultimately unregister itself and call
            // cancelStatisticsReply
            return;
        }
        IOFMessageListener caller = this.iofMsgListenersMap.get(reply.getXid());
        if (caller != null) {
            caller.receive(this, reply, null);
        }
    }

    @LogMessageDocs({
        @LogMessageDoc(level="INFO",
            message="Switch {switch} flow table is full",
            explanation="The switch flow table at least 98% full, " +
                    "this requires attention if using reactive flow setup"),
        @LogMessageDoc(level="INFO",
            message="Switch {switch} flow table capacity back to normal",
            explanation="The switch flow table is less than 90% full")
    })
    private void checkForTableStats(OFStatisticsReply statReply) {
        if (statReply.getStatisticType() != OFStatisticsType.TABLE) {
            return;
        }
        List<? extends OFStatistics> stats = statReply.getStatistics();
        // Assume a single table only
        OFStatistics stat = stats.get(0);
        if (stat instanceof OFTableStatistics) {
            OFTableStatistics tableStat = (OFTableStatistics) stat;
            int activeCount = tableStat.getActiveCount();
            int maxEntry = tableStat.getMaximumEntries();
            log.debug("Switch {} active entries {} max entries {}",
                    new Object[] { this.stringId, activeCount, maxEntry});
            int percentFull = activeCount * 100 / maxEntry;
            if (flowTableFull && percentFull < 90) {
                log.info("Switch {} flow table capacity is back to normal",
                        toString());
                floodlightProvider.addSwitchEvent(this.datapathId,
                        "SWITCH_FLOW_TABLE_NORMAL < 90% full", false);
            } else if (percentFull >= 98) {
                log.info("Switch {} flow table is almost full", toString());
                floodlightProvider.addSwitchEvent(this.datapathId,
                        "SWITCH_FLOW_TABLE_ALMOST_FULL >= 98% full", false);
            }
        }
    }


    @Override
    public void cancelStatisticsReply(int transactionId) {
        if (null ==  this.statsFutureMap.remove(transactionId)) {
            this.iofMsgListenersMap.remove(transactionId);
        }
    }

    @Override
    public void cancelAllStatisticsReplies() {
        for (OFStatisticsFuture f : statsFutureMap.values()) {
            f.cancel(true);
        }
        statsFutureMap.clear();
        iofMsgListenersMap.clear();
    }

    @JsonIgnore
    public void setFloodlightProvider(
            IFloodlightProviderService floodlightProvider) {
        this.floodlightProvider = floodlightProvider;
    }

    @Override
    @JsonIgnore
    public void setThreadPoolService(IThreadPoolService tp) {
        this.threadPool = tp;
    }

    @Override
    @JsonIgnore
    public void setDebugCounterService(IDebugCounterService debugCounters)
            throws CounterException {
        this.debugCounters = debugCounters;
        registerOverloadCounters();
    }

    @JsonIgnore
    @Override
    public boolean isConnected() {
        // no lock needed since we use volatile
        return connected;
    }

    @JsonIgnore
    @Override
    public boolean isActive() {
        // no lock needed since we use volatile
        return isConnected() && this.role == Role.MASTER;
    }

    @Override
    @JsonIgnore
    public void setConnected(boolean connected) {
        // No lock needed since we use volatile
        if (connected && this.connectedSince == null)
            this.connectedSince = new Date();
        else if (!connected)
            this.connectedSince = null;
        this.connected = connected;
    }

    @Override
    public Role getHARole() {
        return role;
    }

    @JsonIgnore
    @Override
    public void setHARole(Role role) {
        this.role = role;
    }

    @LogMessageDoc(level="INFO",
            message="Switch {switch} flow cleared",
            explanation="The switch flow table has been cleared, " +
                    "this normally happens on switch connection")
    @Override
    public void clearAllFlowMods() {
        if (channel == null || !isConnected())
            return;
        // Delete all pre-existing flows
        log.info("Clearing all flows on switch {}", this);
        OFMatch match = new OFMatch().setWildcards(OFMatch.OFPFW_ALL);
        OFMessage fm = ((OFFlowMod) floodlightProvider.getOFMessageFactory()
            .getMessage(OFType.FLOW_MOD))
                .setMatch(match)
            .setCommand(OFFlowMod.OFPFC_DELETE)
            .setOutPort(OFPort.OFPP_NONE)
            .setLength(U16.t(OFFlowMod.MINIMUM_LENGTH));
        fm.setXid(getNextTransactionId());
        OFMessage barrierMsg = floodlightProvider.getOFMessageFactory().getMessage(
                OFType.BARRIER_REQUEST);
        barrierMsg.setXid(getNextTransactionId());
        List<OFMessage> msglist = new ArrayList<OFMessage>(2);
        msglist.add(fm);
        msglist.add(barrierMsg);
        channel.write(msglist);
    }

    @Override
    public boolean updateBroadcastCache(Long entry, Short port) {
        if (timedCache.update(entry)) {
            AtomicLong count = portBroadcastCacheHitMap.get(port);
            if(count == null) {
                AtomicLong newCount = new AtomicLong(0);
                AtomicLong retrieved;
                if((retrieved = portBroadcastCacheHitMap.putIfAbsent(port, newCount)) == null ) {
                    count = newCount;
                } else {
                    count = retrieved;
                }
            }
            count.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    @Override
    @JsonIgnore
    public Map<Short, Long> getPortBroadcastHits() {
        Map<Short, Long> res = new HashMap<Short, Long>();
        for (Map.Entry<Short, AtomicLong> entry : portBroadcastCacheHitMap.entrySet()) {
            res.put(entry.getKey(), entry.getValue().get());
        }
        return res;
    }

    @Override
    public void flush() {
        Map<IOFSwitch,List<OFMessage>> msg_buffer_map = local_msg_buffer.get();
        List<OFMessage> msglist = msg_buffer_map.get(this);
        if ((msglist != null) && (msglist.size() > 0)) {
            this.write(msglist);
            msglist.clear();
        }
    }

    public static void flush_all() {
        Map<IOFSwitch,List<OFMessage>> msg_buffer_map = local_msg_buffer.get();
        for (IOFSwitch sw : msg_buffer_map.keySet()) {
            sw.flush();
        }
    }

    @Override
    @JsonSerialize(using=ToStringSerializer.class)
    public SocketAddress getInetAddress() {
        if (channel == null)
            return null;
        return channel.getRemoteAddress();
    }

    @Override
    public Future<OFFeaturesReply> querySwitchFeaturesReply()
            throws IOException {
        OFMessage request =
                floodlightProvider.getOFMessageFactory().
                    getMessage(OFType.FEATURES_REQUEST);
        request.setXid(getNextTransactionId());
        OFFeaturesReplyFuture future =
                new OFFeaturesReplyFuture(threadPool, this, request.getXid());
        this.featuresFutureMap.put(request.getXid(), future);
        List<OFMessage> msglist = new ArrayList<OFMessage>(1);
        msglist.add(request);
        this.write(msglist);
        return future;
    }

    @Override
    public void deliverOFFeaturesReply(OFMessage reply) {
        OFFeaturesReplyFuture future = this.featuresFutureMap.get(reply.getXid());
        if (future != null) {
            future.deliverFuture(this, reply);
            // The future will ultimately unregister itself and call
            // cancelFeaturesReply
            return;
        }
        log.error("Switch {}: received unexpected featureReply", this);
    }

    @Override
    public void cancelFeaturesReply(int transactionId) {
        this.featuresFutureMap.remove(transactionId);
    }


    @Override
    public int getBuffers() {
        return buffers;
    }


    @Override
    public int getActions() {
        return actions;
    }


    @Override
    public int getCapabilities() {
        return capabilities;
    }


    @Override
    public byte getTables() {
        return tables;
    }

    @Override
    public OFDescriptionStatistics getDescriptionStatistics() {
        return new OFDescriptionStatistics(description);
    }


    @Override
    public void setFloodlightProvider(Controller controller) {
        floodlightProvider = controller;
    }

    @JsonIgnore
    protected void setInputThrottleThresholds(int pktInHigh, int pktInLow,
            int pktInPerMac, int pktInPerPort) {
        packetInRateThresholdHigh = pktInHigh;
        packetInRateThresholdLow = pktInLow;
        packetInRatePerMacThreshold = pktInPerMac;
        packetInRatePerPortThreshold = pktInPerPort;
    }

    @Override
    public boolean isOverloaded() {
        return packetInThrottleEnabled;
    }

    @Override
    public boolean inputThrottled(OFMessage ofm) {
        if (ofm.getType() != OFType.PACKET_IN) {
            return false;
        }
        ctrSwitchPktin.updateCounterNoFlush();
        // Compute current packet in rate
        messageCount++;
        if (messageCount % 1000 == 0) {
            long now = System.currentTimeMillis();
            if (now != lastMessageTime) {
                currentRate = (int) (1000000 / (now - lastMessageTime));
                lastMessageTime = now;
            } else {
                currentRate = Integer.MAX_VALUE;
            }
        }
        if (!packetInThrottleEnabled) {
            if (currentRate <= packetInRateThresholdHigh) {
                return false; // most common case
            }
            enablePacketInThrottle();
        } else if (currentRate < packetInRateThresholdLow) {
            disablePacketInThrottle();
            return false;
        }

        // Now we are in the slow path where we need to do filtering
        // First filter based on OFMatch
        OFPacketIn pin = (OFPacketIn)ofm;
        OFMatch match = new OFMatch();
        match.loadFromPacket(pin.getPacketData(), pin.getInPort());
        if (ofMatchCache.update(match)) {
           ctrSwitchPktinDrops.updateCounterNoFlush();
            return true;
        }

        // We have packet in with a distinct flow, check per mac rate
        messageCountUniqueOFMatch++;
        if ((messageCountUniqueOFMatch % packetInRatePerMacThreshold) == 1) {
            checkPerSourceMacRate(pin);
        }

        // Check per port rate
        if ((messageCountUniqueOFMatch % packetInRatePerPortThreshold) == 1) {
            checkPerPortRate(pin);
        }
        return false;
    }

    private void disablePacketInThrottle() {
        ofMatchCache = null;
        macCache = null;
        macBlockedCache = null;
        portCache = null;
        portBlockedCache = null;
        packetInThrottleEnabled = false;
        floodlightProvider.addSwitchEvent(this.datapathId,
                "SWITCH_OVERLOAD_THROTTLE_DISABLED ==>" +
                "Pktin rate " + currentRate + "/s", false);
        log.info("Packet in rate is {}, disable throttling on {}",
                currentRate, this);
    }

    private void enablePacketInThrottle() {
        ofMatchCache = new TimedCache<OFMatch>(2048, 5000); // 5 second interval
        macCache = new TimedCache<Long>(64, 1000 );  // remember last second
        macBlockedCache = new TimedCache<Long>(256, 5000 );  // 5 second interval
        portCache = new TimedCache<Short>(16, 1000 );  // rememeber last second
        portBlockedCache = new TimedCache<Short>(64, 5000 );  // 5 second interval
        packetInThrottleEnabled = true;
        messageCountUniqueOFMatch = 0;
        floodlightProvider.addSwitchEvent(this.datapathId,
                "SWITCH_OVERLOAD_THROTTLE_ENABLED ==>" +
                "Pktin rate " + currentRate + "/s", false);
        log.info("Packet in rate is {}, enable throttling on {}",
                currentRate, this);
    }

    private void registerOverloadCounters() throws CounterException {
        if (debugCountersRegistered) {
            return;
        }
        if (debugCounters == null) {
            log.error("Debug Counter Service not found");
            debugCounters = new NullDebugCounter();
            debugCountersRegistered = true;
        }
        // every level of the hierarchical counter has to be registered
        // even if they are not used
        ctrSwitch = debugCounters.registerCounter(
                                   PACKAGE , stringId,
                                   "Counter for this switch",
                                   CounterType.ALWAYS_COUNT);
        ctrSwitchPktin = debugCounters.registerCounter(
                                   PACKAGE, stringId + "/pktin",
                                   "Packet in counter for this switch",
                                   CounterType.ALWAYS_COUNT);
        ctrSwitchWrite = debugCounters.registerCounter(
                                   PACKAGE, stringId + "/write",
                                   "Write counter for this switch",
                                   CounterType.ALWAYS_COUNT);
        ctrSwitchPktinDrops = debugCounters.registerCounter(
                                   PACKAGE, stringId + "/pktin/drops",
                                   "Packet in throttle drop count",
                                   CounterType.ALWAYS_COUNT,
                                   IDebugCounterService.CTR_MDATA_WARN);
        ctrSwitchWriteDrops = debugCounters.registerCounter(
                                   PACKAGE, stringId + "/write/drops",
                                   "Switch write throttle drop count",
                                   CounterType.ALWAYS_COUNT,
                                   IDebugCounterService.CTR_MDATA_WARN);
    }

    private void checkPerSourceMacRate(OFPacketIn pin) {
        byte[] data = pin.getPacketData();
        byte[] mac = Arrays.copyOfRange(data, 6, 12);
        MACAddress srcMac = MACAddress.valueOf(mac);
        short ethType = (short) (((data[12] & 0xff) << 8) + (data[13] & 0xff));
        if (ethType != Ethernet.TYPE_LLDP && ethType != Ethernet.TYPE_BSN &&
                macCache.update(srcMac.toLong())) {
            // Check if we already pushed a flow in the last 5 seconds
            if (macBlockedCache.update(srcMac.toLong())) {
                return;
            }
            // write out drop flow per srcMac
            int port = pin.getInPort();
            SwitchPort swPort = new SwitchPort(getId(), port);
            ForwardingBase.blockHost(floodlightProvider,
                    swPort, srcMac.toLong(), (short) 5,
                    AppCookie.makeCookie(OFSWITCH_APP_ID, 0));
            floodlightProvider.addSwitchEvent(this.datapathId,
                    "SWITCH_PORT_BLOCKED_TEMPORARILY " +
                    "OFPort " + port + " mac " + srcMac, false);
            log.info("Excessive packet in from {} on {}, block host for 5 sec",
                    srcMac.toString(), swPort);
        }
    }

    private void checkPerPortRate(OFPacketIn pin) {
        Short port = pin.getInPort();
        if (portCache.update(port)) {
            // Check if we already pushed a flow in the last 5 seconds
            if (portBlockedCache.update(port)) {
                return;
            }
            // write out drop flow per port
            SwitchPort swPort = new SwitchPort(getId(), port);
            ForwardingBase.blockHost(floodlightProvider,
                    swPort, -1L, (short) 5,
                    AppCookie.makeCookie(OFSWITCH_APP_ID, 1));
            floodlightProvider.addSwitchEvent(this.datapathId,
                    "SWITCH_PORT_BLOCKED_TEMPORARILY " +
                    "OFPort " + port, false);
            log.info("Excessive packet in from {}, block port for 5 sec",
                    swPort);
        }
    }

    @Override
    @JsonIgnore
    @LogMessageDoc(level="WARN",
        message="Switch {switch} flow table is full",
        explanation="The controller received flow table full " +
                "message from the switch, could be caused by increased " +
                "traffic pattern",
                recommendation=LogMessageDoc.REPORT_CONTROLLER_BUG)
    public void setTableFull(boolean isFull) {
        if (isFull && !flowTableFull) {
            floodlightProvider.addSwitchEvent(this.datapathId,
                    "SWITCH_FLOW_TABLE_FULL " +
                    "Table full error from switch", false);
            log.warn("Switch {} flow table is full", stringId);
        }
        flowTableFull = isFull;
    }


    @Override
    public short getAccessFlowPriority() {
        return accessFlowPriority;
    }


    @Override
    public short getCoreFlowPriority() {
        return coreFlowPriority;
    }


    @Override
    public void setAccessFlowPriority(short accessFlowPriority) {
        this.accessFlowPriority = accessFlowPriority;
    }


    @Override
    public void setCoreFlowPriority(short coreFlowPriority) {
        this.coreFlowPriority = coreFlowPriority;
    }

    @Override
    public void startDriverHandshake() {
        if (startDriverHandshakeCalled)
            throw new SwitchDriverSubHandshakeAlreadyStarted();
        startDriverHandshakeCalled = true;
    }

    @Override
    public boolean isDriverHandshakeComplete() {
        if (!startDriverHandshakeCalled)
            throw new SwitchDriverSubHandshakeNotStarted();
        return true;
    }

    @Override
    public void processDriverHandshakeMessage(OFMessage m) {
        if (startDriverHandshakeCalled)
            throw new SwitchDriverSubHandshakeCompleted(m);
        else
            throw new SwitchDriverSubHandshakeNotStarted();
    }

    // zzy
    @Override
    @JsonIgnore
    public void setSwitchProperties(OFDescriptionStatistics description) {
        this.description = new OFDescriptionStatistics(description);
    }

    @Override
    public OFPortType getPortType(short port_num) {
        return OFPortType.NORMAL;
    }*/

    // @Override
    // @JsonIgnore
    /* public boolean isFastPort(short port_num) {
	// OFSwitchBase sw = new OFSwitchBasei();
        return false;
    } */
}
