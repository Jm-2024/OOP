package PDashboard;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bacjssemillano
 */
public class LeaveRequest extends javax.swing.JFrame {

                private JComboBox<String> dataSelectionComboBox;
                private JTable jview;
                private DefaultTableModel tableModel;
    
    public LeaveRequest() {
        initComponents();   
        
        setTitle("Leave Request");
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
        JButton sendButton = new JButton("Send");
        JButton replyButton = new JButton("Reply");
        JButton dboardButton = new JButton("Dashboard");

        buttonPanel.add(sendButton);
        buttonPanel.add(replyButton);
        buttonPanel.add(dboardButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for the buttons
        sendButton.addActionListener(e -> sendButton());
        replyButton.addActionListener(e -> replyButton());
        dboardButton.addActionListener(e -> dboardData());
        
    
    }


    // Database connection method
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost/motorph_database";
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
        String query = "SELECT * FROM leave_requests"; // Fetch all records

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Get the table model
            tableModel.setRowCount(0); // Clear existing rows
            String[] columnNames = {
                "EmployeeID", "Fullname", "Reason", "Reply", "Status"
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

    // Replying the Leave request 
    private void replyButton() {
int selectedRow = jview.getSelectedRow();
if (selectedRow == -1) {
    JOptionPane.showMessageDialog(this, "Please select a row to update.");
    return;
}

String employeeID = (String) tableModel.getValueAt(selectedRow, 0); // Get the EmployeeID from the selected row
String reply = JOptionPane.showInputDialog("Enter Reply:");
String status = JOptionPane.showInputDialog("Enter Status:");

String query = "UPDATE leave_requests SET reply = ? WHERE status (?, ?)"; // Corrected query

try (Connection connection = getConnection();
     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

    preparedStatement.setString(1, reply);
    preparedStatement.setString(2, status); 
    preparedStatement.setString(3, employeeID); 
    
    int rowsAffected = preparedStatement.executeUpdate();
    if (rowsAffected > 0) {
        JOptionPane.showMessageDialog(this, "Leave Request updated successfully!");
        fetchData("EmployeeID");  // Refresh data after update
    }
} catch (SQLException e) {
    System.out.println("Error updating data: " + e.getMessage());
}

}
    
private void sendButton() {
    // Show message prompting the user to update a leave request
    JOptionPane.showMessageDialog(this, "Create New Leave Request.");

    // Get inputs from the user
    String EID = JOptionPane.showInputDialog("Enter Employee ID:");  // Asking for Employee ID
    String Ename = JOptionPane.showInputDialog("Enter Fullname:");   // Asking for Full Name
    String reasons = JOptionPane.showInputDialog("Enter Reason:");    // Asking for Reason

    // Check if any input is empty
    if (EID == null || EID.trim().isEmpty() || Ename == null || Ename.trim().isEmpty() || reasons == null || reasons.trim().isEmpty()) {
        // Show an error message if any field is empty
        JOptionPane.showMessageDialog(this, "Error: All fields must be filled out. Please enter all required information.");
        return; // Exit the method without proceeding further
    }

    // Correct SQL query to insert leave request information
    String query = "INSERT INTO leave_requests (EmployeeID, fullname, reason) VALUES (?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        // Set the values in the PreparedStatement
        preparedStatement.setString(1, EID);      // Set EmployeeID (EID)
        preparedStatement.setString(2, Ename);    // Set fullname (Ename)
        preparedStatement.setString(3, reasons);  // Set reason (reasons)

        // Execute the update
        int rowsAffected = preparedStatement.executeUpdate();

        // Check if any rows were affected
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Leave request created successfully!");
            fetchData("EmployeeID");  // Refresh data after update using the EmployeeID
        } else {
            JOptionPane.showMessageDialog(this, "Error: No record created. Please check the Employee ID and try again.");
        }

    } catch (SQLException e) {
        System.out.println("Error inserting data: " + e.getMessage());
    }
}

    private void dboardData() {
        
            Pdashboard HomeFrame = new Pdashboard();
            HomeFrame.setVisible(true);
            HomeFrame.pack();
            HomeFrame.setLocationRelativeTo(null); 
            this.dispose();
        
    }
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 219, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeaveRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LeaveRequest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
