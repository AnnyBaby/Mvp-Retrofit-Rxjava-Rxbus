package com.frame.huxh.mvpdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class StreamUtils {
	public static String stream2String(InputStream is) {
        ByteArrayOutputStream baos = null;
        try {
            int i = -1;
            baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            return baos.toString();
        } catch (Exception e) {
            return null;
        }finally{
            closeStream(baos);
        }
    }

    public static byte[] stream2ByteArray(InputStream is) {
    	try {
    		ByteBuffer buffer = ByteBuffer.allocate(is.available());
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = is.read(buff, 0, 1024)) != -1) {
            	buffer.put(buff, 0, rc);
            }
            return buffer.array();
        } catch (Exception e) {
            return null;
        }
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
