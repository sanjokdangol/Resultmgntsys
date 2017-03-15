/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package reultmgntsys;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Grade extends JInternalFrame {

    JLabel jlbname;
    JTextField jtfname, idjtf;
    JPanel panel, pl, pr;
    private DefaultTableModel model;
    private JTable table;
    JButton addbtn, updatebtn, deletebtn;

    PreparedStatement pmst = null;
    ResultSet rs = null;

    /**
     * no-arg constructor
     */
    Grade() {

        model = new DefaultTableModel();

        panel = new JPanel();

        pl = new JPanel();
        pl.setPreferredSize(new Dimension(300, 400));
        pr = new JPanel();
        pr.setPreferredSize(new Dimension(600, 600));

        table = new JTable(model);
        pr.add(new JScrollPane(table));

        panel.add(pl, new BorderLayout().EAST);
        panel.add(pr, new BorderLayout().WEST);

        createGradeForm();

        table.addMouseListener(edit);
        addbtn.addActionListener(add);
        updatebtn.addActionListener(update);
        deletebtn.addActionListener(delete);

        createTable();

        add(panel);
        initComponents();

    }

    public void createTable() {
        model.setRowCount(0);
        model.setColumnCount(0);
        //add column name or attributes
        model.addColumn("Id");
        model.addColumn("Grade");

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql = "SELECT * FROM grade";
            pmst = cn.prepareStatement(sql);
            rs = pmst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2)

                });

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createGradeForm() {
        panel.setLayout(new GridBagLayout());
        idjtf = new JTextField(4);
        idjtf.setEditable(false);

        jlbname = new JLabel("Grade Name:");
        jtfname = new JTextField(10);

        addbtn = new JButton("Create New");
        updatebtn = new JButton("Update");
        updatebtn.setEnabled(false);
        
        deletebtn = new JButton("Delete");
        deletebtn.setEnabled(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.gridx = 0;
        c.gridy = 0;
        pl.add(new JLabel("Grade Id"), c);

        c.gridx = 1;
        c.gridy = 0;
        pl.add(idjtf);

        c.gridx = 0;
        c.gridy = 1;
        pl.add(jlbname);

        c.gridx = 1;
        c.gridy = 1;
        pl.add(jtfname);

        c.gridx = 0;
        c.gridy = 2;
        pl.add(addbtn);

        c.gridx = 1;
        c.gridy = 2;
        pl.add(updatebtn);

        c.gridx = 2;
        c.gridy = 2;
        pl.add(deletebtn);
    }

    public void initComponents() {
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Create New Grade");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setOpaque(true);
        setPreferredSize(new Dimension(780, 200));
        setIconifiable(true);
        setMaximizable(true);

        setVisible(true);
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
        }

        try {
            setMaximum(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * edit retrive data from table for update manupulate textfield using
     * anonymous class
     */
    MouseListener edit = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent me) {
            
           

            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            String gid = model.getValueAt(row, 0).toString();
            
            if(gid!=""){
                deletebtn.setEnabled(true);
                updatebtn.setEnabled(true);
//                clearbtn.setEnabled(true);
            }
            String name = model.getValueAt(row, 1).toString();

            idjtf.setText(gid);
            jtfname.setText(name);

        }

        //used just to satisfy MouseListener interface
        //i don't know the solution without overriding these abstract methods
        //find if you can
        public void mousePressed(MouseEvent me) {
        }

        ;

            public void mouseReleased(MouseEvent me) {
        }

        ;

            public void mouseEntered(MouseEvent me) {
        }

        ;

            public void mouseExited(MouseEvent me) {
        }
    ;

    };

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

                String sql = "INSERT INTO grade(name) values(?)";
                pmst = con.prepareStatement(sql);

                pmst.setString(1, jtfname.getText());

                int res = pmst.executeUpdate();

                if (res > 0) {
                    //manupulate new data in jtable
                    createTable();
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

    /**
     * update existing student
     */
    ActionListener update = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Connect connect = new Connect();
                Connection con = connect.getConnect();

                String sql = "UPDATE grade "
                        + "SET name=?"
                        + "WHERE id=?";
                pmst = con.prepareStatement(sql);
                pmst.setString(1, jtfname.getText());
                pmst.setString(2, idjtf.getText());
                int response = pmst.executeUpdate();
                if (response > 0) {
                    //manupulate new data in jtable
                    createTable();
                    JOptionPane.showMessageDialog(null, "Successfully Updated, Success Alert!");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Enter number");

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    };

    ActionListener delete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Delete Parmanently?", "Warning", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    Connect connect = new Connect();
                    Connection con = connect.getConnect();
                    String sql = "DELETE FROM grade WHERE id=?";

                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, idjtf.getText());
                    int response = pmst.executeUpdate();

                    if (response > 0) {
                        createTable();
                            updatebtn.setEnabled(false);

                            deletebtn.setEnabled(false);

                        JOptionPane.showMessageDialog(null, "Successfully Deleted,Success Alert");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }

        }
    };

}
