package reultmgntsys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;

//scala
//small talk pure object oriented language
public class Student extends JInternalFrame {

    /**
     * define form elements
     */
    JLabel jlb_firstName, jlb_lastName, jlb_address, jlb_phone, jlb_roll;
    JTextField jtf_id, jtf_firstName, jtf_lastName, jtf_address, jtf_phone, jtf_roll;
    JButton addbtn, updatebtn, deletebtn, addMarkbtn, clearbtn;
    JPanel panel, panelRt, mainPanel;
    ButtonGroup g = null;
    JRadioButton male, female;
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
     * students proterties
     */
    String id, firstname, lastname, address, roll, phone, grade, batch;

    private static int studentId;
    private static int batchId;
    private static int gradeId;
    private static String studentName;

    public static void setStudentId(int stdId) {
        studentId = stdId;
    }

    public static int getStudentId() {
        return studentId;
    }

    public static void setStudentName(String name) {
        studentName = name;
    }

    public static String getStudentName() {
        return studentName;
    }

    public static void setBatchId(int bId) {
        batchId = bId;
    }

    public static int getBatchId() {
        return batchId;
    }

    public static void setGradeId(int gId) {
        gradeId = gId;
    }

    public static int getGradeId() {
        return gradeId;
    }

    /**
     * create no-argument constructor
     */
    Student() {

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

        table.setName("Students");
        panelRt.add(new JScrollPane(table));
//        panelRt.setBackground(Color.yellow);
        panelRt.setPreferredSize(new Dimension(700, 800));
        //fetch data from database to manupulate jtable

        getData();
        createStudentForm();

        //addbtn is not using anonymous class so need to init
        table.addMouseListener(edit);

        addbtn.addActionListener(add);
        updatebtn.addActionListener(update);
        deletebtn.addActionListener(delete);
        addMarkbtn.addActionListener(addMark);
        clearbtn.addActionListener(clear);

        add(mainPanel);
        initComponents();

    }

