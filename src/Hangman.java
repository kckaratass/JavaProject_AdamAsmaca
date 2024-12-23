import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Hangman extends JFrame implements ActionListener {
    // yanlis tahmin edilen kelimeleri sayan sayac
    private int incorrectGuesses;

    // WordDB'den gelen kelimeyi burada sakliyoruz
    private String[] wordChallenge;

    private final WordDB wordDB;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;


    public Hangman(){
        super("Adam Asmaca Oyunu (Java Ed.)");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        // degiskenlerin baslangiclari
        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        createResultDialog();

        addGuiComponents();
    }

    private void addGuiComponents(){
        // adam asmaca resmi
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        // kategori gosterimi
        categoryLabel = new JLabel(wordChallenge[0]); // Burada kategori metni Turkceye cevredildi
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 28,
                CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );

        // gizli kelime
        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        // harf butonlari
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);

        // harf butonlarini olustur
        for(char c = 'A'; c <= 'Z'; c++){
            JButton button = new JButton(Character.toString(c));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setFont(customFont.deriveFont(22f));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            // ASCII degerleriyle mevcut index'i hesapla
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        // sifirlama butonu
        JButton resetButton = new JButton("Sifirla");
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // cikis butonu
        JButton quitButton = new JButton("Cik");
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Sifirla")){
            resetGame();

            if(command.equals("Sifirla")){
                resultDialog.setVisible(false);
            }
        }else if(command.equals("Cik")){
            dispose();
            return;
        }else{
            // harf butonlari

            // butonu devre disi birak
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            // kelime oyuncunun tahminini iceriyor mu kontrol et
            if(wordChallenge[1].contains(command)){
                // oyuncu dogru tahmin etti
                button.setBackground(Color.GREEN);

                // gizli kelimeyi bir char dizisine al, boylece gizli metni guncelle
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for(int i = 0; i < wordChallenge[1].length(); i++){
                    // _ yerine dogru harfi koy
                    if(wordChallenge[1].charAt(i) == command.charAt(0)){
                        hiddenWord[i] = command.charAt(0);
                    }
                }

                // gizli kelimeyi guncelle
                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                // oyuncu kelimeyi dogru tahmin etti
                if(!hiddenWordLabel.getText().contains("*")){
                    // basariyla sonuclar penceresini göster
                    resultLabel.setText("Dogru tahmin ettiniz!");
                    resultDialog.setVisible(true);
                }

            }else{
                // oyuncu yanlis harf secti
                button.setBackground(Color.RED);

                // yanlis tahmin sayacini artir
                ++incorrectGuesses;

                // adam asmaca resmini guncelle
                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");

                // oyuncu kelimeyi dogru tahmin edemedi
                if(incorrectGuesses >= 6){
                    // oyun bittigi penceresini goster
                    resultLabel.setText("Uzgunum, tekrar deneyin!");
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("Kelime: " + wordChallenge[1]);
        }

    }

    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.setTitle("Sonuc");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Sifirla");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }

    private void resetGame(){
        // yeni kelimeyi yukle
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;

        // baslangic resmini yukle
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

        // kategoriyi guncelle
        categoryLabel.setText(wordChallenge[0]);

        // gizli kelimeyi guncelle
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        // butun butonlari yeniden etkinlestir
        for(int i = 0; i < letterButtons.length; i++){
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Hangman().setVisible(true);
            }
        });
    }
}
