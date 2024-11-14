package com.textile;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.textile.Thread.ServerHeartBeatThread;
import com.textile.Thread.ServerReceiveThread;
import com.textile.Thread.ServerSendInfoThread;
import com.textile.domain.TSocketClient;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    private static final ThreadPoolExecutor threadpool = new ThreadPoolExecutor(80, 100,
            30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static List<TSocketClient> tSocketClientList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket clientSocket = serverSocket.accept(); // 等待客户端连接
                clientSocket.setSoTimeout(8000);
                ServerSendInfoThread serverSendInfoThread = new ServerSendInfoThread(tSocketClientList);
                serverSendInfoThread.run();
                for (int i = 0; i < tSocketClientList.size(); i++) {
                    if (Objects.equals(tSocketClientList.get(i).getSocketClientIp(), clientSocket.getInetAddress().toString().substring(1))) {
                        tSocketClientList.remove(i);
                        break;
                    }
                }
                TSocketClient tSocketClient = new TSocketClient();
                tSocketClient.setSocket(clientSocket);
                tSocketClient.setCounter(0);
                tSocketClient.setSocketClientIp(clientSocket.getInetAddress().toString().substring(1));
                tSocketClientList.add(tSocketClient);
                log.warn("**********已经连上客户端************");
                threadpool.execute(new ServerReceiveThread(clientSocket));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}