package org.example;

import javax.swing.*;

public class HelperMethods {

    static JTextPane area = null;
    static int currLine = 0;

    public static void setField(JTextPane area){
        HelperMethods.area = area;
    }

    private static void fieldPrintln(String s){
        area.setText(area.getText() + currLine + " - " + s + "\n");
    }

    public static void log(String s){
        currLine++;
        System.out.println(s);
        if(area != null)
            fieldPrintln(s);
    }
}
