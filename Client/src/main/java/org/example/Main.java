package org.example;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main extends JFrame {
    static String pseudo = "client";
    static int PORT = 3000;
    static ObjectOutputStream out = null;

    public Main(){
        super("Chat Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640,480);
        this.setLocationRelativeTo(null);
        JPanel panel = (JPanel) this.getContentPane();

        JPanel menu = new JPanel();
        menu.setLayout(new FlowLayout());
        panel.add(menu,BorderLayout.NORTH);


        //Port Field
        menu.add(new Label("port:"));
        JTextField portField = new JTextField(Integer.toString(PORT));
        portField.setColumns(10);
        portField.addActionListener(e -> PORT = Integer.parseInt(portField.getText()));
        menu.add(portField);


        //Pseudo field
        menu.add(new Label("pseudo:"));
        JTextField pseudoField = new JTextField(pseudo);
        pseudoField.setColumns(10);
        pseudoField.addActionListener(e -> pseudo = pseudoField.getText());
        menu.add(pseudoField);

        //Connect Button
        JButton connectButton = new JButton("Enter");
        connectButton.addActionListener(e -> new Thread(()->enterRoom()).start());
        menu.add(connectButton);

        //Message Board
        JTextPane messageBoard = new JTextPane();
        panel.add(messageBoard,BorderLayout.CENTER);
        HelperMethods.setField(messageBoard);

        //Message sending menu
        JPanel sendMenu = new JPanel();
        sendMenu.setLayout(new FlowLayout());
        panel.add(sendMenu,BorderLayout.SOUTH);
        //Message field
        JTextField messageField = new JTextField(20);
        sendMenu.add(messageField);

        //Send button
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            try {
                out.writeObject(pseudo + ":" + messageField.getText());
                messageField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        sendMenu.add(sendButton);

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        //Apply look and feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        Main main = new Main();
        main.setVisible(true);


    }

    public void enterRoom(){
        ClientThread t1 = new ClientThread(pseudo,PORT);
        t1.start();
    }

    public static void setOutputStream(ObjectOutputStream outputStream){
        out = outputStream;
    }
}