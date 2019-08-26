/**
 * This code is licenced under the GPL version 2.
 */
package pcap.api.internal;

import java.foreign.annotations.NativeCallback;
import java.foreign.memory.Pointer;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
@NativeCallback("(u64:u8u64:${pcap_pkthdr}u64:u8)v")
@FunctionalInterface
public interface PcapHandler {

    void gotPacket(Pointer<Byte> user, Pointer<PcapPktHdr> header, Pointer<Byte> packets) throws IllegalAccessException;

}
