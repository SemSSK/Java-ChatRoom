package org.example.View;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.ImGuiViewport;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.example.AppState;
import org.example.ConnexionData;
import org.example.Main;
import org.example.Message;

import java.io.IOException;

public class GUI extends Application {

    private AppState appState;
    private ConnexionData clientInfo;
    private ImString currentMessage = new ImString();
    private ImGuiIO io;

    public GUI(AppState appState, ConnexionData clientInfo){
        ImGui.createContext();
        io = ImGui.getIO();
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        this.clientInfo = clientInfo;
        this.appState = appState;
        setupStyle();
    }


    @Override
    protected void configure(Configuration config){
        config.setTitle("Client");
        config.setHeight(600);
        config.setWidth(800);
    }

    @Override
    public void process() {
        setupDockSpace();
        if(appState.getState() != GuiState.Started){
            ImGui.begin("Join chat");
                if(appState.getState() == GuiState.Failed){
                    ImGui.textColored(1.0f,0.0f,0.0f,1.0f,
                            "Server " + appState.getIp() + ":" + appState.getPort() + " closed");
                }
                ImGui.inputText("@IP",appState.getIp());
                ImGui.inputInt("port",appState.getPort());
                ImGui.inputText("pseudo",appState.getPseudo());
                ImGui.setCursorPosX(ImGui.getWindowWidth() * 0.9f);
                if(ImGui.button("join")){
                    Main.enterRoom();
                }
            ImGui.end();
        }
        else if(appState.getState() == GuiState.Started){

            ImGui.begin("Chat");
                clientInfo.getMessages().forEach(m->message(m));
            ImGui.end();

            ImGui.begin("sender");
                ImGui.inputText(" ",currentMessage);
                ImGui.sameLine();
                if( ImGui.button("Send")) {
                    try {
                        clientInfo.sendMessage(new Message(0, appState.getPseudo().get(), currentMessage.get()));
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
        ImGuiViewport viewport = ImGui.getMainViewport();
        int windowFlags = ImGuiWindowFlags.NoDocking;
        ImGui.setNextWindowPos(0.0f,0.0f, ImGuiCond.Always);
        ImGui.setNextWindowSize(viewport.getSizeX(),viewport.getSizeY());
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
            clientInfo.getChatMembers().forEach(name -> {
                ImGui.text(name);
            });
        ImGui.end();
    }


    @Override
    public void dispose(){
        super.dispose();
        System.exit(0);
    }

}
