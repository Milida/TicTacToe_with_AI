package main.java;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;

public class Controller { //TODO zamykanie okienka w trybie tekstowym, żeby kończyło działanie programu!
    public final static boolean[] graphic = new boolean[1];
    public final static boolean[] end = new boolean[1];
    private static final String[] firstPlayer = {"User"};
    private static final String[] secondPlayer = {"User"};
    private static Game game;
    public static MultiWindowTextGUI gui;
    public static final String VERTICAL = "vertical";
    public static final String HORIZONTAL = "horizontal";
    public static final String BACKSLASH = "backslash";
    public static final String SLASH = "slash";
    public static final String DRAW = "draw";
    public static final String XWINS = "xWins";
    public static final String OWINS = "oWins";
    public static GraphicView graphicView = new GraphicView();
    private static Interactable interactable;

    public static void main(String[] args) {
        graphic[0] = true;
        end[0] = false;
        while (!end[0]){
            if(graphic[0]) {
                graphicView.graphicVersion();
            } else {
                TextView.textVersion();
            }
        }
    }

    public static void play(MultiWindowTextGUI g, String first, String second) {
        if (!graphic[0]) {
            gui = g;
            gui.addWindow(TextView.gameWindow);
        } else {
            if (!"User".equals(first) && !"User".equals(second)){
                GraphicView.boardLabel.removeMouseListener(GraphicView.mouseListener);
                GraphicView.endOfGame = true;
            }
        }
        firstPlayer[0] = first;
        secondPlayer[0] = second;
        game = new Game();
        enableButtons();
        game.play(firstPlayer[0], secondPlayer[0]); //start the game
        if (graphic[0]) {
            if (!"User".equals(first) && !"User".equals(second)){
                GraphicView.boardLabel.addMouseListener(GraphicView.mouseListener);
            }
        }
    }

    public static void setEnd(boolean end){
        game.end[0] = end;
    }

    public static void updateCurrentMove(boolean XMove) {
        if (!graphic[0]) {
            if (XMove) {
                TextView.gamePanel.removeComponent(TextView.OMoveLabel);
                TextView.gamePanel.addComponent(0, TextView.XMoveLabel);
            } else {
                TextView.gamePanel.removeComponent(TextView.XMoveLabel);
                TextView.gamePanel.addComponent(0, TextView.OMoveLabel);
            }
        } else {
            if (XMove) {
                GraphicView.gamePanel.remove(GraphicView.OMoveLabel);
                GraphicView.gamePanel.add(GraphicView.XMoveLabel, 0);
            } else {
                GraphicView.gamePanel.remove(GraphicView.XMoveLabel);
                GraphicView.gamePanel.add(GraphicView.OMoveLabel, 0);
            }
            GraphicView.frame.revalidate();
            GraphicView.frame.repaint();
        }
    }

    public static void disableButtons() {
        if (!graphic[0]) {
            interactable = TextView.gameWindow.getFocusedInteractable();
            for (Button[] buttons : TextView.btnFields) {
                for (Button button : buttons) {
                    button.setEnabled(false);
                }
            }
        } else {
            GraphicView.endOfGame = true;
        }
    }

    public static void enableButtons() {
        if (graphic[0]) {
            GraphicView.endOfGame = false;
        } else {
            for (Button[] buttons : TextView.btnFields) {
                for (Button button : buttons) {
                    button.setEnabled(true);
                }
            }
            TextView.gameWindow.setFocusedInteractable(interactable);
        }
    }

