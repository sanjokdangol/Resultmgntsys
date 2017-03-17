package reultmgntsys;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



//class SignUpForm implements ActionListener{
public class LoginForm {

    private JLabel usernameLbl, passwordLbl;
    private JTextField usernameTf, passwordTf;
    private JButton submitBtn;

    private JFrame frame;
    private JPanel panel;

    LoginForm() {
        usernameLbl = new JLabel("Username");        

        passwordLbl = new JLabel("Password");

        usernameTf = new JTextField(5);
        passwordTf = new JTextField(5);

        submitBtn = new JButton("Submit");
        

        frame = new JFrame();
        panel = new JPanel();
    }

    private void addElementsToPanel() {
        panel.add(usernameLbl);
        panel.add(usernameTf);
        panel.add(passwordLbl);
        panel.add(passwordTf);
        panel.add(submitBtn);

//        testBtn.addActionListener(this);
 
        ActionListener submit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                final String username = usernameTf.getText();
                final String password = passwordTf.getText();

                if(username.equalsIgnoreCase("admin") &&
                    password.equalsIgnoreCase("admin")){
                    
                    Desktop desktop = new Desktop();
                    desktop.display();
                }else{
                    JOptionPane.showMessageDialog(panel,"ERROR");
                }

            }
        };
        submitBtn.addActionListener(submit);
        
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

}
















