/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anne
 */
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.io.IOException;
import java.awt.FontFormatException;

public class CustomFontLoader {

    public static Font loadFont(String fontFileName, float fontSize) {
        try {
            // Load font as resource stream from the fonts folder
            InputStream is = CustomFontLoader.class.getResourceAsStream("/fonts/" + fontFileName);
            if (is == null) {
                System.err.println("Font file not found in resources/fonts folder.");
                return null;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);

            // Register the font with the graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Font myFont = loadFont("YourFontFile.ttf", 16f);
        if (myFont != null) {
            System.out.println("Font loaded successfully!");
            // You can now set this font to your UI components, e.g.:
            // someComponent.setFont(myFont);
        }
    }
}