    public static void updateScreen() {
        if (!graphic[0]) {
            try {
                gui.updateScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            GraphicView.frame.revalidate();
            GraphicView.frame.repaint();
        }
    }

    public static void waitForEndOfGame() {
        if (!graphic[0]) {
            gui.waitForWindowToClose(TextView.gameWindow);
        }
    }

    public static void showResult(String way, Point coordinates, FIELD_STATE sign) {
        if (!graphic[0]) {
            if (VERTICAL.equals(way)) {
                int co;
                if (coordinates.y == 0) {
                    co = 6;
                } else if (coordinates.y == 1) {
                    co = 7;
                } else {
                    co = 8;
                }
                TextView.buttonPanel.removeComponent(TextView.btnFields[coordinates.x][coordinates.y]);
                Label btn1 = new Label(sign.toString() + "");
                btn1.setForegroundColor(TextColor.ANSI.BLACK);
                btn1.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co - 3, btn1);
                TextView.buttonPanel.removeComponent(TextView.btnFields[Math.abs((coordinates.x + 1) % 3)][coordinates.y]);
                Label btn2 = new Label(sign.toString() + "");
                btn2.setForegroundColor(TextColor.ANSI.BLACK);
                btn2.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co, btn2);
                TextView.buttonPanel.removeComponent(TextView.btnFields[Math.abs((coordinates.x+ 2) % 3)][coordinates.y]);
                Label btn3 = new Label(sign.toString() + "");
                btn3.setForegroundColor(TextColor.ANSI.BLACK);
                btn3.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co - 6, btn3);
            } else if (HORIZONTAL.equals(way)) {
                int co;
                if (coordinates.x == 0) {
                    co = 6;
                } else if (coordinates.x == 1) {
                    co = 3;
                } else {
                    co = 0;
                }
                TextView.buttonPanel.removeComponent(TextView.btnFields[coordinates.x][coordinates.y]);
                Label btn1 = new Label(sign.toString() + "");
                btn1.setForegroundColor(TextColor.ANSI.BLACK);
                btn1.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co + 1, btn1);
                TextView.buttonPanel.removeComponent(TextView.btnFields[coordinates.x][Math.abs((coordinates.y + 1) % 3)]);
                Label btn2 = new Label(sign.toString() + "");
                btn2.setForegroundColor(TextColor.ANSI.BLACK);
                btn2.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co + 2, btn2);
                TextView.buttonPanel.removeComponent(TextView.btnFields[coordinates.x][Math.abs((coordinates.y + 2) % 3)]);
                Label btn3 = new Label(sign.toString() + "");
                btn3.setForegroundColor(TextColor.ANSI.BLACK);
                btn3.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(co, btn3);
            } else if (SLASH.equals(way)) {
                TextView.buttonPanel.removeComponent(TextView.btnFields[0][0]);
                Label btn1 = new Label(sign.toString() + "");
                btn1.setForegroundColor(TextColor.ANSI.BLACK);
                btn1.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(6, btn1);
                TextView.buttonPanel.removeComponent(TextView.btnFields[1][1]);
                Label btn2 = new Label(sign.toString() + "");
                btn2.setForegroundColor(TextColor.ANSI.BLACK);
                btn2.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(4, btn2);
                TextView.buttonPanel.removeComponent(TextView.btnFields[2][2]);
                Label btn3 = new Label(sign.toString() + "");
                btn3.setForegroundColor(TextColor.ANSI.BLACK);
                btn3.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(2, btn3);
            } else if (BACKSLASH.equals(way)) {
                TextView.buttonPanel.removeComponent(TextView.btnFields[0][2]);
                Label btn1 = new Label(sign.toString() + "");
                btn1.setForegroundColor(TextColor.ANSI.BLACK);
                btn1.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(0, btn1);
                TextView.buttonPanel.removeComponent(TextView.btnFields[1][1]);
                Label btn2 = new Label(sign.toString() + "");
                btn2.setForegroundColor(TextColor.ANSI.BLACK);
                btn2.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(4, btn2);
                TextView.buttonPanel.removeComponent(TextView.btnFields[2][0]);
                Label btn3 = new Label(sign.toString() + "");
                btn3.setForegroundColor(TextColor.ANSI.BLACK);
                btn3.setBackgroundColor(TextColor.ANSI.CYAN);
                TextView.buttonPanel.addComponent(8, btn3);
            }
        } else {
            GraphicView.boardLabel.removeMouseListener(GraphicView.mouseListener);
            GraphicView.endOfGame = true;
            if (BACKSLASH.equals(way)) {
                GraphicView.resultSign = new ResultSign('\\', 0);
            } else if (SLASH.equals(way)) {
                GraphicView.resultSign = new ResultSign('/', 0);
            } else if (HORIZONTAL.equals(way)) {
                GraphicView.resultSign = new ResultSign('|', coordinates.x);
            } else if (VERTICAL.equals(way)) {
                GraphicView.resultSign = new ResultSign('-', coordinates.y);
            }
        }
    }

    public static void displayResult(String result) {
        if (!graphic[0]) {
            if (DRAW.equals(result)) {
                TextView.resultLabel.setText("Game result: Draw");
            } else if (XWINS.equals(result)) {
                TextView.resultLabel.setText("Game result: X wins");
            } else if (OWINS.equals(result)) {
                TextView.resultLabel.setText("Game result: O wins");
            }
            for (Button[] buttons : TextView.btnFields) {
                for (Button btn : buttons) {
                    btn.setEnabled(false);
                }
            }
        } else {
            if (DRAW.equals(result)) {
                GraphicView.resultLabel.setText("Game result: Draw");
            } else if (XWINS.equals(result)) {
                GraphicView.resultLabel.setText("Game result: X wins");
            } else if (OWINS.equals(result)) {
                GraphicView.resultLabel.setText("Game result: O wins");
            }
            GraphicView.boardLabel.removeMouseListener(GraphicView.mouseListener);
        }
    }

    public static void showMove(Point coordinates, FIELD_STATE sign){
        if (!graphic[0]) {
            TextView.btnFields[coordinates.x][coordinates.y].setLabel(sign.toString() + "");
        } else {
            graphicView.paintSign(sign, coordinates);
        }
    }

    public static void displayResultString() {
        if (!graphic[0]) {
            TextView.gamePanel.addComponent(2, new Label(""));
            TextView.resultLabel.setForegroundColor(TextColor.ANSI.BLACK);
            TextView.resultLabel.setBackgroundColor(TextColor.ANSI.CYAN);
            TextView.gamePanel.addComponent(3, TextView.resultLabel);
        } else {
            GraphicView.boardLabel.removeMouseListener(GraphicView.mouseListener);
        }
    }

    public static void move (Point coordinates) {
        if(coordinates.x > 3 || coordinates.y > 3) return;
        game.moveOnButton(coordinates);
    }

    public static void displayOccupiedCellWarning() {
        if(!graphic[0]) {
            BasicWindow win = new BasicWindow();
            win.setHints(Collections.singletonList(Window.Hint.CENTERED));
            Panel panel = new Panel();
            panel.addComponent(new Label("This cell is occupied! Choose another one!"));
            panel.addComponent(new Button("OK", win::close));
            win.setComponent(panel);
            gui.addWindowAndWait(win);
        } else {
            JOptionPane.showConfirmDialog(null,
                    "This cell is occupied! Choose another one!", "Invalid move", JOptionPane.DEFAULT_OPTION);
        }
    }
}
