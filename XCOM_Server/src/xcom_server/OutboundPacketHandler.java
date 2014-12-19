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
import java.util.Map.Entry;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import xcom_server.generated.Packet;
import xcom_server.generated.PacketService;

/**
 *
 * @author root
 */
public class OutboundPacketHandler implements PacketService.Iface 
{
    private DefaultTableModel dtm;
    private int bufferSize = 10;
    
    public OutboundPacketHandler(DefaultTableModel dtm) 
    {
        this.dtm = dtm;
        
    }

    @Override
    public void ping() throws TException 
    {
        System.out.println("ping.");
    }

    @Override
    public int connect(String ip) throws TException 
    {
        System.out.println("Connected: "+ip);
        try {
                    if(!XCOM_server.pvs.allowedConnections.contains(ip))
                    {
                        XCOM_server.pvs.allowedConnections.add(ip);
                    }
            XCOM_server.StartPacketSniffer();
        } catch (Exception ex) {
            Logger.getLogger(OutboundPacketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bufferSize;
    }

    @Override
    public void SendPackets(List<Packet> packets) throws TException 
    {
        if(XCOM_server.guiEnabled)
        {
            for(final Packet packet:packets)
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() 
                    {
                      dtm.addRow(Utils.CreateVectorForRow(packet));
                    }      
               });
            }
        }
        
        xcom_server.XCOM_server.pvs.AddOutboundPackets(packets);
        
    }
}
