using System.Net;
using System.Text;
using System;
using System.IO;
using System.Windows.Forms;

namespace MJsniffer
{
    public class UDPHeader
    {
        //UDP header fields
        internal ushort usSourcePort;            //Sixteen bits for the source port number        
        internal ushort usDestinationPort;       //Sixteen bits for the destination port number
        internal ushort usLength;                //Length of the UDP header
        internal ushort sChecksum;                //Sixteen bits for the checksum
                                                //(checksum can be negative so taken as short)              
        //End UDP header fields

        private byte[] byUDPData = new byte[4096];  //Data carried by the UDP packet

        public UDPHeader(byte [] byBuffer, int nReceived)
        {
            MemoryStream memoryStream = new MemoryStream(byBuffer, 0, nReceived);
            BinaryReader binaryReader = new BinaryReader(memoryStream);

            //The first sixteen bits contain the source port
            usSourcePort = (ushort)IPAddress.NetworkToHostOrder(binaryReader.ReadInt16());

            //The next sixteen bits contain the destination port
            usDestinationPort = (ushort)IPAddress.NetworkToHostOrder(binaryReader.ReadInt16());

            //The next sixteen bits contain the length of the UDP packet
            usLength = (ushort)IPAddress.NetworkToHostOrder(binaryReader.ReadInt16());

            //The next sixteen bits contain the checksum
            sChecksum = (ushort)IPAddress.NetworkToHostOrder(binaryReader.ReadInt16());            

            //Copy the data carried by the UDP packet into the data buffer
            //The UDP header is of 8 bytes so we start copying after it
            //Array.Copy(byBuffer,8,byUDPData,0,nReceived - 8);
        }

        public string SourcePort
        {
            get
            {
                return usSourcePort.ToString();
            }
        }

        public string DestinationPort
        {
            get
            {
                return usDestinationPort.ToString();
            }
        }

        public string Length
        {
            get
            {
                return usLength.ToString ();
            }
        }

        public int LengthInt 
        {
            get
            {
                return (int)usLength;
            }
        }

        public string Checksum
        {
            get
            {
                //Return the checksum in hexadecimal format
                return string.Format("0x{0:x2}", sChecksum);
            }
        }

        public byte[] Data
        {
            get
            {
                return byUDPData;
            }
        }

        public string RawChecksum
        {
            get
            {
                return sChecksum.ToString();
            }
        }
    }
}