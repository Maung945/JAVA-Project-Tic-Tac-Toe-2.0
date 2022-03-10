import jdk.swing.interop.SwingInterOpUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
/**
 * Myo Aung
 * Term Project- Tic-Tac-Toe 2.0 - CS003B Fall 2021
 * Professor Kutukian
 * GameLogic Implementation - Display Menu, Display Game Rules, Error-catching such as duplicate symbols and '.' symbol,
 *                          - Saving Results and retrieving them to resume the game.
 */
public class GameLogic {
    private int numberOfPlayers = 0;
    protected int totalCells;
    private int row, col, remainingMove, status,playerMove, winningSequenceSize, menuChoice;
    private char[] symbols;
    private Board objBoard;

    public GameLogic() {}                                                                       // Constructor

    public void displayRules() {                                                                // Simple display-rules method
        System.out.println("              ****************************");
        System.out.println("             * Welcome to Tic-Tac-Toe 2.0 *");
        System.out.println("              ****************************");
        System.out.println("    1) Number of players can be 3 to 10.");
        System.out.println("    2) Winning sequence can be set from 3 to number of players plus ONE");
        System.out.println("    3) Any player who reaches the chosen Winning Sequence first is the Winner!");
        System.out.println("    4) If the board is full without a winner, the game is considered a TIE");
        System.out.println("    5) The last game played is always saved and can be resumed.");
        System.out.println();
        System.out.println("                       ENJOY THE GAME!!                                   ");
        System.out.println("----------------------------");
    }

