import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Scanner;
public class App extends JFrame {
    public App() {
        super("Insurance Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setVisible(true);
    }

    public void initUI() {

        Records.load();

        CardLayout layout = new CardLayout();
        setLayout(layout);
        getContentPane().setLayout(layout);
        getContentPane().setBackground(Color.white);

        initHomeScreen();
        initLoginScreen();
        initRegisterScreen();
        initLoggedInScreen();
        initRenewScreen();
    }

    void setUI(Component component) {
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);
        component.setFont(new Font("Consolas", Font.BOLD, 14));

        if(component instanceof JTextField){
            component.setBackground(Color.LIGHT_GRAY);
            component.setForeground(Color.DARK_GRAY);
        } else if(component instanceof JTextArea){
            component.setBackground(Color.GRAY);
            component.setForeground(Color.WHITE);
            component.setFont(new Font("Consolas", Font.PLAIN, 14));
        } else if(component instanceof JButton){
            ((JButton) component).setBorder(null);
            ((JButton) component).setFocusable(false);
        }
    }

    void initHomeScreen() {
        Panel homeScreenPanel = new Panel(null);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(getWidth()/2 - 100/2, 50, 100, 40);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "login-screen");
            }
        });
        homeScreenPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(getWidth()/2 - 100/2, 100, 100, 40);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "register-screen");
            }
        });
        homeScreenPanel.add(registerButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(getWidth()/2 - 100/2, 150, 100, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        homeScreenPanel.add(exitButton);

        add(homeScreenPanel, "home-screen");

        try {
            JLabel logo = new JLabel(new ImageIcon(
                    ImageIO.read(new File("logo.png"))
            ));
            logo.setBounds(getWidth()/2 - 94/2, 200, 94, 94);
            homeScreenPanel.add(logo);

            setUI(loginButton);
            setUI(registerButton);
            setUI(exitButton);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void initLoginScreen() {
        Panel loginScreenPanel = new Panel(null);

        final JTextField idField = new JTextField("Your ID");
        idField.setBounds(getWidth()/2 - 200/2, 50, 200, 40);
        idField.setHorizontalAlignment(SwingConstants.CENTER);
        loginScreenPanel.add(idField);

        final JTextField passwordField = new JTextField("Your Password");
        passwordField.setBounds(getWidth()/2 - 200/2, 100, 200, 40);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        loginScreenPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(getWidth()/2 - 100/2 - 50, 150, 100, 40);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Records.authenticate(idField.getText(), passwordField.getText())){
                    ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "logged-in-screen");
                } else if(!Records.doesUserExists(idField.getText())){
                    idField.setText("Not Registered");
                } else {
                    passwordField.setText("Invalid Password");
                }
            }
        });
        loginScreenPanel.add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(getWidth()/2 - 100/2 + 50, 150, 100, 40);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "home-screen");
            }
        });
        loginScreenPanel.add(backButton);

        add(loginScreenPanel, "login-screen");

        setUI(idField);
        setUI(passwordField);
        setUI(backButton);
    }

    void initRegisterScreen() {
        Panel registerScreenPanel = new Panel(null);

        final JTextField idField = new JTextField("Your ID");
        idField.setBounds(getWidth()/2 - 200/2, 50, 200, 40);
        idField.setHorizontalAlignment(SwingConstants.CENTER);
        registerScreenPanel.add(idField);

        final JTextField passwordField = new JTextField("Your Password");
        passwordField.setBounds(getWidth()/2 - 200/2, 100, 200, 40);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        registerScreenPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(getWidth()/2 - 100/2 - 50, 150, 100, 40);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Records.doesUserExists(idField.getText())){
                    idField.setText("id already exists");
                } else {
                    Records.createUser(idField.getText(), passwordField.getText());
                    ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "home-screen");
                }
            }
        });
        registerScreenPanel.add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(getWidth()/2 - 100/2 + 50, 150, 100, 40);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "home-screen");
            }
        });
        registerScreenPanel.add(backButton);

        add(registerScreenPanel, "register-screen");

        setUI(idField);
        setUI(passwordField);
        setUI(backButton);
    }

    void initLoggedInScreen() {
        Panel loggedInScreenPanel = new Panel(null);
        final JLabel status = new JLabel("Policy Status will be displayed here ");

        JTextArea disclaimerArea = new JTextArea("Health Insurance is an essential\n" +
                "requirement in these times. It provides coverage against \nthe cost of\n" +
                "medical treatment and other associated spends. \n\nSome of the common\n" +
                "items covered under this insurance are hospitalization expenses,\n" +
                "pre-and-post hospitalization expenses, ambulance charges, room rent,\n" +
                "doctor’s consultation expenses, day-care procedure charges,\n" +
                "evacuation charges, critical illness-related expenses, etc.");
        disclaimerArea.setBounds(20, 20, getWidth() - 40, getHeight() - 230);
        loggedInScreenPanel.add(disclaimerArea);

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(getWidth()/2 - 100/2, 200, 100, 40);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Records.buy();
                status.setText(Records.policyStatus());
            }
        });
        loggedInScreenPanel.add(buyButton);

        JButton renewButton = new JButton("Renew");
        renewButton.setBounds(getWidth()/2 - 100/2, 250, 100, 40);
        renewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Records.policyCompleted()){
                    status.setText("Policy Already Completed!");
                } else if(Records.policyCancelled()){
                    status.setText("Policy Cancelled: You have to buy again!");
                } else {
                    ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "renew-screen");
                }
            }
        });
        loggedInScreenPanel.add(renewButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(getWidth()/2 - 100/2, 300, 100, 40);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Records.cancel();
                status.setText(Records.policyStatus());
            }
        });
        loggedInScreenPanel.add(cancelButton);

        add(loggedInScreenPanel, "logged-in-screen");

        status.setBounds(10, getHeight() - 60, getWidth() - 100, 30);
        loggedInScreenPanel.add(status);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(getWidth() - 90, getHeight() - 60, 90, 30);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText(Records.policyStatus());
            }
        });
        loggedInScreenPanel.add(refreshButton);

        JButton backButton = new JButton("Log Out");
        backButton.setBounds(getWidth() - 90, getHeight() - 90, 90, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "home-screen");
            }
        });
        loggedInScreenPanel.add(backButton);

        setUI(disclaimerArea);
        setUI(buyButton);
        setUI(renewButton);
        setUI(cancelButton);
        setUI(backButton);
        setUI(status);
    }

    void initRenewScreen() {
        Panel renewScreenPanel = new Panel(null);
        final JLabel amountStatus = new JLabel("Click Refresh");

        final JTextField amountField = new JTextField("Enter Renew Amount");
        amountField.setBounds(getWidth()/2 - 200/2, 100, 200, 40);
        amountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Records.updateAmount(Double.parseDouble(amountField.getText()));
                amountStatus.setText(Records.amountInfo());
            }
        });
        renewScreenPanel.add(amountField);

        amountStatus.setBounds(getWidth()/2 - 200/2, 150, 200, 40);
        renewScreenPanel.add(amountStatus);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(getWidth() - 90, getHeight() - 60, 90, 30);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountStatus.setText(Records.amountInfo());
            }
        });
        renewScreenPanel.add(refreshButton);
        add(renewScreenPanel, "renew-screen");

        JButton backButton = new JButton("Back");
        backButton.setBounds(0, getHeight() - 60, 90, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "logged-in-screen");
            }
        });
        renewScreenPanel.add(backButton);

        setUI(amountField);
        setUI(amountStatus);
        setUI(refreshButton);
        setUI(backButton);
    }

    public static void main(String[] args) {
        new App();
    }
}

