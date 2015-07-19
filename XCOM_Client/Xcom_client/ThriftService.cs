using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using Thrift.Protocol;
using Thrift.Transport;

namespace Xcom_client
{
    public static class ThriftService
    {
        public static PacketService.Client client;
        private static TBufferedTransport transport;
        private static TBinaryProtocol protocol;

        public static void ConnectToThriftService(string ip,int port)
        {
            try
            {
                var socket = new TSocket(ip, 9888);
                transport = new TBufferedTransport(socket);
                protocol = new TBinaryProtocol(transport);
                client = new PacketService.Client(protocol);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            new Thread(new ThreadStart(ConnectThread)).Start();
            if (!transport.IsOpen)
                Thread.Sleep(100);
        }

        private static void ConnectThread()
        {
            transport.Open();
            XCOM_Core.bufferSize = ThriftService.client.connect(XCOM_Core.local_ip);
        }
    }
}
