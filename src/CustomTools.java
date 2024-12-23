import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomTools {
    // Bir JLabel ile görüntü oluştur
    public static JLabel loadImage(String resource) {
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Hata: " + e);
        }
        return null;
    }

    public static void updateImage(JLabel imageContainer, String resource) {
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            System.out.println("Hata: " + e);
        }
    }

    public static Font createFont(String resource) {
        // Yazı tipi dosyasının yolunu al
        String filePath = CustomTools.class.getClassLoader().getResource(resource).getPath();

        // Yoldaki boşluk karakterlerini kontrol et (hata)
        if (filePath.contains("%20")) {
            filePath = filePath.replaceAll("%20", " ");
        }

        // Yazı tipi oluştur
        try {
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile);
            return customFont;
        } catch (Exception e) {
            System.out.println("Hata: " + e);
        }
        return null;
    }

    public static String hideWords(String word) {
        String hiddenWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) == ' ')) {
                hiddenWord += "*";
            } else {
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }
}
