
package reultmgntsys;

import java.awt.event.*;
import javax.swing.*;


public class Desktop extends JFrame{
    JDesktopPane jdesktop = new JDesktopPane();

    JMenuBar menuBar;
    JMenu file, student,result,faculty, subject;
    JMenuItem exit,newStudent, findStudent, newGrade, newSubject, addBatch, showResult;
    public Desktop(){
        //init nimbus look n feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
                  e.printStackTrace();
        }
        createMenuBar();

        jdesktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

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
       
        addBatch = new JMenuItem("Create Batch");
        
        showResult = new JMenuItem("View Result");
        
        result.add(showResult);
        
        //adding item to student menu
        student.add(newStudent);
        
        student.add(findStudent);
        
        
        /**
         * Add every menu item for
         * faculty
         * */
        faculty.add(addBatch);
        faculty.add(newGrade);
        faculty.add(newSubject);
        
        
        file.add(exit);
        
        showResult.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               Result result = new Result();
               result.moveToFront();
               jdesktop.add(result);
               add(jdesktop);               
           } 
        });
        /**
         * link to add create Batch
         */
        addBatch.addActionListener(new ActionListener(){
            @Override
           public void actionPerformed(ActionEvent ae){
               Batch batch = new Batch();
               batch.moveToFront();
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
               s.moveToFront();
               
               jdesktop.add(s);
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
                g.moveToFront();
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
               std.moveToFront();
               jdesktop.add(std);
               add(jdesktop);
                
            }
  
    });
    
    
    }
    
    public void display(){
        Desktop d = new Desktop();     
        //d.internal();
        d.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
        d.setVisible(true);
    }
    
}
