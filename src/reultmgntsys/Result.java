package reultmgntsys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;

//scala
//small talk pure object oriented language
public class Result extends JInternalFrame {

    JButton searchbtn;
    JPanel panel, panelRt, mainPanel;

    JComboBox batchcb, gradecb;

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
     * create no-argument constructor
     */
    Result() {

        //init nimbus look n feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel();
        panel = new JPanel();
        panelRt = new JPanel();

        panel.setLayout(new GridBagLayout());
        panelRt.setPreferredSize(new Dimension(900, 800));

        //set float of panel
        mainPanel.add(panel, new BorderLayout().EAST);
        mainPanel.add(panelRt, new BorderLayout().WEST);

        model = new DefaultTableModel();
        table = new JTable(model);
        panelRt.add(new JScrollPane(table));

        panelRt.setPreferredSize(new Dimension(600, 800));

        createStudentForm();

        searchbtn.addActionListener(search);
        table.addMouseListener(viewResult);

        add(mainPanel);
        initComponents();

    }

    public void createStudentForm() {

        //buttons
        searchbtn = new JButton("submit");

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Search Student"), c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Grade"), c);

        Vector v = new Vector();

        try {
            Connect cn = new Connect();
            Connection con = cn.getConnect();
            pmst = con.prepareStatement("Select * from grade");
            ResultSet rs = pmst.executeQuery();
            while (rs.next()) {
                v.addElement(new Item(Integer.parseInt(rs.getString(1)), rs.getString(2)));
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "grade error: " + e);
        }

        gradecb = new JComboBox(v);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(gradecb, c);

        Vector vector = new Vector();
        try {
            Connect cn = new Connect();
            Connection con = cn.getConnect();
            pmst = con.prepareStatement("Select id,name from batch");
            ResultSet rs = pmst.executeQuery();

            while (rs.next()) {
                vector.addElement(new Item(Integer.parseInt(rs.getString(1)), rs.getString(2)));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "batch error:" + e);
        }

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Batch: "), c);

        batchcb = new JComboBox(vector);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(batchcb, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(searchbtn, c);

    }

    public void initComponents() {

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("View Result");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setOpaque(true);
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

    public void getData(int bid, int gid) {
        //remove duplication data in jtable
        //clear table before manupulating new data
        model.setRowCount(0);
        model.setColumnCount(0);
        //add column name or attributes
        model.addColumn("Id");
        model.addColumn("First Name");
        model.addColumn("Last name");
        model.addColumn("Grade");
        model.addColumn("Address");
        model.addColumn("Roll");
        model.addColumn("Phone");

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            //String sql = "SELECT * FROM students JOIN marks ON marks.studentId=students.id WHERE students.batchId=?";
            String sql = "SELECT * FROM students WHERE batchId=? AND gradeId=?";
            pmst = cn.prepareStatement(sql);
            pmst.setString(1, String.valueOf(bid));
            pmst.setString(2, String.valueOf(gid));

            rs = pmst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(9),
                    rs.getString(4),
                    rs.getString(6),
                    rs.getString(7)

                });

            }

        } catch (Exception e) {
            System.out.println("getdata error:" + e);
        }
    }

    ActionListener search = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Item grd = (Item) gradecb.getSelectedItem();
            Student.setGradeId(grd.getId());

            Item bth = (Item) batchcb.getSelectedItem();
            Student.setBatchId(grd.getId());
            getData(bth.getId(), grd.getId());

        }
    };

    MouseListener viewResult = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent me) {

            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            String studentId = model.getValueAt(row, 0).toString();
            String fname = model.getValueAt(row, 1).toString();
            String lname = model.getValueAt(row, 2).toString();
            Student.setStudentName(fname+" "+lname);
            Student.setStudentId(Integer.parseInt(studentId));
            Mark m = new Mark();
            

            

        }

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

}
