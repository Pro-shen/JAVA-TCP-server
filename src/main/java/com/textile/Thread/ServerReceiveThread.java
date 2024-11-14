package com.textile.Thread;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerReceiveThread implements Runnable {

    private static Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);

    private Socket socket;

    public ServerReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //输入流接收数据
            InputStream inputStream = socket.getInputStream();
            //输出流发送数据
            OutputStream outputStream = socket.getOutputStream();
            while (true) {
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    if (new String(buffer).substring(0, 4).equals("NOOP")) {
                        log.warn("**********客户端发送心跳包**********");
                    }
                }
                buffer = new byte[350];
            }

        } catch (Exception e) {

        }
    }

}
