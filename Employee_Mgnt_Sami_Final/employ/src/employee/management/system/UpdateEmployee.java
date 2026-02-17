package employee.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

/**
 * Fixed UpdateEmployee:
 * - Removed variable shadowing
 * - Corrected column name typos (aadhar)
 * - Populates fields correctly
 * - Performs update and redirects to View_Employee after success
 */
public class UpdateEmployee extends JFrame implements ActionListener {
    // Display (non-editable) labels
    private JLabel lName;
    private JLabel lDob;
    private JLabel lAadhar;
    private JLabel tempid;

    // Editable fields
    private JTextField tfname, taddress, tphone, temail, tsalary, tdesignation, teducation;

    private JButton updateBtn, backBtn;
    private final String empNumber;

    UpdateEmployee(String number) {
        this.empNumber = number;

        setTitle("Update Employee Details");
        getContentPane().setBackground(new Color(163, 255, 188));
        setLayout(null);

        JLabel heading = new JLabel("Update Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // Name (display only)
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50, 150, 150, 30);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(nameLabel);

        lName = new JLabel();
        lName.setBounds(200, 150, 300, 30);
        lName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lName);

        // Father's name (editable)
        JLabel fnameLabel = new JLabel("Father's Name");
        fnameLabel.setBounds(400, 150, 150, 30);
        fnameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(fnameLabel);

        tfname = new JTextField();
        tfname.setBounds(600, 150, 150, 30);
        tfname.setBackground(new Color(177, 252, 197));
        add(tfname);

        // DOB (display only)
        JLabel dobLabel = new JLabel("Date Of Birth");
        dobLabel.setBounds(50, 200, 150, 30);
        dobLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(dobLabel);

        lDob = new JLabel();
        lDob.setBounds(200, 200, 200, 30);
        lDob.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lDob);

        // Salary
        JLabel salaryLabel = new JLabel("Salary");
        salaryLabel.setBounds(400, 200, 150, 30);
        salaryLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(salaryLabel);

        tsalary = new JTextField();
        tsalary.setBounds(600, 200, 150, 30);
        tsalary.setBackground(new Color(177, 252, 197));
        add(tsalary);

        // Address
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(50, 250, 150, 30);
        addressLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(addressLabel);

        taddress = new JTextField();
        taddress.setBounds(200, 250, 150, 30);
        taddress.setBackground(new Color(177, 252, 197));
        add(taddress);

        // Phone
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(400, 250, 150, 30);
        phoneLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(phoneLabel);

        tphone = new JTextField();
        tphone.setBounds(600, 250, 150, 30);
        tphone.setBackground(new Color(177, 252, 197));
        add(tphone);

        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 300, 150, 30);
        emailLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(emailLabel);

        temail = new JTextField();
        temail.setBounds(200, 300, 150, 30);
        temail.setBackground(new Color(177, 252, 197));
        add(temail);

        // Education
        JLabel educationLabel = new JLabel("Highest Education");
        educationLabel.setBounds(400, 300, 180, 30);
        educationLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(educationLabel);

        teducation = new JTextField();
        teducation.setBounds(600, 300, 150, 30);
        teducation.setBackground(new Color(177, 252, 197));
        add(teducation);

        // Designation
        JLabel designationLabel = new JLabel("Designation");
        designationLabel.setBounds(50, 350, 150, 30);
        designationLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(designationLabel);

        tdesignation = new JTextField();
        tdesignation.setBounds(200, 350, 150, 30);
        tdesignation.setBackground(new Color(177, 252, 197));
        add(tdesignation);

        // Aadhar (display only)
        JLabel aadharLabel = new JLabel("Aadhar Number");
        aadharLabel.setBounds(400, 350, 150, 30);
        aadharLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(aadharLabel);

        lAadhar = new JLabel();
        lAadhar.setBounds(600, 350, 150, 30);
        lAadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lAadhar);

        // Employee ID (display)
        JLabel empidLabel = new JLabel("Employee ID");
        empidLabel.setBounds(50, 400, 150, 30);
        empidLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        add(empidLabel);

        tempid = new JLabel();
        tempid.setBounds(200, 400, 150, 30);
        tempid.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        tempid.setForeground(Color.RED);
        add(tempid);

        // Populate data from DB
        populateFields();

        // Buttons
        updateBtn = new JButton("UPDATE");
        updateBtn.setBounds(450, 550, 150, 40);
        updateBtn.setBackground(Color.black);
        updateBtn.setForeground(Color.WHITE);
        updateBtn.addActionListener(this);
        add(updateBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(250, 550, 150, 40);
        backBtn.setBackground(Color.black);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(this);
        add(backBtn);

        setSize(900, 700);
        setLayout(null);
        setLocation(300, 50);
        setVisible(true);
    }

    private void populateFields() {
        try {
            conn c = new conn();
            // Use the same column name that AddEmployee uses for employee id (empid)
            String query = "SELECT * FROM employee WHERE empid = '" + empNumber + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                // display-only fields
                lName.setText(resultSet.getString("name"));
                lDob.setText(resultSet.getString("dob"));
                lAadhar.setText(resultSet.getString("aadhar")); // fixed column name

                // editable fields
                tfname.setText(resultSet.getString("fname"));
                tsalary.setText(resultSet.getString("salary"));
                taddress.setText(resultSet.getString("address"));
                tphone.setText(resultSet.getString("phone"));
                temail.setText(resultSet.getString("email"));
                teducation.setText(resultSet.getString("education"));
                tdesignation.setText(resultSet.getString("designation"));

                // emp id display
                tempid.setText(resultSet.getString("empid"));
            } else {
                JOptionPane.showMessageDialog(null, "No employee found with id: " + empNumber);
                setVisible(false);
                new View_Employee();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading employee details.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateBtn) {
            String fname = tfname.getText().trim();
            String salary = tsalary.getText().trim();
            String address = taddress.getText().trim();
            String phone = tphone.getText().trim();
            String email = temail.getText().trim();
            String education = teducation.getText().trim();
            String designation = tdesignation.getText().trim();

            // Basic validation (you can extend it)
            if (fname.isEmpty() || salary.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all required fields.");
                return;
            }

            try {
                conn c = new conn();
                String query = "UPDATE employee SET fname = '" + fname + "', salary = '" + salary + "', address = '" + address + "', phone = '" + phone + "', email = '" + email + "', education = '" + education + "', designation = '" + designation + "' WHERE empid = '" + empNumber + "'";
                c.statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details updated successfully");

                // Close this window and return to View_Employee (so admin sees updated list)
                setVisible(false);
                new View_Employee();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Update failed. Check console for details.");
            }
        } else if (e.getSource() == backBtn) {
            setVisible(false);
            new View_Employee();
        }
    }

    public static void main(String[] args) {
        // pass a test id here or call this from your View_Employee as usual
        new UpdateEmployee("1001");
    }
}
