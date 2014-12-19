/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xcom_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.joda.time.DateTime;
import xcom_server.generated.Packet;

/**
 *
 * @author root
 */
public class PacketVerificationService 
{
    //Should be used for supporting IP Clients
    private HashMap<String,CopyOnWriteArrayList<Packet>> inbound_packets;
    private HashMap<String,CopyOnWriteArrayList<Packet>> outbound_packets;
    private ReentrantReadWriteLock lockInbound;
    private ReentrantReadWriteLock lockOutbound;
    private List<Packet> lost_packets_outbound;
    private List<Packet> lost_packets_inbound;
    public boolean startVerifying = false;
    public List<String> allowedConnections;
    private HashMap<String,Boolean> synchronizedPackets;
    
    public PacketVerificationService()
    {
        lockInbound = new ReentrantReadWriteLock();
        lockOutbound = new ReentrantReadWriteLock();
        lost_packets_outbound = new ArrayList<Packet>();
        lost_packets_inbound = new ArrayList<Packet>();
        inbound_packets =  new HashMap<String,CopyOnWriteArrayList<Packet>>();
        outbound_packets = new HashMap<String,CopyOnWriteArrayList<Packet>>();
        allowedConnections = new ArrayList<String>();
        synchronizedPackets = new HashMap<String,Boolean>();
    }

