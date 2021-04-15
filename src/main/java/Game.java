package main.java;

import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Game {
    private static final Board board = new Board();
    private FIELD_STATE sign; //determines whose move is it
    private Point coordinates;
    private final static String user = "User";
    private final static String easyComputer = "Easy computer";
    private final static String mediumComputer = "Medium computer";
    private final static String hardComputer = "Hard computer";
    private final FIELD_STATE[] moves = new FIELD_STATE[2];
    private int[] iter;
    private Player first;
    private Player second;
    public boolean[] end = {false};
    private String finalPlayer2;
    private String finalPlayer1;
    private final EasyComputer easy1 = new EasyComputer(new Random(System.currentTimeMillis()));
    private final MediumComputer medium1 = new MediumComputer(new Random(System.currentTimeMillis()));
    private final HardComputer hard1 = new HardComputer(new Random(System.currentTimeMillis()));
    private final EasyComputer easy2 = new EasyComputer(new Random(System.currentTimeMillis()));
    private final MediumComputer medium2 = new MediumComputer(new Random(System.currentTimeMillis()));
    private final HardComputer hard2 = new HardComputer(new Random(System.currentTimeMillis()));
    private static boolean escapeThread;

    class GameThread extends Thread {
        public void run() {
            Controller.disableButtons();
            while (!end[0] && !escapeThread) {
                try {
                    sleep(1000);
                    synchronized (board) {
                        computerMove();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MoveThread extends Thread {
        public void run() {
            Controller.disableButtons();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!end[0]) {
                computerMove();
            }
            Controller.enableButtons();
        }
    }

    /**
     * Main function of the game
     * @param player1 tells if the one who plays 'X' is a computer or user
     * @param player2 tells if the one who plays 'O' is a computer or user
     */
    public void play(String player1, String player2) {
        iter = new int[]{0};
        board.resetBoard();
        moves[0] = FIELD_STATE.X;
        moves[1] = FIELD_STATE.O;
        if ("easy".equals(player1)) { //watch two computers on easy level play
            first = easy1;
            player1 = easyComputer;
        } else if ("medium".equals(player1)) { //watch two computers play, one on medium level (plays 'X') and second on easy level (plays 'O')
            first = medium1;
            player1 = mediumComputer;
        } else if ("hard".equals(player1)) {
            first = hard1;
            player1 = hardComputer;
        } else {
            first = new User();
            player1 = user;
        }
        if ("easy".equals(player2)) {
            second = easy2;
            player2 = easyComputer;
        } else if ("medium".equals(player2)) { //watch two computers on medium level play
            second = medium2;
            player2 = mediumComputer;
        } else if ("hard".equals(player2)) {
            second = hard2;
            player2 = hardComputer;
        } else { //two users plays
            second = new User();
            player2 = user;
        }
        finalPlayer2 = player2;
        finalPlayer1 = player1;
        GameThread gameThread = new GameThread(); //przed dodaniem tego okienka musimy jeszcze dodać okienko z wynikiem, a potem tylko przesunąć je do góry, zeby komputer mógł wyświetlić wynik
        if(!player1.equals(user) && !player2.equals(user)) {
            Controller.updateScreen();
            escapeThread = false;
            end[0] = false;
            gameThread.start();
        } else if(!player1.equals(user)){
            computerMove();
        }
        Controller.waitForEndOfGame();
        hard1.setMovesMade();
        hard2.setMovesMade();
        if(!player1.equals(user) && !player2.equals(user)) {
            while(!end[0]) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            escapeThread = true;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if given sign has won the game
     * @return returns true if given sign has won the game
     */
    public boolean checkForWin() {
        if (Board.fieldStateEquals(new Point(Math.abs((coordinates.x + 1) % 3), coordinates.y), sign) && Board.fieldStateEquals(new Point(Math.abs((coordinates.x + 2) % 3),coordinates.y), sign)) { //pionowo
            Controller.showResult(Controller.VERTICAL, coordinates, sign);
            return true;
        } else if (Board.fieldStateEquals(new Point(coordinates.x, Math.abs((coordinates.y + 1) % 3)), sign) && Board.fieldStateEquals(new Point(coordinates.x, Math.abs((coordinates.y + 2) % 3)), sign)) { //poziomo
            Controller.showResult(Controller.HORIZONTAL, coordinates, sign);
            return true;
        } else if (Board.fieldStateEquals(new Point(2, 0), sign) && Board.fieldStateEquals(new Point(1, 1), sign) && Board.fieldStateEquals(new Point(0, 2), sign)) { //one cross win
            Controller.showResult(Controller.SLASH, coordinates, sign);
            return true;
        } else if (Board.fieldStateEquals(new Point(0 ,0), sign) && Board.fieldStateEquals(new Point(1, 1), sign) && Board.fieldStateEquals(new Point(2, 2), sign)){ //two cross win
            Controller.showResult(Controller.BACKSLASH, coordinates, sign);
            return true;
        }
        return false;
    }

    /**
     * Checks if the game has ended and if so, who won
     * @param iter how many moves were made
     * @return returns true if the game gas ended
     */
    public boolean checkResult (int iter) {
        boolean xWins = false;
        boolean oWins = false;
        if (sign.equals(FIELD_STATE.O)) {
            oWins = checkForWin();
        } else {
            xWins = checkForWin();
        }
        if (!xWins && !oWins && iter >= 8) {
            Controller.displayResult(Controller.DRAW);
            return true;
        } else if (oWins) {
            Controller.displayResult(Controller.OWINS);
            return true;
        } else if (xWins) {
            Controller.displayResult(Controller.XWINS);
            return true;
        }
        return false;
    }

    public void moveOnButton(Point coordinates) {
        sign = moves[iter[0] % 2]; //check whose move is it
        if (sign.equals(FIELD_STATE.X)) { //if it's user's move
            this.coordinates = first.move(sign, coordinates); //first player makes a move
        } else { //if it's medium level computer's move
            this.coordinates = second.move(sign, coordinates); //second player makes move
        }
        if(this.coordinates != null && this.coordinates.x != 3) {
            if (!end[0]) {
                Controller.showMove(this.coordinates, sign);
                end[0] = checkResult(iter[0]); //checking if game has ended
                iter[0]++; //increase number of used moves
                if (end[0]) {
                    Controller.displayResultString();
                    return;
                }
                Controller.updateCurrentMove(iter[0] % 2 == 0);
            }
        } else {
            Controller.displayOccupiedCellWarning();
            return;
        }
        if ((!finalPlayer2.equals(user) || !finalPlayer1.equals(user)) && !end[0]){ //TODO żeby nie zachodziły na siebie
            Controller.updateScreen();
            MoveThread moveThread = new MoveThread();
            moveThread.start();
        }
    }

    public void computerMove() {
        sign = moves[iter[0] % 2]; //check whose move is it
        if(!end[0]) {
            if (sign.equals(FIELD_STATE.X)) { //if it's user's move
                coordinates = first.move(sign, new Point(2, 0)); //first player makes a move
            } else { //if it's medium level computer's move
                coordinates = second.move(sign, new Point(2, 0)); //second player makes move
            }
            if(!end[0]) {
                Controller.showMove(coordinates, sign);
                if (coordinates.x != 3) {
                    end[0] = checkResult(iter[0]); //checking if game has ended
                    iter[0]++; //increase number of used moves
                    if (end[0]) {
                        Controller.displayResultString();
                        return;
                    }
                    Controller.updateCurrentMove(iter[0] % 2 == 0);
                }
            }
        }
    }
}
