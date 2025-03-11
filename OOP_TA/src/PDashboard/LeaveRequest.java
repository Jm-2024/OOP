package PDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LeaveRequest extends JFrame {
    private JTextField txtName, txtStartDate, txtEndDate, txtRequestID;
    private JComboBox<String> cbLeaveType, cbStatus;
    private JTextArea txtReason;
    private JButton btnSubmit, btnViewRequests, btnUpdateStatus , okButton;
    private Connection conn;

    public LeaveRequest() {
        setTitle("Leave Request System");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 5, 5));

        // Form components
        add(new JLabel("Employee Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Leave Type:"));
        String[] leaveTypes = {"Sick Leave", "Vacation Leave", "Emergency Leave"};
        cbLeaveType = new JComboBox<>(leaveTypes);
        add(cbLeaveType);

        add(new JLabel("Start Date (YYYY-MM-DD):"));
        txtStartDate = new JTextField();
        add(txtStartDate);

        add(new JLabel("End Date (YYYY-MM-DD):"));
        txtEndDate = new JTextField();
        add(txtEndDate);

        add(new JLabel("Reason:"));
        txtReason = new JTextArea(3, 20);
        add(new JScrollPane(txtReason));

        btnSubmit = new JButton("Submit Request");
        btnViewRequests = new JButton("View Requests");
        add(btnSubmit);
        add(btnViewRequests);

        // Status update section
        add(new JLabel("Request ID to Update:"));
        txtRequestID = new JTextField();
        add(txtRequestID);

        add(new JLabel("New Status:"));
        String[] statuses = {"Pending", "Approved", "Rejected"};
        cbStatus = new JComboBox<>(statuses);
        add(cbStatus);

        btnUpdateStatus = new JButton("Update Status");
        okButton = new JButton("OK");
        add(btnUpdateStatus);
        add(okButton);

        connectDatabase();

        // Button Listeners
        btnSubmit.addActionListener(e -> submitLeaveRequest());
        btnViewRequests.addActionListener(e -> viewLeaveRequests());
        btnUpdateStatus.addActionListener(e -> updateLeaveStatus());
        okButton.addActionListener(e -> this.dispose());

        setVisible(true);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/motorph_database", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void submitLeaveRequest() {
        try {
            String query = "INSERT INTO leave_requests (employee_name, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, txtName.getText());
            stmt.setString(2, cbLeaveType.getSelectedItem().toString());
            stmt.setString(3, txtStartDate.getText());
            stmt.setString(4, txtEndDate.getText());
            stmt.setString(5, txtReason.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Leave Request Submitted Successfully!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Submission Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void viewLeaveRequests() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM leave_requests");

            StringBuilder result = new StringBuilder("Leave Requests:\n");
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Name: ").append(rs.getString("employee_name"))
                      .append(", Type: ").append(rs.getString("leave_type"))
                      .append(", Status: ").append(rs.getString("status"))
                      .append("\n");
            }
            JOptionPane.showMessageDialog(this, result.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Requests!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateLeaveStatus() {
        try {
            String query = "UPDATE leave_requests SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cbStatus.getSelectedItem().toString());
            stmt.setInt(2, Integer.parseInt(txtRequestID.getText()));

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Leave Request Status Updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Request ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error Updating Status!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LeaveRequest();
    }
}
