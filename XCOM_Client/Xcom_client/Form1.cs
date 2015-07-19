using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace Xcom_client
{
    public partial class Form1 : Form
    {
        string local_ip = "";
        
        public Form1()
        {
            InitializeComponent();
            GetIpAddress();
        }

        private void GetIpAddress()
        {
            if (System.Net.NetworkInformation.NetworkInterface.GetIsNetworkAvailable())
            {
                local_ip = LocalIPAddress();
                lbl_ip.Text = local_ip;
            }
            else
            {
                lbl_ip.Text = "Not Connected to network!";
                lbl_ip.Text = local_ip;
            }
        }

        private string LocalIPAddress()
        {
            IPHostEntry host;
            string localIP = "";
            host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    localIP = ip.ToString();
                    break;
                }
            }
            return localIP;
        }

        private void btn_connect_Click(object sender, EventArgs e)
        {
            if (local_ip.Length != 0)
            {
                string[] info = txt_address.Text.Split(':');
                XCOM_Core.Initialize(local_ip, info[0], Convert.ToInt32(info[1]));
            }
            else
            {
                MessageBox.Show("Not Connected to network!");
            }
        }
    }
}
