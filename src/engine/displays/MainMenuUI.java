package engine.displays;

import game.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The main menu window for the Santorini game.
 * <p>
 * Displays a title, an image panel on the left, and start/exit buttons on the right.
 * Buttons have hover effects and trigger game start or application exit.
 * </p>
 */
public class MainMenuUI extends JFrame {

    /** Color used for the title text. */
    private static final Color TITLE_COLOR = new Color(255, 200, 120);
    /** Foreground color for buttons in their normal state. */
    private static final Color BUTTON_FOREGROUND = new Color(230, 180, 90);
    /** Background color for buttons in their normal state. */
    private static final Color BUTTON_BACKGROUND = new Color(30, 30, 30);
    /** Foreground color for buttons on mouse hover. */
    private static final Color HOVER_FOREGROUND = new Color(255, 230, 140);
    /** Background color for buttons on mouse hover. */
    private static final Color HOVER_BACKGROUND = new Color(50, 50, 50);
    /** Border color for buttons on mouse hover. */
    private static final Color HOVER_BORDER = new Color(255, 255, 180);

    /**
     * Constructs and displays the main menu UI.
     * <p>
     * Sets up the frame, adds the image and right-side panels, and makes it visible.
     * </p>
     */
    public MainMenuUI() {
        setupFrame();
        add(createImagePanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Configures frame properties: title, default close operation,
     * size, layout, and centering on screen.
     */
    private void setupFrame() {
        setTitle("Santorini - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Builds the left panel displaying the game’s artwork.
     *
     * @return a JPanel containing the scaled image
     */
    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(10, 10, 10));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        imagePanel.setPreferredSize(new Dimension(340, 520));
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));

        ImageIcon icon = new ImageIcon("src/resources/minotaur.png");
        Image scaledImage = icon.getImage()
                .getScaledInstance(300, 500, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        return imagePanel;
    }

    /**
     * Builds the right panel containing the game title and control buttons.
     *
     * @return a JPanel with title label, Start Game, and Exit buttons
     */
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(5, 5, 5),
                        0, getHeight(), new Color(30, 30, 30)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = createTitleLabel();

        JButton startButton = createDarkGodButton("Start Game", () -> {
            dispose();
            Game.startGame();
        });
        JButton exitButton = createDarkGodButton("Exit", () -> System.exit(0));

        rightPanel.add(Box.createVerticalStrut(60));
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createVerticalStrut(70));
        rightPanel.add(startButton);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(exitButton);

        return rightPanel;
    }

    /**
     * Creates the title label with custom font and alignment.
     *
     * @return a centered JLabel for the game title
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Santorini: Rise of Gods");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return titleLabel;
    }

    /**
     * Creates a styled button with hover effects.
     *
     * @param text   the button text
     * @param action the action to invoke on click
     * @return a JButton with custom styling and hover behavior
     */
    private JButton createDarkGodButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(BUTTON_FOREGROUND);
        button.setPreferredSize(new Dimension(220, 50));
        button.setMaximumSize(new Dimension(220, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(BUTTON_FOREGROUND, 2));
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.addActionListener(e -> action.run());

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(HOVER_FOREGROUND);
                button.setBackground(HOVER_BACKGROUND);
                button.setBorder(BorderFactory.createLineBorder(HOVER_BORDER, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(BUTTON_FOREGROUND);
                button.setBackground(BUTTON_BACKGROUND);
                button.setBorder(BorderFactory.createLineBorder(BUTTON_FOREGROUND, 2));
            }
        });

        return button;
    }
}
