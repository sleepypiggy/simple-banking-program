import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        loadGlobalFont();
        new BankUI();
        ArrayList<BankAccount> accountList = new ArrayList<BankAccount>();
        BankAccount currentAccount = null;
    }

    private static void loadGlobalFont() {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Stardew_Valley.ttf"));
            Font uiFont = baseFont.deriveFont(Font.PLAIN, 30f);

            Object[] keys = UIManager.getLookAndFeelDefaults().keySet().toArray();
            for (Object key : keys) {
                Object value = UIManager.get(key);
                if (value instanceof Font) {
                    UIManager.put(key, uiFont);
                }
            }
            Font buttonFont = uiFont.deriveFont(Font.PLAIN, 20f); // Smaller size for buttons
            UIManager.put("Button.font", buttonFont);  // Set this specifically for buttons
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}