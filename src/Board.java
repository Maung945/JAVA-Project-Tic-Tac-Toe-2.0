/**
 * Myo Aung
 * Term Project- Tic-Tac-Toe 2.0 - CS003B Fall 2021
 * Professor Kutukian
 * Board Implementation - Display Board, Setting Symbols, Checking for Winner
 */

public class Board{
    private int board[][];
    public Board(int size){
        board = new int[size][size];
    }

    public int[][] getBoard() {                                                     // Get board method
        return board;
    }

    public void setBoard(int [][] board) {
        this.board = board;
    }

    public void initialize(int size){                                              // Initialize the board
        for(int i = 0; i < size; i++)
            for(int j = 0; j <size; j++)
                board[i][j] = (int)'.';
    }

    public int setSymbol(int x, int y, int symbol){                                // Create setSymbol Method
        if(board[x][y] != (int)'.')
            return 0;
        board[x][y] = symbol;
        return 1;
    }

    public void displayBoard() {                                                  // This method displays board
        System.out.println("*** GAME BOARD ***");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (j > 0 && j < board.length) {
                    System.out.print("| ");
                }
                System.out.print((char) board[i][j] + " ");
            }
            System.out.println();

            if (i < board.length - 1)
                for (int k = 0; k < board.length - 1; k++) {                      // Align the output nicely using nested "for loop"
                    if (k == 0) {
                        System.out.print("--+");                                 // Display for row borders
                    }
                    if (k == board.length - 2) {
                        System.out.print("---");
                    } else
                        System.out.print("---+");
                }
            System.out.println();
        }
    }
    //  ============================CHECKING FOR WINNER===========================================
    public int checkNumber(int sym, int row, int column, int sequenceLength) {  // This method return winning "symbol"/ Player
        if(checkRow(sym, row, column , sequenceLength)) {                       // Row check
            return sym;
        }
        else if(checkColumn(sym, row, column , sequenceLength)) {               // Column check
            return sym;
        }
        else if(checkBackwardDiagonal(sym, row, column , sequenceLength)) {     // Backward diagonal check '\'
            return sym;
        }
        else if(checkForwardDiagonal(sym, row, column , sequenceLength)) {      // Forward diagonal check '/'
            return sym;
        }
        else
            return -1;
    }
    //Row Check----------------------------------------------------------------------------------------
    public boolean checkRow(int sym, int row, int column, int sequenceLength) {
        int count = 0;
        count = getLeftMatchCount(sym, row, column, sequenceLength);            // Call getLeftMatchCount method below, and set count
        if(count >= sequenceLength) {                                           // If count meets and exceeds, return ture
            return true;                                                        // Winner is found
        }
        if(count + getRightMatchCount(sym, row, column, sequenceLength) -1 >= sequenceLength) {
            return true;                                                        // Call getRightMatchCount method below, add to left count
        }                                                                       // This makes sure the chekRow meets requirements
        return false;                                                           // Otherwise, return false
    }
    //------------------------
    public int getLeftMatchCount(int sym, int row, int column, int sequenceLength) { // Left count
        int matchCount = 0;                                                      // Set the matchCount to '0'
        for(int i = column; i > column - sequenceLength; i--) {
            if ((i < 0) || (board[row][i] != sym)) {                             // Check for boundaries and same symbols
                return matchCount;                                               // If satisfies return matchCount
            }
            matchCount++;                                                       // Keep the match count for return
        }
        return sequenceLength;                                                  // If nothing on the left, simply return sequenceLength
    }

    public int getRightMatchCount(int sym, int row, int column, int sequenceLength) { // Right count
        int matchCount = 0;
        for(int i = column; i < column + sequenceLength; i++) {                // Notice i++ for it is going to right
            if ((i > board.length - 1) || (board[row][i] != sym)) {            // Check for boundaries and same simple
                return matchCount;
            }
            matchCount++;                                                      // Keep the match count for return
        }
        return sequenceLength;
    }

    //Column Check-----------------------------------------------------------------------------------------------
    public boolean checkColumn(int sym, int row, int column, int sequenceLength) {
        int count = 0;                                                        // **Same concept as Row Check**
        count = getUpCount(sym,row, column, sequenceLength);
        if(count >= sequenceLength){
            return true;
        }
        if((count + getDownCount(sym, row, column, sequenceLength) -1 ) >= sequenceLength){
            return true;
        }
        return false;
    }
    //------------------------
    public int getUpCount(int sym, int row, int column, int sequenceLength) {
        int matchingCount = 0;
        for(int i = row; i > row - sequenceLength; i--) {
            if ((i < 0) || (board[i][column] != sym)) {
                return matchingCount;
            }
            matchingCount++;
        }
        return sequenceLength;
    }

    public int getDownCount(int sym, int row, int column, int sequenceLength) {
        int matchingCount = 0;
        for(int i = row; i < row + sequenceLength; i++) {
            if ((i > board.length-1 ) || (board[i][column] != sym)) {
                return matchingCount;
            }
            matchingCount++;
        }
        return sequenceLength;
    }

    //Forward Diagonal check-----------------------------------------------------------------------------------------
    public boolean checkForwardDiagonal(int sym, int row, int column, int sequenceLength) {
        int count = 0;                                                      // Set count to zero
        count = getRightUpCount(sym, row, column, sequenceLength);          // Call getRightUpCount method below, set count
        if(count >= sequenceLength){                                        // If count meets and exceeds, return true
            return true;                                                    // Winner is found
        }
        if((count + getLeftDownCount(sym,row,column,sequenceLength) -1) >= sequenceLength){
            return true;                                                    // Call leftDownCount method below, add to right-up count
        }                                                                   // If count meets and exceeds, winner is found
        return false;
    }

    public int getRightUpCount(int sym, int row, int column, int sequenceLength){ // Right-up count (ForwardDiagonal)
        int matchCount = 0;                                                 // Set matchCount to zero
        int j = column;                                                     // Set j to incoming column
        for(int i = row; i > row - sequenceLength; i--) {                   // Use for loop to return matchCount
            if((i<0) ||(j > board.length-1 ) || (board[i][j] != sym)) {     // Checks for the boundaries
                return matchCount;                                          // If conditions are met, return matchCount
            }
            j++;                                                            // Increment j (column up)
            matchCount++;                                                   // Increment matchCount
            if(j < column + sequenceLength) {                               // Check if j exceeds column boundaries
                continue;
            }
        }
        return sequenceLength;                                            // Otherwise, return sequenceLength
    }

    public int getLeftDownCount(int sym, int row, int column, int sequenceLength) {
        int matchCount = 0;
        int j = column;
        for(int i = row; i < row + sequenceLength; i++){
            if((i > board.length -1 ) ||(j < 0) || (board[i][j] != sym)){
                return matchCount;
            }
            j--;
            if(j > column + sequenceLength){
                continue;
            }
        }
        return sequenceLength;
    }

    //Backward Diagonal Check-----------------------------------------------------------------------------------------
    public boolean checkBackwardDiagonal(int sym, int row, int column, int sequenceLength) {
        int count = 0;
        count = getLeftUpCount(sym, row, column, sequenceLength);
        if(count >= sequenceLength){
            return true;
        }
        if((count + getRightDownCount(sym,row,column,sequenceLength) -1 ) >= sequenceLength){
            return true;
        }
        return false;
    }

    public int getLeftUpCount(int sym, int row, int column, int sequenceLength){
        int matchCount = 0;
        int j = column;
        for(int i = row; i > row - sequenceLength; i--) {
            if((i<0) ||(j<0) || (board[i][j] != sym)) {
                return matchCount;
            }
            j--;
            matchCount++;
            if(j > column - sequenceLength){
                continue;
            }
        }
        return sequenceLength;
    }

    public int getRightDownCount(int sym, int row, int column, int sequenceLength){
        int matchCount = 0;
        int j = column;
        for(int i = row; i < row + sequenceLength; i++){
            if((i > board.length -1 ) ||(j > board.length -1 ) || (board[i][j] != sym)){
                return matchCount;
            }
            j++;
            matchCount++;
            if(j < column + sequenceLength){
                continue;
            }
        }
        return sequenceLength;
    }
}



