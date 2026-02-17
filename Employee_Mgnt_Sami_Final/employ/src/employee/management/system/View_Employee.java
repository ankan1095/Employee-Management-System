package employee.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class View_Employee extends JFrame implements ActionListener {

    JTable table;
    Choice choiceEMP;
    JButton searchbtn, print, update, back;

    View_Employee() {

        getContentPane().setBackground(new Color(255, 131, 122));
        setLayout(null);

        JLabel search = new JLabel("Search by Employee ID");
        search.setBounds(20, 20, 180, 20);
        search.setFont(new Font("serif", Font.BOLD, 15));
        add(search);

        choiceEMP = new Choice();
        choiceEMP.setBounds(200, 20, 150, 20);
        add(choiceEMP);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                choiceEMP.add(resultSet.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create table
        table = new JTable();

        // ✅ Show all employee details
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM employee");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Allow full view and scrolling
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // disable auto-resize to show scrollbars
        table.setFillsViewportHeight(true);

        // ✅ Add tooltip to show full cell data on mouse hover
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = table.getValueAt(row, col);
                    if (value != null) {
                        table.setToolTipText(value.toString());
                    } else {
                        table.setToolTipText(null);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 100, 880, 550);
        add(scrollPane);

        // Buttons
        searchbtn = new JButton("Search");
        searchbtn.setBounds(20, 70, 80, 25);
        searchbtn.addActionListener(this);
        add(searchbtn);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 25);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 25);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 70, 80, 25);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchbtn) {
            String query = "SELECT * FROM employee WHERE empId = '" + choiceEMP.getSelectedItem() + "'";
            try {
                conn c = new conn();
                ResultSet resultSet = c.statement.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(choiceEMP.getSelectedItem());

        } else {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
