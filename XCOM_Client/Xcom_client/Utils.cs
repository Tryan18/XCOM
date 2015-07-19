using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using MJsniffer;

namespace Xcom_client
{
    public static class Utils
    {
        static MD5 md5Hash = MD5.Create();

        public static string GetMd5Hash(byte[] buffer,int length)
        {
            byte[] input = new byte[length];
            Buffer.BlockCopy(buffer, 0, input, 0, length);
            // Convert the input string to a byte array and compute the hash.
            //byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(input));
            byte[] data = md5Hash.ComputeHash(input);

            // Create a new Stringbuilder to collect the bytes
            // and create a string.
            StringBuilder sBuilder = new StringBuilder();

            // Loop through each byte of the hashed data 
            // and format each one as a hexadecimal string.
            for (int i = 0; i < data.Length; i++)
            {
                sBuilder.Append(data[i].ToString("x2"));
            }

            // Return the hexadecimal string.
            return sBuilder.ToString();
        }

        public static string GetMd5Hash(string input)
        {
            //byte[] input = new byte[length];
            //Buffer.BlockCopy(buffer, 0, input, 0, length);
            // Convert the input string to a byte array and compute the hash.
            byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(input));
            //byte[] data = md5Hash.ComputeHash(input);

            // Create a new Stringbuilder to collect the bytes
            // and create a string.
            StringBuilder sBuilder = new StringBuilder();

            // Loop through each byte of the hashed data 
            // and format each one as a hexadecimal string.
            for (int i = 0; i < data.Length; i++)
            {
                sBuilder.Append(data[i].ToString("x2"));
            }

            // Return the hexadecimal string.
            return sBuilder.ToString();
        }

        internal static IPpacket CreateIPpacket(IPHeader ipHeader)
        {
            IPpacket ip = new IPpacket();
            ip.DesIP = ipHeader.DestinationAddress.ToString();
            ip.Flags = ipHeader.Flags_con;
            ip.FragmentationOffset = ipHeader.FragmentationOffset;
            ip.HeaderLength = ipHeader.HeaderLength;
            ip.Identification = ipHeader.Identification;
            ip.Protocol = ipHeader.ProtocolString;
            ip.SrcIP = ipHeader.SourceAddress.ToString();
            ip.Tos = ipHeader.DifferentiatedServices_con;
            ip.TotalLength = ipHeader.TotalLength;
            ip.Ttl = ipHeader.TTL;
            ip.Version = ipHeader.Version_con;
            ip.HeaderLength = ipHeader.HeaderLength;
            ip.MessageLength = ipHeader.MessageLength.ToString();
            return ip;
        }

        internal static TCPpacket CreateTCPpacket(TCPHeader tcpHeader)
        {
            TCPpacket tcp = new TCPpacket();
            tcp.AckNumber = tcpHeader.AcknowledgementNumber;
            tcp.Checksum = tcpHeader.RawChecksum;
            tcp.Flags = tcpHeader.Flags_con;
            tcp.DataOffset = tcpHeader.DataOffset;
            tcp.DesPort = tcpHeader.DestinationPort;
            tcp.HeaderLength = tcpHeader.HeaderLength;
            tcp.MessageLength = tcpHeader.MessageLength.ToString();
            tcp.SeqNumber = tcpHeader.SequenceNumber;
            tcp.SrcPort = tcpHeader.SourcePort;
            tcp.UrgentPointer = tcp.UrgentPointer;
            tcp.Window = tcpHeader.WindowSize;
            return tcp;
        }

        internal static UDPpacket CreateUDPpacket(UDPHeader udpHeader)
        {
            UDPpacket udp = new UDPpacket();
            udp.Checksum = udpHeader.RawChecksum;
            udp.DesPort = udpHeader.DestinationPort;
            udp.Length_Header_and_Data = udpHeader.Length;
            udp.SrcPort = udpHeader.SourcePort;
            return udp;
        }

        //internal static string CreateHash(Packet p)
        //{
        //    string hash = "";
        //    string preIPHash = "";
        //    string preTCPHash = "";
        //    string preUDPHash = "";
        //    IPpacket ip = p.IpPacket;
        //    TCPpacket tcp = p.TcpPacket;
        //    UDPpacket udp = p.UdpPacket;
            
        //    preIPHash = ip.DesIP + ip.Flags + ip.FragmentationOffset + ip.HeaderLength + ip.Identification + ip.MessageLength + ip.Protocol + ip.SrcIP + ip.Tos + ip.TotalLength + ip.Ttl + ip.Version;
        //    if (p.Type == "TCP")
        //        preTCPHash = tcp.AckNumber + tcp.Checksum + tcp.DataOffset + tcp.DesPort + tcp.HeaderLength + tcp.MessageLength + tcp.SeqNumber + tcp.SrcPort + tcp.UrgentPointer + tcp.Window;
        //    else
        //        preUDPHash = udp.Checksum + udp.DesPort + udp.Length_Header_and_Data + udp.SrcPort;
        //    hash += preIPHash + preTCPHash + preUDPHash;
        //    hash = GetMd5Hash(hash);
        //    return hash;
        //}

        internal static string CreateHash(Packet p,byte[] payload)
        {
            string stringToBeHashed = "";
            IPpacket ip = p.IpPacket;
            TCPpacket tcp = p.TcpPacket;
            UDPpacket udp = p.UdpPacket;
            
            stringToBeHashed = ip.SrcIP + ip.DesIP + ip.Identification + ip.TotalLength;
            if (tcp != null)
                stringToBeHashed += tcp.DesPort;
            else
                stringToBeHashed += udp.DesPort;
            byte[] indexBuffer = Encoding.UTF8.GetBytes(stringToBeHashed);
            
            if (payload != null && payload.Length != 0)
            {
                byte[] totalBuffer = new byte[indexBuffer.Length+payload.Length];
                Buffer.BlockCopy(indexBuffer,0,totalBuffer,0,indexBuffer.Length);
                Buffer.BlockCopy(payload, 0, totalBuffer, indexBuffer.Length, payload.Length);
                return GetMd5Hash(totalBuffer, totalBuffer.Length);
            }
            else
            {
                return GetMd5Hash(indexBuffer,indexBuffer.Length);
            }
        }

        public enum Protocol
        {
            TCP = 6,
            UDP = 17,
            Unknown = -1
        };
    }
}
