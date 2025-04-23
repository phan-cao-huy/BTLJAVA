package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class WinPanel extends JPanel {
    private Image backgroundImage;
    private Image youWinImage;

    public WinPanel(CardLayout cardLayout, JPanel mainPanel, GamePanel gp) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/title/mapTitle.png")).getImage();
            youWinImage = ImageIO.read(getClass().getResource("/title/Youwin.png")); //
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        // 🔹 Ảnh YOU WIN ở NORTH
        if (youWinImage != null) {
            Image scaled = youWinImage.getScaledInstance(400, 150, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
            label.setOpaque(false);
            add(label, BorderLayout.NORTH);
        }

        // 🔹 Panel chứa nút
        JPanel gridPanel = new JPanel(new GridLayout(3, 1, 0, 15));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(150, 250, 150, 250));

        JButton btnReplay = new JButton("Chơi lại");
        JButton btnLevel = new JButton("Cấp độ");
        JButton btnExit = new JButton("Thoát");

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        for (JButton btn : new JButton[]{btnReplay, btnLevel, btnExit}) {
            btn.setFont(buttonFont);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 255, 255, 180)); // Nền bán trong suốt
        }

        gridPanel.add(btnReplay);
        gridPanel.add(btnLevel);
        gridPanel.add(btnExit);
        add(gridPanel, BorderLayout.CENTER);

        // 🔹 Nút hành động
        btnReplay.addActionListener(e -> {
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