class Records {

    public static User currentUser;

    public static LinkedList<User> users = new LinkedList<>();

    public static boolean doesUserExists(String id) {
        for(User user : users){
            if(user.isIDSame(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean authenticate(String id, String password) {
        for(User user : users){
            if(user.authenticate(id, password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public static void createUser(String id, String password) {
        User user = new User(id, password);
        users.add(user);
        user.save();
    }

    public static void load() {
        File data = new File("users.dat");
        if(!data.exists())
            return;
        try{
            Scanner scanner = new Scanner(data);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String id = line.substring(0, line.indexOf(' '));
                String password = line.substring(line.indexOf(' ') + 1);

                User user = new User(id, password);
                users.add(user);

                File policyFile = new File(id + ".policy");
                if(policyFile.exists()) {
                    Scanner policyReader = new Scanner(policyFile);
                    user.setPolicyStatus(policyReader.nextLine());
                    user.setAmount(policyReader.nextDouble());
                    policyReader.close();
                }
            }
            scanner.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String policyStatus() {
        return currentUser.getPolicyStatus();
    }

    public static void buy() {
        currentUser.setPolicyStatus("Policy Status: Purchased, Incomplete!");
        currentUser.setAmount(0.0);
        currentUser.savePolicy();
    }

    public static void cancel() {
        currentUser.setPolicyStatus("Policy Status: Cancelled!");
        currentUser.savePolicy();
    }

    public static String amountInfo() {
        double diff = 100000 - currentUser.getAmount();
        if(diff <= 0){
            currentUser.setPolicyStatus("Policy Status: Completed!");
            currentUser.savePolicy();
            return "Policy Completed";
        }
        return "Amount Left: " + diff;
    }

    public static void updateAmount(double renew) {
        double amt = currentUser.getAmount() + renew;
        currentUser.setAmount(amt);
        currentUser.savePolicy();
    }

    public static boolean policyCompleted() {
        return currentUser.getPolicyStatus().contains("Completed");
    }

    public static boolean policyCancelled() {
        return currentUser.getPolicyStatus().contains("Cancelled");
    }
}


class User {

    String id;
    String password;

    String policyStatus = "Policy Status: Not Purchased Yet!";

    double amount = 0.0;

    User(String id, String password){
        this.id = id;
        this.password = password;
    }

    boolean authenticate(String testID, String testPassword){
        return id.equals(testID) && password.equals(testPassword);
    }

    boolean isIDSame(String otherID){
        return id.equals(otherID);
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    void savePolicy() {
        try {
            PrintWriter writer = new PrintWriter(id + ".policy");
            writer.println(policyStatus);
            writer.println(amount);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void save() {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("users.dat", true));
            writer.println(id + " " + password);
            writer.close();

            savePolicy();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


