
package reultmgntsys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

//scala
//small talk pure object oriented language
public class Student extends JFrame{
    JLabel jlb_firstName, jlb_lastName, jlb_address, jlb_phone, jlb_roll;
    JTextField jtf_firstName, jtf_lastName, jtf_address, jtf_phone, jtf_roll;
    JButton addbtn, updatebtn,deletebtn;
    JPanel panel, panelTbl, mainpanel;
    JFrame frame;
    
    PreparedStatement pmst = null;
    ResultSet rs = null;
    
    DefaultTableModel model;
    JTable table;

    
    
    Student(){
        
        panel = new JPanel();   
        panel.setSize(300, 300);
   
        
        jlb_firstName = new JLabel("First Name:");
    	jlb_lastName = new JLabel("Last Name:");
        jlb_address = new JLabel("Address:");
        jlb_phone = new JLabel("Phone:");
        jlb_roll = new JLabel("Roll:");

    	jtf_firstName = new JTextField(10);
    	jtf_lastName = new JTextField(10);
        jtf_address = new JTextField(10);
        jtf_phone = new JTextField(10);
        jtf_roll = new JTextField(10);
        addbtn = new JButton("submit");
        updatebtn = new JButton("Update");
        deletebtn = new JButton("Delete");
        addbtn.addActionListener(add);
        

        
        
        
        
        updatebtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    Connect connect = new Connect();
                    Connection con = connect.getConnect();
                    
                    String sql = "UPDATE students "
                            + "SET firstname=?, lastname=?, address=? "
                            + "WHERE id=?";
                    pmst = con.prepareStatement(sql);
                    pmst.setString(1,jtf_firstName.getText());
                    pmst.setString(2,jtf_lastName.getText());
                    pmst.setString(3,jtf_address.getText());
                    pmst.executeUpdate();
                    
                }catch(Exception e){
                    System.out.println(e);
                }
                
            }
        });
        
    }
    
    
    ActionListener add =new ActionListener() {
        Connect connect = new Connect();
        Connection con = connect.getConnect();
            @Override
            public void actionPerformed(ActionEvent ae){
                try{                    
                    Connection con = connect.getConnect();
                    
                    String sql = "INSERT INTO students(firstname, lastname, address) values(?,?,?)";
                    pmst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                    
                    pmst.setString(1, jtf_firstName.getText());
                    pmst.setString(2, jtf_lastName.getText());
                    pmst.setString(3, jtf_address.getText());
                   
                    int res = pmst.executeUpdate();
                    
//                    rs = pmst.getGeneratedKeys();
//                    while(rs.next()){
//                        System.out.println(rs.getString(1));
//                    }
//                    
                    if(res>0){
                        JOptionPane.showMessageDialog(null,"Successfully Added,Success Alert");
                     
                        getData();
                    }                    
                    
                    
                   
                }catch(Exception ex){
                    System.out.println(ex);
                }finally{
                    if(pmst != null){
                        connect.closeConnection();
                        
                    }
                }                
            }
        };
    
    public void getData(){
        
       model = new DefaultTableModel();
       table = new JTable(model); 
    
       
     add(new JScrollPane(table)); 

       model.addColumn("Id");
       model.addColumn("First Name");
       model.addColumn("Last name");
       model.addColumn("Address");
        
       
        try{
            
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql ="SELECT * FROM students";
            pmst = cn.prepareStatement(sql);
            rs = pmst.executeQuery();
            
            while(rs.next()){
                String id = rs.getString(1);
                String fname = rs.getString(2);
                String lname = rs.getString(3);
                String address = rs.getString(4);
                model.addRow(new Object[]{id,fname,lname,address});

            }
            
        
            
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void addToPanel(){
    	
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();        
        
        c.gridx =0;
        c.gridy = 0;
        
        
    	panel.add(jlb_firstName,c);
        
        c.gridx =1;
        c.gridy = 0;
    	panel.add(jtf_firstName,c);
        
        c.gridx =0;
        c.gridy = 1;
    	panel.add(jlb_lastName,c);
        
        c.gridx =1;
        c.gridy = 1;
    	panel.add(jtf_lastName,c);
        
        c.gridx =0;
        c.gridy = 2;        
        panel.add(jlb_address,c);
        
        c.gridx =1;
        c.gridy = 2; 
        panel.add(jtf_address,c);
        
        c.gridx =0;
        c.gridy = 3;
        panel.add(jlb_phone, c);
        
        c.gridx =1;
        c.gridy = 3;
        panel.add(jtf_phone,c);
        
        c.gridx =0;
        c.gridy = 4;
        panel.add(jlb_roll,c);
        
        c.gridx =1;
        c.gridy = 4;
        panel.add(jtf_roll,c);
        
        c.gridx=1;
        c.gridy = 5;
        panel.add(addbtn,c);
        
        c.gridx=2;
        c.gridy = 5;
        panel.add(updatebtn,c);
        
        c.gridx=3;
        c.gridy = 5;
        panel.add(deletebtn,c);
        
        
       getData();      
        
        
        
        c.gridx=3;
        c.gridy=0;
        panel.add((new JScrollPane(table)),c);
        
        

    }
    
    

    public void display(){
    	frame = new JFrame("Create Student");
       
        addToPanel();        
         frame.add(panel);     
       
    	frame.setSize(800,600);
        frame.setDefaultCloseOperation(3);
    	frame.setVisible(true);
        frame.pack();

    }
    public static void main(String[] args) {
       new Student().display();
        //new Student().getData();
    }
 
}

