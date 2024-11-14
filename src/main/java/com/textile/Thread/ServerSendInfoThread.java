package com.textile.Thread;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.textile.Main;
import com.textile.domain.TSocketClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class ServerSendInfoThread implements Runnable {

    private static Logger log = LoggerFactory.getLogger(ServerSendInfoThread.class);


    private List<TSocketClient> tSocketClientList;


    public ServerSendInfoThread(List<TSocketClient> tSocketClientList) {
        this.tSocketClientList = tSocketClientList;
    }


    @Override
    public void run() {
        serverSendClientThreadTimer();
    }

    public void serverSendClientThreadTimer() {
        Timer timer = new Timer();
        timer.schedule(new ServerSendClientThreadTask(), 500, 100);
    }


    class ServerSendClientThreadTask extends TimerTask {
        @Override
        public void run() {
            try {
                for (int i = 0; i < tSocketClientList.size(); i++) {
                    if (tSocketClientList.get(i).getSocket() != null) {
                        OutputStream outputStream = tSocketClientList.get(i).getSocket().getOutputStream();
                        byte[] bytes = new byte[4];
                        Random random = new Random();
                        int inti = random.nextInt();
                        bytes = toLH4(inti);
                        outputStream.write(bytes);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * int转byte字节数组
     *
     * @param n
     * @return
     */
    public static byte[] toLH4(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

}
