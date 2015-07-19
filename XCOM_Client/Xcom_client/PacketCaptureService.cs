using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using PacketDotNet;
using SharpPcap;

namespace Xcom_client
{
    public static class PacketCaptureService
    {
        private static string local_IP;

        public static void StartPacketCaptureThread(string local_ip)
        {
            PacketCaptureService.local_IP = local_ip;
            new Thread(new ThreadStart(StartPacketCaptureThreadStart)).Start();
        }

        private static void StartPacketCaptureThreadStart()
        {
            string ver = SharpPcap.Version.VersionString;
            /* Print SharpPcap version */
            Console.WriteLine("SharpPcap {0}", ver);
            Console.WriteLine();

            /* Retrieve the device list */
            var devices = CaptureDeviceList.Instance;

            /*If no device exists, print error */
            if (devices.Count < 1)
            {
                Console.WriteLine("No device found on this machine");
                return;
            }

            Console.WriteLine("The following devices are available on this machine:");
            Console.WriteLine("----------------------------------------------------");
            Console.WriteLine();

            int i = 0;
            int j = 0;
            /* Scan the list printing every entry */
            foreach (var dev in devices)
            {
                /* Description */
                Console.WriteLine("{0}) {1} {2}", i, dev.Name, dev.Description);
                i++;
                if (dev.Description.Contains(local_IP))
                {
                    j = i;
                    break;
                }
            }

            //Console.WriteLine();
            //Console.Write("-- Please choose a device to capture: ");
            //i = int.Parse( Console.ReadLine() );

            var device = devices[j];

            //Register our handler function to the 'packet arrival' event
            device.OnPacketArrival +=
                new PacketArrivalEventHandler(device_OnPacketArrival);

            // Open the device for capturing
            int readTimeoutMilliseconds = 1000;
            device.Open(DeviceMode.Normal, readTimeoutMilliseconds);

            //tcpdump filter to capture only TCP/IP packets
            string filter = "not dst net 10.6.0.0/24 and not dst net 10.8.0.0/24 and not ip broadcast and not ip6";
            device.Filter = filter;

            Console.WriteLine();
            Console.WriteLine
                ("-- The following tcpdump filter will be applied: \"{0}\"",
                filter);

            // Start capture 'INFINTE' number of packets
            device.Capture();
            
            // Close the pcap device
            // (Note: this line will never be called since
            //  we're capturing infinite number of packets
            //device.Close();
        }

        /// <summary>
        /// Prints the time, length, src ip, src port, dst ip and dst port
        /// for each TCP/IP packet received on the network
        /// </summary>
        private static void device_OnPacketArrival(object sender, CaptureEventArgs e)
        {
            var time = e.Packet.Timeval.Date;
            var len = e.Packet.Data.Length;

            var packet = PacketDotNet.Packet.ParsePacket(e.Packet.LinkLayerType, e.Packet.Data);
            String bytes = UTF8Encoding.UTF8.GetString(packet.Bytes);
            Packet p = new Packet();
            IpPacket ipPacket = (IpPacket)packet.Extract(typeof(IpPacket));
            if (ipPacket != null)
            {
                p.IpPacket = Utils.CreateIPpacket(new MJsniffer.IPHeader(ipPacket.Header, ipPacket.Header.Length));
                p.Timespan = time.ToString();
                if (ipPacket.PayloadPacket != null)
                {
                    TcpPacket tcpPacket = (TcpPacket)ipPacket.PayloadPacket.Extract(typeof(TcpPacket));
                    if (tcpPacket != null)
                    {
                        p.Type = "TCP";
                        p.TcpPacket = Utils.CreateTCPpacket(new MJsniffer.TCPHeader(tcpPacket.Header, tcpPacket.Header.Length));
                        XCOM_Core.AddPacket(p,tcpPacket.PayloadData);
                    }
                    else
                    {
                        UdpPacket udpPacket = (UdpPacket)ipPacket.PayloadPacket.Extract(typeof(UdpPacket));
                        if (udpPacket != null)
                        {
                            p.Type = "UDP";
                            p.UdpPacket = Utils.CreateUDPpacket(new MJsniffer.UDPHeader(udpPacket.Header, udpPacket.Header.Length));
                            XCOM_Core.AddPacket(p, udpPacket.PayloadData);
                        }
                    }
                }
            }
            
            //if (tcpPacket != null)
            //{
            //    var tcpPacket = (IpPacket)tcpPacket.ParentPacket;
            //    System.Net.IPAddress srcIp = ipPacket.SourceAddress;
            //    System.Net.IPAddress dstIp = ipPacket.DestinationAddress;
            //    int srcPort = tcpPacket.SourcePort;
            //    int dstPort = tcpPacket.DestinationPort;

            //    Console.WriteLine("{0}:{1}:{2},{3} Len={4} {5}:{6} -> {7}:{8}",
            //        time.Hour, time.Minute, time.Second, time.Millisecond, len,
            //        srcIp, srcPort, dstIp, dstPort);
            //}
        }
    }
}
