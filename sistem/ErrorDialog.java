package sistem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ErrorDialog extends Dialog {

    public ErrorDialog(Frame parent, String message) {
        super(parent,"Error!", false);
        Label labelaGreska = new Label(message, Label.CENTER);
        labelaGreska.setForeground(Color.RED);
        setLayout(new BorderLayout(10, 10));
        add(labelaGreska, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setSize(450, 90);
        setResizable(true);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    public ErrorDialog(Frame parent, String message, Timer timer) {
        super(parent,"Error!", false);
        Label labelaGreska = new Label(message, Label.CENTER);
        labelaGreska.setForeground(Color.RED);
        setLayout(new BorderLayout(10, 10));
        add(labelaGreska, BorderLayout.CENTER);
        Button resetButton = new Button("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.activity();
                dispose();
            }
        });
        add(resetButton, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setSize(450, 90);
        setResizable(true);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
