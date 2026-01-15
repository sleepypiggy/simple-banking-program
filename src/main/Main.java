package main;

import ui.BankUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        loadGlobalFont();
        new BankUI();
    }

    private static void loadGlobalFont() {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/Stardew_Valley.ttf"));
            Font uiFont = baseFont.deriveFont(Font.PLAIN, 30f);

            Object[] keys = UIManager.getLookAndFeelDefaults().keySet().toArray();
            for (Object key : keys) {
                Object value = UIManager.get(key);
                if (value instanceof Font) {
                    UIManager.put(key, uiFont);
                }
            }
            Font buttonFont = uiFont.deriveFont(Font.PLAIN, 20f);
            UIManager.put("Button.font", buttonFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}