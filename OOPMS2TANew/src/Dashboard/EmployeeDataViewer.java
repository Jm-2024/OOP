package Dashboard;

import Dashboard.PDashboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeDataViewer extends JFrame {

    private JComboBox<String> dataSelectionComboBox;
    private JTable jview;
    private DefaultTableModel tableModel;

    public EmployeeDataViewer() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Employee Data");
        setSize(1000, 600);
        setLayout(new BorderLayout());

        // Create JComboBox with columns to view
        String[] dataOptions = {"View"};
        dataSelectionComboBox = new JComboBox<>(dataOptions);
        add(dataSelectionComboBox, BorderLayout.NORTH);

        // Create JTable with empty data
        tableModel = new DefaultTableModel();
        jview = new JTable(tableModel);
        add(new JScrollPane(jview), BorderLayout.CENTER);

        // Add ActionListener to JComboBox to fetch and display data
        dataSelectionComboBox.addActionListener(e -> {
            String selectedOption = (String) dataSelectionComboBox.getSelectedItem();
            fetchData(selectedOption);
        });

        // Create Insert, Update, Delete buttons
        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton dboardButton = new JButton("Dashboard");

        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(dboardButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for the buttons
        updateButton.addActionListener(e -> updateData());
        deleteButton.addActionListener(e -> deleteData());
        dboardButton.addActionListener(e -> dboardData());
        
    }

    // Database connection method
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3307/oopmsta_database";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // Fetch data based on selected option (column)
    private void fetchData(String selectedOption) {
        String query = "SELECT * FROM employees"; // Fetch all records

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Get the table model
            tableModel.setRowCount(0); // Clear existing rows
            String[] columnNames = {
                "EmployeeID", "Fullname", "Birthday", "Address", "Contact", "SSS", "Pagibig", "TIN", "PhilHealth",
                "Status", "Position", "Supervisor", "BasicSalary", "RiceSubsidy", "PhoneAllowance",
                "ClothingAllowance", "GrossRate", "HourRate"
            };
            tableModel.setColumnIdentifiers(columnNames);

            // Add fetched data to table
            while (resultSet.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = resultSet.getObject(columnNames[i]);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    // Update an existing record
    private void updateData() {
        int selectedRow = jview.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        String employeeID = (String) tableModel.getValueAt(selectedRow, 0); // Get the EmployeeID from the selected row
        String EmployeeID = JOptionPane.showInputDialog("Enter Employee ID:");
        String fullname = JOptionPane.showInputDialog("Enter Fullname:");
        String birthday = JOptionPane.showInputDialog("Enter Birthday:");
        String address = JOptionPane.showInputDialog("Enter Address:");
        String contact = JOptionPane.showInputDialog("Enter Contact:");
        String sss = JOptionPane.showInputDialog("Enter SSS Number:");
        String pagibig = JOptionPane.showInputDialog("Enter Pag-Ibig Number:");
        String tin = JOptionPane.showInputDialog("Enter TIN Number:");
        String philhealth = JOptionPane.showInputDialog("Enter PhilHealth:");
        String status = JOptionPane.showInputDialog("Enter Status:");
        String position = JOptionPane.showInputDialog("Enter Position:");
        String supervisor = JOptionPane.showInputDialog("Enter Supervisor:");
        String basicsalary = JOptionPane.showInputDialog("Enter Basic Salary:");
        String ricesubsidy = JOptionPane.showInputDialog("Enter Rice Subsidy:");
        String phoneallowance = JOptionPane.showInputDialog("Enter Phone Allowance:");
        String clothingallowance = JOptionPane.showInputDialog("Enter Clothing Allowance:");
        String grossrate = JOptionPane.showInputDialog("Enter Gross Rate:");
        String hourrate = JOptionPane.showInputDialog("Enter Hour Rate:");

        String query = "UPDATE employeerecords SET EmployeeID, fullname, birthday, address, contact, sss, pagibig, tin, "
                + "philhealth, status, position, supervisor, basicsalary, ricesubsidy, phoneallowance, clothingallowance, grossrate, hourrate = ? WHERE EmployeeID, fullname, birthday, address, contact, sss, pagibig, tin, "
                + "philhealth, status, position, supervisor, basicsalary, ricesubsidy, phoneallowance, clothingallowance, grossrate, hourrate = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, EmployeeID);
            preparedStatement.setString(2, fullname);
            preparedStatement.setString(3, birthday);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, contact);
            preparedStatement.setString(6, sss);
            preparedStatement.setString(7, pagibig);
            preparedStatement.setString(8, tin);
            preparedStatement.setString(9, philhealth);
            preparedStatement.setString(10, status);
            preparedStatement.setString(11, position);
            preparedStatement.setString(12, supervisor);
            preparedStatement.setString(13, basicsalary);
            preparedStatement.setString(14, ricesubsidy);
            preparedStatement.setString(15, phoneallowance);
            preparedStatement.setString(16, clothingallowance);
            preparedStatement.setString(17, grossrate);
            preparedStatement.setString(18, hourrate);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
                fetchData("EmployeeID");  // Refresh data after update
            }
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }

    // Delete a record
    private void deleteData() {
        int selectedRow = jview.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        String employeeID = (String) tableModel.getValueAt(selectedRow, 0); // Get the EmployeeID from the selected row

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Delete Record", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM employeerecords WHERE EmployeeID = ?";

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, employeeID);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Record deleted successfully!");
                    fetchData("EmployeeID");  // Refresh data after delete
                }
            } catch (SQLException e) {
                System.out.println("Error deleting data: " + e.getMessage());
            }
        }
    }
    
    private void dboardData() {
        
            PDashboard Dash = new PDashboard();
            Dash.setVisible(true);
            Dash.pack();
            Dash.setLocationRelativeTo(null); 
            this.dispose();
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeDataViewer().setVisible(true);
        });
    }
}
