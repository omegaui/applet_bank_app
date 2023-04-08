
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    <applet code="App" width="380" height="600">
    </applet>
 */

public class App extends Applet {
    @Override
    public void init() {
        setLayout(new CardLayout());

        // Home Screen
        Panel homeScreenPanel = new Panel(null);

        Label appTitle = new Label("Bank App");
        appTitle.setBounds(100, 30, 200, 50);
        appTitle.setFont(new Font("Consolas", Font.BOLD, 34));
        homeScreenPanel.add(appTitle);

        Button loginButton = new Button("Login");
        loginButton.setBounds(140, 100, 80, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getLayout()).show(App.this, "login-screen");
            }
        });
        homeScreenPanel.add(loginButton);

        Button registerButton = new Button("Register");
        registerButton.setBounds(140, 150, 80, 30);
        homeScreenPanel.add(registerButton);

        Button exitButton = new Button("Exit");
        exitButton.setBounds(140, 200, 80, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        homeScreenPanel.add(exitButton);

        add(homeScreenPanel, "home-screen");

        // Login Screen
        Panel loginScreenPanel = new Panel(null);
        Label loginTitle = new Label("App Login");
        loginTitle.setBounds(100, 30, 200, 50);
        loginTitle.setFont(new Font("Consolas", Font.BOLD, 34));
        loginScreenPanel.add(loginTitle);

        TextField usernameField = new TextField("Enter your user name");
        usernameField.setBounds(120, 100, 140, 30);
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getLayout()).show(App.this, "login-screen");
            }
        });
        loginScreenPanel.add(usernameField);

        TextField passwordField = new TextField("Enter your password");
        passwordField.setBounds(120, 150, 140, 30);
        loginScreenPanel.add(passwordField);

        Button backButton = new Button("Back");
        backButton.setBounds(150, 200, 80, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getLayout()).show(App.this, "home-screen");
            }
        });
        loginScreenPanel.add(backButton);

        add(loginScreenPanel, "login-screen");
    }
}
