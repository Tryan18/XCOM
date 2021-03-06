package xcom_server.generated;
/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Packet implements org.apache.thrift.TBase<Packet, Packet._Fields>, java.io.Serializable, Cloneable, Comparable<Packet> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Packet");

  private static final org.apache.thrift.protocol.TField IP_PACKET_FIELD_DESC = new org.apache.thrift.protocol.TField("ipPacket", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField TCP_PACKET_FIELD_DESC = new org.apache.thrift.protocol.TField("tcpPacket", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField UDP_PACKET_FIELD_DESC = new org.apache.thrift.protocol.TField("udpPacket", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField TIMESPAN_FIELD_DESC = new org.apache.thrift.protocol.TField("timespan", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField HASH_FIELD_DESC = new org.apache.thrift.protocol.TField("hash", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PacketStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PacketTupleSchemeFactory());
  }

  public IPpacket ipPacket; // required
  public TCPpacket tcpPacket; // required
  public UDPpacket udpPacket; // required
  public String timespan; // required
  public String type; // required
  public String hash; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IP_PACKET((short)1, "ipPacket"),
    TCP_PACKET((short)2, "tcpPacket"),
    UDP_PACKET((short)3, "udpPacket"),
    TIMESPAN((short)4, "timespan"),
    TYPE((short)5, "type"),
    HASH((short)6, "hash");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // IP_PACKET
          return IP_PACKET;
        case 2: // TCP_PACKET
          return TCP_PACKET;
        case 3: // UDP_PACKET
          return UDP_PACKET;
        case 4: // TIMESPAN
          return TIMESPAN;
        case 5: // TYPE
          return TYPE;
        case 6: // HASH
          return HASH;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IP_PACKET, new org.apache.thrift.meta_data.FieldMetaData("ipPacket", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, IPpacket.class)));
    tmpMap.put(_Fields.TCP_PACKET, new org.apache.thrift.meta_data.FieldMetaData("tcpPacket", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TCPpacket.class)));
    tmpMap.put(_Fields.UDP_PACKET, new org.apache.thrift.meta_data.FieldMetaData("udpPacket", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UDPpacket.class)));
    tmpMap.put(_Fields.TIMESPAN, new org.apache.thrift.meta_data.FieldMetaData("timespan", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.HASH, new org.apache.thrift.meta_data.FieldMetaData("hash", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Packet.class, metaDataMap);
  }

  public Packet() {
  }

  public Packet(
    IPpacket ipPacket,
    TCPpacket tcpPacket,
    UDPpacket udpPacket,
    String timespan,
    String type,
    String hash)
  {
    this();
    this.ipPacket = ipPacket;
    this.tcpPacket = tcpPacket;
    this.udpPacket = udpPacket;
    this.timespan = timespan;
    this.type = type;
    this.hash = hash;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Packet(Packet other) {
    if (other.isSetIpPacket()) {
      this.ipPacket = new IPpacket(other.ipPacket);
    }
    if (other.isSetTcpPacket()) {
      this.tcpPacket = new TCPpacket(other.tcpPacket);
    }
    if (other.isSetUdpPacket()) {
      this.udpPacket = new UDPpacket(other.udpPacket);
    }
    if (other.isSetTimespan()) {
      this.timespan = other.timespan;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetHash()) {
      this.hash = other.hash;
    }
  }

  public Packet deepCopy() {
    return new Packet(this);
  }

  @Override
  public void clear() {
    this.ipPacket = null;
    this.tcpPacket = null;
    this.udpPacket = null;
    this.timespan = null;
    this.type = null;
    this.hash = null;
  }

  public IPpacket getIpPacket() {
    return this.ipPacket;
  }

  public Packet setIpPacket(IPpacket ipPacket) {
    this.ipPacket = ipPacket;
    return this;
  }

  public void unsetIpPacket() {
    this.ipPacket = null;
  }

  /** Returns true if field ipPacket is set (has been assigned a value) and false otherwise */
  public boolean isSetIpPacket() {
    return this.ipPacket != null;
  }

  public void setIpPacketIsSet(boolean value) {
    if (!value) {
      this.ipPacket = null;
    }
  }

  public TCPpacket getTcpPacket() {
    return this.tcpPacket;
  }

  public Packet setTcpPacket(TCPpacket tcpPacket) {
    this.tcpPacket = tcpPacket;
    return this;
  }

  public void unsetTcpPacket() {
    this.tcpPacket = null;
  }

  /** Returns true if field tcpPacket is set (has been assigned a value) and false otherwise */
  public boolean isSetTcpPacket() {
    return this.tcpPacket != null;
  }

  public void setTcpPacketIsSet(boolean value) {
    if (!value) {
      this.tcpPacket = null;
    }
  }

  public UDPpacket getUdpPacket() {
    return this.udpPacket;
  }

  public Packet setUdpPacket(UDPpacket udpPacket) {
    this.udpPacket = udpPacket;
    return this;
  }

  public void unsetUdpPacket() {
    this.udpPacket = null;
  }

  /** Returns true if field udpPacket is set (has been assigned a value) and false otherwise */
  public boolean isSetUdpPacket() {
    return this.udpPacket != null;
  }

  public void setUdpPacketIsSet(boolean value) {
    if (!value) {
      this.udpPacket = null;
    }
  }

  public String getTimespan() {
    return this.timespan;
  }

  public Packet setTimespan(String timespan) {
    this.timespan = timespan;
    return this;
  }

  public void unsetTimespan() {
    this.timespan = null;
  }

  /** Returns true if field timespan is set (has been assigned a value) and false otherwise */
  public boolean isSetTimespan() {
    return this.timespan != null;
  }

  public void setTimespanIsSet(boolean value) {
    if (!value) {
      this.timespan = null;
    }
  }

  public String getType() {
    return this.type;
  }

  public Packet setType(String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String getHash() {
    return this.hash;
  }

  public Packet setHash(String hash) {
    this.hash = hash;
    return this;
  }

  public void unsetHash() {
    this.hash = null;
  }

  /** Returns true if field hash is set (has been assigned a value) and false otherwise */
  public boolean isSetHash() {
    return this.hash != null;
  }

  public void setHashIsSet(boolean value) {
    if (!value) {
      this.hash = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IP_PACKET:
      if (value == null) {
        unsetIpPacket();
      } else {
        setIpPacket((IPpacket)value);
      }
      break;

    case TCP_PACKET:
      if (value == null) {
        unsetTcpPacket();
      } else {
        setTcpPacket((TCPpacket)value);
      }
      break;

    case UDP_PACKET:
      if (value == null) {
        unsetUdpPacket();
      } else {
        setUdpPacket((UDPpacket)value);
      }
      break;

    case TIMESPAN:
      if (value == null) {
        unsetTimespan();
      } else {
        setTimespan((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case HASH:
      if (value == null) {
        unsetHash();
      } else {
        setHash((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IP_PACKET:
      return getIpPacket();

    case TCP_PACKET:
      return getTcpPacket();

    case UDP_PACKET:
      return getUdpPacket();

    case TIMESPAN:
      return getTimespan();

    case TYPE:
      return getType();

    case HASH:
      return getHash();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IP_PACKET:
      return isSetIpPacket();
    case TCP_PACKET:
      return isSetTcpPacket();
    case UDP_PACKET:
      return isSetUdpPacket();
    case TIMESPAN:
      return isSetTimespan();
    case TYPE:
      return isSetType();
    case HASH:
      return isSetHash();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Packet)
      return this.equals((Packet)that);
    return false;
  }

  public boolean equals(Packet that) {
    if (that == null)
      return false;

    boolean this_present_ipPacket = true && this.isSetIpPacket();
    boolean that_present_ipPacket = true && that.isSetIpPacket();
    if (this_present_ipPacket || that_present_ipPacket) {
      if (!(this_present_ipPacket && that_present_ipPacket))
        return false;
      if (!this.ipPacket.equals(that.ipPacket))
        return false;
    }

    boolean this_present_tcpPacket = true && this.isSetTcpPacket();
    boolean that_present_tcpPacket = true && that.isSetTcpPacket();
    if (this_present_tcpPacket || that_present_tcpPacket) {
      if (!(this_present_tcpPacket && that_present_tcpPacket))
        return false;
      if (!this.tcpPacket.equals(that.tcpPacket))
        return false;
    }

    boolean this_present_udpPacket = true && this.isSetUdpPacket();
    boolean that_present_udpPacket = true && that.isSetUdpPacket();
    if (this_present_udpPacket || that_present_udpPacket) {
      if (!(this_present_udpPacket && that_present_udpPacket))
        return false;
      if (!this.udpPacket.equals(that.udpPacket))
        return false;
    }

    boolean this_present_timespan = true && this.isSetTimespan();
    boolean that_present_timespan = true && that.isSetTimespan();
    if (this_present_timespan || that_present_timespan) {
      if (!(this_present_timespan && that_present_timespan))
        return false;
      if (!this.timespan.equals(that.timespan))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_hash = true && this.isSetHash();
    boolean that_present_hash = true && that.isSetHash();
    if (this_present_hash || that_present_hash) {
      if (!(this_present_hash && that_present_hash))
        return false;
      if (!this.hash.equals(that.hash))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Packet other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetIpPacket()).compareTo(other.isSetIpPacket());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIpPacket()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ipPacket, other.ipPacket);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTcpPacket()).compareTo(other.isSetTcpPacket());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTcpPacket()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tcpPacket, other.tcpPacket);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUdpPacket()).compareTo(other.isSetUdpPacket());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUdpPacket()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.udpPacket, other.udpPacket);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTimespan()).compareTo(other.isSetTimespan());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimespan()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timespan, other.timespan);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHash()).compareTo(other.isSetHash());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHash()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.hash, other.hash);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Packet(");
    boolean first = true;

    sb.append("ipPacket:");
    if (this.ipPacket == null) {
      sb.append("null");
    } else {
      sb.append(this.ipPacket);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tcpPacket:");
    if (this.tcpPacket == null) {
      sb.append("null");
    } else {
      sb.append(this.tcpPacket);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("udpPacket:");
    if (this.udpPacket == null) {
      sb.append("null");
    } else {
      sb.append(this.udpPacket);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("timespan:");
    if (this.timespan == null) {
      sb.append("null");
    } else {
      sb.append(this.timespan);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("hash:");
    if (this.hash == null) {
      sb.append("null");
    } else {
      sb.append(this.hash);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (ipPacket != null) {
      ipPacket.validate();
    }
    if (tcpPacket != null) {
      tcpPacket.validate();
    }
    if (udpPacket != null) {
      udpPacket.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PacketStandardSchemeFactory implements SchemeFactory {
    public PacketStandardScheme getScheme() {
      return new PacketStandardScheme();
    }
  }

  private static class PacketStandardScheme extends StandardScheme<Packet> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Packet struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IP_PACKET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.ipPacket = new IPpacket();
              struct.ipPacket.read(iprot);
              struct.setIpPacketIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TCP_PACKET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.tcpPacket = new TCPpacket();
              struct.tcpPacket.read(iprot);
              struct.setTcpPacketIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UDP_PACKET
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.udpPacket = new UDPpacket();
              struct.udpPacket.read(iprot);
              struct.setUdpPacketIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TIMESPAN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.timespan = iprot.readString();
              struct.setTimespanIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // HASH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.hash = iprot.readString();
              struct.setHashIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Packet struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ipPacket != null) {
        oprot.writeFieldBegin(IP_PACKET_FIELD_DESC);
        struct.ipPacket.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.tcpPacket != null) {
        oprot.writeFieldBegin(TCP_PACKET_FIELD_DESC);
        struct.tcpPacket.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.udpPacket != null) {
        oprot.writeFieldBegin(UDP_PACKET_FIELD_DESC);
        struct.udpPacket.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.timespan != null) {
        oprot.writeFieldBegin(TIMESPAN_FIELD_DESC);
        oprot.writeString(struct.timespan);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.hash != null) {
        oprot.writeFieldBegin(HASH_FIELD_DESC);
        oprot.writeString(struct.hash);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PacketTupleSchemeFactory implements SchemeFactory {
    public PacketTupleScheme getScheme() {
      return new PacketTupleScheme();
    }
  }

  private static class PacketTupleScheme extends TupleScheme<Packet> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Packet struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetIpPacket()) {
        optionals.set(0);
      }
      if (struct.isSetTcpPacket()) {
        optionals.set(1);
      }
      if (struct.isSetUdpPacket()) {
        optionals.set(2);
      }
      if (struct.isSetTimespan()) {
        optionals.set(3);
      }
      if (struct.isSetType()) {
        optionals.set(4);
      }
      if (struct.isSetHash()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetIpPacket()) {
        struct.ipPacket.write(oprot);
      }
      if (struct.isSetTcpPacket()) {
        struct.tcpPacket.write(oprot);
      }
      if (struct.isSetUdpPacket()) {
        struct.udpPacket.write(oprot);
      }
      if (struct.isSetTimespan()) {
        oprot.writeString(struct.timespan);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetHash()) {
        oprot.writeString(struct.hash);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Packet struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.ipPacket = new IPpacket();
        struct.ipPacket.read(iprot);
        struct.setIpPacketIsSet(true);
      }
      if (incoming.get(1)) {
        struct.tcpPacket = new TCPpacket();
        struct.tcpPacket.read(iprot);
        struct.setTcpPacketIsSet(true);
      }
      if (incoming.get(2)) {
        struct.udpPacket = new UDPpacket();
        struct.udpPacket.read(iprot);
        struct.setUdpPacketIsSet(true);
      }
      if (incoming.get(3)) {
        struct.timespan = iprot.readString();
        struct.setTimespanIsSet(true);
      }
      if (incoming.get(4)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.hash = iprot.readString();
        struct.setHashIsSet(true);
      }
    }
  }

}

