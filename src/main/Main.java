package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("My first game");

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        GamePanel gamePanel = new GamePanel(cardLayout, mainPanel);
        WinPanel winPanel = new WinPanel(cardLayout,mainPanel,gamePanel);
        SettingsMenu setPAnel = new SettingsMenu(cardLayout, mainPanel,gamePanel);

        mainPanel.add(gamePanel, "game");
        mainPanel.add(winPanel, "win");
        mainPanel.add(setPAnel,"set");

        window.setContentPane(mainPanel);
        window.pack();
        window.setVisible(true);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        gamePanel.setupGame();
        gamePanel.startGameThread();


    }
}