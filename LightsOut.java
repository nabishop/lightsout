
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class LightsOut extends JFrame implements ActionListener {
   private static final int EXTRA_SPACE = 75;

   private Board theGame;
   private JButton newGame, quit, cheat;
   private int rows, columns;
   private GamePanel center;

   public LightsOut() {
      super();

      newGame = new JButton("New");
      newGame.addActionListener(this);
      quit = new JButton("Quit");
      quit.addActionListener(this);
      cheat = new JButton("Magic");
      cheat.addActionListener(this);

      JPanel bottom = new JPanel();
      bottom.add(newGame);
      bottom.add(quit);
      bottom.add(cheat);

      center = new GamePanel(0, 0, new Board(0, 0));
      this.getContentPane().add(center, BorderLayout.CENTER);
      this.getContentPane().add(bottom, BorderLayout.SOUTH);

      this.createGame();

      this.setTitle("Lights Out");
      this.setVisible(true);
      this.setResizable(false);
      this.setLocation(100, 100);

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   private void createGame() {
      rows = Integer.parseInt(JOptionPane.showInputDialog(this,
            "How many rows (at least three)?"));
      columns = Integer.parseInt(JOptionPane.showInputDialog(this,
            "How many columns (at least three)?"));
      if (rows < 3)
         rows = 3;
      if (columns < 3)
         columns = 3;
      int difficulty = Integer.parseInt(
            JOptionPane.showInputDialog(this, "Difficulty Level (1-50)?"));
      if (difficulty <= 0)
         difficulty = 1;
      theGame = new Board(rows, columns);
      center.setGame(theGame);

      this.setResizable(true);
      this.setSize(GamePanel.SQUARE_SIZE * columns,
            GamePanel.SQUARE_SIZE * rows + EXTRA_SPACE);
      this.setResizable(false);

      theGame.generatePuzzle(difficulty);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == newGame)
         this.createGame();
      if (e.getSource() == quit)
         System.exit(0);
      if (e.getSource() == cheat)
         center.cheat();
   }

   public static void main(String[] args) {
      LightsOut game = new LightsOut();
   }
}