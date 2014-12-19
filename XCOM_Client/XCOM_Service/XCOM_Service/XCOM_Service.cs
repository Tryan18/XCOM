using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading;
using PacketDotNet;
using SharpPcap;
using SharpPcap.WinPcap;

namespace XCOM_Service
{
    public partial class XCOM_Service : ServiceBase
    {
        private static WinPcapDevice device;
        private static Thread com_thread;

        public XCOM_Service()
        {
            InitializeComponent();
        }

        protected override void OnStart(string[] args)
        {
            start_malware();
        }

        protected override void OnStop()
        {
            if (com_thread != null && com_thread.ThreadState == System.Threading.ThreadState.Running)
            {
                com_thread.Abort();
                Thread.Sleep(1000);
                device.Close();
            }
        }

        private static void start_malware()
        {
            var devices = CaptureDeviceList.Instance;
            device = (WinPcapDevice)devices[0];
            device.Open();

            //Generate a random packet
            Packet bytes = CreateMalwarePacket();
            com_thread = new Thread (new ParameterizedThreadStart(SendPacket));
            com_thread.Start(bytes);

            //while(true)
            //{
            //    Thread.Sleep(1000);
            //}
            
            
            //Console.WriteLine("-- Device closed.");
            //Console.Write("Hit 'Enter' to exit...");
            //Console.ReadLine();
        }

        private static void SendPacket(object obj)
        {
            Packet bytes = (Packet)obj;
            while (true)
            {
                try
                {
                    //Send the packet out the network device
                    device.SendPacket(bytes);
                    Console.WriteLine("-- Packet sent successfuly.");
                }
                catch (Exception e)
                {
                    Console.WriteLine("-- " + e.Message);
                }
                Thread.Sleep(2000);
            }
        }

        private static Packet CreateMalwarePacket()
        {
            //08:00:27:95:a6:a3 source mac
            //ushort tcpSourcePort = 123;
            //ushort tcpDestinationPort = 321;
            //var tcpPacket = new TcpPacket(tcpSourcePort, tcpDestinationPort);

            var ipSourceAddress = System.Net.IPAddress.Parse("10.8.0.148");
            var ipDestinationAddress = System.Net.IPAddress.Parse("8.8.8.8");
            var ipPacket = new IPv4Packet(ipSourceAddress, ipDestinationAddress);
            ipPacket.TimeToLive = 128;
            ipPacket.Checksum = ipPacket.CalculateIPChecksum();
            UdpPacket udpPacket = UdpPacket.RandomPacket();
            udpPacket.PayloadData = Encoding.UTF8.GetBytes("XCOM Prototype : Don't be frightend, this is not malware! Just science project");
            //udpPacket.SourcePort=50093;
            udpPacket.DestinationPort=80;
            ipPacket.PayloadPacket = udpPacket;
            var sourceHwAddress = "08-00-27-95-A6-A3";
            var ethernetSourceHwAddress = System.Net.NetworkInformation.PhysicalAddress.Parse(sourceHwAddress);
            var destinationHwAddress = "08-00-27-AB-3E-A6";
            var ethernetDestinationHwAddress = System.Net.NetworkInformation.PhysicalAddress.Parse(destinationHwAddress);

            // NOTE: using EthernetPacketType.None to illustrate that the Ethernet
            //       protocol type is updated based on the packet payload that is
            //       assigned to that particular Ethernet packet
            var ethernetPacket = new EthernetPacket(ethernetSourceHwAddress,
                ethernetDestinationHwAddress,
                EthernetPacketType.None);

            // Now stitch all of the packets together
            //ipPacket.PayloadPacket = tcpPacket;
            ethernetPacket.PayloadPacket = ipPacket;

            // and print out the packet to see that it looks just like we wanted it to
            Console.WriteLine(ethernetPacket.ToString());

            // to retrieve the bytes that represent this newly created EthernetPacket use the Bytes property
            //byte[] packetBytes = ethernetPacket.Bytes;
            return ethernetPacket;
        }
    }
}
