/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xcom_server;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import org.joda.time.DateTime;
import xcom_server.generated.IPpacket;
import xcom_server.generated.TCPpacket;
import xcom_server.generated.UDPpacket;

/**
 *
 * @author root
 */
public class InboundPacketHandler implements PacketListener 
{
  private DefaultTableModel dtm;
  private DateTime compareDate;

    public InboundPacketHandler(DefaultTableModel dtm)
    {
        this.dtm = dtm;
        //this.compareDate = XCOM_server.syncDateTime;
        //compareDate = compareDate.
    }

    @Override
    public void packetArrived(Packet packet) 
    {
        try
        {
        IPpacket ip2 = null;
        TCPpacket tcp2 = null;
        UDPpacket udp2 = null;
        xcom_server.generated.Packet p = new xcom_server.generated.Packet();
        if(packet instanceof IPPacket)
        {
            IPPacket ip = (IPPacket)packet;
            if(!XCOM_server.pvs.allowedConnections.contains(ip.getSourceAddress()))
                return;
            
            ip2 = Utils.CreateIPpacket(ip);
            p.ipPacket = ip2;
//            if(!ip.isValidIPChecksum())
//            {
//                System.out.println("Wrong checksum: "+ip.getChecksum());
//                //return;
//            }
//            else
//            {
//                System.out.println("Correct checksum.");
//            }
        }
        if(packet instanceof TCPPacket)
        {
            TCPPacket tcp = (TCPPacket)packet;
            tcp2 = Utils.CreateTCPpacket(tcp);
            p.tcpPacket = tcp2;
            p.type = "TCP";
//            if(!Utils.isValidTCPChecksum(tcp))
//            {
//                //System.out.println("Wrong TCP checksum: "+tcp.getChecksum());
//                //return;
//            }
//            else
//            {
//                //System.out.println("Correct TCP checksum.");
//            }
            
            //debug
            //System.out.println("From: "+source+" To: "+destination+" Port: "+destinationPort);
        }
        else if(packet instanceof UDPPacket)
        {
            UDPPacket udp = (UDPPacket)packet;
            udp2 = Utils.CreateUDPpacket(udp);
            p.udpPacket = udp2;
            p.type = "UDP";
//            if(!Utils.isValidUDPChecksum(udp))
//            {
//                //System.out.println("Wrong UDP checksum."+udp.getChecksum());
//                //return;;
//            }
            //System.out.println("Correct UDP checksum.");
        }
        else
        {
            return;
        }
        
        p.hash = Utils.CreateHash(p);
        p.timespan = "";
        //String timespan = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.ffffff").format(Calendar.getInstance().getTime());

        final xcom_server.generated.Packet pa = p;
        if(XCOM_server.guiEnabled)
        {
            SwingUtilities.invokeLater(new Runnable() {
            public void run() 
            {
                if(XCOM_server.guiEnabled)
                    dtm.addRow(Utils.CreateVectorForRow(pa));
            }      
           });
        }
        xcom_server.XCOM_server.pvs.AddInboundPacket(pa);
//        xcom_server.generated.Packet p = new xcom_server.generated.Packet();
//        p.desIP = destination;
//        p.desPort = String.valueOf(destinationPort);
//        p.type = type;
//        p.srcIP = source;
//        p.srcPort = sourcePort;
//        p.checksum = String.valueOf(checksum);
//        p.hash = hash;
        //xcom_server.XCOM_server.pvs.AddInboundPacket(p);
        //System.out.println("From: "+source+" To: "+destination+" Port: "+destinationPort);
        }
        catch(Exception ex)
        {
            System.err.println(ex);
        }
    }
}