    public void playGame() {                                                                    // The playGame method would start the entire game
        numberOfPlayers = 0;
        Scanner sc = new Scanner(System.in);
        GameLogic objLogic = new GameLogic();                                                   // Minimum numbers of cells win
        symbols = new char[10];                                                                 // Declare a set of symbols

        while(true){
            System.out.println("                ************************");
            System.out.println("               *       Main Menu        *");
            System.out.println("                ************************");
            System.out.println("                  [1] PLAY GAME");
            System.out.println("                  [2] GAME INSTRUCTIONS");
            System.out.println("                  [3] RESUME GAME");
            System.out.println("                  [4] EXIT");
            try {
                System.out.print("Choose a number to proceed: ");                                   // Take choice as input
                menuChoice = sc.nextInt();
            }catch (Exception e) {
            }

            sc.nextLine();                                                                      // Cancel new line character
            System.out.println("----------------------------");

            if(menuChoice == 1) {                                                               // MenuChoice 1, New Game
                numberOfPlayers = 0;
                System.out.println("              ****************************");
                System.out.println("             * Welcome to Tic-Tac-Toe 2.0 *");
                System.out.println("              ****************************");

                while(!(numberOfPlayers >=3 && numberOfPlayers <=10)){                          // Check valid entry of player count
                    try {
                        System.out.print("Enter the number of players (3 - 10): ");
                        numberOfPlayers = sc.nextInt();
                    }catch (Exception e) {
                        System.out.println("Invalid choice! Try again!");
                        sc.next();
                    }
                }
                for(int counter = 0; counter < numberOfPlayers; counter++) {
                    System.out.print("Enter the symbols for player " + (counter+1) + ": ");
                    char playerSymbol = sc.next().charAt(0);
                    boolean symbolExist = false;                                                // Set symbolExist boolean to fault
                    if(playerSymbol == '.') {                                                   // This one catches the '.' symbol which would cause program to crash
                        symbolExist = true;
                        System.out.println("This choice is not allowed!");
                    }
                    for(int x = 0; x < counter; x++) {                                          // This code with catch the duplicate symbols
                        if(playerSymbol == symbols[x]) {
                            symbolExist = true;
                            System.out.println("Invalid choice! Please choose symbol again.");
                        }
                    }
                    if(!symbolExist) {                                                          // If ture, set counter back
                        symbols[counter] = playerSymbol;
                    }
                    else
                        counter--;                                                              // Otherwise, keeps counting down
                }

                objBoard = new Board(numberOfPlayers + 1);                                  // Create a board of size n + 1
                winningSequenceSize = 3;                                                        // Hardcode the minimum winningSequenceSize
                remainingMove = (numberOfPlayers + 1) * (numberOfPlayers + 1);                  // Calculates total remaining move
                playerMove = 0;
                objBoard.initialize(numberOfPlayers + 1);                                   //Initialize the board
                    do {
                        try {
                            System.out.print("Enter the winning sequence length between 3" + " and " + (numberOfPlayers + 1) + ": ");
                            winningSequenceSize = sc.nextInt();
                        } catch (Exception e) {
                            System.out.print("Invalid Choice! Try Again: ");

                            sc.next();
                            winningSequenceSize = sc.nextInt();
                        }
                    } while (!(winningSequenceSize >= 3 && winningSequenceSize <= numberOfPlayers + 1));
            }

            else if(menuChoice == 2){                                                           // MenuChoice 2, Display Rules
                objLogic.displayRules();                                                        // Shows the game rules
            }

            else if(menuChoice == 3) {                                                          // MenuChoice 3, Resume Game
                readFile();
            }

            else if(menuChoice == 4){                                                           // MenuChoice 3, Exit
                System.out.println("Thank you for stopping by! Good Bye!");
                break;
            }

            else{
                System.out.println("Invalid Choice!! Try again!!");
            }
            //===============================================================
            if(menuChoice == 1 || menuChoice == 3) {
                objLogic.totalCells = winningSequenceSize;
                Player objPlayer[] = new Player[numberOfPlayers];                               // Player class object

                // Initialize the symbols
                for(int i = 0; i < numberOfPlayers; i++) {
                    objPlayer[i] = new Player((int)symbols[i]);
                }

                System.out.println("Total Players: "+ numberOfPlayers);
                System.out.println("Winning Sequence: "+ winningSequenceSize);
                System.out.println("Symbols: ");
                for(int i = 0; i < numberOfPlayers; i++) {
                    System.out.println("Player " + (i + 1) + ": " + (char) objPlayer[i].getSymbol());
                }
                System.out.println("----------------------------");

                status = -1;

                while(remainingMove > 0){
                    row = 0;
                    col = 0;
                    saveResult();
                    objBoard.displayBoard();                                                    //Shows the board
                    playerMove = (playerMove % numberOfPlayers) + 1;                            //Displays whose move
                    System.out.println("Player "+ playerMove +" (Symbol: "+(char)objPlayer[playerMove - 1].getSymbol() + ")");
                    System.out.println("--------------------");
                    System.out.print("Enter row: ");
                    try {
                        row = sc.nextInt();
                    }catch (Exception e) {
                        System.out.println("Invalid Choice!");
                        sc.next();
                    }
                    while(!(row >= 1 && row <= numberOfPlayers + 1)) {
                        if(row == -1) {
                            System.out.println("Bye! The game has been saved. Come back to resume the game!");
                            return;
                        }
                        System.out.print("Enter row: ");
                        row = sc.nextInt();
                    }

                    System.out.print("Enter column: ");
                    try {
                        col = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid Choice!");
                        sc.next();
                    }
                    while(!(col >= 1 && col <= numberOfPlayers + 1)){
                        if(col == -1) {
                            System.out.println("Bye! The game has been saved. Come back to resume the game!");
                            return;
                        }
                        System.out.print("Enter column: ");
                        col = sc.nextInt();

                    }
                    sc.nextLine();                                                              // Cancels new line character
                    status = objBoard.setSymbol(row - 1, col - 1, objPlayer[playerMove - 1].getSymbol());

                    if (status == 0) {
                        System.out.println("Warning!! Position is Occupied!");
                        System.out.println();
                        playerMove = playerMove - 1;
                        continue;
                    }

                    //Checking & Declaring the Winner========================================================================
                    status = objBoard.checkNumber(objPlayer[playerMove - 1].getSymbol(), row - 1, col - 1, objLogic.totalCells);
                    if(status != -1){
                        objBoard.displayBoard();                                                // Display the board
                        if((char)status == symbols[0])                                          // Choose the winner
                            System.out.println("PLAYER 1 IS THE WINNER!");
                        else if((char)status == symbols[1])
                            System.out.println("PLAYER 2 IS THE WINNER!");
                        else if((char)status == symbols[2])
                            System.out.println("PLAYER 3 IS THE WINNER!");
                        else if((char)status == symbols[3])
                            System.out.println("PLAYER 4 IS THE WINNER!");
                        else if((char)status == symbols[4])
                            System.out.println("PLAYER 5 IS THE WINNER!");
                        else if((char)status == symbols[5])
                            System.out.println("PLAYER 6 IS THE WINNER!");
                        else if((char)status == symbols[6])
                            System.out.println("PLAYER 7 IS THE WINNER!");
                        else if((char)status == symbols[7])
                            System.out.println("PLAYER 8 IS THE WINNER!");
                        else if((char)status == symbols[8])
                            System.out.println("PLAYER 9 IS THE WINNER!");
                        else if((char)status == symbols[9])
                            System.out.println("PLAYER 10 IS THE WINNER!");
                        break;
                    }
                    if(playerMove == numberOfPlayers) {                                         // reset the value of k
                        playerMove = 0;
                    }
                    remainingMove--;
                    System.out.println();
                }

                if(status == -1){
                    objBoard.displayBoard();
                    System.out.println("The game is a tie!");
                }
            }
            System.out.println();
        }
    }
//saveResult and readResult Methods Below=====================================================
    public void saveResult() {                                                                  // This method saves the data into ".txt" file
        try {
            FileWriter output = new FileWriter("GameResults.txt");
            output.write(remainingMove + "\n");
            output.write(status + "\n");
            output.write(numberOfPlayers + "\n");
            output.write(winningSequenceSize + "\n");
            output.write(playerMove + "\n");

            for(int i = 0; i < numberOfPlayers; i++) {
                output.write(symbols[i]);
            }
            output.write("\n");
            for(int i = 0; i < objBoard.getBoard().length; i++) {
                for(int j = 0; j < objBoard.getBoard().length; j++) {
                    output.write(objBoard.getBoard()[i][j] + " ");
                }
                output.write("\n");
            }
            output.close();
            } catch (IOException ex) {
                System.out.print("An Error Occurred.");
                ex.printStackTrace();
            }
    }

