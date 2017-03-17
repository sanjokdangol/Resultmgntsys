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

/**
 *
 * @author sanjok
 */
public class Mark extends JFrame {

    PreparedStatement pmst = null;
    ResultSet rs = null;

    JPanel p, pr;
    JButton submitbtn, updatebtn, deletebtn, clearbtn;
    JLabel resultjlb = new JLabel();
    JLabel[] subjectId = new JLabel[10];
    JLabel[] subjects = new JLabel[10];
    JTextField[] marks = new JTextField[10];
    private int count;

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    JPanel panel = new JPanel();

    private int total = 0;
    private int totalObtained = 0;
    private float percentage = 0;


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }


    public int getTotalObtained() {
        return totalObtained;
    }


    public void setTotalObtained(int totalObtained) {
        this.totalObtained = totalObtained;
    }

 
    public float getPercentage() {
        return percentage;
    }

  
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    public int getcount() {
        return count;
    }

    Mark() {

        //init nimbus look n feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        p = new JPanel();
        p.setPreferredSize(new Dimension(400, 500));
        pr = new JPanel();
        pr.setPreferredSize(new Dimension(600, 500));
      
        panel.add(p, new BorderLayout().EAST);
        panel.add(pr, new BorderLayout().WEST);
        
        pr.add(new JScrollPane(table));
        
        //add panel to internalframe
        add(panel);
        //invoke methods
        createTable();
        createForm();
        initComponents();

    }

    public void createTable() {
        String result = "";
        String grade = "";
        int total = 0;
        int totalObtained = 0;
        int flag = 0;
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Subjects");
        model.addColumn("Marks");
        model.addColumn("Full Mark");
        model.addColumn("Pass Mark");
        model.addColumn("Result");
        model.addColumn("Grade");

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql = "SELECT subjects.name, mark, fm, pm FROM marks JOIN subjects ON marks.subject = subjects.id WHERE studentId=?";
            pmst = cn.prepareStatement(sql);
            pmst.setInt(1, Student.getStudentId());
            rs = pmst.executeQuery();

            while (rs.next()) {
                total += Integer.parseInt(rs.getString(3));
                totalObtained += Integer.parseInt(rs.getString(4));
                int mark = Integer.parseInt(rs.getString(2));
                int pm = Integer.parseInt(rs.getString(4));
                int fm = Integer.parseInt(rs.getString(3));
                float per = (pm*100)/fm;

                if (mark >= pm && mark < fm &&  mark >= 90) {
                    result = "BoardFirst.";
                    grade = "A+";

                }else if(mark >= pm && mark < 90 && mark >= 80 ){
                    result = "Dist. Div";
                    grade = "A";
                }else if(mark >= pm && mark < 80 && mark >= 60 ){
                    result = "1st Div";
                    grade = "B+";
                }else if (mark >= pm && mark < 60 && mark >= 50) {
                    result = "2nd Div";
                    grade = "B";

                }else if(mark >= pm && mark < 50 && mark >= 40 ){
                    result = "3rd Div";
                    grade = "C";
                
                }else if (mark < pm) {
                    result = "fail";
                    grade = "F";
                    flag = 1;
                    
                }else{
                    result = "incorrect";
                }
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    result,
                    grade

                });

            }
            this.setTotal(total);
            this.setTotalObtained(totalObtained);
            percentage = (totalObtained*100)/total;
            this.setPercentage(percentage);
            if(flag == 1){
                resultjlb.setText("Failed");
            }else{
                resultjlb.setText("Passed");
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createForm() {

        p.setLayout(new GridBagLayout());
        //p.setLayout(new GridBagLayout());
   
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0;
        c.gridy = 0;
        p.add(new JLabel("Grand Total: "),c);
        
        c.gridx = 1;
        c.gridy = 0;
        p.add(new JLabel(String.valueOf(this.getTotal())),c);
        
        c.gridx = 0;
        c.gridy = 1;
        p.add(new JLabel("Total Marks Obtained: "),c);
        
        c.gridx = 1;
        c.gridy = 1;
        p.add(new JLabel(String.valueOf(this.getTotalObtained())),c);
        
        c.gridx = 0;
        c.gridy = 2;
        p.add(new JLabel("Percentage: "),c);
        
        c.gridx = 1;
        c.gridy = 2;
        p.add(new JLabel(String.valueOf(this.getPercentage())+"%"),c);
        
        c.gridx = 0;
        c.gridy = 3;
        p.add(new JLabel("Result"),c);
        
        c.gridx = 1;
        c.gridy = 3;
        p.add(resultjlb,c);        

        c.gridx = 2;
        c.gridy = 0;

        submitbtn = new JButton("Submit");
        p.add(submitbtn, c);

        c.gridx = 0;
        c.gridy = 4;
        p.add(new JLabel("Batch: "), c);

        c.gridx = 1;
        c.gridy = 4;
        JLabel batchtxt = new JLabel();
        batchtxt.setText(String.valueOf(Student.getBatchId()));
        p.add(batchtxt, c);

        c.gridx = 0;
        c.gridy = 5;
        p.add(new JLabel("Grade: "), c);

        c.gridx = 1;
        c.gridy = 5;
        JLabel gradetxt = new JLabel();
        gradetxt.setText(String.valueOf(Student.getGradeId()));
        p.add(gradetxt, c);

        c.gridx = 0;
        c.gridy = 6;
        p.add(new JLabel("Student ID: "), c);        
        

        c.gridx = 1;
        c.gridy = 6;
        JLabel studentIdtxt = new JLabel();
        studentIdtxt.setText(String.valueOf(Student.getStudentId()));
        p.add(studentIdtxt, c);

        c.gridx = 2;
        c.gridy = 6;
        p.add(new JLabel(Student.getStudentName()), c);
        
        Connect connect = new Connect();
        Connection con = connect.getConnect();
        try {
            String sql = "Select id,name from subjects";
            pmst = con.prepareStatement(sql);
            rs = pmst.executeQuery();
            int i = 0, j = 7;
            while (rs.next()) {
                subjectId[i] = new JLabel(rs.getString(1));
                c.insets = new Insets(3, 3, 3, 3);
                c.gridx = 0;
                c.gridy = j;
                subjects[i] = new JLabel(rs.getString(2));

                p.add(subjects[i], c);

                c.gridx = 1;
                c.gridy = j;
                marks[i] = new JTextField(10);
                p.add(marks[i], c);
                i++;
                j++;
                setCount(i);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        submitbtn.addActionListener(add);
//        updatebtn.addActionListener(update);
//        deletebtn.addActionListener(delete);
//        clearbtn.addActionListener(clear);
    }

    public void initComponents() {

        setResizable(true);
        setTitle("Marks");
        setLocationRelativeTo(null);
        //pack();
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Add new student
     */
    ActionListener add = new ActionListener() {
        Connect connect = new Connect();
        Connection con = connect.getConnect();

        @Override
        public void actionPerformed(ActionEvent ae) {
            int res = 0;
            
            try{
                Connection con = connect.getConnect();
                String sqlFind = "SELECT studentId from marks WHERE studentId=?";
                pmst = con.prepareStatement(sqlFind);
                pmst.setString(1, String.valueOf(Student.getStudentId()));
                rs = pmst.executeQuery();
                if(!rs.next()){
                    try {
               

                for (int i = 0; i < getcount(); i++) {
                    String sql = "INSERT INTO marks(studentId, subject, mark, GradeId, BatchId) values(?,?,?,?,?)";
                    pmst = con.prepareStatement(sql);
                    pmst.setInt(1, Student.getStudentId());
                    pmst.setString(2, subjectId[i].getText());
                    System.out.println("sub: " + subjects[i].getText());
                    if (marks[i].getText().equals("")) {
                        pmst.setInt(3, 0);
                    } else {
                        pmst.setInt(3, Integer.parseInt(marks[i].getText()));
                    }

                    pmst.setInt(4, Student.getGradeId());
                    pmst.setInt(5, Student.getBatchId());

                    res = pmst.executeUpdate();

                }
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
                }else{
                    JOptionPane.showMessageDialog(null, "Result Already Published");
                }
            }catch(Exception e){
                System.out.println("Find error: "+e);
            }
            
        }
    };

    

}
