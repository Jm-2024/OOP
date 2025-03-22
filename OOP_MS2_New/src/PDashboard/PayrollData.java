/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PDashboard;

import Payroll.Plogin;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 *
 * @author bacjssemillano
 */
public class PayrollData extends javax.swing.JFrame {

    private Component frame;

    /**
     * Creates new form PayrollData
     */
    public PayrollData() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jsettings = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        EID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jepass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jsettings.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menu", "Logout" }));
        jsettings.setToolTipText("");
        jsettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsettingsActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextPane1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jTextPane1.setText("Welcome to Your Payroll Data");
        jTextPane1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTextPane1);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Enter your Employee Number");

        EID.setToolTipText("Enter your Employee Number");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Enter your Password");

        jepass.setToolTipText("Use your correct password");
        jepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jepassActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setText("Dashboard");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(jsettings, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(EID, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel2))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jepass, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jsettings, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(EID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(jepass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jsettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsettingsActionPerformed

        String selectedItem = (String) jsettings.getSelectedItem();

        if (selectedItem.equals("Logout")) {
            logout();

        }
    }//GEN-LAST:event_jsettingsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String eid = EID.getText().trim();
        String Pass = jepass.getText().trim();

        if ("".equals(eid) && "".equals(Pass)) {
            JOptionPane.showMessageDialog(frame, "Please enter an Employee ID and Password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection parameters
        String SUrl = "jdbc:MySQL://127.0.0.1:3307/oopmsta_database";
        String SUser = "root";
        String SPass = "";

        try {
            // Establish the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            Statement rt = con.createStatement();

            // Query to fetch employee record based on Employee ID (eid)
            String query = "SELECT * FROM employees WHERE EmployeeID = '" + eid + "' ";
            String queries = "SELECT * FROM payrollusers WHERE password = '" + Pass + "'";
            ResultSet rs = st.executeQuery(query);
            ResultSet rss = rt.executeQuery(queries);

            // If the record exists, show it
            if (rs.next()&& rss.next()) {
                String fullName = rs.getString("fullname");
                String birthday = rs.getString("birthday");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String sss = rs.getString("sss");
                String pagibig = rs.getString("pagibig");
                String tin = rs.getString("tin");
                String philhealth = rs.getString("philhealth");
                String status = rs.getString("status");
                String position = rs.getString("position");
                String supervisor = rs.getString("supervisor");
                String basicsalary = rs.getString("basicsalary");
                String ricesubsidy = rs.getString("ricesubsidy");
                String phoneallowance = rs.getString("phoneallowance");
                String clothingallowance = rs.getString("clothingallowance");
                String grossrate = rs.getString("grossrate");
                String hourrate = rs.getString("hourrate");

                // Displaying the result in a message dialog
                String message = "YOUR EMPLOYEE RECORD: \n" +
                "Full Name: " + fullName + "\n" +
                "Birthday: " + birthday + "\n" +
                "Address: " + address + "\n" +
                "Contact: " + contact + "\n" +
                "SSS: " + sss + "\n" +
                "Pag-IBIG: " + pagibig + "\n" +
                "TIN: " + tin + "\n" +
                "Philhealth: " + philhealth + "\n" +
                "Status: " + status + "\n" +
                "Position: " + position + "\n" +
                "Supervisor: " + supervisor + "\n" +
                "Basic Salary: " + basicsalary + "\n" +
                "Rice Subsidy: " + ricesubsidy + "\n" +
                "Phone Allowance: " + phoneallowance + "\n" +
                "Clothing Allowance: " + clothingallowance + "\n" +
                "Gross Rate: " + grossrate + "\n" +
                "Hour Rate: " + hourrate;

                JOptionPane.showMessageDialog(frame, message, "Employee Record", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            st.close();
            rt.close();
            rss.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        EID.setText("");
        jepass.setText("");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        Pdashboard HomeFrame = new Pdashboard();
        HomeFrame.setVisible(true);
        HomeFrame.pack();
        HomeFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jepassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jepassActionPerformed

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
            java.util.logging.Logger.getLogger(PayrollData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayrollData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayrollData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayrollData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PayrollData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPasswordField jepass;
    private javax.swing.JComboBox<String> jsettings;
    // End of variables declaration//GEN-END:variables

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", 
                "Log Out", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Perform logout actions, e.g., close the app or show login screen
            JOptionPane.showMessageDialog(this, "You have been logged out.");
                    
                Plogin HomeFrame = new Plogin();
                HomeFrame.setVisible(true);
                HomeFrame.pack();
                HomeFrame.setLocationRelativeTo(null); 
                this.dispose();

        }
    }

}
