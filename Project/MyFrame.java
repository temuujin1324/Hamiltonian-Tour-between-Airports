package Project;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.Border;
import javax.swing.event.SwingPropertyChangeSupport;

public class MyFrame extends JFrame {

    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton button = new JButton();
    static JTextField textField = new JTextField();
    static String usertext = "";
    static String[] SplicedInput;

    public static void CreateFrame() {
        frame = new JFrame();
        panel = new JPanel();
        frame.setTitle("TEAM 7!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(700, 500);

        frame.getContentPane().setBackground(new Color(123, 50, 250));
        frame.add(panel);

        panel.setLayout(null);
        panel.setBackground(new Color(123, 50, 250));

        textField = new JTextField("Please enter your destinations seperated by commas!", 100);
        textField.setBounds(100, 200, 500, 30);

        panel.add(textField);

        button = new JButton("Submit");
        button.setBounds(120, 250, 100, 40);
        button.addActionListener((e) -> {
            submitAction();
        });
        panel.add(button);

        frame.setVisible(true);

    }

    public static void submitAction() {
        // You can do some validation here before assign the text to the variable
        usertext = textField.getText();
        receive(usertext);
    }

    public static String[] receive(String input) {

        SplicedInput = usertext.split(",");

        for (int i = 0; i < SplicedInput.length; i++) {

            System.out.println(SplicedInput[i]);

        }
        return SplicedInput;
    }

    public static void main(String[] args) {
        CreateFrame();
        submitAction();
        System.out.println(Arrays.toString(SplicedInput));

    }

}