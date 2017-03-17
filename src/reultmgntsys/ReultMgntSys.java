/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reultmgntsys;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author sanjok
 */
public class ReultMgntSys {
/**
     * define form elements
     */
    JLabel jlb_firstName, jlb_lastName, jlb_address, jlb_phone, jlb_roll;
    JTextField jtf_id, jtf_firstName, jtf_lastName, jtf_address, jtf_phone, jtf_roll;
    JButton addbtn, updatebtn, deletebtn;
    JPanel panel, panelTbl, mainpanel;
    JFrame frame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new LoginForm().display();        
        
    }
    
}
