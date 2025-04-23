package main;

import entity.player;
import object.SuperObject;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    final public int tileSize = originalTileSize * scale;
    final public int maxScreenRow = 12;
    final public int maxScreenCol = 16;
    final public int screenWidth = tileSize * maxScreenCol; // 768pixel
    final public int screenHeight = tileSize * maxScreenRow; // 576pixel
    public int cameraX = 0;
    public int cameraY = 0;
    public long startTime;         // thời điểm bắt đầu game (tính bằng nano giây)
    int elapsedTimeInSeconds; // thời gian đã trôi qua (giây)
    public boolean timerStarted = false; // để bắt đầu đếm khi game bắt đầu


    public int gameState = 0;
    final public int titleState = 0;// trang thai menu
    final public int playState = 1; // trang thai game
    final public int pauseState = 2;
    final public int winState = 3;
    public CardLayout cardLayout;
    public JPanel mainPanel;
    public BufferedImage background,WinGame;

    KeyHandle keyH = new KeyHandle();
    public CollisionChecker Checker = new CollisionChecker(this);
    public player player = new player(this, keyH);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    TileManager tileM = new TileManager(this);
    public SuperObject obj[] = new SuperObject[11]; // Can have up to 11 objects
    public final int maxWorldCol = 70;
    public final int maxWorldRow = 70;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        gameState = titleState;
        ReadBackground("/title/mapTitle.png");
    }
    public GamePanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);


        gameState = titleState;
        ReadBackground("/title/mapTitle.png");
    }
    void ReadBackground(String x){
        try{
            background = ImageIO.read(getClass().getResource(x));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void setupGame(){
    aSetter.setObject();
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void resetGame(){
        gameState = playState;
        elapsedTimeInSeconds = 0;
        startTime = System.nanoTime();
        timerStarted = true;
        // Đặt lại vị trí và trạng thái người chơi
        player.setDefaultValue();
        player.getPlayerImage();
        player.hasKey = 0;
        player.PickupAnnounce = "";
        player.AnnounceCounter = 0;


        // Đặt lại camera
        cameraX = 0;
        cameraY = 0;

        // Đặt lại object trong game
        aSetter.setObject();

        // Lấy lại quyền điều khiển
        this.requestFocusInWindow();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (gameState == titleState) {
            if (keyH.enterPressed) {
                gameState = playState;
                keyH.enterPressed = false;
                startTime = System.nanoTime();
                timerStarted = true;
            }
        }
        else if (gameState == playState) {
            if (keyH.escPressed) {
                gameState = pauseState;
                cardLayout.show(mainPanel,"set");
                timerStarted = false;
                keyH.escPressed = false;
                return;  // bỏ qua update tiếp
            }
            player.update();
            if(timerStarted){
                long currentTime = System.nanoTime();
                elapsedTimeInSeconds = (int)((currentTime - startTime)/(1_000_000_000));
            }
        }
        else if(gameState == winState){
               cardLayout.show(mainPanel,"win");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(gameState == titleState){
            drawInterface(g2);
        }
        else if(gameState == playState || gameState == winState){

        tileM.draw(g2);
        drawTime(g2);
        // Draw objects
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, cameraX, cameraY, tileSize);
            }
        }

        player.draw(g2);

        }
        g2.dispose();

    }
    private void drawTime(Graphics2D g2){
        g2.setFont(new Font("Arial",Font.BOLD,24));
        g2.setColor(Color.WHITE);
        int minute = elapsedTimeInSeconds/60;
        int second = elapsedTimeInSeconds%60;
        String text = "Time: "+minute+": "+second+"s";
        g2.drawString(text,20,30);
    }

    private void drawInterface(Graphics2D g2) {
       g2.drawImage(background,0,0,screenWidth,screenHeight,null);

        g2.setFont(new Font("SansSerif",Font.BOLD,48));
        g2.setColor(Color.WHITE);
        String text = "My first Game";
        int x = getXCentered(text,g2);
        int y = tileSize*3;
        g2.drawString(text,x,y);

        g2.setFont(new Font("Arial",Font.PLAIN,28));
        text = "Press ENTER to start";
        x = getXCentered(text,g2);
        y += 100;
        g2.drawString(text,x,y);

    }

    public int getXCentered(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (screenWidth - length) / 2;
    }



}