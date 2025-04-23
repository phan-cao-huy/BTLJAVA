package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SettingsMenu extends JPanel  {
    private Image backgroundImage;
    public JButton btnResume,btnReplay,btnLevel,btnExit;
    public SettingsMenu(CardLayout cardLayout,JPanel mainPanel, GamePanel gp ) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/title/mapTitle.png")).getImage();
        } catch ( IllegalArgumentException e) {
            e.printStackTrace();
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl = new JLabel("SETTING", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        add(lbl, gbc);

        btnResume = new JButton("Tiếp tục");
        gbc.gridy = 1;
        add(btnResume, gbc);

        btnReplay = new JButton("Chơi lại");
        gbc.gridy = 2;
        add(btnReplay,gbc);

        btnLevel = new JButton("Cấp độ");
        gbc.gridy = 3;
        add(btnLevel, gbc);

        btnExit = new JButton("Thoát");
        gbc.gridy = 4;
        add(btnExit, gbc);



        btnResume.addActionListener(e -> {
            gp.gameState = gp.playState;
            gp.startTime = System.nanoTime() - gp.elapsedTimeInSeconds * 1_000_000_000L;
            gp.timerStarted = true;
            cardLayout.show(mainPanel,"game");
            gp.requestFocusInWindow();
        });
        btnReplay.addActionListener( e ->{
            gp.resetGame();
            cardLayout.show(mainPanel, "game");
            gp.requestFocusInWindow();
        });
        btnExit.addActionListener(e -> System.exit(0));
        btnLevel.addActionListener(e -> JOptionPane.showMessageDialog(mainPanel, "Tính năng đang phát triển..."));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
