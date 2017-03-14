/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package reultmgntsys;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

/**
 *
 * @author sanjok
 */
class M extends Subject{
    
}
public class Mark extends JInternalFrame {
    PreparedStatement pmst = null;
    ResultSet rs = null;
    
    JPanel p;
    JButton submitbtn, updatebtn, deletebtn, clearbtn;
    JLabel[] subjects = new JLabel[10];
    JTextField[] marks = new JTextField[10];
    private int count;
    
    public void setCount(int count){
        this.count = count;        
    }
    
    public int getcount(){
        return count;
    }
    
    
    Mark(){
                   //init nimbus look n feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
                  e.printStackTrace();
        }
        p = new JPanel();
       //add panel to internalframe
        add(p);
        //invoke methods
        createForm();
        initComponents();
    }
    
    public void createForm(){
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Connect connect = new Connect();
        Connection con = connect.getConnect();
        try{
            String sql = "Select name from subjects";
            pmst = con.prepareStatement(sql);
            rs = pmst.executeQuery();
            int i =0;
            while(rs.next()){
                c.insets = new Insets(3,3,3,3);
                c.gridx=0;
                c.gridy=i;
                subjects[i] = new JLabel(rs.getString(1));                
                
                p.add(subjects[i],c);
                
                c.gridx = 1;
                c.gridy = i;
                marks[i] = new JTextField(10);
                p.add(marks[i],c);
                i++;
                setCount(i);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        submitbtn = new JButton("Submit");
        p.add(submitbtn);
        submitbtn.addActionListener(add);
//        updatebtn.addActionListener(update);
//        deletebtn.addActionListener(delete);
//        clearbtn.addActionListener(clear);
    }
      public void initComponents(){
        setClosable(true);      
        setMaximizable(true); 
        setResizable(true); 
        setTitle("Add Marks"); 
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setOpaque(true);
        setPreferredSize(new Dimension(780, 200));
        setIconifiable(true);
        setMaximizable(true);

        setVisible(true);
        
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        
        try{
            setMaximum(true);
        }catch(Exception ex){
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
            int res=0;
            try {
                Connection con = connect.getConnect();

                
                for(int i=0; i< getcount(); i++){
                    String sql = "INSERT INTO marks(studentId, subject, mark) values(?,?,?)";
                    pmst = con.prepareStatement(sql);
                    pmst.setInt(1, 1);
                    pmst.setString(2, subjects[i].getText());
                    System.out.println("sub: "+subjects[i].getText());
                    if(marks[i].getText().equals("")){
                        pmst.setInt(3,0);
                    }else{
                        pmst.setInt(3, Integer.parseInt(marks[i].getText()));
                    }
                    
                    System.out.println("mrk: "+marks[i].getText());
                    res = pmst.executeUpdate();

                }
                if (res > 0) {
                    //manupulate new data in jtable
                    //createTable();
                    JOptionPane.showMessageDialog(null, "Successfully Added,Success Alert");
                }

            }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            
            }catch (Exception ex) {
                System.out.println(ex);
            } 
        }
    };
    
    
   
}

