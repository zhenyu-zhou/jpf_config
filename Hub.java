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

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import org.openflow.protocol.*;
import org.openflow.protocol.action.OFAction;
import org.openflow.protocol.action.OFActionOutput;
import org.openflow.util.U16;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.floodlightcontroller.core.internal.OFSwitchImpl;

/**
 *
 * @author David Erickson (daviderickson@cs.stanford.edu) - 04/04/10
 */
public class Hub implements IFloodlightModule, IOFMessageListener {
    // protected static Logger log = LoggerFactory.getLogger(net.floodlightcontroller.hub.Hub.class);
	protected static Logger log = null; //LoggerFactory.getLogger(edu.duke.cs.legosdn.tests.apps.hub.Hub.class);

    protected IFloodlightProviderService floodlightProvider;

    public void setFloodlightProvider(IFloodlightProviderService floodlightProvider) {
        // this.floodlightProvider = floodlightProvider;
    }

    @Override
    public String getName() {
        return net.floodlightcontroller.hub.Hub.class.getPackage().getName();
    }

    public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
	// if(msg.getLength() == 1)
		// System.exit(-1);
		// throw new RuntimeException("xid");
        // OFPacketIn pi = new OFPacketIn(); 
	OFPacketIn pi =  (OFPacketIn) msg;

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
	// if(true) {
            byte[] packetData = pi.getPacketData();
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                               + po.getActionsLength()  +  packetData.length));
            po.setPacketData(packetData);
        }  else {
            po.setLength(U16.t(OFPacketOut.MINIMUM_LENGTH
                               + po.getActionsLength()));
        }
        try {
		// if(sw != null)
            		sw.write(po, cntx);
		// System.out.println("in try");
        } catch (IOException e) {
		// IOException e=  new IOException();
            log.error("Failure writing PacketOut", e);
		System.out.println("in catch");
        }

        return Command.STOP;
    }

    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        return false;
    }

    // @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        return false;
    }

    // IFloodlightModule

    // @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // We don't provide any services, return null
        return null;
    }

    // @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService>
            getServiceImpls() {
        // We don't provide any services, return null
        return null;
    }

    // @Override
    public Collection<Class<? extends IFloodlightService>>
            getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l = 
                new ArrayList<Class<? extends IFloodlightService>>();
        // l.add(IFloodlightProviderService.class);
        return l;
    }

    // @Override
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
 	new Hub().receive(null, new OFMessage(), new FloodlightContext());
	// new Hub().receive(null, null, null);
	// new Hub().receive(new OFSwitchImpl(), new OFMessage(), new FloodlightContext());
	// hehe(4);
    }
}
