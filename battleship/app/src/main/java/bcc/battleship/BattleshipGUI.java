package bcc.battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//made mostly by o3-mini-high with maybe 5-10 iterations. What a time to be alive!
public class BattleshipGUI extends JFrame {
    // Manage game screens using CardLayout.
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private HomePanel homePanel;
    private ShipPlacementPanel shipPlacementPanel;
    private PassAndPlayPanel passAndPlayPanel;
    private GamePanel gamePanel;
    
    // Two players for the game.
    private Player player1;
    private Player player2;
    
    // For ship placement phase.
    private int currentPlacementPlayer = 1;
    
    // For gameplay: track which player is active.
    // currentGamePlayer is 1 or 2.
    private int currentGamePlayer = 1;
    
    public BattleshipGUI() {
        // Initialize players.
        player1 = new Player();
        player2 = new Player();
        
        // Setup the main frame. (900x600 instead of 900x700)
        setTitle("Battleship Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels.
        homePanel = new HomePanel();
        shipPlacementPanel = new ShipPlacementPanel();
        passAndPlayPanel = new PassAndPlayPanel();
        gamePanel = new GamePanel();
        
        // Add panels to main panel.
        mainPanel.add(homePanel, "Home");
        mainPanel.add(shipPlacementPanel, "ShipPlacement");
        mainPanel.add(passAndPlayPanel, "PassAndPlay");
        mainPanel.add(gamePanel, "Game");
        
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // ---------------------------------------------------------------------
    // Home Screen Panel
    // ---------------------------------------------------------------------
    private class HomePanel extends JPanel {
        public HomePanel() {
            setLayout(new BorderLayout());
            JLabel titleLabel = new JLabel("Welcome to Battleship!", SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
            add(titleLabel, BorderLayout.CENTER);
            
            JButton startButton = new JButton("Start Game");
            startButton.setFont(new Font("SansSerif", Font.BOLD, 20));
            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Begin ship placement with player 1.
                    currentPlacementPlayer = 1;
                    shipPlacementPanel.resetForPlayer(currentPlacementPlayer);
                    cardLayout.show(mainPanel, "ShipPlacement");
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(startButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    
    // ---------------------------------------------------------------------
    // Ship Placement Panel
    // ---------------------------------------------------------------------
    private class ShipPlacementPanel extends JPanel {
        private JLabel instructionLabel;
        private JPanel gridPanel;
        private JButton[][] buttons;
        private JComboBox<String> orientationComboBox;
        private JLabel shipLabel;
        private JButton nextPlayerButton;
        
        private int currentShipIndex = 0; // which ship is being placed
        
        public ShipPlacementPanel() {
            setLayout(new BorderLayout());
            
            instructionLabel = new JLabel("", SwingConstants.CENTER);
            instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            add(instructionLabel, BorderLayout.NORTH);
            
            gridPanel = new JPanel(new GridLayout(Constants.NUM_ROWS, Constants.NUM_COLS));
            buttons = new JButton[Constants.NUM_ROWS][Constants.NUM_COLS];
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    JButton btn = new JButton();
                    btn.setBackground(Color.BLUE);
                    final int row = i;
                    final int col = j;
                    btn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            placeShipAt(row, col);
                        }
                    });
                    buttons[i][j] = btn;
                    gridPanel.add(btn);
                }
            }
            add(gridPanel, BorderLayout.CENTER);
            
            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Orientation:"));
            orientationComboBox = new JComboBox<>(new String[]{"Horizontal", "Vertical"});
            controlPanel.add(orientationComboBox);
            
            shipLabel = new JLabel("");
            shipLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            controlPanel.add(shipLabel);
            
            nextPlayerButton = new JButton("Next Player");
            nextPlayerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    nextPlayerPlacement();
                }
            });
            controlPanel.add(nextPlayerButton);
            add(controlPanel, BorderLayout.SOUTH);
        }
        
        public void resetForPlayer(int playerNumber) {
            currentShipIndex = 0;
            // Reset buttons.
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    buttons[i][j].setBackground(Color.BLUE);
                    buttons[i][j].setEnabled(true);
                }
            }
            updateInstruction();
        }
        
        private void updateInstruction() {
            if (currentShipIndex < Constants.SHIP_LENGTHS.length) {
                instructionLabel.setText("Player " + currentPlacementPlayer +
                        ": Place your ship of length " + Constants.SHIP_LENGTHS[currentShipIndex]);
                shipLabel.setText("Ship length: " + Constants.SHIP_LENGTHS[currentShipIndex]);
            } else {
                instructionLabel.setText("Player " + currentPlacementPlayer + ": All ships placed.");
                shipLabel.setText("");
                // Disable grid buttons.
                for (int i = 0; i < Constants.NUM_ROWS; i++) {
                    for (int j = 0; j < Constants.NUM_COLS; j++) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        }
        
        private void placeShipAt(int row, int col) {
            if (currentShipIndex >= Constants.SHIP_LENGTHS.length) {
                return;
            }
            int direction = orientationComboBox.getSelectedItem().equals("Horizontal") ?
                    Constants.HORIZONTAL : Constants.VERTICAL;
            boolean success;
            if (currentPlacementPlayer == 1) {
                success = player1.chooseShipLocation(currentShipIndex, row, col, direction);
            } else {
                success = player2.chooseShipLocation(currentShipIndex, row, col, direction);
            }
            if (success) {
                // Mark ship placement on the board (set the background to gray).
                if (direction == Constants.HORIZONTAL) {
                    for (int i = 0; i < Constants.SHIP_LENGTHS[currentShipIndex]; i++) {
                        if (col + i < Constants.NUM_COLS) {
                            buttons[row][col + i].setBackground(Color.GRAY);
                        }
                    }
                } else {
                    for (int i = 0; i < Constants.SHIP_LENGTHS[currentShipIndex]; i++) {
                        if (row + i < Constants.NUM_ROWS) {
                            buttons[row + i][col].setBackground(Color.GRAY);
                        }
                    }
                }
                currentShipIndex++;
                updateInstruction();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ship placement. Try again.");
            }
        }
        
        private void nextPlayerPlacement() {
            if (currentShipIndex < Constants.SHIP_LENGTHS.length) {
                JOptionPane.showMessageDialog(this, "Place all ships before continuing.");
                return;
            }
            if (currentPlacementPlayer == 1) {
                currentPlacementPlayer = 2;
                resetForPlayer(currentPlacementPlayer);
            } else {
                // Both players have placed their ships; start gameplay with player 1.
                currentGamePlayer = 1;
                gamePanel.updateBoards();
                cardLayout.show(mainPanel, "PassAndPlay");
                passAndPlayPanel.setMessage("Player 2 has finished. Pass the computer to Player 1 and click Continue.");
            }
        }
    }
    
    // ---------------------------------------------------------------------
    // Pass-And-Play Panel
    // ---------------------------------------------------------------------
    private class PassAndPlayPanel extends JPanel {
        private JLabel messageLabel;
        private JButton continueButton;
        
        public PassAndPlayPanel() {
            setLayout(new BorderLayout());
            messageLabel = new JLabel("", SwingConstants.CENTER);
            messageLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
            add(messageLabel, BorderLayout.CENTER);
            
            continueButton = new JButton("Continue");
            continueButton.setFont(new Font("SansSerif", Font.BOLD, 20));
            continueButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // When continuing, show the game screen for the current active player.
                    gamePanel.updateBoards();
                    cardLayout.show(mainPanel, "Game");
                }
            });
            JPanel panel = new JPanel();
            panel.add(continueButton);
            add(panel, BorderLayout.SOUTH);
        }
        
        public void setMessage(String message) {
            messageLabel.setText(message);
        }
    }
    
    // ---------------------------------------------------------------------
    // Game Play Panel
    // ---------------------------------------------------------------------
    private class GamePanel extends JPanel {
        private JLabel turnLabel;
        // Two sub-panels: one for "Your Board" and one for "Opponent Board".
        private JPanel yourBoardPanel;
        private JPanel opponentBoardPanel;
        
        // 2D arrays for buttons.
        private JButton[][] yourBoardButtons;
        private JButton[][] opponentBoardButtons;
        
        public GamePanel() {
            setLayout(new BorderLayout());
            
            turnLabel = new JLabel("", SwingConstants.CENTER);
            turnLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
            add(turnLabel, BorderLayout.NORTH);
            
            // Create a panel to hold both boards side-by-side.
            JPanel boardsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            
            // Your Board: shows your ship placements and any opponent guesses.
            yourBoardPanel = new JPanel(new GridLayout(Constants.NUM_ROWS, Constants.NUM_COLS));
            yourBoardButtons = new JButton[Constants.NUM_ROWS][Constants.NUM_COLS];
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    JButton btn = new JButton();
                    btn.setBackground(Color.BLUE);
                    btn.setFont(new Font("SansSerif", Font.BOLD, 10));
                    btn.setEnabled(false); // Not clickable.
                    yourBoardButtons[i][j] = btn;
                    yourBoardPanel.add(btn);
                }
            }
            
            // Opponent Board: interactive grid for making guesses.
            opponentBoardPanel = new JPanel(new GridLayout(Constants.NUM_ROWS, Constants.NUM_COLS));
            opponentBoardButtons = new JButton[Constants.NUM_ROWS][Constants.NUM_COLS];
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    JButton btn = new JButton();
                    btn.setBackground(Color.BLUE);
                    btn.setFont(new Font("SansSerif", Font.BOLD, 10));
                    final int row = i;
                    final int col = j;
                    btn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            processGuess(row, col);
                        }
                    });
                    opponentBoardButtons[i][j] = btn;
                    opponentBoardPanel.add(btn);
                }
            }
            
            // Add labels above each board.
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.add(new JLabel("Your Board", SwingConstants.CENTER), BorderLayout.NORTH);
            leftPanel.add(yourBoardPanel, BorderLayout.CENTER);
            
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(new JLabel("Opponent Board", SwingConstants.CENTER), BorderLayout.NORTH);
            rightPanel.add(opponentBoardPanel, BorderLayout.CENTER);
            
            boardsPanel.add(leftPanel);
            boardsPanel.add(rightPanel);
            add(boardsPanel, BorderLayout.CENTER);
        }
        
        // Refresh the boards based on the current player's grids.
        public void updateBoards() {
            Player currentPlayer = (currentGamePlayer == 1) ? player1 : player2;
            // Update turn label.
            turnLabel.setText("Player " + currentGamePlayer + "'s Turn");
            
            // Update "Your Board" using currentPlayer.myGrid.
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    int status = currentPlayer.getMyGrid().getStatus(i, j);
                    JButton btn = yourBoardButtons[i][j];
                    // Default text is empty.
                    btn.setText("");
                    
                    // If a cell contains a ship and is still unguessed, show it (via background color).
                    if (status == Constants.UNGUESSED) {
                        if (currentPlayer.getMyGrid().hasShip(i, j)) {
                            btn.setBackground(Color.GRAY);
                        } else {
                            btn.setBackground(Color.BLUE);
                        }
                    } else if (status == Constants.HIT) {
                        btn.setText("X");
                        btn.setBackground(Color.RED);
                    } else if (status == Constants.MISSED) {
                        btn.setText("O");
                        btn.setBackground(Color.BLUE);
                    }
                }
            }
            
            // Update "Opponent Board" using currentPlayer.opponentGrid.
            // For unguessed cells, leave the text empty.
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    int status = currentPlayer.getOpponentGrid().getStatus(i, j);
                    JButton btn = opponentBoardButtons[i][j];
                    String text = (status == Constants.UNGUESSED) ? "" : (status == Constants.HIT ? "X" : "O");
                    if(status == Constants.HIT)btn.setForeground(Color.RED);
                    btn.setText(text);
                    btn.setBackground(Color.BLUE);
                    btn.setEnabled(status == Constants.UNGUESSED);
                }
            }
            
        }
        private void freezeGame() {
            // Disable all opponent board buttons.
            for (int i = 0; i < Constants.NUM_ROWS; i++) {
                for (int j = 0; j < Constants.NUM_COLS; j++) {
                    opponentBoardButtons[i][j].setEnabled(false);
                }
            }
            // Optionally disable other interactive components or panels.
        }
        // Process a guess on the opponent board.
        private void processGuess(int row, int col) {
            Player currentPlayer = (currentGamePlayer == 1) ? player1 : player2;
            Player opponent = (currentGamePlayer == 1) ? player2 : player1;
            
            // Check if this guess has already been made.
            if (currentPlayer.getOpponentGrid().getStatus(row, col) != Constants.UNGUESSED) {
                JOptionPane.showMessageDialog(GamePanel.this, "Already guessed that location.");
                return;
            }
            
            // Make the guess on the opponent's grid.
            boolean hit = opponent.recordOpponentGuess(row, col);
            // Update the current player's opponentGrid accordingly.
            currentPlayer.getOpponentGrid().setStatus(row, col, hit ? Constants.HIT : Constants.MISSED);
            
            // Update the opponent board button.
            opponentBoardButtons[row][col].setText(hit ? "X" : "O");
            opponentBoardButtons[row][col].setEnabled(false);
            
            JOptionPane.showMessageDialog(GamePanel.this, "Your guess was a " + (hit ? "hit!" : "miss."));
            
            if (hit && checkwin(opponent)) {
                JOptionPane.showMessageDialog(GamePanel.this, "Player " + currentGamePlayer + " has won!");
                freezeGame();
                return; // End the game; no further turn switching.
            }
            // End turn: switch players.
            endTurn();
        }
        
        private void endTurn() {
            // Switch the active player.
            currentGamePlayer = (currentGamePlayer == 1) ? 2 : 1;
            
            // Prepare the Pass-and-Play screen message.
            passAndPlayPanel.setMessage("Turn over. Pass the computer to Player " + currentGamePlayer + " and click Continue.");
            cardLayout.show(mainPanel, "PassAndPlay");
        }
    }
    
    // ---------------------------------------------------------------------
    // Main Method
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BattleshipGUI();
            }
        });
    }
    
    //write code to see if all of the opponents ships have been sunk. 
    private boolean checkwin(Player opponent) {
        return opponent.getMyGrid().allShipsSank(); 
    }
}
