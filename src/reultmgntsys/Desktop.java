
package reultmgntsys;

import java.awt.event.*;
import javax.swing.*;




public class Desktop extends JFrame{
    JDesktopPane jdesktop = new JDesktopPane();

    JMenuBar menuBar;
    JMenu file, student,result,faculty, subject;
    JMenuItem exit,newStudent, findStudent, newGrade, newSubject, addMark, addBatch;
    public Desktop(){
        //init nimbus look n feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
                  e.printStackTrace();
        }
        createMenuBar();
        
    }
    
    public void createMenuBar(){
        setTitle("Result Management System");       
        
//        
//        add(jdesktop);
        
        // Creates a menubar for a JFrame
        menuBar = new JMenuBar();
         
        // Add the menubar to the frame
        setJMenuBar(menuBar);
         
        // Define and add two drop down menu to the menubar
        file = new JMenu("File");
        student = new JMenu("Student");
        result = new JMenu("Result");
        faculty = new JMenu("Faculty");
        
        menuBar.add(file);
        menuBar.add(student);
        menuBar.add(result);
        menuBar.add(faculty);
         
        // Create menu items
        newStudent = new JMenuItem("Add Student");
        findStudent = new JMenuItem("Find");
        exit = new JMenuItem("Exit");
        
        newGrade = new JMenuItem("Add Class");
        newSubject = new JMenuItem("Add Subject");
        addMark = new JMenuItem("Add Mark");
        addBatch = new JMenuItem("Create Batch");
        
        
        //adding item to student menu
        student.add(newStudent);
        
        student.add(findStudent);
        student.add(addMark);
        
        /**
         * Add every menu item for
         * faculty
         * */
        faculty.add(addBatch);
        faculty.add(newGrade);
        faculty.add(newSubject);
        
        
        file.add(exit);
        /**
         * link to add create Batch
         */
        addBatch.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               Batch batch = new Batch();
               jdesktop.add(batch);
               add(jdesktop);               
           } 
        });
        
        /**
         * link to add new student
         */
        newSubject.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               Subject s = new Subject();
               jdesktop.add(s);
               add(jdesktop);               
           } 
        });
        
        /**
         * Add Mark
         */
         addMark.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               Mark m = new Mark();
               jdesktop.add(m);
               add(jdesktop);               
           } 
        });
        
         /**
          * add new Grade
          */
        newGrade.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                Grade g = new Grade();
                jdesktop.add(g);
                add(jdesktop);
            }
            
        });
       
        /**
         * Add new Student
         */
        newStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
               Student std = new Student();
               jdesktop.add(std);
               add(jdesktop);
                
            }
  
    });
    
    
    }
    public void internal(){
        JInternalFrame jif = new JInternalFrame();
        
        JPanel p = new JPanel();
        p.add(new JLabel("test"));
        jif.add(p);
        jdesktop.add(jif);
        add(jdesktop);
        setContentPane(jdesktop);
        jif.setSize(600, 600);
        jif.setVisible(true);
        try{
            jif.setMaximum(true);
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    public void display(){
        Desktop d = new Desktop();     
        //d.internal();
        d.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        d.setVisible(true);
    }
    
}
