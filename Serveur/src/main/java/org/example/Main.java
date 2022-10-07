package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;

public class Main extends JFrame {
    static int PORT = 3000;

    public Main(){
        super("Chat Server");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640,480);
        this.setLocationRelativeTo(null);
        JPanel panel = (JPanel) this.getContentPane();

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout());
        panel.add(menu,BorderLayout.NORTH);

        menu.add(new JLabel("Port"),BorderLayout.NORTH);

        //Port Field
        JTextField portField = new JTextField(Integer.toString(PORT));
        portField.setColumns(20);
        portField.addActionListener(e -> PORT = Integer.parseInt(portField.getText()));
        menu.add(portField);

        //Start Button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> new Thread(()->startServer()).start());
        menu.add(startButton);

        JTextPane serverLogs = new JTextPane();
        panel.add(serverLogs,BorderLayout.CENTER);

        HelperMethods.setField(serverLogs);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        //Apply look and feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        Main main = new Main();
        main.setVisible(true);


    }

    void startServer(){
        try {
            HelperMethods.log("Starting server");
            ServerSocket server = new ServerSocket(PORT);
            HelperMethods.log("Server started on port : " + PORT);
            while(true){
                ServerThread client = new ServerThread(server.accept());
                client.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}