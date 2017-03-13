

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;




//class SignUpForm implements ActionListener{
class SignUpForm {

    private JLabel usernameLbl, passwordLbl;
    private JTextField usernameTf, passwordTf;
    private JButton testBtn;

    private JFrame frame;
    private JPanel panel;

    SignUpForm() {
        usernameLbl = new JLabel();
        usernameLbl.setText("Username");

        passwordLbl = new JLabel("Password");

        usernameTf = new JTextField(5);
        passwordTf = new JTextField(5);

        testBtn = new JButton("Submit");

        frame = new JFrame();
        panel = new JPanel();
    }

    private void addElementsToPanel() {
        panel.add(usernameLbl);
        panel.add(usernameTf);
        panel.add(passwordLbl);
        panel.add(passwordTf);

//        testBtn.addActionListener(this);

        ActionListener actionListenerObj = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final String username = usernameTf.getText();
                final String password = passwordTf.getText();

                if(username.equalsIgnoreCase("admin") &&
                    password.equalsIgnoreCase("admin")){
                    JOptionPane.showMessageDialog(panel,"SUCCESS");
                }else{
                    JOptionPane.showMessageDialog(panel,"ERROR");
                }

            }
        };
        testBtn.addActionListener(actionListenerObj);
        panel.add(testBtn);
    }

    public void display() {

        final LayoutManager mgr = new FlowLayout(FlowLayout.CENTER);
        panel.setLayout(mgr);
        
		frame.add(panel);
        
		addElementsToPanel();


        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        final String username = usernameTf.getText();
//        final String password = passwordTf.getText();
//        System.out.println(username + " -> " + password);
//    }
}

public class LoginForm{

    public static void main(String[] args) {
		try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
                  e.printStackTrace();
        }
	
	
        SignUpForm form = new SignUpForm();
        form.display();
		
		
    }
}














