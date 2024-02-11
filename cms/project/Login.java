package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textemail;
	private JPasswordField textpassword;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 437);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textemail = new JTextField();
		textemail.setBackground(new Color(255, 255, 255));
		textemail.setBounds(43, 89, 195, 25);
		contentPane.add(textemail);
		textemail.setColumns(10);
		
		textpassword = new JPasswordField();
		textpassword.setBounds(43, 151, 195, 25);
		contentPane.add(textpassword);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(null);
		comboBox.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "Student", "Instructor"}));
		comboBox.setBounds(280, 151, 135, 25);
		contentPane.add(comboBox);
		
		btnNewButton.setBorder(null);
		btnNewButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnNewButton.setBackground(new Color(0, 255, 0));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    String email = textemail.getText();
			    String password = textpassword.getText();
			    String value = comboBox.getSelectedItem().toString();
			    if (!email.equals("") && !password.equals("") && !value.equals("")) {
			        try {
			            String url = "jdbc:mysql://localhost:3306/cms";
			            String username1 = "root";
			            String password1 = "";
			            Connection con = DriverManager.getConnection(url, username1, password1);
			            Statement st = con.createStatement();

			            // Mysql query
			            String tableName = "";
			            String emailColumnName = "";
			            String passwordColumnName = "";

			            if (value.equals("Student")) {
			                tableName = "students";
			                emailColumnName = "email";
			                passwordColumnName = "password";
			            } else if (value.equals("Admin")) {
			                tableName = "admin";
			                emailColumnName = "admin_email";
			                passwordColumnName = "admin_password";
			            } else {
			                tableName = "instructor";
			                emailColumnName = "teacher_email";
			                passwordColumnName = "teacher_password";
			            }

			            // Using PreparedStatement to prevent SQL injection
			            String sql = "SELECT * FROM " + tableName + " WHERE " + emailColumnName + "=? AND " + passwordColumnName + "=?";
			            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			                preparedStatement.setString(1, email);
			                preparedStatement.setString(2, password);
			                ResultSet rs = preparedStatement.executeQuery();

			                if (rs.next()) {
			                    // If username and password are correct.
			                    Dashboard d = new Dashboard();
			                    d.setValue(value);
			                    d.setVisible(true);
			                    dispose();
			                    String query = "SELECT COUNT(username) FROM students";
					            ResultSet r = st.executeQuery(query);
					            while (r.next()) {
					                int studentCount = r.getInt(1);
					                d.getStudenttotal().setText(String.valueOf(studentCount));
					            }
					            //to find total instructor
					            String query1 = "SELECT COUNT(teacher_name) FROM instructor";
					            ResultSet rs1 = st.executeQuery(query1);
					            while (rs1.next()) {
					                int teacherCount = rs1.getInt(1);
					                d.getInstotal().setText(String.valueOf(teacherCount));
					            }
					            String query2 = "SELECT COUNT(course) FROM course";
					            ResultSet rs2 = st.executeQuery(query2);
					            while (rs2.next()) {
					                int courseCount = rs2.getInt(1);
					                d.getCoursetotal().setText(String.valueOf(courseCount));
					            }
					         
						        	String sql1 = "Select * from course";
									ResultSet rs3 = st.executeQuery(sql1);
									ResultSetMetaData rsmd = (ResultSetMetaData) rs3.getMetaData();
									DefaultTableModel model = (DefaultTableModel) d.getCoursetable().getModel();
									
									int cols = rsmd.getColumnCount();
									String[] colName = new String[cols];
									for(int i = 0; i<cols; i++) {
										colName[i]=rsmd.getColumnName(i+1);
									}
									model.setColumnIdentifiers(colName);
									String id, course, year, sem, mod1, mod2, mod3, mod4;
									while (rs3.next()) {
										id = rs3.getString(1);
										course = rs3.getString(2);
										year = rs3.getString(3);
										sem = rs3.getString(4);
										mod1 = rs3.getString(5);
										mod2 = rs3.getString(6);
										mod3 = rs3.getString(7);
										mod4 = rs3.getString(8);
										
										String[] row = {id, course, year, sem, mod1, mod2, mod3, mod4 };
										model.addRow(row);
									}
					            
								if(value.equals("Admin") || value.equals("Student")) {
									String sql2 = "Select * from instructor";
									ResultSet rs4 = st.executeQuery(sql2);
									ResultSetMetaData rsmd1 = (ResultSetMetaData) rs4.getMetaData();
									DefaultTableModel model1 = (DefaultTableModel) d.getInstable().getModel();
									
									int cols1 = rsmd1.getColumnCount();
									String[] colName1 = new String[cols1];
									for(int i = 0; i<cols1; i++) {
										colName1[i]=rsmd1.getColumnName(i+1);
									}
									model1.setColumnIdentifiers(colName1);
									String tid, name, mail, pass, md1, md2, md3, md4 ;
									while (rs4.next()) {
										tid = rs4.getString(1);
										name = rs4.getString(2);
										mail = rs4.getString(3);
										pass = rs4.getString(4);
										md1 =	rs4.getString(5);
										md2 =	rs4.getString(6);
										md3 =	rs4.getString(7);
										md4 =	rs4.getString(8);
										
										String[] row1 = {tid, name, mail, pass, md1, md2, md3, md4 };
										model1.addRow(row1);
									}
								}else if(value.equals("Instructor")) {
									String sqlf="select * from instructor where teacher_email= '"+email+"' and teacher_password='"+password+"'";
						            ResultSet rsf = st.executeQuery(sqlf);
						            ResultSetMetaData rsmd1 = (ResultSetMetaData) rsf.getMetaData();
									DefaultTableModel model1 = (DefaultTableModel) d.getInstable().getModel();
									int cols1 = rsmd1.getColumnCount();
									String[] colName1 = new String[cols1];
									for(int i = 0; i<cols1; i++) {
										colName1[i]=rsmd1.getColumnName(i+1);
									}
									model1.setColumnIdentifiers(colName1);
									String tid, name, mail, pass, md1, md2, md3, md4 ;
									while (rsf.next()) {
										tid = rsf.getString(1);
										name = rsf.getString(2);
										mail = rsf.getString(3);
										pass = rsf.getString(4);
										md1 =	rsf.getString(5);
										md2 =	rsf.getString(6);
										md3 =	rsf.getString(7);
										md4 =	rsf.getString(8);
										
										String[] row1 = {tid, name, mail, pass, md1, md2, md3, md4 };
										model1.addRow(row1);
									}
								}
								if(value.equals("Admin") || value.equals("Instructor")) {
									String sql3 = "Select * from students";
									ResultSet rs5 = st.executeQuery(sql3);
									ResultSetMetaData rsmd2 = (ResultSetMetaData) rs5.getMetaData();
									DefaultTableModel model2 = (DefaultTableModel) d.getStutable().getModel();
									
									int cols2 = rsmd2.getColumnCount();
									String[] colName2 = new String[cols2];
									for(int i = 0; i<cols2; i++) {
										colName2[i]=rsmd2.getColumnName(i+1);
									}
									model2.setColumnIdentifiers(colName2);
									String sid, sname, smail, spass, scourse ;
									while (rs5.next()) {
										sid = rs5.getString(1);
										sname = rs5.getString(2);
										smail = rs5.getString(3);
										spass = rs5.getString(4);
										scourse = rs5.getString(5);
										
										String[] row1 = {sid, sname, smail, spass, scourse };
										model2.addRow(row1);
									}
								}else if(value.equals("Student")) {
									String sqlf="select * from students where email= '"+email+"' and password='"+password+"'";
						            ResultSet rsf = st.executeQuery(sqlf);
						            ResultSetMetaData rsmd1 = (ResultSetMetaData) rsf.getMetaData();
									DefaultTableModel model1 = (DefaultTableModel) d.getStutable().getModel();
									int cols1 = rsmd1.getColumnCount();
									String[] colName1 = new String[cols1];
									for(int i = 0; i<cols1; i++) {
										colName1[i]=rsmd1.getColumnName(i+1);
									}
									model1.setColumnIdentifiers(colName1);
									String tid, name, mail, pass, scourse ;
									while (rsf.next()) {
										tid = rsf.getString(1);
										name = rsf.getString(2);
										mail = rsf.getString(3);
										pass = rsf.getString(4);
										scourse = rsf.getString(5);
										
										String[] row1 = {tid, name, mail, pass, scourse };
										model1.addRow(row1);
									}

								}
								if(value.equals("Admin") || value.equals("Instructor")) {
									String sql3 = "Select * from result";
									ResultSet rs5 = st.executeQuery(sql3);
									ResultSetMetaData rsmd2 = (ResultSetMetaData) rs5.getMetaData();
									DefaultTableModel model2 = (DefaultTableModel) d.getResulttable().getModel();
									
									int cols2 = rsmd2.getColumnCount();
									String[] colName2 = new String[cols2];
									for(int i = 0; i<cols2; i++) {
										colName2[i]=rsmd2.getColumnName(i+1);
									}
									model2.setColumnIdentifiers(colName2);
									String rid, sid, courses, seme, md1, md2, md3, md4, mr1, mr2, mr3, mr4, re ;
									while (rs5.next()) {
										rid = rs5.getString(1);
										sid = rs5.getString(2);
										courses = rs5.getString(3);
										seme = rs5.getString(4);
										md1 = rs5.getString(5);
										mr1 = rs5.getString(6);
										md2 = rs5.getString(7);
										mr2 = rs5.getString(8);
										md3 = rs5.getString(9);
										mr3 = rs5.getString(10);
										md4 = rs5.getString(11);
										mr4 = rs5.getString(12);
										re =rs5.getString(13);
										String[] row1 = {rid, sid, courses, seme, md1, mr1, md2, mr2, md3, mr3, md4, mr4, re};
										model2.addRow(row1);
									}
								}

			                } else {
			                    // If username and password are wrong
			                    JOptionPane.showMessageDialog(null, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
			                    textemail.setText("");
			                    textpassword.setText("");
			                }
			                
			            }
			            con.close();
			        } catch (Exception ea) {
			            System.out.println(ea);
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Login Failed");
			    }
			}
		});
		
		btnNewButton.setBounds(43, 206, 195, 31);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2_1 = new JLabel("Don't have an account?");
		lblNewLabel_2_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(222, 240, 130, 14);
		contentPane.add(lblNewLabel_2_1);
		
		
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Yu Gothic UI", Font.BOLD, 21));
		lblLogin.setBounds(27, 29, 256, 31);
		contentPane.add(lblLogin);
		
		JLabel emailtxt = new JLabel("Email");
		emailtxt.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		emailtxt.setBounds(76, 64, 86, 14);
		contentPane.add(emailtxt);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblNewLabel_2.setBounds(76, 125, 86, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Mode");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
		lblNewLabel_3.setBounds(306, 122, 46, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Sign Up");
		lblNewLabel_2_1_1.setForeground(new Color(0, 255, 0));
		lblNewLabel_2_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Signup s = new Signup();
				s.setVisible(true);
				dispose();
			}
		});
		lblNewLabel_2_1_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		lblNewLabel_2_1_1.setBounds(362, 240, 63, 15);
		contentPane.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/Images/icons8-username-24.png")));
		lblNewLabel.setBounds(37, 58, 30, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/Images/icons8-password-24.png")));
		lblNewLabel_1.setBounds(43, 118, 30, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(Login.class.getResource("/Images/icons8-mode-24.png")));
		lblNewLabel_1_1.setBounds(280, 118, 30, 30);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("Hi Welcome Back!");
		lblNewLabel_4.setForeground(new Color(0, 255, 0));
		lblNewLabel_4.setBackground(new Color(0, 255, 0));
		lblNewLabel_4.setFont(new Font("Yu Gothic UI", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_4.setBounds(253, 7, 211, 78);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Login.class.getResource("/Images/college logo.png")));
		lblNewLabel_6.setBounds(0, 248, 425, 158);
		contentPane.add(lblNewLabel_6);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setBounds(426, 0, 405, 405);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Login.class.getResource("/Images/clip-sign-up.png")));
		lblNewLabel_5.setBounds(44, 11, 310, 312);
		panel.add(lblNewLabel_5);
		
		
	}
}
