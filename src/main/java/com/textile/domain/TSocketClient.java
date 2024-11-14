package com.textile.domain;

import java.net.Socket;

public class TSocketClient {

    private Socket socket;

    private String socketClientIp;

    private Integer counter;

    private Boolean fileSendTF = true;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getSocketClientIp() {
        return socketClientIp;
    }

    public void setSocketClientIp(String socketClientIp) {
        this.socketClientIp = socketClientIp;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Boolean getFileSendTF() {
        return fileSendTF;
    }

    public void setFileSendTF(Boolean fileSendTF) {
        this.fileSendTF = fileSendTF;
    }
}
