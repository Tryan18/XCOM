using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using PacketDotNet;

namespace Xcom_client
{
    public static class XCOM_Core
    {
        public static int bufferSize = -1;
        private static List<Packet> packets = new List<Packet>();
        public static string ip;
        public static int port;
        public static string local_ip;
        private static Semaphore lockPackets = new Semaphore(1, 1);

        public static void Initialize(string local_ip,string ip,int port)
        {
            XCOM_Core.local_ip = local_ip;
            PacketCaptureService.StartPacketCaptureThread(local_ip);
            //ThriftService.ConnectToThriftService("10.6.0.176", 9888);
        }

        public static void AddPacket(Packet packet,byte[] payload)
        {
            packet.Hash = Utils.CreateHash(packet,payload);
            lockPackets.WaitOne();
            packets.Add(packet);
            if (packets.Capacity > bufferSize)
            {
                //ThriftService.client.SendPackets(packets);
                packets.Clear();
            }
            lockPackets.Release();
        }
    }
}
