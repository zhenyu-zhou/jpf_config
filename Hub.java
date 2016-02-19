/**
*    Copyright 2011, Big Switch Networks, Inc. 
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

package edu.duke.cs.legosdn.tests.apps.hub;

import net.floodlightcontroller.core.*;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import org.openflow.protocol.*;
import org.openflow.protocol.action.*;
import org.openflow.util.U16;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.internal.OFSwitchImpl;
import java.io.*;
// import net.floodlightcontroller.core.OFSwitchBase;
import edu.duke.cs.legosdn.tests.apps.zzy.OFSwitchBase_zzy;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

import net.floodlightcontroller.core.IFloodlightProviderService.Role;
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
import org.openflow.protocol.OFPortStatus.OFPortReason;
import org.openflow.protocol.statistics.OFDescriptionStatistics;
import org.openflow.protocol.statistics.OFStatistics;
import org.openflow.protocol.statistics.OFStatisticsType;
import org.openflow.protocol.statistics.OFTableStatistics;
import org.openflow.util.HexString;

/**
 *
 * @author David Erickson (daviderickson@cs.stanford.edu) - 04/04/10
 */
public class Hub implements IFloodlightModule, IOFMessageListener {
    // protected static Logger log = LoggerFactory.getLogger(net.floodlightcontroller.hub.Hub.class);
	protected static Logger log = null; // LoggerFactory.getLogger(edu.duke.cs.legosdn.tests.apps.hub.Hub.class);

    protected IFloodlightProviderService floodlightProvider;

    public void setFloodlightProvider(IFloodlightProviderService floodlightProvider) {
        // this.floodlightProvider = floodlightProvider;
    }

    // @Override
    public String getName() {
        return net.floodlightcontroller.hub.Hub.class.getPackage().getName();
    }

    public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
	if(msg.getLength() == 998)
		System.out.println("msg 1");
		// System.exit(-1);
		// throw new RuntimeException("msg");
	else if(msg.getLength() == 2)
		// throw new RuntimeException("msg");
		System.out.println("msg 2");
	else
		System.out.println("other msg");
        /* OFPacketIn pi = new OFPacketIn(); 
 	//OFPacketIn pi =  (OFPacketIn) msg;

        OFPacketOut po = new OFPacketOut();
        po.setBufferId(pi.getBufferId())
          .setInPort(pi.getInPort());

	// throw new RuntimeException("zzy");

        // set actions
        OFActionOutput action = new OFActionOutput()
                .setPort(OFPort.OFPP_FLOOD.getValue());
        po.setActions(Collections.singletonList((OFAction) action));
        po.setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);

        // set data if is is included in the packetin
        if (pi.getBufferId() == OFPacketOut.BUFFER_ID_NONE) {
	    System.out.println("in if");
            byte[] packetData = pi.getPacketData();
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                               + po.getActionsLength()  +  packetData.length));
            po.setPacketData(packetData);
        }  else {
		System.out.println("in elseeeeee");
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                               + po.getActionsLength()));
        }*/
         try {
		OFPacketOut po = new OFPacketOut();
		// if(sw != null)
            		// sw.write(po, cntx);
		File outFile = new File("");
		OutputStream os = new FileOutputStream(outFile);
		os.write("hehe".getBytes());
		os.close();
		System.out.println("in try");
		throw new RuntimeException("zzy");
        } catch (IOException e) {
		// IOException e=  new IOException();
            // log.error("Failure writing PacketOut");
		System.out.println("in catch");
        }

	// throw new RuntimeException("zzy");
        return Command.STOP;
    }

	// public Command receive_zzy(Integer sw, OFPacketIn pi, FloodlightContext cntx) {
    public Command receive_zzy(OFSwitchBase_zzy sw, OFPacketIn pi, FloodlightContext cntx) {
    // public Command receive_zzy(IOFSwitch sw, OFPacketIn pi, FloodlightContext cntx) {
	/* if(sw.isFastPort((short)1234))
	{System.out.println("fast");}
	else
	{System.out.println("slow");} */

	/* if (sw.getId() == 5)
		System.out.println("sw 5");
	else
		System.out.println("other sw");*/

        OFPacketOut po = new OFPacketOut();
        po.setBufferId(pi.getBufferId())
          .setInPort(pi.getInPort());

        // set actions
        OFActionOutput action = new OFActionOutput()
                .setPort(OFPort.OFPP_FLOOD.getValue());
        po.setActions(Collections.singletonList((OFAction) action));
        po.setActionsLength((short) OFActionOutput.MINIMUM_LENGTH);

        // set data if is is included in the packetin
        if (pi.getBufferId() == OFPacketOut.BUFFER_ID_NONE) {
	    System.out.println("in if");
            byte[] packetData = pi.getPacketData();
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                              + po.getActionsLength()  +  packetData.length));
		// po.setLength(U16.t(po.getActionsLength()));
            po.setPacketData(packetData);
        }  else {
		System.out.println("in else");
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                               + po.getActionsLength()));
        }

        try {
		// if(sw != null)
            		sw.write(po, cntx);
        } catch (IOException e) {
		// IOException e=  new IOException();
            // log.error("Failure writing PacketOut");
		System.out.println("in catch");
        }

       return Command.STOP;
    }

    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        return false;
    }

    // // @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        return false;
    }

    // IFloodlightModule

    // // @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // We don't provide any services, return null
        return null;
    }

    // // @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService>
            getServiceImpls() {
        // We don't provide any services, return null
        return null;
    }

    // // @Override
    public Collection<Class<? extends IFloodlightService>>
            getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l = 
                new ArrayList<Class<? extends IFloodlightService>>();
        // l.add(IFloodlightProviderService.class);
        return l;
    }

    // // @Override
    public void init(FloodlightModuleContext context)
            throws FloodlightModuleException {

    }

    public void startUp(FloodlightModuleContext context) {
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
    }

    public static void hehe(int x)
	{
		if(x == 0)
			throw new RuntimeException("x=0!!!");
	}

    public static void main(String[] args)
    {
	System.out.println("zzy: in hub main");
	// IOFSwitch sw = new OFSwitchImpl();
	// net.floodlightcontroller.core.internal.OFSwitchImpl sw = new net.floodlightcontroller.core.internal.OFSwitchImpl();
	// new Hub().receive(sw, new OFMessage(), new FloodlightContext());
 	// new Hub().receive(null, new OFMessage(), new FloodlightContext());
	// new Hub().receive(null, null, null);
	// new Hub().receive(new OFSwitchImpl(), new OFMessage(), new FloodlightContext());
	// hehe(4);

	// new Hub().receive_zzy(new OFSwitchImpl(), new OFPacketIn(), new FloodlightContext());
	// new Hub().receive_zzy(null, new OFPacketIn(), new FloodlightContext());
	new Hub().receive_zzy(new OFSwitchBase_zzy(), new OFPacketIn(), new FloodlightContext());
    }
}

