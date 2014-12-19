/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xcom_server;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.PacketCapture;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.joda.time.DateTime;
import xcom_server.generated.PacketService;

/**
 *
 * @author ubuntu
 */
public class XCOM_server {

    private static final int INFINITE = -1;
    public static OutboundPacketHandler handler;
    public static PacketService.Processor processor;
    // BPF filter for capturing any packet
    private static final String FILTER = "(tcp || udp)";

    private static PacketCapture m_pcap;
    private static String m_device;
    public static MainOverview mo;
    public static CheckOverview co;
    protected static PacketVerificationService pvs;
    private static DefaultTableModel dtm_inbound;
    private static DefaultTableModel dtm_outbound;
    protected static DefaultTableModel dtm_inbound_error;
    protected static DefaultTableModel dtm_outbound_error;
    public static DateTime syncDateTime;
    private static TSimpleServer server;
    public static boolean guiEnabled = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        Initialize();
        SetupTableGUI();
        StartPacketService();
        StartMirrorSnifferService();
    }
    
    private static void Initialize() 
    {
        syncDateTime = DateTime.now().minusDays(2);
        pvs = new PacketVerificationService();
    }
    
    private static void SetupTableGUI()
    {
        String[] columns = Utils.GetColumns();
        dtm_inbound = new DefaultTableModel(columns,0);
        dtm_outbound = new DefaultTableModel(columns,0);
        dtm_inbound_error = new DefaultTableModel(columns,0);
        dtm_outbound_error = new DefaultTableModel(columns,0);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mo = new MainOverview();
                mo.setVisible(true);
                mo.table_packets_inbound.setModel(dtm_inbound);
                mo.table_packets_outbound.setModel(dtm_outbound);
                co = new CheckOverview();
                co.table_packets_error_outbound.setModel(dtm_outbound_error);
                co.table_packets_error_inbound.setModel(dtm_inbound_error);
               Utils.AutoScrollTable(mo.table_packets_inbound);
               Utils.AutoScrollTable(mo.table_packets_outbound);
            }
        });
    }
    
    private static void StartMirrorSnifferService() throws Exception
    {
        m_pcap = new PacketCapture();

        // Step 2:  Check for devices 
        //m_device = m_pcap.findDevice();

        // Step 3:  Open Device for Capturing (requires root)
        m_pcap.open("eth1", true);

        // Step 4:  Add a BPF Filter (see tcpdump documentation)
        m_pcap.setFilter(FILTER, true);
        //m_pcap.setFilter("not dst host 10.6.0.176 and not dst host 10.8.0.148",true);
        m_pcap.setFilter("not dst 239.255.255.250 and not dst 10.8.0.255", true);
        //m_pcap.setFilter("not dst host 10.8.0.147",true);
        // Step 5:  Register a Listener for Raw Packets
        m_pcap.addPacketListener(new InboundPacketHandler(dtm_inbound));
        
        // Step 6:  Capture Data (max. PACKET_COUNT packets)
        
    }
    
    public static void StartPacketSniffer() throws Exception
    {
        if(m_pcap != null)
        {
            System.out.println("Inbound Mirror Packet capture started!");
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        m_pcap.capture(INFINITE);
                    } catch (CapturePacketException ex) {
                        Logger.getLogger(XCOM_server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
        else
        {
            System.out.println("Packet Sniffer not started!!! (Reason: m_pcap == null)");
        }
    }

    private static void StartPacketService() throws Exception
    {
        try {
            handler = new OutboundPacketHandler(dtm_outbound);
        processor = new PacketService.Processor(handler);

        new Thread(new Runnable() {
          public void run() {
            simple(processor);
          }
        }).start();
        
    } catch (Exception e) {
      e.printStackTrace();
    }
      //System.out.println("Starting outbound original packet service!");
    }
    
    public static void StopPacketService()
    {
        System.out.println("Inbound Mirror Packet capture stopped!");
        m_pcap.close();
        System.out.println("Stopping the outbound server...");
        server.stop();
        
    }
    
    public static void simple(PacketService.Processor processor) {
        try {
        TServerTransport serverTransport = new TServerSocket(9888);
        server = new TSimpleServer(new Args(serverTransport).processor(processor));
        
      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

        System.out.println("Starting the outbound server...");
        server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}


