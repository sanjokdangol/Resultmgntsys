package reultmgntsys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

//scala
//small talk pure object oriented language
public class Student extends JFrame {
    /**
     * define form elements
     */
    JLabel jlb_firstName, jlb_lastName, jlb_address, jlb_phone, jlb_roll;
    JTextField jtf_id, jtf_firstName, jtf_lastName, jtf_address, jtf_phone, jtf_roll;
    JButton addbtn, updatebtn, deletebtn;
    JPanel panel, panelTbl, mainpanel;
    JFrame frame;
    
    /**
     * set null used for sql query
     */
    PreparedStatement pmst = null;
    ResultSet rs = null;

    /**
     * define for jtable;
     */
    DefaultTableModel model;
    JTable table;
    
    /**
     * students proterties
     */
    String firstname, lastname, address;
    int id, roll, phone;

    /**
     * create no-argument constructor
     */
    Student() {
        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table));
        //fetch data from database to manupulate jtable

        getData();

        panel = new JPanel();
        panel.setSize(600, 300);

        //init form elements
        //labels
        jlb_firstName = new JLabel("First Name:");
        jlb_lastName = new JLabel("Last Name:");
        jlb_address = new JLabel("Address:");
        jlb_phone = new JLabel("Phone:");
        jlb_roll = new JLabel("Roll:");
        
        //textfields
        jtf_id = new JTextField(5);
        jtf_firstName = new JTextField(10);
        jtf_lastName = new JTextField(10);
        jtf_address = new JTextField(10);
        jtf_phone = new JTextField(10);
        jtf_roll = new JTextField(10);
        
        //buttons
        addbtn = new JButton("submit");
        updatebtn = new JButton("Update");
        deletebtn = new JButton("Delete");
        
        //addbtn is not using anonymous class so need to init
        addbtn.addActionListener(add);

        /**
         * edit 
         * retrive data from table for update 
         * manupulate textfield
         * using anonymous class
         */
        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                String id = model.getValueAt(row, 0).toString();
                firstname = model.getValueAt(row, 1).toString();
                lastname = model.getValueAt(row, 2).toString();
                address = model.getValueAt(row, 3).toString();

                System.out.println(id);
                jtf_id.setText(id);
                jtf_firstName.setText(firstname);
                jtf_lastName.setText(lastname);
                jtf_address.setText(address);

            }

            //used just to satisfy MouseListener interface
            //i don't know the solution without overriding these abstract methods
            //find if you can
            public void mousePressed(MouseEvent me) {
            };

            public void mouseReleased(MouseEvent me) {
            };

            public void mouseEntered(MouseEvent me) {
            };

            public void mouseExited(MouseEvent me) {
            };

        });
        
        /**
         * update existing student
         */
        updatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Connect connect = new Connect();
                    Connection con = connect.getConnect();

                    String sql = "UPDATE students "
                            + "SET firstname=?, lastname=?, address=? "
                            + "WHERE id=?";
                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, jtf_firstName.getText());
                    pmst.setString(2, jtf_lastName.getText());
                    pmst.setString(3, jtf_address.getText());
                    pmst.setString(4, jtf_id.getText());
                    int response = pmst.executeUpdate();
                    if (response > 0) {
                        //manupulate new data in jtable
                        getData();
                        JOptionPane.showMessageDialog(null, "Successfully Updated, Success Alert!");
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });

        deletebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    Connect connect = new Connect();
                    Connection con = connect.getConnect();
                    String sql = "DELETE FROM students WHERE id=?";

                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, jtf_id.getText());
                    int response = pmst.executeUpdate();

                    if (response > 0) {
                        getData();
                        JOptionPane.showMessageDialog(null, "Successfully Deleted,Success Alert");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        });

    }

    /**
     * Add new student
     */
    ActionListener add = new ActionListener() {
        Connect connect = new Connect();
        Connection con = connect.getConnect();

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Connection con = connect.getConnect();

                String sql = "INSERT INTO students(firstname, lastname, address) values(?,?,?)";
                pmst = con.prepareStatement(sql);

                pmst.setString(1, jtf_firstName.getText());
                pmst.setString(2, jtf_lastName.getText());
                pmst.setString(3, jtf_address.getText());

                int res = pmst.executeUpdate();

                if (res > 0) {
                    //manupulate new data in jtable
                    getData();
                    JOptionPane.showMessageDialog(null, "Successfully Added,Success Alert");
                }

            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                if (pmst != null) {
                    connect.closeConnection();

                }
            }
        }
    };

    public void getData() {
        //remove duplication data in jtable
        //clear table before manupulating new data
        model.setRowCount(0);
        model.setColumnCount(0);
        //add column name or attributes
        model.addColumn("Id");
        model.addColumn("First Name");
        model.addColumn("Last name");
        model.addColumn("Address");

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql = "SELECT * FROM students";
            pmst = cn.prepareStatement(sql);
            rs = pmst.executeQuery();

            while (rs.next()) {            
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3), rs.getString(4)
                });

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * student entry form
     * using GridBagLayout
     */
    public void addToPanel() {

        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        panel.add(jtf_id, c);

        c.gridx = 1;
        c.gridy = 0;

        panel.add(jlb_firstName, c);

        c.gridx = 2;
        c.gridy = 0;
        panel.add(jtf_firstName, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(jlb_lastName, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(jtf_lastName, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(jlb_address, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(jtf_address, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(jlb_phone, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(jtf_phone, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(jlb_roll, c);

        c.gridx = 1;
        c.gridy = 4;
        panel.add(jtf_roll, c);

        c.gridx = 1;
        c.gridy = 5;
        panel.add(addbtn, c);

        c.gridx = 2;
        c.gridy = 5;
        panel.add(updatebtn, c);

        c.gridx = 3;
        c.gridy = 5;
        panel.add(deletebtn, c);

        c.gridx = 3;
        c.gridy = 0;
        panel.add((new JScrollPane(table)), c);

    }

    /**
     * diplay form
     * call it whenever you want to setVisible student entry form
     */
    public void display() {
        frame = new JFrame("Create Student");
        addToPanel();
        frame.add(panel);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.pack();

    }

    public static void main(String[] args) {
        new Student().display();
        
    }

}
