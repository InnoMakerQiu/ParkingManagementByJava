package ClientSide.SystemClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * ButtonListenerOfLogin class represents the action listener for the login button.
 * It handles the login process and initiates the main menu window upon successful login.
 */
class ButtonListenerOfLogin implements ActionListener {

    final JFrame jf;

    final JTextField account;

    final JTextField password;

    ButtonListenerOfLogin(JFrame jf, JTextField account, JTextField password) {
        this.jf = jf;
        this.account = account;
        this.password = password;
    }

    /**
     * Handles the action event when the login button is clicked.
     * It performs the authentication process and opens the main menu window upon successful login.
     *
     * @param e The ActionEvent associated with the button click.
     */

    public void actionPerformed(ActionEvent e) {
        String username = this.account.getText();
        String password = this.password.getText();

        // Perform authentication (Replace with your authentication logic)
        if (passwordAuthentication(username, password)) {
            // Open the main menu window upon successful login
            new Welcome(jf);
        } else {
            // Display an error message for incorrect login information
            JOptionPane jop = new JOptionPane();
            JOptionPane.showMessageDialog(jop, "信息输入有误，请重新输入");
        }
    }

    /**
     * Simulates password authentication by sending user credentials to the server.
     * Replace this method with your actual authentication logic.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return True if authentication is successful, false otherwise.
     */
    private boolean passwordAuthentication(String username, String password) {
        // Replace the following lines with your authentication logic
        // (e.g., contacting a server for authentication)
        Client.sendMessage("account:" + username + ", password:" + password);
        String isPassed = Client.receiveMessage();
        System.out.println(isPassed);
        return isPassed != null && isPassed.equals("passed");
    }
}
