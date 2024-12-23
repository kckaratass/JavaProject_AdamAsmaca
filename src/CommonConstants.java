import java.awt.*;

public class CommonConstants {
    // File paths
    public static final String DATA_PATH = "resources/data.txt";
    public static final String IMAGE_PATH = "resources/1.png";
    public static final String FONT_PATH = "resources/Cartoonero.ttf";

    // Color config (Modern and unique tones)
    public static final Color PRIMARY_COLOR = Color.decode("#2E3440"); // Koyu lacivert-gri (minimalist temel renk)
    public static final Color SECONDARY_COLOR = Color.decode("#88C0D0"); // Pastel mavi (modern bir vurgu rengi)
    public static final Color ACCENT_COLOR = Color.decode("#BF616A");   // Şarap kırmızısı (canlı bir vurgu rengi)
    public static final Color BACKGROUND_COLOR = Color.decode("#3B4252"); // Koyu gri-mavi (arka plan için ideal)
    public static final Color TEXT_COLOR = Color.decode("#E5E9F0"); // Buz beyazı (metin için net bir renk)

    // Size config
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int) (FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int) (FRAME_SIZE.width / 2), (int) (FRAME_SIZE.height / 6));
}
