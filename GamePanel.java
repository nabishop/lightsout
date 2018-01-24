import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GamePanel extends JPanel implements MouseListener {
   public static final int SQUARE_SIZE = 65;

   private Board theBoard;
   private boolean cheat;

   public GamePanel(int r, int c, Board b) {
      theBoard = b;
      cheat = false;

      this.addMouseListener(this);
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      for (int i = 0; i < theBoard.getColumns(); i++) // Draw Boxes
         for (int j = 0; j < theBoard.getRows(); j++) {
            if (theBoard.buttonLit(j, i))
               g.setColor(Color.YELLOW);
            else
               g.setColor(Color.BLACK);
            g.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE,
                  SQUARE_SIZE);
         }
      g.setColor(Color.GRAY);
      for (int i = 0; i < theBoard.getColumns() + 1; i++) // Draw vertical lines
         g.drawLine(i * SQUARE_SIZE, 0, i * SQUARE_SIZE,
               theBoard.getRows() * SQUARE_SIZE);
      for (int j = 0; j < theBoard.getRows() + 1; j++) // Draw horizontal lines
         g.drawLine(0, j * SQUARE_SIZE, theBoard.getColumns() * SQUARE_SIZE,
               j * SQUARE_SIZE);
      if (cheat) {
         g.setColor(Color.RED);
         Set<ButtonPress> presses = this.showSolution();
         for (ButtonPress bP : presses) {
            g.drawString("*", bP.col * SQUARE_SIZE + SQUARE_SIZE / 2,
                  bP.row * SQUARE_SIZE + SQUARE_SIZE / 2);
         }
      }
   }

   public Set<ButtonPress> showSolution() {
      for (int i = 0; i < Math.pow(2, theBoard.getColumns()); i++) {
         String firstRow = Integer.toString(i, 2); // base 2 representation of i
         firstRow = numZeros(firstRow);
         Set<ButtonPress> buttonPresses = new HashSet<ButtonPress>();
         for (int j = 0; j < firstRow.length(); j++) {
            if (firstRow.charAt(j) == '1') {
               buttonPresses.add(new ButtonPress(0, j));
               theBoard.pressButton(0, j);
            }
         }
         for (int r = 1; r < theBoard.getRows(); r++) {
            for (int c = 0; c < theBoard.getColumns(); c++) {
               if (theBoard.buttonLit(r - 1, c)) {
                  buttonPresses.add(new ButtonPress(r, c));
                  theBoard.pressButton(r, c);
               }
            }
         }
         if (theBoard.isSolved()) {
            for (ButtonPress bP : buttonPresses) {
               theBoard.pressButton(bP.row, bP.col);
            }
            return buttonPresses;
         } else {
            for (ButtonPress bP : buttonPresses) {
               theBoard.pressButton(bP.row, bP.col);
            }
         }
      }
      return null;
   }

   public void setGame(Board b) {
      theBoard = b;
      cheat = false;
      repaint();
   }

   // Fills in zeros to make the string of length = # columns
   public String numZeros(String a) {
      while (a.length() != theBoard.getColumns())
         a = "0" + a;
      return a;
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
      int c = e.getX() / SQUARE_SIZE;
      int r = e.getY() / SQUARE_SIZE;
      theBoard.pressButton(r, c);
      this.repaint();
      if (theBoard.isSolved()) {
         int op = JOptionPane.showConfirmDialog(this,
               "Congratulations!  Play Again?", "Lights Out",
               JOptionPane.YES_NO_OPTION);
         if (op == JOptionPane.NO_OPTION)
            System.exit(0);
         else {
            int d = Integer.parseInt(JOptionPane.showInputDialog(this,
                  "Difficulty Level (1-50)?"));
            cheat = false;
            theBoard.generatePuzzle(d);
            this.repaint();
         }
      }
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void cheat() {
      cheat = true;
      repaint();
   }

   public class ButtonPress {
      public int row, col;

      public ButtonPress(int r, int c) {
         row = r;
         col = c;
      }
   }
}