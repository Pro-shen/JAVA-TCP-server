package com.textile.Thread;

import com.textile.Main;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerHeartBeatThread {


    private Timer timer;

    private int count;


    public ServerHeartBeatThread(int count) {
        this.count = count;
    }

    public void THeartbeatThreadStart() {
        timer = new Timer();
        timer.schedule(new ServerHeartBeatThreadTask(), 500, 10 * 1000);
    }

    public void setCountTo0(Socket socket) {
        for (int i = 0; i < Main.tSocketClientList.size(); i++) {
            if (socket == Main.tSocketClientList.get(i).getSocket()) {
                Main.tSocketClientList.get(i).setCounter(0);
            }
        }
    }

    class ServerHeartBeatThreadTask extends TimerTask {
        @Override
        public void run() {
            try {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < Main.tSocketClientList.size(); i++) {
                    Main.tSocketClientList.get(i).setCounter(Main.tSocketClientList.get(i).getCounter() + 1);
                    if (Main.tSocketClientList.get(i).getCounter() >= count) {
                        Main.tSocketClientList.get(i).getSocket().close();
                        list.add(i);
                    }
                }
                for (int i = list.size() - 1; i >= 0; i--) {
                    Main.tSocketClientList.get((int) list.get(i)).getSocket().close();
                    Main.tSocketClientList.remove((int) list.get(i));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
