package main.java;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;

import static java.lang.System.exit;

/**
 * This first tutorial, demonstrating setting up a simple {@link Terminal} and performing some basic operations on it.
 * @author Martin
 */
public class TextView {
    final static String[] firstPlayer = {null};
    final static String[] secondPlayer = {null};
    public static Label XMoveLabel = new Label("It's X's move");
    public static Label OMoveLabel = new Label("It's O's move");
    public static Panel gamePanel =  new Panel();
    public static final Panel resultPanel = new Panel();
    public static final BasicWindow resultWin = new BasicWindow();
    public static final Label resultLabel = new Label("");
    public static BasicWindow gameWindow = new BasicWindow();
    public static Panel buttonPanel = new Panel();
    public static Terminal terminal;
    public static final Button[][] btnFields = new Button[3][3];
    public static Button exitBtn = new Button("Return", () -> {
        gameWindow.close();
        gamePanel.removeAllComponents();
        buttonPanel.removeAllComponents();
        for (int i  = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btnFields[i][j].setLabel("_");
                btnFields[i][j].setEnabled(true);
            }
        }
        buttonPanel.addComponent(btnFields[2][0]);
        buttonPanel.addComponent(btnFields[2][1]);
        buttonPanel.addComponent(btnFields[2][2]);
        buttonPanel.addComponent(btnFields[1][0]);
        buttonPanel.addComponent(btnFields[1][1]);
        buttonPanel.addComponent(btnFields[1][2]);
        buttonPanel.addComponent(btnFields[0][0]);
        buttonPanel.addComponent(btnFields[0][1]);
        buttonPanel.addComponent(btnFields[0][2]);

    });
    public static final Button resultExit = new Button("Exit", () -> {
                resultWin.close();
                gameWindow.close();
            });
    public static void textVersion() {
        if (firstPlayer[0] == null) {
            firstPlayer[0] = "User";
        }
        if (secondPlayer[0] == null) {
            secondPlayer[0] = "User";
        }
        TextView.gameWindow.setComponent(TextView.gamePanel);
        TextView.gameWindow.setHints(Collections.singletonList(Window.Hint.CENTERED));
        TextView.resultPanel.addComponent(TextView.resultExit);
        TextView.resultWin.setComponent(TextView.resultPanel);
        TextView.resultPanel.addComponent(0, TextView.resultLabel);
        TextView.gameWindow.setTitle(firstPlayer[0] + " vs. " + secondPlayer[0]);
        GridLayout gridLayout = new GridLayout(3);
        gridLayout.setHorizontalSpacing(3);
        TextView.buttonPanel.setLayoutManager(gridLayout);
        Button btn02 = new Button("_", () -> Controller.move(new Point(0, 2)));
        Button.FlatButtonRenderer flatButtonRenderer = new Button.FlatButtonRenderer();
        btn02.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn02);
        Button btn12 = new Button("_", () -> Controller.move(new Point(1, 2)));
        btn12.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn12);
        Button btn22 = new Button("_", () -> Controller.move(new Point(2, 2)));
        btn22.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn22);
        Button btn01 = new Button("_", () -> Controller.move(new Point(0, 1)));
        btn01.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn01);
        Button btn11 = new Button("_", () -> Controller.move(new Point(1, 1)));
        btn11.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn11);
        Button btn21 = new Button("_", () -> Controller.move(new Point(2, 1)));
        btn21.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn21);
        Button btn00 = new Button("_", () -> Controller.move(new Point(0, 0)));
        btn00.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn00);
        Button btn10 = new Button("_", () -> Controller.move(new Point(1, 0)));
        btn10.setRenderer(flatButtonRenderer);
        TextView.buttonPanel.addComponent(btn10);
        Button btn20 = new Button("_", () -> Controller.move(new Point(2, 0)));
        btn20.setRenderer(flatButtonRenderer);
        btnFields[0][0] = btn00;
        btnFields[1][0] = btn10;
        btnFields[2][0] = btn20;
        btnFields[0][1] = btn01;
        btnFields[1][1] = btn11;
        btnFields[2][1] = btn21;
        btnFields[0][2] = btn02;
        btnFields[1][2] = btn12;
        btnFields[2][2] = btn22;
        buttonPanel.addComponent(btn20);
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            Panel panel = new Panel();
            BasicWindow window = new BasicWindow("Tic Tac Toe");
            window.setHints(Collections.singletonList(Window.Hint.CENTERED));
            window.setComponent(panel);
            Label label = new Label(
                    "████████████████████████████████████████████████████████\n" +
                            "█─▄─▄─█▄─▄█─▄▄▄─███─▄─▄─██▀▄─██─▄▄▄─███─▄─▄─█─▄▄─█▄─▄▄─█\n" +
                            "███─████─██─███▀█████─████─▀─██─███▀█████─███─██─██─▄█▀█\n" +
                            "▀▀▄▄▄▀▀▄▄▄▀▄▄▄▄▄▀▀▀▀▄▄▄▀▀▄▄▀▄▄▀▄▄▄▄▄▀▀▀▀▄▄▄▀▀▄▄▄▄▀▄▄▄▄▄▀\nMenu:");
            panel.addComponent(label);

            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

            firstPlayer[0] = "user";
            secondPlayer[0] = "user";

            BasicWindow window1 = new BasicWindow();
            Button exitBtn = new Button("Exit", () -> {
                Controller.end[0] = true;
                window.close();
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exit(0);
            });
            Button graphicVersion = new Button("Go to graphic mode", () -> {
                window.close();
                Controller.graphic[0] = true;
                try {
                    terminal.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Button playBtn = new Button("Play (User vs. User)", () -> {
                gameWindow.setComponent(gamePanel);
                gamePanel.removeComponent(buttonPanel);
                gamePanel.removeComponent(TextView.exitBtn);
                TextView.gamePanel.addComponent(TextView.XMoveLabel);
                gamePanel.addComponent(buttonPanel);
                gamePanel.addComponent(TextView.exitBtn);
                Controller.play(gui, firstPlayer[0], secondPlayer[0]);
            });

            Button easyComputer = new Button("Easy computer", () -> {
                firstPlayer[0] = "easy";
                if(secondPlayer[0].equals("user")){
                    playBtn.setLabel("Play (Easy Computer vs. User)");
                } else {
                    playBtn.setLabel("Play (Easy Computer vs. " + secondPlayer[0].substring(0, 1).toUpperCase() + secondPlayer[0].substring(1) + " Computer)");
                }
                window1.close();
            });
            Button mediumComputer = new Button("Medium computer", () -> {
                firstPlayer[0] = "medium";
                if(secondPlayer[0].equals("user")){
                    playBtn.setLabel("Play (Medium Computer vs. User)");
                } else {
                    playBtn.setLabel("Play (Medium Computer vs. " + secondPlayer[0].substring(0, 1).toUpperCase() + secondPlayer[0].substring(1) + " Computer)");
                }
                window1.close();
            });
            Button hardComputer = new Button("Hard computer", () -> {
                firstPlayer[0] = "hard";
                if(secondPlayer[0].equals("user")){
                    playBtn.setLabel("Play (Hard Computer vs. User)");
                } else {
                    playBtn.setLabel("Play (Hard Computer vs. " + secondPlayer[0].substring(0, 1).toUpperCase() + secondPlayer[0].substring(1) + " Computer)");
                }
                window1.close();
            });
            Button user = new Button("User", () -> {
                firstPlayer[0] = "user";
                if(secondPlayer[0].equals("user")){
                    playBtn.setLabel("Play (User vs. User)");
                } else {
                    playBtn.setLabel("Play (User vs. " + secondPlayer[0].substring(0, 1).toUpperCase() + secondPlayer[0].substring(1) + " Computer)");
                }
                window1.close();
            });

            Button easyComputer2 = new Button("Easy computer", () -> {
                secondPlayer[0] = "easy";
                if(firstPlayer[0].equals("user")){
                    playBtn.setLabel("Play (User vs. Easy Computer)");
                } else {
                    playBtn.setLabel("Play (" + firstPlayer[0].substring(0, 1).toUpperCase() + firstPlayer[0].substring(1) + " Computer vs. Easy Computer)");
                }
                window1.close();
            });
            Button mediumComputer2 = new Button("Medium computer", () -> {
                secondPlayer[0] = "medium";
                if(firstPlayer[0].equals("user")){
                    playBtn.setLabel("Play (User vs. Medium Computer)");
                } else {
                    playBtn.setLabel("Play (" + firstPlayer[0].substring(0, 1).toUpperCase() + firstPlayer[0].substring(1) + " Computer vs. Medium Computer)");
                }
                window1.close();
            });
            Button hardComputer2 = new Button("Hard computer", () -> {
                secondPlayer[0] = "hard";
                if(firstPlayer[0].equals("user")){
                    playBtn.setLabel("Play (User vs. Hard Computer)");
                } else {
                    playBtn.setLabel("Play (" + firstPlayer[0].substring(0, 1).toUpperCase() + firstPlayer[0].substring(1) + " Computer vs. Hard Computer)");
                }
                window1.close();
            });
            Button user2 = new Button("User", () -> {
                secondPlayer[0] = "user";
                if(firstPlayer[0].equals("user")){
                    playBtn.setLabel("Play (User vs. User)");
                } else {
                    playBtn.setLabel("Play (" + firstPlayer[0].substring(0, 1).toUpperCase() + firstPlayer[0].substring(1) + " Computer vs. User)");
                }
                window1.close();
            });

            Button returnBtn = new Button("Return", window1::close);
            Button firstPlayerChoose = new Button("Choose first player", () -> {
                Panel panel1 = new Panel();
                window1.setTitle("Choose first player");
                window1.setComponent(panel1);
                panel1.removeAllComponents();
                panel1.addComponent(easyComputer);
                panel1.addComponent(mediumComputer);
                panel1.addComponent(hardComputer);
                panel1.addComponent(user);
                panel1.addComponent(returnBtn);
                gui.addWindow(window1);
            });
            Button secondPlayerChoose = new Button("Choose second player", () -> {
                Panel panel1 = new Panel();
                window1.setTitle("Choose second player");
                window1.setComponent(panel1);
                panel1.removeAllComponents();
                panel1.addComponent(easyComputer2);
                panel1.addComponent(mediumComputer2);
                panel1.addComponent(hardComputer2);
                panel1.addComponent(user2);
                panel1.addComponent(returnBtn);
                gui.addWindow(window1);
            });

            panel.addComponent(firstPlayerChoose);
            panel.addComponent(secondPlayerChoose);
            panel.addComponent(playBtn);
            panel.addComponent(graphicVersion);
            panel.addComponent(exitBtn);

            // Create gui and start gui
            gui.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


