
package reultmgntsys;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.DesktopPaneUI;



public class Desktop extends JFrame{
    JDesktopPane jdesktop;
    JMenuBar menuBar;
    JMenu file, student,result,faculty;
    JMenuItem exit,newStudent, findStudent, newGrade;
    public Desktop(){
        createMenuBar();
        
    }
    
    public void createMenuBar(){
        setTitle("Result Management System");        
         
        
        jdesktop = new JDesktopPane();
        add(jdesktop);
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
        
        
        //adding item to student menu
        student.add(newStudent);
        student.add(findStudent);
        
        /**
         * Add every menu item for
         * faculty
         * */
        faculty.add(newGrade);
        
        
        file.add(exit);
        
        newGrade.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                Grade g = new Grade();
                g.display();
            }
            
        });
       
        newStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                Student student =new Student();
                jdesktop.add(student);   
            }
  
    });
    
    
    }
    public void display(){
        Desktop d = new Desktop();
     
        d.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        d.setVisible(true);
    }
    
}
