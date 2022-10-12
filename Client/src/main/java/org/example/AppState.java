package org.example;

import imgui.type.ImInt;
import imgui.type.ImString;
import org.example.View.GuiState;


public class AppState {
    private GuiState state = GuiState.Default;
    private ImString pseudo = new ImString("client");
    private ImInt port = new ImInt(3000);
    private ImString ip = new ImString("127.0.0.1");


    public void setState(GuiState state) {
        this.state = state;
    }

    public GuiState getState() {
        return state;
    }

    public ImString getPseudo() {
        return pseudo;
    }

    public synchronized void setPseudo(ImString pseudo) {
        this.pseudo = pseudo;
    }

    public ImInt getPort() {
        return port;
    }

    public synchronized void setPort(ImInt port) {
        this.port = port;
    }

    public ImString getIp() {
        return ip;
    }

    public synchronized void setIp(ImString ip) {
        this.ip = ip;
    }
}
