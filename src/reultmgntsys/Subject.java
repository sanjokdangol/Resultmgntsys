/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package reultmgntsys;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
public class Subject extends JInternalFrame{
    JLabel sname, fm, pm;
    JTextField snamejtf, fmjtf, pmjtf, idjtf;
    JButton createbtn, updatebtn, deletebtn, clearbtn;
    JPanel pr,pl, mainPanel;
    
    TitledBorder titleBorder;
    private DefaultTableModel model;
    private JTable table;
    
    PreparedStatement pmst =null;
    ResultSet rs = null;
    
    Subject(){
                   //init nimbus look n feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
                  e.printStackTrace();
        }
        //important to initilize at begining of constructor
        model = new DefaultTableModel();
        table = new JTable(model);
        
        titleBorder = BorderFactory.createTitledBorder("Subject");
        //create panel
        pr = new JPanel();
        pl = new JPanel();
        mainPanel = new JPanel();
   
        //set float of panel
        mainPanel.add(pl, new BorderLayout().EAST);
        mainPanel.add(pr, new BorderLayout().WEST);
        
//        p.setSize(300,300);
        pl.setPreferredSize(new Dimension(500,500));
        pr.setPreferredSize(new Dimension(600,500));
             
        
        pr.setBorder(titleBorder);
        pl.setBorder(titleBorder);
        
        //pr.setBackground(Color.LIGHT_GRAY);        
        //pl.setBackground(Color.GRAY);
        
       
        add(mainPanel);
     
        createTable();
        createForm();           
        
        table.addMouseListener(edit);

        createbtn.addActionListener(add);
        updatebtn.addActionListener(update);
        deletebtn.addActionListener(delete);
        clearbtn.addActionListener(clear);
        
        initComponents();
    }
    
    public void createForm(){
        idjtf = new JTextField(5);
        pl.add(idjtf);
        idjtf.setEditable(false);
        sname = new JLabel("Subject Name:");
        pm = new JLabel("Pass Mark:");
        fm = new JLabel("Full Mark:");
        
        snamejtf = new JTextField(10);
        pmjtf = new JTextField(10);
        fmjtf = new JTextField(10);
        
        createbtn = new JButton("Create");
        updatebtn = new JButton("Update");
        deletebtn = new JButton("Delete");       
        clearbtn = new JButton("Clear");
        
        
        pl.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        //add labels
        c.insets = new Insets(3,3,3,3);

        c.fill = GridBagConstraints.BOTH;  
        
        c.weightx = 1d;
        c.gridx=0;
        c.gridy=1;
        pl.add(sname,c); 
        
        c.gridx=1;
        c.gridy=1;
        pl.add(snamejtf,c);
        
        c.gridx=0;
        c.gridy=2;
        pl.add(fm,c);
        
        c.gridx=1;
        c.gridy=2;
        pl.add(fmjtf,c);
        
        c.gridx=0;
        c.gridy=3;
        pl.add(pm,c);
        
        c.gridx=1;
        c.gridy=3;
        pl.add(pmjtf,c);

        
        //add button
         c.gridx=0;
        c.gridy=4;
        pl.add(createbtn,c);
        
        c.gridx=1;
        c.gridy=4;
        pl.add(updatebtn,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1d;
        c.gridx=2;
        c.gridy=4;
        pl.add(deletebtn,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1d;
        c.gridx=3;
        c.gridy=4;
        pl.add(clearbtn,c);
        
   
        pr.add(new JScrollPane(table));
    }
    
    public void createTable(){
        
        model.setRowCount(0);
        model.setColumnCount(0);
        //add column name or attributes
        model.addColumn("Id");
        model.addColumn("Subject");
        model.addColumn("Pass Mark");
        model.addColumn("Full Mark");
      

        try {
            Connect con = new Connect();
            Connection cn = con.getConnect();
            String sql = "SELECT * FROM subjects";
            pmst = cn.prepareStatement(sql);
            rs = pmst.executeQuery();

            while (rs.next()) {            
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3), 
                    rs.getString(4)                 
                    
                });

            }

        }catch (Exception e) {
            System.out.println(e);
        }        
        
    }
    
    public void initComponents(){
        setClosable(true);      
        setMaximizable(true); 
        setResizable(true); 
        setTitle("Create New Subject"); 
        setVisible(true);
        
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
            try {
                Connection con = connect.getConnect();

                String sql = "INSERT INTO subjects(name, pm, fm) values(?,?,?)";
                pmst = con.prepareStatement(sql);

                pmst.setString(1, snamejtf.getText());
                pmst.setString(2, pmjtf.getText());
                pmst.setString(3, fmjtf.getText());

                int res = pmst.executeUpdate();

                if (res > 0) {
                    //manupulate new data in jtable
                    createTable();
                    JOptionPane.showMessageDialog(null, "Successfully Added,Success Alert");
                }

            }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Enter number");
            
            }catch (Exception ex) {
                System.out.println(ex);
            } finally {
                if (pmst != null) {
                    connect.closeConnection();

                }
            }
        }
    };
    
    
    /**
         * edit 
         * retrive data from table for update 
         * manupulate textfield
         * using anonymous class
         */
        MouseListener edit = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                String id, name, pm, fm;

                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                id = model.getValueAt(row, 0).toString();
                name = model.getValueAt(row, 1).toString();
                pm = model.getValueAt(row, 2).toString();
                fm = model.getValueAt(row, 3).toString();        


                System.out.println(id);
                
                idjtf.setText(id);
                snamejtf.setText(name);
                pmjtf.setText(pm);
                fmjtf.setText(fm);
                

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

                    String sql = "UPDATE subjects "
                            + "SET name=?, pm=?, fm=? "
                            + "WHERE id=?";
                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, snamejtf.getText());
                    pmst.setString(2, pmjtf.getText());
                    pmst.setString(3, fmjtf.getText());
                    pmst.setString(4, idjtf.getText());
                    int response = pmst.executeUpdate();
                    if (response > 0) {
                        //manupulate new data in jtable
                        createTable();
                        JOptionPane.showMessageDialog(null, "Successfully Updated, Success Alert!");
                    }

                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Enter number");

                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (pmst != null) {
                        connect.closeConnection();

                    }
                }

            }
        };
        
        ActionListener delete = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    Connect connect = new Connect();
                    Connection con = connect.getConnect();
                    String sql = "DELETE FROM subjects WHERE id=?";

                    pmst = con.prepareStatement(sql);
                    pmst.setString(1, idjtf.getText());
                    int response = pmst.executeUpdate();

                    if (response > 0) {
                        createTable();
                        JOptionPane.showMessageDialog(null, "Successfully Deleted,Success Alert");
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        };
        
        ActionListener clear = new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent ae){
              idjtf.setText("");
              snamejtf.setText("");
              pmjtf.setText("");
              fmjtf.setText("");
          }  
        };
}


