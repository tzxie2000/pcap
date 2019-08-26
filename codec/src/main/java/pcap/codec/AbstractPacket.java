/**
 * This code is licenced under the GPL version 2.
 */
package pcap.codec;

import pcap.common.memory.Memory;
import pcap.common.memory.MemoryAllocator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public abstract class AbstractPacket implements Packet {

    protected static final IllegalArgumentException ILLEGAL_HEADER_EXCEPTION
            = new IllegalArgumentException("Missing required header field(s).");

    protected Memory payloadBuffer;

    /**
     * Returns the {@link Memory} object representing this packet's payload.
     * @return returns empty buffer if a payload doesn't exits, {@link Memory} object otherwise.
     */
    public Memory getPayloadBuffer() {
        if (payloadBuffer == null) {
            payloadBuffer = Properties.BYTE_BUF_ALLOCATOR.allocate(0);
        }
        return payloadBuffer;
    }

    @Override
    public <T extends Packet> boolean contains(Class<T> clazz) {
        return !get(clazz).isEmpty();
    }

    @Override
    public <T extends Packet> List<T> get(Class<T> clazz) {
        List<Packet> packets = new ArrayList<Packet>();
        Iterator<Packet> iterator = this.iterator();
        while (iterator.hasNext()) {
            Packet packet = iterator.next();
            if (clazz.isInstance(packet)) {
                packets.add(packet);
            }
        }
        return (List<T>) packets;
    }

    @Override
    public PacketIterator iterator() {
        return new PacketIterator(this);
    }

    @Override
    public void forEach(Consumer<? super Packet> action) throws NullPointerException {
        PacketIterator iterator = iterator();
        while (iterator.hasNext()) {
            try {
                action.accept(iterator.next());
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public static abstract class Header implements Packet.Header {

        protected static final MemoryAllocator ALLOCATOR = Properties.BYTE_BUF_ALLOCATOR;

        protected Memory buffer;

        /**
         * Returns header as byte buffer.
         * @return return byte buffer.
         */
        public Memory getBuffer() {
            if (buffer == null) {
                buffer = ALLOCATOR.allocate(0);
            }
            return buffer;
        }

        public abstract Builder getBuilder();

    }

    /**
     * Packet builder.
     */
    public static abstract class Builder implements pcap.common.util.Builder<Packet, Memory>, Serializable {

        public void reset() {
            reset(-1, -1);
        }

        public void reset(int offset, int length) {
            throw new UnsupportedOperationException("Not implemented yet.");
        }

    }

    /**
     * Packet factory.
     */
    public static abstract class Factory implements pcap.common.util.Factory<Packet, Memory> {

    }

}
