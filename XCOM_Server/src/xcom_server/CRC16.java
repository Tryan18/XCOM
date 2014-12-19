/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xcom_server;

/**
 *
 * @author root
 */
public final class CRC16
{
  
  public static int getCrc16(byte[] buffer) {
    return getCrc16(buffer, 0, buffer.length, 0xA001, 0);
    }

    public synchronized static int getCrc16(byte[] buffer, int offset, int bufLen, int polynom, int preset) {
        preset &= 0xFFFF;
        polynom &= 0xFFFF;
        int crc = preset;
        for (int i = 0; i < bufLen; i++) {
            int data = buffer[i + offset] & 0xFF;
            crc ^= data;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >> 1) ^ polynom;
                } else {
                    crc = crc >> 1;
                }
            }
        }
        return crc & 0xFFFF;
    }
}