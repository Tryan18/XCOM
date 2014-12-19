/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xcom_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;
import xcom_server.generated.IPpacket;
import xcom_server.generated.Packet;
import xcom_server.generated.TCPpacket;
import xcom_server.generated.UDPpacket;

/**
 *
 * @author root
 */
public class Utils 
{
    public static void AutoScrollTable(final JTable table_packets) 
    {
        TableModelListener l = new TableModelListener() 
        {
            @Override
            public void tableChanged(final TableModelEvent e) 
            {
                if(e.getType() == TableModelEvent.INSERT)
                {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            int viewRow = table_packets.convertRowIndexToView(e.getFirstRow());
                            table_packets.scrollRectToVisible(table_packets.getCellRect(viewRow, 0, true));    
                        }
                    });
                }
            }
        };
        table_packets.getModel().addTableModelListener(l);
    }
    
    public static String GenerateMD5Hash(String input)
    {
        String hash = "";
        try
        {
            byte[] bytesOfMessage = input.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdbytes = md.digest(bytesOfMessage);
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) 
            {
              sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
              //System.out.println("Digest(in hex format):: " + sb.toString());
            }
            hash = sb.toString();
        }
        finally
        {
            return hash;
        }
    }
    
    public static String GenerateMD5Hash(byte[] input)
    {
        String hash = "";
        try
        {
            byte[] bytesOfMessage = input;

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdbytes = md.digest(bytesOfMessage);
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) 
            {
              sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
              //System.out.println("Digest(in hex format):: " + sb.toString());
            }
            hash = sb.toString();
        }
        finally
        {
            return hash;
        }
    }

    public static byte[] concatByteArrays(byte[] tempIpData, byte[] data) 
    {
        try {
            ByteArrayOutputStream os;
            os = new ByteArrayOutputStream();
            os.write(tempIpData);
            os.write(data);
            return os.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    static boolean isValidTCPChecksum(TCPPacket tcp) 
    {
        byte[] totalData = concatByteArrays(tcp.getHeader(),tcp.getData());
        int checksum = CRC16.getCrc16(totalData);
        return (checksum == tcp.getChecksum());
        
    }

    static boolean isValidUDPChecksum(UDPPacket udp) 
    {
        byte[] totalData = concatByteArrays(udp.getHeader(),udp.getData());
        int checksum = CRC16.getCrc16(totalData);
        return (checksum == udp.getChecksum());
    }

//    static String CreateHash(Packet p) 
//    {
//        String hash = "";
//        String preIPHash = "";
//        String preTCPHash = "";
//        String preUDPHash = "";
//        IPpacket ip = p.ipPacket;
//        TCPpacket tcp = p.tcpPacket;
//        UDPpacket udp = p.udpPacket;
//
//        preIPHash = ip.desIP + ip.flags + ip.fragmentationOffset + ip.headerLength + ip.identification + ip.messageLength + ip.protocol + ip.srcIP + ip.tos + ip.totalLength + ip.ttl + ip.version;
//        if (p.type == "TCP")
//            preTCPHash = tcp.ackNumber + tcp.checksum + tcp.dataOffset + tcp.desPort + tcp.headerLength + tcp.messageLength + tcp.seqNumber + tcp.srcPort + tcp.urgentPointer + tcp.window;
//        else
//            preUDPHash = udp.checksum + udp.desPort + udp.length_Header_and_Data + udp.srcPort;
//        hash += preIPHash + preTCPHash + preUDPHash;
//        
//        return hash;
//    }
    
    static String CreateHash(Packet p)
    {
        String stringToBeHashed = "";
        IPpacket ip = p.ipPacket;
        TCPpacket tcp = p.tcpPacket;
        UDPpacket udp = p.udpPacket;
        stringToBeHashed = ip.srcIP+ip.desIP+ip.identification;
        if(p.tcpPacket != null)
        {
            stringToBeHashed += tcp.desPort;
        }
        else
        {
            stringToBeHashed += udp.desPort;
        }
        return GenerateMD5Hash(stringToBeHashed);
    }

    static IPpacket CreateIPpacket(IPPacket ip) 
    {
        IPpacket ip2 = new IPpacket();
        ip2.desIP = ip.getDestinationAddress();
        ip2.flags = String.valueOf(ip.getFragmentFlags());
        ip2.fragmentationOffset = String.valueOf(ip.getFragmentOffset());
        ip2.headerLength = String.valueOf(ip.getHeaderLength());
        ip2.identification = String.valueOf(ip.getId());
        ip2.messageLength = String.valueOf(ip.getIPData().length);
        ip2.protocol = String.valueOf(ip.getProtocol());
        ip2.srcIP = ip.getSourceAddress();
        ip2.tos = String.valueOf(ip.getTypeOfService());
        ip2.totalLength = String.valueOf(ip.getLength());
        ip2.ttl = String.valueOf(ip.getTimeToLive());
        ip2.version = String.valueOf(ip.getVersion());
        return ip2;
    }

    static TCPpacket CreateTCPpacket(TCPPacket tcp) 
    {
        TCPpacket tcp2 = new TCPpacket();
        tcp2.ackNumber = String.valueOf(tcp.getAcknowledgementNumber());
        tcp2.checksum = String.valueOf(tcp.getChecksum());
        tcp2.dataOffset = String.valueOf(tcp.getTCPHeaderLength() - tcp.getPayloadDataLength());
        tcp2.desPort = String.valueOf(tcp.getDestinationPort());
        tcp2.flags = String.valueOf(Utils.GetTCPFlags(tcp));
        return tcp2;
    }

    static UDPpacket CreateUDPpacket(UDPPacket udp) 
    {
        UDPpacket udp2 = new UDPpacket();
        udp2.checksum = String.valueOf(udp.getChecksum());
        udp2.desPort = String.valueOf(udp.getDestinationPort());
        udp2.length_Header_and_Data = String.valueOf(udp.getLength());
        udp2.srcPort = String.valueOf(udp.getSourcePort());
        return udp2;
    }

    static Vector CreateVectorForRow(xcom_server.generated.Packet p) 
    {
        final Vector vec = new Vector();
        vec.add(p.hash);
        vec.add(p.ipPacket.srcIP);
        vec.add(p.ipPacket.desIP);
        vec.add(p.ipPacket.flags);
        vec.add(p.ipPacket.fragmentationOffset);
        vec.add(p.ipPacket.headerLength);
        vec.add(p.ipPacket.identification);
        vec.add(p.ipPacket.messageLength);
        vec.add(p.ipPacket.protocol);
        vec.add(p.ipPacket.tos);
        vec.add(p.ipPacket.totalLength);
        vec.add(p.ipPacket.ttl);
        vec.add(p.ipPacket.version);
        vec.add(p.type);
        if(p.type.equals("TCP"))
        {
            vec.add(p.tcpPacket.srcPort);
            vec.add(p.tcpPacket.desPort);
            vec.add(p.tcpPacket.checksum);
            vec.add(p.tcpPacket.messageLength);
            vec.add(p.tcpPacket.ackNumber);
            vec.add(p.tcpPacket.seqNumber);
            vec.add(p.tcpPacket.dataOffset);
            vec.add(p.tcpPacket.flags);
            vec.add(p.tcpPacket.headerLength);
            vec.add(p.tcpPacket.urgentPointer);
            vec.add(p.tcpPacket.window);
        }
        else
        {
            vec.add(p.udpPacket.srcPort);
            vec.add(p.udpPacket.desPort);
            vec.add(p.udpPacket.checksum);
            vec.add(p.udpPacket.length_Header_and_Data);
            
            for(int i=0;i<7;i++)
                vec.add("");
        }
        return vec;
    }
    static String[] GetColumns() 
    {
        String[] columns = new String[] {"Hash","SrcIP","DesIP","IP_flags","FragOffset","HeaderLength","Identification","MessageLength","Protocol","Tos","TotalLength","TTL","Version",
        "Type","SrcPort","DesPort","Checksum","MessageLength","AckNumber","SeqNumber","DataOffset","Flags","HeaderLength","UrgentPointer","Window"};
        return columns;
    }
    
    private static String GetTCPFlags(TCPPacket header) 
    {
        String flags = "n/a";
        return flags;
    }
}
