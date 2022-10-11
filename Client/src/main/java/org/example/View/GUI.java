package org.example.View;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.example.AppState;
import org.example.Main;
import org.example.Message;

import java.io.IOException;
import java.util.List;

public class GUI extends Application {

    private ImInt port;
    private ImString host;
    private ImString pseudo;
    private Configuration config;
    private AppState appState;
    private List<Message> messages;
    private List<String> clientsList;
    private ImString currentMessage = new ImString();

    public GUI(ImInt port, ImString host, ImString pseudo, List<Message> messages,List<String> clientsList,AppState appState){
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        this.port = port;
        this.host = host;
        this.pseudo = pseudo;
        this.messages = messages;
        this.clientsList = clientsList;
        this.appState = appState;
        setupStyle();
    }

    @Override
    protected void configure(Configuration config){
        this.config = config;
        this.config.setTitle("Client");
        this.config.setHeight(600);
        this.config.setWidth(800);
    }

    @Override
    public void process() {
        setupDockSpace();
        if(appState.getState() != GuiState.Started){
            ImGui.begin("Join chat");
                if(appState.getState() == GuiState.Failed){
                    ImGui.textColored(1.0f,0.0f,0.0f,1.0f,"Server " + host + ":" + port + " closed");
                }
                ImGui.inputText("@IP",host);
                ImGui.inputInt("port",port);
                ImGui.inputText("pseudo",pseudo);
                if(ImGui.button("join")){
                    Main.enterRoom();
                }
            ImGui.end();
        }
        else if(appState.getState() == GuiState.Started){

            ImGui.begin("Chat");
                messages.forEach(m->message(m));
            ImGui.end();

            ImGui.begin("sender");
                ImGui.inputText("message",currentMessage);
                if(ImGui.button("Send")){
                    try {
                        Main.sendMessage(new Message(0,pseudo.get(),currentMessage.get()));
                        currentMessage.clear();
                    } catch (IOException e) {
                    }
                }
            ImGui.end();
            displayClientsList();
        }
        ImGui.end();
    }

    private void setupDockSpace(){
        int windowFlags = ImGuiWindowFlags.NoDocking;
        ImGui.setNextWindowPos(0.0f,0.0f, ImGuiCond.Always);
        ImGui.setNextWindowSize(config.getWidth(),config.getHeight());
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding,0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize,0.0f);
        windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
                ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
                ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin("DockSpace demo",new ImBoolean(true),windowFlags);
        ImGui.popStyleVar(2);
        //Dockspace
        ImGui.dockSpace(ImGui.getID("Dockspace"));
    }

    private void setupStyle(){
        ImGuiStyle style = ImGui.getStyle();

        //Shapes
        style.setWindowPadding(15.0f,15.0f);
        style.setWindowRounding(5.0f);
        style.setFramePadding(5.0f,5.0f);
        style.setFrameRounding(4.0f);
        style.setItemSpacing(12.0f,8.0f);
        style.setItemInnerSpacing(8.0f,6.0f);
        style.setIndentSpacing(25.0f);
        style.setScrollbarSize(15.0f);
        style.setScrollbarRounding(9.0f);
        style.setGrabMinSize(5.0f);
        style.setGrabRounding(3.0f);

        //Colors
        style.setColor(ImGuiCol.Text,0.80f, 0.80f, 0.83f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled,0.24f, 0.23f, 0.29f, 1.00f);
        style.setColor(ImGuiCol.WindowBg,0.06f, 0.05f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.ChildBg,0.07f, 0.07f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.PopupBg,0.07f, 0.07f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.Border,0.80f, 0.80f, 0.83f, 0.88f);
        style.setColor(ImGuiCol.BorderShadow,0.92f, 0.91f, 0.88f, 0.00f);
        style.setColor(ImGuiCol.FrameBg,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.FrameBgHovered,0.24f, 0.23f, 0.29f, 1.00f);
        style.setColor(ImGuiCol.FrameBgActive,0.56f, 0.56f, 0.58f, 1.00f);
        style.setColor(ImGuiCol.TitleBg,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed,1.00f, 0.98f, 0.95f, 0.75f);
        style.setColor(ImGuiCol.TitleBgActive,0.07f, 0.07f, 0.09f, 1.00f);
        style.setColor(ImGuiCol.MenuBarBg,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarBg,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrab,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabActive,0.06f, 0.05f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.CheckMark,0.80f, 0.80f, 0.83f, 0.31f);
        style.setColor(ImGuiCol.SliderGrab,0.80f, 0.80f, 0.83f, 0.31f);
        style.setColor(ImGuiCol.SliderGrabActive,0.06f, 0.05f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.Button,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ButtonHovered,0.24f, 0.23f, 0.29f, 1.00f);
        style.setColor(ImGuiCol.ButtonActive,0.56f, 0.56f, 0.58f, 1.00f);
        style.setColor(ImGuiCol.Header,0.10f, 0.09f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered,0.56f, 0.56f, 0.58f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive,0.06f, 0.05f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.ResizeGrip,0.00f, 0.00f, 0.00f, 0.00f);
        style.setColor(ImGuiCol.ResizeGripHovered,0.56f, 0.56f, 0.58f, 1.00f);
        style.setColor(ImGuiCol.ResizeGripActive,0.06f, 0.05f, 0.07f, 1.00f);
        style.setColor(ImGuiCol.PlotLines,0.40f, 0.39f, 0.38f, 0.63f);
        style.setColor(ImGuiCol.PlotLinesHovered,0.25f, 1.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram,0.40f, 0.39f, 0.38f, 0.63f);
        style.setColor(ImGuiCol.PlotHistogramHovered,0.25f, 1.00f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg,0.25f, 1.00f, 0.00f, 0.43f);
        style.setColor(ImGuiCol.ModalWindowDimBg,1.00f, 0.98f, 0.95f, 0.73f);
    }

    private void message(Message message){
        ImGui.text(message.getPseudo() + " : " + message.getContent());
    }

    private void displayClientsList(){
        ImGui.begin("Chat members");
            clientsList.forEach(name -> {
                ImGui.text(name);
            });
        ImGui.end();
    }
    public void start(){
        launch(this);
    }

}
