package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class GraphicView extends JPanel{
    final static String[] firstPlayer = {null};
    final static String[] secondPlayer = {null};
    public static final Object lock = new Object();
    private static JLabel logoLabel;
    private static JPanel logoPanel;
    private static JPanel mainPanel = new JPanel();
    public static JLabel XMoveLabel = new JLabel("It's X's move");
    public static JLabel OMoveLabel = new JLabel("It's O's move");
    public static final int FRAME_SIZE = 25;
    public static final int TILE_SIZE = 164;
    public static List<Sign> signs = new ArrayList<>();
    public static ResultSign resultSign;
    public static GraphicView boardLabel = new GraphicView(signs);
    public static JPanel gamePanel = new JPanel();
    private static JPanel movePanel = new JPanel();
    public static JFrame frame;
    private static String[] firstPlayerString = {null};
    private static String[] secondPlayerString = {null};
    private static boolean[] first = {true};
    private static BufferedImage image;
    public static final JLabel resultLabel = new JLabel();
    public static MouseAdapter mouseListener;
    public static boolean endOfGame = false;
    private static Font labelFont;
    private static Font buttonFont;
    private static Font playerFont;
    static class GThread extends Thread {
        @Override
        public void run() {
            Controller.play(null, firstPlayer[0], secondPlayer[0]);
        }
    }
    public static GThread gThread;

    private GraphicView(List<Sign> s) { //TODO wyświetlanie wyniku gry, powrót z rozpoczętej gry, zmiana wyświetlania czyj ruch
        /*ImageIcon board= new ImageIcon("src/main/resources/img/board.png");
        JLabel boardl = new JLabel(board);
        boardLabel.add(boardl);*/
        //InputStream fileName = GraphicView.class.getResourceAsStream("/main/resources/img/board.png");
        // return ImageIO.read(fileName);
        try {
            image = ImageIO.read(GraphicView.class.getResource("/main/resources/img/board.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        signs = s;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(image,0,0,this);
        List<Sign> signs = GraphicView.signs;
        for (Sign s : signs) {
            s.draw(g2d);
        }
        if (resultSign != null) {
            resultSign.draw(g2d);
        }
    }

    public GraphicView() {
        endOfGame = false;
        if (firstPlayer[0] != null) {
            firstPlayerString[0] = firstPlayerString[0];
        } else {
            firstPlayer[0] = "User";
            firstPlayerString[0] = "User";
        }
        if (secondPlayer[0] != null) {
            secondPlayerString[0] = secondPlayerString[0];
        } else {
            secondPlayer[0] = "User";
            secondPlayerString[0] = "User";
        }
        ImageIcon logo = new ImageIcon("src/main/resources/img/logo.png");
        logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        logoPanel = new JPanel(new GridLayout());
        logoPanel.add(logoLabel);
        gamePanel.add(XMoveLabel);
        labelFont = XMoveLabel.getFont();
        labelFont = new Font(labelFont.getName(), Font.PLAIN, 20);
        playerFont = new Font(labelFont.getName(), Font.PLAIN, 30);
        XMoveLabel.setFont(labelFont);
        OMoveLabel.setFont(labelFont);
        resultLabel.setFont(labelFont);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        boardLabel.setSize(image.getWidth(null), image.getHeight(null));
        gamePanel.add(boardLabel);
        JButton returnBt = new JButton("Return");
        returnBt.addActionListener((actionEvent) -> {
            Controller.setEnd(true);
            gThread.stop();
            frame.remove(gamePanel);
            frame.add(mainPanel);
            frame.revalidate();
            frame.repaint();
            signs.clear();
            resultLabel.setText("");
            resultSign = null;
        });
        buttonFont = returnBt.getFont();
        buttonFont = new Font(buttonFont.getName(), Font.PLAIN, 20);
        returnBt.setFont(buttonFont);
        gamePanel.add(resultLabel);
        //JPanel returnPanel = new JPanel();
        //returnPanel.add(returnBt);
        //gamePanel.add(returnPanel);
        gamePanel.add(returnBt);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new java.awt.GridLayout(6, 1, 10, 10));
        JButton playBtn = new JButton("Play (" + firstPlayerString[0] + " vs. " + secondPlayerString[0] + ")");
        playBtn.setFont(buttonFont);
        playBtn.addActionListener((actionEvent) -> {
            gamePanel.remove(0);
            gamePanel.add(XMoveLabel, 0);
            endOfGame = false;
            frame.remove(mainPanel);
            boardLabel.addMouseListener(mouseListener);
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gThread = new GThread();
            gThread.start();
        });
        JButton firstPlayerChoose = new JButton("Choose first player");
        firstPlayerChoose.setFont(buttonFont);
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new java.awt.GridLayout(6, 1, 10, 10));
        JButton easyComputer = new JButton("Easy computer");
        easyComputer.addActionListener((actionEvent) -> {
            if (first[0]) {
                firstPlayer[0] = "easy";
                firstPlayerString[0] = "Easy computer";
            } else {
                secondPlayer[0] = "easy";
                secondPlayerString[0] = "Easy computer";
            }

            mainPanel.remove(playerPanel);
            playerPanel.remove(0);
            mainPanel.add(buttonPanel);
            playBtn.setText("Play (" + firstPlayerString[0] + " vs. " + secondPlayerString[0] + ")");
            frame.revalidate();
            frame.repaint();
        });
        easyComputer.setFont(buttonFont);
        JButton mediumComputer = new JButton("Medium computer");
        mediumComputer.addActionListener((actionEvent) -> {
            if (first[0]) {
                firstPlayer[0] = "medium";
                firstPlayerString[0] = "Medium computer";
            } else {
                secondPlayer[0] = "medium";
                secondPlayerString[0] = "Medium computer";
            }

            mainPanel.remove(playerPanel);
            playerPanel.remove(0);
            mainPanel.add(buttonPanel);
            playBtn.setText("Play (" + firstPlayerString[0] + " vs. " + secondPlayerString[0] + ")");
            frame.revalidate();
            frame.repaint();
        });
        mediumComputer.setFont(buttonFont);
        JButton hardComputer = new JButton("Hard computer");
        hardComputer.addActionListener((actionEvent) -> {
            if (first[0]) {
                firstPlayer[0] = "hard";
                firstPlayerString[0] = "Hard computer";
            } else {
                secondPlayer[0] = "hard";
                secondPlayerString[0] = "Hard computer";
            }

            mainPanel.remove(playerPanel);
            playerPanel.remove(0);
            mainPanel.add(buttonPanel);
            playBtn.setText("Play (" + firstPlayerString[0] + " vs. " + secondPlayerString[0] + ")");
            frame.revalidate();
            frame.repaint();
        });
        hardComputer.setFont(buttonFont);
        JButton user = new JButton("User");
        user.addActionListener((actionEvent) -> {
            if (first[0]) {
                firstPlayer[0] = "User";
                firstPlayerString[0] = "User";
            } else {
                secondPlayer[0] = "User";
                secondPlayerString[0] = "User";
            }
            mainPanel.remove(playerPanel);
            playerPanel.remove(0);
            mainPanel.add(buttonPanel);
            playBtn.setText("Play (" + firstPlayerString[0] + " vs. " + secondPlayerString[0] + ")");
            frame.revalidate();
            frame.repaint();
        });
        user.setFont(buttonFont);
        JButton returnBtn = new JButton("Return");
        returnBtn.addActionListener((actionEvent) -> {
            mainPanel.remove(playerPanel);
            playerPanel.remove(0);
            mainPanel.add(buttonPanel);
            frame.repaint();
        });
        returnBtn.setFont(buttonFont);
        playerPanel.add(easyComputer);
        playerPanel.add(mediumComputer);
        playerPanel.add(hardComputer);
        playerPanel.add(user);
        playerPanel.add(returnBtn);
        firstPlayerChoose.addActionListener((actionEvent) -> {
            first[0] = true;
            JLabel firstLabel = new JLabel("Choose first player: ");
            firstLabel.setFont(labelFont);
            playerPanel.add(firstLabel, 0);
            mainPanel.remove(buttonPanel);
            mainPanel.add(playerPanel);
            frame.revalidate();
            frame.repaint();
        });
        firstPlayerChoose.setFont(buttonFont);
        JButton secondPlayerChoose = new JButton("Choose second player");
        secondPlayerChoose.addActionListener((actionEvent) -> {
            first[0] = false;
            JLabel secondLabel = new JLabel("Choose second player: ");
            secondLabel.setFont(labelFont);
            playerPanel.add(secondLabel, 0);
            mainPanel.remove(buttonPanel);
            mainPanel.add(playerPanel);
            frame.revalidate();
            frame.repaint();
        });
        secondPlayerChoose.setFont(buttonFont);
        JButton textVersion = new JButton("Switch to text mode");
        textVersion.setFont(buttonFont);
        textVersion.addActionListener((actionEvent) -> {
            frame.setVisible(false);
            Controller.graphic[0] = false;
            synchronized (lock) {
                lock.notify();
            }
        });
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(logoPanel);
        JLabel menuLabel = new JLabel("  Menu:");
        menuLabel.setFont(labelFont);
        buttonPanel.add(menuLabel);
        buttonPanel.add(firstPlayerChoose);
        buttonPanel.add(secondPlayerChoose);
        buttonPanel.add(playBtn);
        buttonPanel.add(textVersion);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(false);
                exit(0);
            }
        });
        exitButton.setFont(buttonFont);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel);
    }

    public void graphicVersion(){
        if (firstPlayer[0] != null) {
            firstPlayerString[0] = firstPlayer[0];
        } else {
            firstPlayerString[0] = "User";
        }
        if (secondPlayer[0] != null) {
            secondPlayerString[0] = secondPlayer[0];
        } else {
            secondPlayerString[0] = "User";
        }
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                exit(0);
            }
        });
        frame.add(mainPanel);
        frame.setSize(image.getWidth(null)+15, image.getHeight(null)+125);
        //frame.setSize(500, 450);
        frame.setVisible(true);
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!endOfGame) {
                    int x = 0;
                    int y = 0;
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    if (mouseX < FRAME_SIZE || mouseX > FRAME_SIZE + TILE_SIZE && mouseX < FRAME_SIZE * 2 + TILE_SIZE || mouseX > FRAME_SIZE * 2 + TILE_SIZE * 2 && mouseX < FRAME_SIZE * 3 + TILE_SIZE * 2 || mouseX > FRAME_SIZE * 3 + TILE_SIZE * 3) {
                        return;
                    }
                    if (mouseY < FRAME_SIZE || mouseY > FRAME_SIZE + TILE_SIZE && mouseY < FRAME_SIZE * 2 + TILE_SIZE || mouseY > FRAME_SIZE * 2 + TILE_SIZE * 2 && mouseY < FRAME_SIZE * 3 + TILE_SIZE * 2 || mouseY > FRAME_SIZE * 3 + TILE_SIZE * 3) {
                        return;
                    }
                    if (mouseX > FRAME_SIZE * 2 + TILE_SIZE && mouseX < 2 * (FRAME_SIZE + TILE_SIZE)) {
                        mouseX -= FRAME_SIZE - 3;
                    } else if (mouseX > FRAME_SIZE * 3 + TILE_SIZE * 2 && mouseX < 3 * (FRAME_SIZE + TILE_SIZE)) {
                        mouseX -= 2 * FRAME_SIZE - 6;
                    }
                    if (mouseY > FRAME_SIZE * 2 + TILE_SIZE && mouseY < 2 * (FRAME_SIZE + TILE_SIZE)) {
                        mouseY -= FRAME_SIZE - 3;
                    } else if (mouseY > FRAME_SIZE * 3 + TILE_SIZE * 2 && mouseY < 3 * (FRAME_SIZE + TILE_SIZE)) {
                        mouseY -= 2 * FRAME_SIZE - 6;
                    }

                    x += (mouseX - FRAME_SIZE - 15) / TILE_SIZE; //TODO prawie zrobione ale poprawić klikanie!
                    y += (mouseY - FRAME_SIZE - 15) / TILE_SIZE;
                    //boardLabel.removeMouseListener(mouseListener);
                    Controller.move(new Point(x, y));
                    //boardLabel.addMouseListener(mouseListener);
                }
            }
        };
        synchronized(lock) {
            while (frame.isVisible())
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        try {
            gThread.join();
        } catch (Exception e) {

        }
    }

    public void paintSign(FIELD_STATE sign, Point coordinates) {
        Sign s = new Sign(sign, coordinates);
        signs.add(s);
        frame.revalidate();
        boardLabel.revalidate();
        boardLabel.repaint();
        frame.repaint();
        Controller.updateCurrentMove(!(sign.equals(FIELD_STATE.X)));
    }
}
