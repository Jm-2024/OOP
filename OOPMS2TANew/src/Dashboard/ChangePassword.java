package Dashboard;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Base class for database connection
class DatabaseConnection {
    protected Connection conn;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/oopmsta_database", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Encapsulation: User class with private attributes
class User {
    private String username;
    private String oldPassword;
    private String newPassword;

    public User(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

// GUI class
public class ChangePassword extends DatabaseConnection {
    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtOldPassword, txtNewPassword;
    private JButton btnChangePassword, btnOK;

    public ChangePassword() {
        frame = new JFrame("Change Password");
        frame.setSize(350, 250);
        frame.setLayout(new GridLayout(5, 2));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        frame.add(txtUsername);

        frame.add(new JLabel("Old Password:"));
        txtOldPassword = new JPasswordField();
        frame.add(txtOldPassword);

        frame.add(new JLabel("New Password:"));
        txtNewPassword = new JPasswordField();
        frame.add(txtNewPassword);

        btnChangePassword = new JButton("Change Password");
        frame.add(btnChangePassword);
        
                // OK Button
        btnOK = new JButton("OK");
        frame.add(btnOK);
        
        // OK Button action - Closes the window
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the window
            }
        });              

        btnChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String oldPass = new String(txtOldPassword.getPassword()).trim();
                String newPass = new String(txtNewPassword.getPassword()).trim();

                if (username.isEmpty() || oldPass.isEmpty() || newPass.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = new User(username, hashPassword(oldPass), hashPassword(newPass));
                changePassword(user);
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }

    // Polymorphic method
    public void changePassword(User user) {
        if (verifyOldPassword(user)) {
            try {
                String query = "UPDATE payrollusers SET password = ? WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, user.getNewPassword());
                stmt.setString(2, user.getUsername());
                int updated = stmt.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(frame, "Password changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error updating password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                stmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                closeConnection();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Old password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to verify old password
    public boolean verifyOldPassword(User user) {
        try {
            String query = "SELECT * FROM payrollusers WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getOldPassword());
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new ChangePassword();
    }

}
