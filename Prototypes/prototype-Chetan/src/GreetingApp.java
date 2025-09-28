import javax.swing.*;
import java.awt.event.*;

public class GreetingApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Greeting App");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 150, 25);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 60, 80, 25);
        JTextField ageField = new JTextField();
        ageField.setBounds(100, 60, 150, 25);

        JButton greetButton = new JButton("Greet");
        greetButton.setBounds(100, 100, 100, 25);

        JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(20, 140, 250, 25);

        greetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String age = ageField.getText();
                messageLabel.setText("Hi " + name + ", you are " + age + " years old.");
            }
        });

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(ageLabel);
        frame.add(ageField);
        frame.add(greetButton);
        frame.add(messageLabel);

        frame.setVisible(true);
    }
}

