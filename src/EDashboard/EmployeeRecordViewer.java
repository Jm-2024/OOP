/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDashboard;

import javax.swing.*;
import java.sql.*;

public class EmployeeRecordViewer {
    public static void main(String[] args) {
        // Initialize the frame, inputs, and buttons
        JFrame frame = new JFrame();
        JTextField EID = new JTextField(20); // For input of Employee ID
        JButton viewRecordButton = new JButton("View Request");
        JButton EDashboardButton = new JButton("Dashboard");

        // Action listener for the View Record button
        viewRecordButton.addActionListener(e -> {
            String eid = EID.getText().trim();

            if ("".equals(eid)) {
                JOptionPane.showMessageDialog(frame, "Please enter an Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database connection parameters
            String SUrl = "jdbc:MySQL://localhost/motorph_database";
            String SUser = "root";
            String SPass = "";

            try {
                // Establish the connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
                Statement st = con.createStatement();
                Statement rt = con.createStatement();

                // Query to fetch employee record based on Employee ID (eid)
                String query = "SELECT * FROM leave_requests WHERE EmployeeID = '" + eid + "'";
                String queries = "SELECT * FROM leaverequest WHERE reply = ?";
                ResultSet rs = st.executeQuery(query);
                ResultSet rss = rt.executeQuery(queries);

                // If the record exists, show it
                if (rs.next()) {
                    String fullName = rs.getString("fullname");
                    String reply = rs.getString("reply");
                    String status = rs.getString("status");



                    // Displaying the result in a message dialog
                    String message = "Employee Record: \n" +
                            "Full Name: " + fullName + "\n" +
                            "Reply: " + reply + "\n" +
                            "Status: " + status;

                    JOptionPane.showMessageDialog(frame, message, "Employee Record", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set up the frame layout and components
        frame.setLayout(new java.awt.FlowLayout());
        frame.add(new JLabel("Enter Employee ID:"));
        frame.add(EID);
        frame.add(viewRecordButton);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