    private void StartVerifyingThread(final String srcIP) 
    {
        System.out.println("Starting Verifying thread. IP: "+srcIP);
        new Thread(new Runnable(){

            @Override
            public void run() 
            {
                Packet foundInboundPacket;
                synchronizedPackets.put(srcIP, false);
                
                while(true)
                {
                    try
                    {
                    foundInboundPacket = null;
                    if(outbound_packets.get(srcIP).size() != 0)
                    {
                        Packet p = outbound_packets.get(srcIP).get(0);
                        //for(Packet inP : inbound_packets.get(srcIP).toArray(new Packet[inbound_packets.get(srcIP).size()]))
                        CopyOnWriteArrayList<Packet> temp = inbound_packets.get(srcIP);
                        if(temp == null)
                        {
                            //System.out.println("SrcIP not found!: "+srcIP);
                            continue;
                        }
                        Object[] objs = (Object[])temp.toArray();
                        for(Object obj : objs)
                        {
                            Packet inP = (Packet)obj;
                            if(p.hash.equals(inP.hash))
                            {
                                foundInboundPacket = inP;
                                break;
                            }
                        }
                        if(foundInboundPacket == null)
                        {
                            if(DateTime.parse(p.timespan).isBefore(DateTime.now().minusSeconds(30)))
                            {
                                if(synchronizedPackets.get(srcIP))
                                {
                                    System.out.println("Lost Packet Outbound: "+ p.hash);
                                    lost_packets_outbound.add(p);
                                    AddRowSafeThread(p,false);
                                    outbound_packets.get(srcIP).remove(p);
                                }
                                else
                                {
                                    outbound_packets.get(srcIP).remove(p);
                                }
                            }
                        }
                        else
                        {
                            synchronizedPackets.put(srcIP, true);
                            //System.out.println("Found Packet Outbound: "+ p.hash);
                            inbound_packets.get(srcIP).remove(foundInboundPacket);
                            outbound_packets.get(srcIP).remove(p);
                        }
                    }
                    else
                    {
                           try {
                                Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PacketVerificationService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    }
                    catch(Exception ex)
                    {
                        System.out.println("SrcIP: "+ srcIP);
                        throw ex;
                    }
                }
            }
        }).start();
    }
    
    private void AddRowSafeThread(final Packet p,final boolean inbound) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                //Should be !inbound considering that each missed outbound packet should be added outbound list 
                if(!inbound)
                    XCOM_server.dtm_inbound_error.addRow(Utils.CreateVectorForRow(p));
                else
                    XCOM_server.dtm_outbound_error.addRow(Utils.CreateVectorForRow(p));
            }      
        });
    }
    
    private void StartLostPacketThread(final String srcIP)
    {
        new Thread(new Runnable(){

            @Override
            public void run() 
            {
                while(true)
                {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PacketVerificationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(!synchronizedPackets.get(srcIP))
                        continue;
                    DateTime nowDT = DateTime.now();
                    CopyOnWriteArrayList<Packet> temp = inbound_packets.get(srcIP);
                    if(temp == null)
                        continue;
                    Object[] objs = (Object[])temp.toArray();
                    for(Object obj : objs)//inbound_packets.get(srcIP).toArray(new Packet[inbound_packets.get(srcIP).size()]))
                    {
                        Packet p = (Packet)obj;
                        DateTime dt = DateTime.parse(p.timespan);
                        if(dt.plus(40000).isBefore(nowDT))
                        {
                            System.out.println("Lost Packet Inbound: " + p.ipPacket.desIP + " " + p.hash);
                            lost_packets_inbound.add(p);
                            AddRowSafeThread(p,true);
                            inbound_packets.get(srcIP).remove(p);
                        }
                    }
                }
            }
        }).start();
    }

    //directly received from client
    void AddOutboundPackets(List<Packet> packets) 
    {
        //#Old:String stringToBeHashed = packet.desIP+packet.desPort+packet.srcIP+packet.srcPort+packet.type+packet.checksum+packet.timespan;
        //#New:stringToBeHashed = ip.srcIP+ip.desIP+ip.identification+ip.totalLength;
        
        String srcIP = "";
        lockOutbound.readLock().lock();
        try
        {
            if(packets.size() != 0)
            {
                srcIP = packets.get(0).ipPacket.srcIP;
            }
            if(!outbound_packets.containsKey(srcIP))
            { 
                outbound_packets.put(srcIP, new CopyOnWriteArrayList<Packet>());
                //lockOutbound.readLock().unlock();
                StartVerifyingThread(srcIP);
            }
        }
        finally{lockOutbound.readLock().unlock();}
        
        CopyOnWriteArrayList<Packet> temp = outbound_packets.get(srcIP);
        for(Packet packet : packets)
        {
            packet.timespan = DateTime.now().toString();
            temp.add(packet);
        }
        startVerifying = true;
        //temp.addAll(packets);
    }
    
    //received from mirror port
    void AddInboundPacket(Packet packet)
    {
        //#Old:String stringToBeHashed = packet.desIP+packet.desPort+packet.srcIP+packet.srcPort+packet.type+packet.checksum;
        //#New:stringToBeHashed = ip.srcIP+ip.desIP+ip.identification+(tcp/udp).desPort;
        if(!startVerifying)
            return;
        lockInbound.readLock().lock();
        try
        {
            if(!inbound_packets.containsKey(packet.ipPacket.srcIP))
            {
                inbound_packets.put(packet.ipPacket.srcIP, new CopyOnWriteArrayList<Packet>());
                //lockInbound.readLock().unlock();
                StartLostPacketThread(packet.ipPacket.srcIP);
                StartBufferSizeTimers();
            }
        }
        finally{lockInbound.readLock().unlock();}
        
        CopyOnWriteArrayList<Packet> temp = inbound_packets.get(packet.ipPacket.srcIP);
        packet.timespan = DateTime.now().toString();
        temp.add(packet);
    }

    private void StartBufferSizeTimers() 
    {
        new Thread(new Runnable(){

            @Override
            public void run() 
            {
                while(true)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PacketVerificationService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() 
                        {
                            int inbound_size = 0;
                            for(Entry<String,CopyOnWriteArrayList<Packet>> entry : inbound_packets.entrySet())
                            {
                                inbound_size += entry.getValue().size();
                            }
                            int outbound_size = 0;
                            for(Entry<String,CopyOnWriteArrayList<Packet>> entry : outbound_packets.entrySet())
                            {
                                outbound_size += entry.getValue().size();
                            }
                            XCOM_server.mo.lbl_buffer_size_inbound.setText(String.valueOf(inbound_size));
                            XCOM_server.mo.lbl_buffer_size_outbound.setText(String.valueOf(outbound_size));
                        }
                    });
                }
            }
        }).start();
    }
}
