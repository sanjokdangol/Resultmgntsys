/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package reultmgntsys;


import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Grade extends JInternalFrame {
    JLabel jlbname;
    JTextField jtfname;
    JPanel panel, pl, pr;
    private DefaultTableModel model;
    private JTable table;
    
    PreparedStatement pmst =null;
    ResultSet rs = null;
    
    /**
     * no-arg constructor
     */
    Grade(){
        table = new JTable();
        model = new DefaultTableModel();
        
        jlbname = new JLabel("Grade Name:");
        
        jtfname = new JTextField(10);
        
        panel = new JPanel();
        pl = new JPanel();
        pl.setPreferredSize(new Dimension(300,400));
        pr = new JPanel();
        pr.setPreferredSize(new Dimension(300,400));
        pr.add(new JScrollPane(table));
        
        panel.add(pl, new BorderLayout().EAST);
        panel.add(pr, new BorderLayout().WEST);        
        
        add(panel);
        
        Subject s = new Subject();        
        s.createTable();
        
        createGradeForm();
        initComponents();
        
    }
    
    public void createGradeForm(){
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx = 1;
        c.gridy= 1;        
        pl.add(jlbname);
        
        c.gridx = 2;
        c.gridy= 1; 
        pl.add(jtfname);
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
    
    
}
