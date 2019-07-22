buffer.order(ByteOrder.BIG_ENDIAN);

// for Little-Endian machine  -->  reverseBytes and putInt again
        for (int i = 0; i < n; i++) {
            buffer.putInt(i * 4, reverseBytes(buffer.getInt(i * 4)));
        }


for (int i = 0; i < 10; i++){
        // for Little-Endian machine  -->  getInt and reverseBytes again
            System.out.println(reverseBytes(buffer.getInt(i * 4)));
        }