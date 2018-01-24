public class Board {
   private boolean[][] myBoard;
   private int rows, columns;

   public Board(int rows, int columns) {
      myBoard = new boolean[rows][columns];
      this.rows = rows;
      this.columns = columns;
      for (int r = 0; r < rows; r++) {
         for (int c = 0; c < columns; c++) {
            myBoard[r][c] = true;
         }
      }
   }

   public int getColumns() {
      return columns;
   }

   public int getRows() {
      return rows;
   }

   public void pressButton(int row, int col) {
      // nesw
      myBoard[row][col] = !myBoard[row][col];
      if (row > 0) {
         myBoard[row - 1][col] = !myBoard[row - 1][col];
      }
      if (row < myBoard.length - 1) {
         myBoard[row + 1][col] = !myBoard[row + 1][col];
      }
      if (col > 0) {
         myBoard[row][col - 1] = !myBoard[row][col - 1];
      }
      if (col < myBoard[0].length - 1) {
         myBoard[row][col + 1] = !myBoard[row][col + 1];
      }
   }

   public boolean buttonLit(int r, int c) {
      if (myBoard[r][c]) {
         return false;
      } else {
         return true;
      }

   }

   public boolean isSolved() {
      boolean solved = true;
      for (int r = 0; r < getRows(); r++) {
         for (int c = 0; c < getColumns(); c++) {
            if (myBoard[r][c] && solved) {
               solved = true;
            } else {
               solved = false;
            }
         }
      }
      return solved;

   }

   public void resetPuzzle() {
      for (int r = 0; r < getRows(); r++) {
         for (int c = 0; c < getColumns(); c++) {
            myBoard[r][c] = true;
         }
      }

   }

   public void generatePuzzle(int difficulty) {
      for (int r = 0; r < getRows(); r++) {
         for (int c = 0; c < getColumns(); c++) {
            myBoard[r][c] = true;
         }
      }
      for (int x = 0; x < difficulty; x++) {
         int rowNum = (int) (Math.random() * getRows());
         int colNum = (int) (Math.random() * getColumns());
         pressButton(rowNum, colNum);
      }
   }

}