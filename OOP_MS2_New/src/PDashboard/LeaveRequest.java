package PDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class LeaveRequest extends JFrame {
    private JTextField txtEID, txtName, txtStartDate, txtEndDate, txtRequestID;
    private JComboBox<String> cbLeaveType, cbStatus;
    private JTextArea txtReason;
    private JButton btnSubmit, btnViewRequests, btnUpdateStatus , okButton;
    private Connection conn;

    public LeaveRequest() {
        setTitle("Leave Request System");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 5, 5));

        // Form components
        
        add(new JLabel("Employee ID"));
        txtEID = new JTextField();
        add(txtEID);
        
        add(new JLabel("Employee Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Leave Type:"));
        String[] leaveTypes = {"Sick Leave", "Vacation Leave", "Emergency Leave"};
        cbLeaveType = new JComboBox<>(leaveTypes);
        add(cbLeaveType);

        add(new JLabel("Start Date (MM-DD-YYYY):"));
        txtStartDate = new JTextField();
        add(txtStartDate);

        add(new JLabel("End Date (MM-DD-YYYY):"));
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
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/oopmsta_database", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

private void submitLeaveRequest() {
    try {
        // Trim and fetch values
        String employeeID = txtEID.getText().trim();
        String employeeName = txtName.getText().trim();
        String leaveType = (cbLeaveType.getSelectedItem() != null) ? cbLeaveType.getSelectedItem().toString().trim() : "";
        String startDate = txtStartDate.getText().trim();
        String endDate = txtEndDate.getText().trim();
        String reason = txtReason.getText().trim();

        // Check if any required field is empty
        if (employeeID.isEmpty() || employeeName.isEmpty() || leaveType.isEmpty() || 
            startDate.isEmpty() || endDate.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return; // Stop execution if validation fails
        }

        // Prepare SQL query
        String query = "INSERT INTO leaverequest (employeeid, employee_name, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, employeeID);
        stmt.setString(2, employeeName);
        stmt.setString(3, leaveType);
        stmt.setString(4, startDate);
        stmt.setString(5, endDate);
        stmt.setString(6, reason);

        // Execute update
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
        ResultSet rs = stmt.executeQuery("SELECT id, employeeid, employee_name, leave_type, start_date, end_date, reason, status FROM leaverequest");

        // Define column names
        String[] columnNames = {"Request ID", "Employee ID", "Employee Name", "Leave Type", "Start Date", "End Date", "Reason", "Status"};

        // Create table model with predefined column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Add rows to the table
        while (rs.next()) {
            Object[] row = {
                rs.getInt("id"),
                rs.getString("employeeid"),
                rs.getString("employee_name"),
                rs.getString("leave_type"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getString("reason"),
                rs.getString("status")
            };
            model.addRow(row);
        }

        // Create JTable with the model
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto-resizing
        table.getTableHeader().setResizingAllowed(true); // Allow manual resizing
        table.getTableHeader().setReorderingAllowed(false); // Prevent reordering

        // Adjust column width dynamically
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(150); // Adjust width as needed
        }

        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Set preferred size

        // Show the table in a resizable JOptionPane
        JOptionPane.showMessageDialog(this, scrollPane, "Leave Requests", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error Fetching Requests!", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


private void updateLeaveStatus() {
    try {
        // Validate input fields
        String requestIDText = txtRequestID.getText().trim();
        String selectedStatus = (cbStatus.getSelectedItem() != null) ? cbStatus.getSelectedItem().toString().trim() : "";

        if (requestIDText.isEmpty() || selectedStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Request ID and Status cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return; // Stop execution if fields are empty
        }

        int requestID = Integer.parseInt(requestIDText); // Convert to integer

        // SQL update query
        String query = "UPDATE leaverequest SET status = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, selectedStatus);
        stmt.setInt(2, requestID);

        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Leave Request Status Updated!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Request ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid Request ID format!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error Updating Status!", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        new LeaveRequest();
    }
}
