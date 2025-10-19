import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Prozor extends Frame {

    private TextField username = new TextField(10);
    private TextField password = new TextField(10);
    private Button login = new Button("Login");


    private void populateWindow(){

        Panel panel = new Panel(new GridLayout(0, 1, 0 ,5)); // proveriti kako koji izgleda
        Panel userPanel = new Panel();
        userPanel.add(new Label("Username:"));
        userPanel.add(username);
        panel.add(userPanel);

        Panel passwordPanel = new Panel();
        passwordPanel.add(new Label("Password:"));
        passwordPanel.add(password);
        panel.add(passwordPanel);


        password.addTextListener((event) -> {
            if(password.getText().length() > 8){
                login.setEnabled(true);
            } else{
                login.setEnabled(false);
            }
        });

        login.setEnabled(false);

        login.addActionListener((event) -> {
            System.out.println("-----------------");
            System.out.println(username.getText());
            System.out.println(password.getText());
        });

        Panel loginPanel = new Panel();
        loginPanel.add(login);
        panel.add(loginPanel);

        add(panel);

    }

    public Prozor() {
        setLocation(700, 200);
        setTitle("Prozor");
        populateWindow();
        pack();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        Prozor prozor = new Prozor();
    }
}
