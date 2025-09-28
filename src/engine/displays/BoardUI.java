package engine.displays;

import engine.actors.Player;
import engine.positions.GameEngine;
import engine.positions.SquareBoard;
import engine.positions.Cell;
import engine.actors.Worker;
import engine.structures.Structure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * GUI component for displaying and interacting with the Santorini game board,
 * including styled mode buttons.
 */
public class BoardUI extends JFrame {
    private static final int GRID_SIZE = 5;
    private static final Dimension CELL_DIMENSION = new Dimension(100, 100);
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private static final Color FOREGROUND_COLOR = new Color(230, 230, 230);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(50, 50, 50);
    private static final Color BUTTON_SELECTED_COLOR = new Color(0, 120, 215);
    private static final Color DEFAULT_CELL_COLOR = Color.LIGHT_GRAY;
    private static final Color PLAYER1_WORKER_COLOR = new Color(220,  20,  60);  // crimson red
    private static final Color PLAYER2_WORKER_COLOR = new Color( 65, 105, 225);  // royal blue


    private final JButton[][] boardButtons = new JButton[GRID_SIZE][GRID_SIZE];
    private final SquareBoard board;
    private final JLabel statusLabel = createLabel("Starting game...", 18, FOREGROUND_COLOR);
    private final JLabel errorLabel = createLabel("", 14, Color.RED);
    private final JLabel timerLabel1 = createLabel("", 14, FOREGROUND_COLOR);
    private final JLabel timerLabel2 = createLabel("", 14, FOREGROUND_COLOR);
    private final JButton resignButton = createControlButton("Resign", this::handleResign);
    private final JButton endTurnButton = createControlButton("End Turn", () -> GameEngine.getInstance().switchTurn());

    public enum BuildMode { NORMAL, WALL, BREAK }
    private BuildMode buildMode = BuildMode.NORMAL;

    public void setBuildMode(BuildMode mode) {
        this.buildMode = mode;
        statusLabel.setText("Mode: " + (mode == BuildMode.WALL  ? "Wall Build"
                : (mode == BuildMode.BREAK? "Break Wall" : "Tower Build")));
    }

    public boolean isWallMode() { return buildMode == BuildMode.WALL; }
    public boolean isBreakMode() { return buildMode == BuildMode.BREAK; }

    public BoardUI(SquareBoard board) {
        this.board = board;
        initializeUI();
        updateBoard();
    }

    private void initializeUI() {
        setTitle("Santorini Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createBoardPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND_COLOR);

        JPanel timerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        timerPanel.setBackground(BACKGROUND_COLOR);
        timerPanel.add(timerLabel1);
        timerPanel.add(timerLabel2);
        topPanel.add(timerPanel);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.setBackground(BACKGROUND_COLOR);
        modePanel.add(createLabel("Build Mode:", 14, FOREGROUND_COLOR));

        JToggleButton towerBtn = createModeButton("Tower", BuildMode.NORMAL);
        JToggleButton wallBtn  = createModeButton("Wall", BuildMode.WALL);
        JToggleButton breakBtn = createModeButton("Break", BuildMode.BREAK);

        ButtonGroup group = new ButtonGroup();
        group.add(towerBtn);
        group.add(wallBtn);
        group.add(breakBtn);
        towerBtn.setSelected(true);

        modePanel.add(towerBtn);
        modePanel.add(wallBtn);
        modePanel.add(breakBtn);
        topPanel.add(modePanel);

        statusLabel.setBackground(BACKGROUND_COLOR);
        statusLabel.setOpaque(true);
        topPanel.add(statusLabel);
        topPanel.add(errorLabel);
        return topPanel;
    }

    private JToggleButton createModeButton(String text, BuildMode mode) {
        JToggleButton btn = new JToggleButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(FOREGROUND_COLOR);
        btn.setBackground(BUTTON_BACKGROUND_COLOR);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                setBuildMode(mode);
                btn.setBackground(BUTTON_SELECTED_COLOR);
            } else {
                btn.setBackground(BUTTON_BACKGROUND_COLOR);
            }
        });
        return btn;
    }

    private JPanel createBoardPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        gridPanel.setBackground(BACKGROUND_COLOR);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = createStyledButton();
                button.setPreferredSize(CELL_DIMENSION);
                int r = row, c = col;
                button.addActionListener(e -> handleCellClick(r, c));
                boardButtons[row][col] = button;
                gridPanel.add(button);
            }
        }
        JPanel outer = new JPanel(new FlowLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        outer.setBackground(BACKGROUND_COLOR);
        outer.add(gridPanel);
        return outer;
    }

    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(BACKGROUND_COLOR);
        bottom.add(endTurnButton);
        bottom.add(resignButton);
        return bottom;
    }

    private JButton createControlButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(FOREGROUND_COLOR);
        btn.setBackground(BUTTON_BACKGROUND_COLOR);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(100, 40));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JButton createStyledButton() {
        JButton button = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(FOREGROUND_COLOR);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private static JLabel createLabel(String text, int size, Color color) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, size));
        lbl.setForeground(color);
        lbl.setBackground(BACKGROUND_COLOR);
        lbl.setOpaque(true);
        return lbl;
    }

    public void updateBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Cell cell = board.getCell(row, col);
                Worker w = board.getWorkerAt(cell);
                Structure s = cell.getStructure();
                JButton btn = boardButtons[row][col];
                StringBuilder txt = new StringBuilder();
                if (s != null && !s.getLabel().isEmpty()) {
                    txt.append(s.getLabel());
                    btn.setBackground(s.getColor());
                } else {
                    btn.setBackground(DEFAULT_CELL_COLOR);
                }
                if (w != null) {
                    // 1) Append the worker’s ID text
                    if (!txt.isEmpty()) txt.append("\n");
                    txt.append(w.getId());

                    // 2) Pick a color based on the ID prefix (e.g. “P1” vs “P2”)
                    if (w.getId().startsWith("P1")) {
                        btn.setBackground(PLAYER1_WORKER_COLOR);
                    } else {
                        btn.setBackground(PLAYER2_WORKER_COLOR);
                    }
                }
                btn.setText(txt.toString());
            }
        }
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    public void clearError() {
        errorLabel.setText("");
    }

    public void updateTimer(Player player, String timeString) {
        JLabel target = (GameEngine.getInstance().getPlayers().indexOf(player) == 0)
                ? timerLabel1 : timerLabel2;
        target.setText(player.getNAME() + ": " + timeString);
    }

    public void showGameOver(String message) {
        JOptionPane.showMessageDialog(this, message);
        endTurnButton.setEnabled(false);
        resignButton.setEnabled(false);
        System.exit(0);
    }

    private void handleResign() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to resign?",
                "Confirm Resign",
                JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) return;

        GameEngine engine = GameEngine.getInstance();
        String name = engine.getCurrentPlayer().getNAME();
        JOptionPane.showMessageDialog(this, name + " has resigned!");
        engine.removeCurrentPlayer();

        // announce winner if there is one
        if (engine.getPlayers().size() == 1) {
            String winner = engine.getPlayers().get(0).getNAME();
            JOptionPane.showMessageDialog(this, winner + " wins by default!");
        }

        // then always exit
        System.exit(0);
    }

    private void handleCellClick(int row, int col) {
        GameEngine.getInstance().takeTurn(row, col);
        updateBoard();
    }
}
