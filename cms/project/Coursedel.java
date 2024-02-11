package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;

public class Coursedel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField delcourse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Coursedel frame = new Coursedel();
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
	public Coursedel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 325);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		delcourse = new JTextField();
		delcourse.setColumns(10);
		delcourse.setBorder(null);
		delcourse.setBounds(134, 123, 191, 20);
		contentPane.add(delcourse);
		
		JLabel lblDeleteCourse = new JLabel("Delete Course");
		lblDeleteCourse.setFont(new Font("Yu Gothic UI", Font.BOLD, 21));
		lblDeleteCourse.setBounds(134, 45, 191, 31);
		contentPane.add(lblDeleteCourse);
		
		JLabel lblCourseId = new JLabel("Course Id");
		lblCourseId.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblCourseId.setBounds(134, 106, 86, 14);
		contentPane.add(lblCourseId);
		
		JLabel lblNewLabel_1_2 = new JLabel("__________________________");
		lblNewLabel_1_2.setBounds(134, 131, 217, 23);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    String del = delcourse.getText();
			    if (!del.equals("")) {
			        try {
			            // Attempt to parse the input string to a numeric type
			            int courseId = Integer.parseInt(del); // Assuming courseId is an integer
			            // If parsing is successful, proceed with deletion
			            String url = "jdbc:mysql://localhost:3306/cms";
			            String username1 = "root";
			            String password1 = "";
			            Connection con = DriverManager.getConnection(url, username1, password1);
			            Statement st = con.createStatement();
			            String sql = "DELETE FROM `course` WHERE id = '" + courseId + "'";
			            st.execute(sql);
			            JOptionPane.showMessageDialog(null, "Deleted Successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
			            delcourse.setText("");
			            con.close(); // Close the connection
			        } catch (NumberFormatException e1) {
			            // If parsing fails, display an error message
			            JOptionPane.showMessageDialog(null, "Invalid Course ID: Must be a number", "Error", JOptionPane.ERROR_MESSAGE);
			        } catch (SQLException e2) {
			            e2.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Database Error: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Enter the Course Id", "Info", JOptionPane.INFORMATION_MESSAGE);
			    }
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnDelete.setBorder(null);
		btnDelete.setBackground(new Color(0, 255, 0));
		btnDelete.setBounds(134, 174, 181, 31);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Coursedel.class.getResource("/Images/dashboard.png")));
		lblNewLabel.setBounds(385, 11, 32, 32);
		contentPane.add(lblNewLabel);
	}

}

