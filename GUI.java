import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;

import javax.swing.*;

public class GUI {
    JTextArea textArea;
    JTextField textField;
    JFrame frame;
    private DataOutputStream out;
    private boolean hasIncomingFile = false;
    private String requestedFile;

    public void loadGUI() {
        buildGUI();
    }

    private void buildGUI() {
        frame = new JFrame("P2P APP");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        frame.getContentPane().add(body);

        textArea = new JTextArea(30, 30);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        body.add(scroll);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        body.add(inputPanel);

        textField = new JTextField(25);
        inputPanel.add(textField);
        JButton button = new JButton("Send");
        inputPanel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                transmitMessage(textField.getText());                     
            } 
        });

        textField.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent arg0) {
                if (arg0.getKeyCode()==KeyEvent.VK_ENTER) {
                    transmitMessage(textField.getText());   
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {}

            @Override
            public void keyTyped(KeyEvent arg0) {}
        });
        

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void setOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public void addToScreen(String message, boolean fromServer) {
        
        if (!fromServer) {
            updateTextArea("ME: " + message);
            textField.setText("");
        } else {
            updateTextArea(message);
        }
    }

    public void updateTextArea(String message) {
        textArea.append(message + "\n");
    }

    private void transmitMessage(String message) {
        try {
            if (message.trim().toLowerCase().startsWith("!download")) {
                String fileName = message.split(" ")[1];
                requestedFile = fileName;
            } 
            out.writeBytes(message + "\r\n");
            addToScreen(message, false);
        } catch (Exception e) {
            addToScreen("Message failed to send...", false);
        }
    }

    public String getRequestedFile() {
        return requestedFile;
    }

    public void resetHasIncomingFile() {
        hasIncomingFile = false;
    }

    public boolean hasIncomingFile() {
        return hasIncomingFile;
    }
}
