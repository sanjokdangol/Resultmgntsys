package reultmgntsys;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Batch extends JInternalFrame {

    JPanel mainPanel, panelRt, panelLt, p;
    JLabel batchName, batchDescription, batchYear, startDate, endDate, batchId;
    JTextField batchNamejtf, batchDescriptionjtf, batchYearjtf, startDatejtf, endDatejtf, idjtf;
    JButton addbtn, updatebtn, deletebtn, clearbtn;
    DefaultTableModel model;
    JTable table;

    PreparedStatement pmst = null;
    ResultSet rs = null;

    Batch() {
        //init nimbus look n feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model = new DefaultTableModel();
        table = new JTable(model);
        mainPanel = new JPanel();

        p = new JPanel();
        p.setLayout(new GridBagLayout());

        panelRt = new JPanel();
        panelRt.setPreferredSize(new Dimension(600, 200));
        //panelRt.setBackground(Color.BLUE);
        panelRt.add(p);

        panelLt = new JPanel();
        panelLt.setPreferredSize(new Dimension(600, 200));
        //panelLt.setBackground(Color.yellow);

        panelLt.add(new JScrollPane(table));

        mainPanel.add(panelRt, new BorderLayout().WEST);
        mainPanel.add(panelLt, new BorderLayout().EAST);

        add(mainPanel);

        createTable();
        createBatchForm();

        table.addMouseListener(edit);

        addbtn.addActionListener(add);
        updatebtn.addActionListener(update);
        deletebtn.addActionListener(delete);
        clearbtn.addActionListener(clear);

        initComponents();
    }

    public void createBatchForm() {
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(3, 3, 3, 3);
        c.gridx = 0;
        c.gridy = 0;

        p.add(new JLabel("Batch ID:"), c);

        c.gridx = 1;
        c.gridy = 0;
        batchId = new JLabel();
        p.add(batchId);

        c.gridx = 0;
        c.gridy = 1;
        batchName = new JLabel("Batch Name:");
        p.add(batchName, c);

        c.gridx = 1;
        c.gridy = 1;
        batchNamejtf = new JTextField(10);
        p.add(batchNamejtf, c);

        c.gridx = 0;
        c.gridy = 2;
        p.add(new JLabel("Description:"), c);

        c.gridx = 1;
        c.gridy = 2;
        batchDescriptionjtf = new JTextField(10);
        p.add(batchDescriptionjtf, c);

        c.gridx = 0;
        c.gridy = 3;
        startDate = new JLabel("Start Date:");
        p.add(startDate, c);

        c.gridx = 1;
        c.gridy = 3;
        startDatejtf = new JTextField(10);
        p.add(startDatejtf, c);

        c.gridx = 0;
        c.gridy = 4;
        endDate = new JLabel("End Date:");
        p.add(endDate, c);

        c.gridx = 1;
        c.gridy = 4;
        endDatejtf = new JTextField(10);
        p.add(endDatejtf, c);

        //buttons
        addbtn = new JButton("submit");
        updatebtn = new JButton("Update");
        updatebtn.setEnabled(false);
        
        deletebtn = new JButton("Delete");
        deletebtn.setEnabled(false);
        
        clearbtn = new JButton("Clear");
        clearbtn.setEnabled(false);

        //add button
        c.gridx = 0;
        c.gridy = 6;
        p.add(addbtn, c);

        c.gridx = 1;
        c.gridy = 6;
        p.add(updatebtn, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1d;
        c.gridx = 2;
        c.gridy = 6;
        p.add(deletebtn, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1d;
        c.gridx = 3;
        c.gridy = 6;
        p.add(clearbtn, c);
    }

    public void createTable() {

        model.setRowCount(0);
        model.setColumnCount(0);
        //add column name or attributes
        model.addColumn("Id");
        model.addColumn("Batch");
        model.addColumn("Description");
        model.addColumn("Start Date");
        model.addColumn("End Date");

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql = "SELECT id, name, description, start_date, end_date FROM batch";
            pmst = cn.prepareStatement(sql);
            rs = pmst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),});

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void initComponents() {
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Create New Batch");
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
     * Add new student
     */
    ActionListener add = new ActionListener() {
        Connect connect = new Connect();
        Connection con = connect.getConnect();

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Connection con = connect.getConnect();

                String sql = "INSERT INTO batch(name, description, start_date, end_date) values(?,?,?,?)";
                pmst = con.prepareStatement(sql);

                pmst.setString(1, batchNamejtf.getText());
                pmst.setString(2, batchDescriptionjtf.getText());
                pmst.setString(3, startDatejtf.getText());
                pmst.setString(4, endDatejtf.getText());

                int res = pmst.executeUpdate();

                if (res > 0) {
                    //manupulate new data in jtable
                    createTable();
                    JOptionPane.showMessageDialog(null, "Successfully Added,Success Alert");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    };

    /**
     * edit retrive data from table for update manupulate textfield using
     * anonymous class
     */
    MouseListener edit = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent me) {

            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            String id = model.getValueAt(row, 0).toString();
            
            if(id!=""){
                deletebtn.setEnabled(true);
                updatebtn.setEnabled(true);
                clearbtn.setEnabled(true);
            }

            try {
                Connect con = new Connect();
                Connection cn = con.getConnect();
                String sql = "SELECT id,name,description,start_date,end_date FROM batch WHERE id=?";
                pmst = cn.prepareStatement(sql);
                pmst.setInt(1, Integer.parseInt(id));
                rs = pmst.executeQuery();

                while (rs.next()) {

                    batchId.setText(rs.getString(1));
                    batchNamejtf.setText(rs.getString(2));
                    batchDescriptionjtf.setText(rs.getString(3));
                    startDatejtf.setText(rs.getString(4));
                    endDatejtf.setText(rs.getString(5));

                }

            } catch (Exception e) {
                System.out.println(e);
            }

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
         * update existing subject
         */
        ActionListener update = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Connect connect = new Connect();
            Connection con = connect.getConnect();
            try {

                String sql = "UPDATE batch SET name=?, description=?, start_date=?, end_date=? WHERE id=?";
                pmst = con.prepareStatement(sql);
                pmst.setString(1, batchNamejtf.getText());
                pmst.setString(2, batchDescriptionjtf.getText());
                pmst.setString(3, startDatejtf.getText());
                pmst.setString(4, endDatejtf.getText());
                pmst.setString(5, batchId.getText());
                int response = pmst.executeUpdate();
                if (response > 0) {
                    //manupulate new data in jtable
                    createTable();
                    JOptionPane.showMessageDialog(null, "Successfully Updated, Success Alert!");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "update error: " + e);
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
                    String sql = "DELETE FROM batch WHERE id=?";

                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, batchId.getText());
                    int response = pmst.executeUpdate();

                    if (response > 0) {
                        createTable();

                        batchId.setText("");
                        batchNamejtf.setText("");
                        batchDescriptionjtf.setText("");
                        startDatejtf.setText("");
                        endDatejtf.setText("");

                        JOptionPane.showMessageDialog(null, "Successfully Deleted,Success Alert");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        }
    };

    ActionListener clear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            batchId.setText("");
            batchNamejtf.setText("");
            batchDescriptionjtf.setText("");
            startDatejtf.setText("");
            endDatejtf.setText("");
            
            updatebtn.setEnabled(false);

            deletebtn.setEnabled(false);

            clearbtn.setEnabled(false);
        }
    };

}
