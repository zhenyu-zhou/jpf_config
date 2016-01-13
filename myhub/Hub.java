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

package myhub;

/* import net.floodlightcontroller.core.*;
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
import net.floodlightcontroller.core.OFSwitchBase; */

import java.io.*;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

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
import org.openflow.protocol.OFPortStatus.OFPortReason;
import org.openflow.protocol.statistics.OFDescriptionStatistics;
import org.openflow.protocol.statistics.OFStatistics;
import org.openflow.protocol.statistics.OFStatisticsType;
import org.openflow.protocol.statistics.OFTableStatistics;
import org.openflow.util.HexString; */

/**
 *
 * @author David Erickson (daviderickson@cs.stanford.edu) - 04/04/10
 */
public class Hub { // implements IFloodlightModule, IOFMessageListener {

    public static void hehe(int x)
	{
		if(x == 0)
			throw new RuntimeException("x=0!!!");
		System.out.println("x: "+x);
	}

    public static void test(OFSwitchImpl sw)
	//public static void test(IOFSwitch sw)
	{
		if(sw.isFastPort((short)800))
			System.out.println(800);
		else
			System.out.println("not 800");
	}

    public static void main(String[] args)
    {
	System.out.println("zzy: in hub main");
	// hehe(4);
	// test(new OFSwitchImpl());
	test(null);
    }
}