    public void readFile() {                                                                    // This method retrieve saved data to resume the last game
        try{
            Scanner sc = new Scanner(new File("GameResults.txt"));
            String line = sc.next();                                                            // Read line1
            remainingMove = Integer.parseInt(line);

            String line2 = sc.next();                                                           // Read line2
            status = Integer.parseInt(line2);                                                   // And set it to "status"

            String line3 = sc.next();
            numberOfPlayers = Integer.parseInt(line3);

            String line4 = sc.next();
            winningSequenceSize = Integer.parseInt(line4);

            String line5 = sc.next();
            playerMove = Integer.parseInt(line5);

            String readSymbols = sc.next();
            symbols = new char [readSymbols.length()];
            for(int i = 0; i < readSymbols.length(); i++) {
                symbols[i] = readSymbols.charAt(i);
            }
            sc.nextLine();
            int [][] readArray = new int[numberOfPlayers + 1][numberOfPlayers + 1];
            for(int i = 0; i < readArray.length; i++) {
                String[] readBoard = sc.nextLine().trim().split(" ");
                for(int j = 0; j < readBoard.length; j++) {
                    readArray[i][j] = Integer.parseInt(readBoard[j]);
                }
            }

            objBoard = new Board(numberOfPlayers +1 );
            objBoard.setBoard(readArray);

            } catch (Exception e) {
            e.printStackTrace();
            }
    }
}
