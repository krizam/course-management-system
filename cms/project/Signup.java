package project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Signup extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textpassword;
    private JTextField textusername;
    private JTextField textemail;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signup frame = new Signup();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Signup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 836, 536);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setToolTipText("");
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Username");
        lblNewLabel_1.setBounds(61, 78, 114, 14);
        lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setBounds(61, 140, 101, 14);
        lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel = new JLabel("Sign up");
        lblNewLabel.setBounds(21, 24, 336, 33);
        lblNewLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 25));
        contentPane.add(lblNewLabel);
        
        
        JLabel coursebox = new JLabel("Course");
        coursebox.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        coursebox.setBounds(31, 201, 60, 14);
        contentPane.add(coursebox);
        
        JComboBox<String> courseBox = new JComboBox<>();
        courseBox.setEditable(true);
        courseBox.addMouseListener(new MouseAdapter() {});
        courseBox.setBackground(new Color(255, 255, 255));
        courseBox.setFont(new Font("Yu Gothic UI", Font.BOLD, 11));
        courseBox.setModel(new DefaultComboBoxModel<String>(new String[] {"BSc Hons Computer Science", "BCA", "CSIT"}));
        courseBox.setBounds(30, 232, 185, 22);
        contentPane.add(courseBox);
        courseBox.setVisible(false);
        coursebox.setVisible(false);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setEditable(true);
        comboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    if (comboBox.getSelectedItem().toString().equals("Students")) {
                    courseBox.setVisible(true);
                    coursebox.setVisible(true);
                } else {
                    courseBox.setVisible(false);
                    coursebox.setVisible(false);
                }
        	}
        });
        comboBox.setBounds(254, 165, 194, 25);
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Admin", "Students", "Instructor"}));
        contentPane.add(comboBox);

        JLabel lblNewLabel_3 = new JLabel("Mode");
        lblNewLabel_3.setBounds(288, 140, 46, 14);
        lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        contentPane.add(lblNewLabel_3);

        // Adjust the position of courseBox
//        courseBox.setBounds(212, 268, 185, 22);
//        passwordtext.add(courseBox);
//        courseBox.setVisible(false);
//        coursebox.setVisible(false);

        JButton btnNewButton = new JButton(" Sign Up");
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(51, 204, 0));
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		   
                       Login lg = new Login();
                       lg.setVisible(true);
                       dispose();
                   }
               });
        		
    
        btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Database 
                String username = textusername.getText();
                String password = textpassword.getText();
                String value = comboBox.getSelectedItem().toString();
                
                String course = courseBox.getSelectedItem().toString();
                String email=textemail.getText();
                //                 Sample database connection parameters
                String url = "jdbc:mysql://localhost:3306/cms";
                String username1 = "root";
                String password1 = "";
                Connection con = null;
                Statement st = null;
                if (!username.equals("") && !password.equals("") &&  !value.equals("") && !textemail.equals("")) {
                try {
                    con = DriverManager.getConnection(url, username1, password1);
                    st = con.createStatement();

                    System.out.println("Connection Success");
                    
                    if(value.equals("Students")) {
                    	String insertQuery = "INSERT INTO students(username,password,course,email) VALUES ('"+username+"','"+password+"','"+course+"','"+email+"')";
                    	st.execute(insertQuery);
                    }
                    else if(value.equals("Admin")) {
                    	String insertQuery= "INSERT INTO admin(admin_name,admin_password,admin_email) VALUES ('"+username+"','"+password+"','"+email+"')";
                    	st.execute(insertQuery);
                    }
                    else{
                    	String insertQuery = "INSERT INTO instructor(teacher_name,teacher_password,teacher_email) VALUES ('"+username+"','"+password+"','"+email+"')";
                    	st.execute(insertQuery);
                    }
                    Login l = new Login();
					l.setVisible(true);
					dispose();
					
                    con.close();   
                } catch (Exception ea) {
                    ea.printStackTrace(); // or log the exception
                }
              }else {
            	  JOptionPane.showMessageDialog(null, "Signup failed","Error",JOptionPane.ERROR_MESSAGE);
              }
            }
        });
        btnNewButton.setBounds(21, 287, 89, 30);
        contentPane.add(btnNewButton);

        JButton loginButton = new JButton("Log In");
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(102, 204, 0));
        loginButton.setBounds(200, 287, 89, 30);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login lg = new Login();
                lg.setVisible(true);
                dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Your login button logic
            	Login lg = new Login();
				lg.setVisible(true);
				dispose();
            }
        });
        contentPane.add(loginButton);

        textpassword = new JPasswordField();
        textpassword.setColumns(10);
        textpassword.setBounds(21, 165, 194, 25);
        contentPane.add(textpassword);
        
        JLabel lblNewLabel_4 = new JLabel("Already have an account ?\r\n");
        lblNewLabel_4.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
        lblNewLabel_4.setBounds(161, 265, 150, 14);
        contentPane.add(lblNewLabel_4);
        
        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setIcon(new ImageIcon(Signup.class.getResource("/Images/icons8-password-24.png")));
        lblNewLabel_7.setBounds(21, 135, 30, 30);
        contentPane.add(lblNewLabel_7);
        
        JLabel lblNewLabel_6 = new JLabel("");
        lblNewLabel_6.setIcon(new ImageIcon(Signup.class.getResource("/Images/icons8-username-24.png")));
        lblNewLabel_6.setBounds(21, 67, 30, 30);
        contentPane.add(lblNewLabel_6);
        
        textusername = new JTextField();
        textusername.setBounds(21, 103, 195, 25);
        contentPane.add(textusername);
        textusername.setColumns(10);
        
        JLabel lblNewLabel_10 = new JLabel("New label");
        lblNewLabel_10.setIcon(new ImageIcon(Signup.class.getResource("/Images/icons8-mode-24.png")));
        lblNewLabel_10.setBounds(248, 135, 30, 30);
        contentPane.add(lblNewLabel_10);
        
        JLabel emailtxt = new JLabel("Email");
        emailtxt.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        emailtxt.setBounds(288, 78, 46, 14);
        contentPane.add(emailtxt);
        
        textemail = new JTextField();
        textemail.setBounds(253, 103, 195, 25);
        contentPane.add(textemail);
        textemail.setColumns(10);
        
        JLabel lblNewLabel_12 = new JLabel("");
        lblNewLabel_12.setIcon(new ImageIcon(Signup.class.getResource("/Images/icons8-mail-24.png")));
        lblNewLabel_12.setBounds(253, 78, 46, 14);
        contentPane.add(lblNewLabel_12);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 204, 0));
        panel.setBounds(450, 0, 370, 554);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon(Signup.class.getResource("/Images/first.png")));
        lblNewLabel_5.setBounds(0, -69, 600, 600);
        panel.add(lblNewLabel_5);
        
        JLabel lblNewLabel_13 = new JLabel("");
        lblNewLabel_13.setIcon(new ImageIcon(Signup.class.getResource("/Images/college logo.png")));
        lblNewLabel_13.setBounds(10, 328, 430, 158);
        contentPane.add(lblNewLabel_13);
        
        JLabel lblNewLabel_14 = new JLabel("Let's get Started");
        lblNewLabel_14.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));
        lblNewLabel_14.setBounds(161, 39, 120, 18);
        contentPane.add(lblNewLabel_14);
        }
}