    public void createStudentForm() {
        //init form elements
        //labels
        JLabel jlb_id = new JLabel("Id");
        jlb_firstName = new JLabel("First Name:");
        jlb_lastName = new JLabel("Last Name:");
        jlb_address = new JLabel("Address:");
        jlb_phone = new JLabel("Phone:");
        jlb_roll = new JLabel("Roll:");

        //textfields
        jtf_id = new JTextField(5);
        jtf_id.setEditable(false);

        jtf_firstName = new JTextField(10);
        jtf_lastName = new JTextField(10);
        jtf_address = new JTextField(10);
        jtf_phone = new JTextField(10);
        jtf_roll = new JTextField(10);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");

        //buttons
        addbtn = new JButton("submit");
        updatebtn = new JButton("Update");
        updatebtn.setEnabled(false);

        deletebtn = new JButton("Delete");
        deletebtn.setEnabled(false);

        clearbtn = new JButton("Clear");
        clearbtn.setEnabled(false);

        addMarkbtn = new JButton("Add Marks");
        addMarkbtn.setEnabled(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.gridx = 0;
        c.gridy = 0;

        panel.add(jlb_id, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(jtf_id, c);

        c.gridx = 0;
        c.gridy = 1;

        panel.add(jlb_firstName, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(jtf_firstName, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(jlb_lastName, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(jtf_lastName, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(jlb_address, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(jtf_address, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(jlb_phone, c);

        c.gridx = 1;
        c.gridy = 4;
        panel.add(jtf_phone, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(jlb_roll, c);

        c.gridx = 1;
        c.gridy = 5;
        panel.add(jtf_roll, c);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(new JLabel("Gender"), c);

        c.gridx = 1;
        c.gridy = 7;
        panel.add(male, c);

        c.gridx = 3;
        c.gridy = 7;
        panel.add(female, c);

        c.gridx = 0;
        c.gridy = 8;
        panel.add(new JLabel("Grade"), c);

        Vector v = new Vector();

        try {
            Connect cn = new Connect();
            Connection con = cn.getConnect();
            pmst = con.prepareStatement("Select * from grade");
            ResultSet rs = pmst.executeQuery();
            while (rs.next()) {
                v.addElement(new Item(Integer.parseInt(rs.getString(1)), rs.getString(2)));
                //            gradecb.addItem(rs.getString(1));
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "grade error: " + e);
        }

        gradecb = new JComboBox(v);
        c.gridx = 1;
        c.gridy = 8;
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
        c.gridy = 9;
        panel.add(new JLabel("Batch: "), c);

        batchcb = new JComboBox(vector);
        c.gridx = 1;
        c.gridy = 9;
        panel.add(batchcb, c);

        c.gridx = 1;
        c.gridy = 10;
        panel.add(addbtn, c);

        c.gridx = 2;
        c.gridy = 10;
        panel.add(updatebtn, c);

        c.gridx = 3;
        c.gridy = 10;
        panel.add(deletebtn, c);

        c.gridx = 4;
        c.gridy = 10;
        panel.add(addMarkbtn, c);

        c.gridx = 5;
        c.gridy = 10;
        panel.add(clearbtn, c);

    }

    public void initComponents() {

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Create New Student");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setOpaque(true);
        //setPreferredSize(new Dimension(780, 200));
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
            if (id != "") {
                deletebtn.setEnabled(true);
                updatebtn.setEnabled(true);
                clearbtn.setEnabled(true);
                addMarkbtn.setEnabled(true);
            }

            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            id = model.getValueAt(row, 0).toString();

            try {
                Connect con = new Connect();
                Connection cn = con.getConnect();
                String sql = "SELECT id,firstname,lastname,address,gradeId,batchId,roll,phone FROM students WHERE id=?";
                pmst = cn.prepareStatement(sql);
                pmst.setInt(1, Integer.parseInt(id));
                rs = pmst.executeQuery();

                while (rs.next()) {

                    id = rs.getString(1);
                    Student.setStudentId(Integer.parseInt(id));
                    jtf_id.setText(id);

                    firstname = rs.getString(2);
                    jtf_firstName.setText(firstname);

                    lastname = rs.getString(3);
                    jtf_lastName.setText(lastname);

                    Student.setStudentName(firstname + " " + lastname);

                    jtf_address.setText(rs.getString(4));

                    grade = rs.getString(5);
                    Student.setGradeId(Integer.parseInt(grade));

                    batch = rs.getString(6);
                    Student.setBatchId(Integer.parseInt(batch));

                    jtf_roll.setText(rs.getString(7));

                    jtf_phone.setText(rs.getString(8));

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
     * Add new student
     */
    ActionListener add = new ActionListener() {
        Connect connect = new Connect();
        Connection con = connect.getConnect();

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Connection con = connect.getConnect();

                String sql = "INSERT INTO students"
                        + "(firstname, lastname, address, gender, phone, roll,batchId, gradeId) "
                        + "values(?,?,?,?,?,?,?,?)";
                pmst = con.prepareStatement(sql);

                pmst.setString(1, jtf_firstName.getText());
                pmst.setString(2, jtf_lastName.getText());
                pmst.setString(3, jtf_address.getText());

                if (male.isSelected()) {
                    pmst.setString(4, male.getText());
                } else if (female.isSelected()) {
                    pmst.setString(4, female.getText());
                } else {
                    pmst.setString(4, "gay");
                }

                pmst.setString(5, jtf_phone.getText());

                if (jtf_roll.getText().equals("")) {
                    pmst.setInt(6, 0);
                } else {
                    pmst.setInt(6, Integer.parseInt(jtf_roll.getText()));
                }

                Item grd = (Item) gradecb.getSelectedItem();
                Item bth = (Item) batchcb.getSelectedItem();

                pmst.setInt(7, bth.getId());

                pmst.setInt(8, grd.getId());

                int res = pmst.executeUpdate();

                if (res > 0) {
                    //manupulate new data in jtable
                    getData();
                    JOptionPane.showMessageDialog(null, "Successfully Added,Success Alert");
                }

            } catch (Exception ex) {
                System.out.println(ex);
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

                String sql = "UPDATE students SET firstname=?, lastname=?, address=?, phone=?, roll=?, gradeId=?, batchId=?, gender=? WHERE id=?";
                pmst = con.prepareStatement(sql);
                pmst.setString(1, jtf_firstName.getText());
                pmst.setString(2, jtf_lastName.getText());
                pmst.setString(3, jtf_address.getText());
                pmst.setString(4, jtf_phone.getText());

                if (jtf_roll.getText().equals("")) {
                    pmst.setInt(5, 0);
                } else {
                    pmst.setInt(5, Integer.parseInt(jtf_roll.getText()));
                }

                Item grd = (Item) gradecb.getSelectedItem();
                Item bth = (Item) batchcb.getSelectedItem();

                pmst.setInt(6, grd.getId());
                pmst.setInt(7, bth.getId());

                if (male.isSelected()) {
                    pmst.setString(8, male.getText());
                } else if (female.isSelected()) {
                    pmst.setString(8, female.getText());
                } else {
                    pmst.setString(8, "gay");
                }

                pmst.setInt(9, Integer.parseInt(jtf_id.getText()));

                int response = pmst.executeUpdate();
                if (response > 0) {
                    //manupulate new data in jtable
                    getData();
                    JOptionPane.showMessageDialog(null, "Successfully Updated, Success Alert!");
                }

            } catch (Exception e) {
                System.out.println("update error: " + e);
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
                    String sql = "DELETE FROM students WHERE id=?";

                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, jtf_id.getText());
                    int response = pmst.executeUpdate();

                    if (response > 0) {
                        getData();
                        jtf_id.setText("");
                        jtf_firstName.setText("");
                        jtf_lastName.setText("");
                        jtf_address.setText("");
                        jtf_phone.setText("");
                        jtf_roll.setText("");
                        updatebtn.setEnabled(false);

                        deletebtn.setEnabled(false);

                        clearbtn.setEnabled(false);
                        addMarkbtn.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Successfully Deleted,Success Alert");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
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
        model.addColumn("Grade");
        model.addColumn("Address");
        model.addColumn("Roll");
        model.addColumn("Phone");

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
                    rs.getString(3),
                    rs.getString(9),
                    rs.getString(4),
                    rs.getString(6),
                    rs.getString(7)

                });

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    ActionListener clear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {

            jtf_id.setText("");
            jtf_firstName.setText("");
            jtf_lastName.setText("");
            jtf_address.setText("");
            jtf_phone.setText("");
            jtf_roll.setText("");
            updatebtn.setEnabled(false);

            deletebtn.setEnabled(false);

            clearbtn.setEnabled(false);
            addMarkbtn.setEnabled(false);
        }
    };

    ActionListener addMark = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Mark mark = new Mark();

        }
    };

}

class Item {

    private int id;
    private String description;

    public Item(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return description;
    }
}
