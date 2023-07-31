package DataStructure;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Customized Data Structure based on ByteBuffer
 */
public class DynamicByteBuffer {
    private ByteBuffer buffer;
    private int capacity = 64;

    //    public DynamicByteBuffer() {
//        buffer = ByteBuffer.allocate(capacity);
//    }
    public DynamicByteBuffer() {
        buffer = ByteBuffer.allocate(capacity);
        //buffer.order(ByteOrder.LITTLE_ENDIAN);
    }


    public void put(byte b) {
        ensureCapacity(buffer.position() + 1);
        buffer.put(b);
    }

    public void put(int index, byte b) {
        ensureCapacity(buffer.position() + 1);
        ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity());
        newBuffer.put(buffer.array(), 0, index);  // 将插入位置之前的元素写入新的ByteBuffer
        newBuffer.put(b);  // 将新的元素写入新的ByteBuffer
        newBuffer.put(buffer.array(), index, buffer.position() - index);  // 将插入位置之后的元素写入新的ByteBuffer
        buffer = newBuffer;
    }

    public byte[] toArray() {
        return Arrays.copyOf(buffer.array(), buffer.position());
    }

    private void expand() {
        ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
        buffer.flip();
        newBuffer.put(buffer);
        buffer = newBuffer;
    }

    public void put(byte[] bytes) {
        ensureCapacity(buffer.position() + bytes.length);
        buffer.put(bytes);
    }

    public void put(int index, byte[] bytes) {
        ensureCapacity(buffer.position() + bytes.length);
        ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity());
        newBuffer.put(buffer.array(), 0, index);  // 将插入位置之前的元素写入新的ByteBuffer
        newBuffer.put(bytes);  // 将新的元素写入新的ByteBuffer
        newBuffer.put(buffer.array(), index, buffer.position() - index);  // 将插入位置之后的元素写入新的ByteBuffer
        buffer = newBuffer;
    }

    private void ensureCapacity(int capacity) {
        while (buffer.capacity() < capacity) {
            expand();
        }
    }
}

