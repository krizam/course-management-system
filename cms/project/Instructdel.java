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

public class Instructdel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField delins;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructdel frame = new Instructdel();
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
	public Instructdel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 311);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		delins = new JTextField();
		delins.setColumns(10);
		delins.setBorder(null);
		delins.setBounds(114, 125, 191, 20);
		contentPane.add(delins);
		
		JLabel lblDeleteInstructorDetails = new JLabel("Delete Instructor Details");
		lblDeleteInstructorDetails.setFont(new Font("Yu Gothic UI", Font.BOLD, 21));
		lblDeleteInstructorDetails.setBounds(114, 47, 240, 31);
		contentPane.add(lblDeleteInstructorDetails);
		
		JLabel lblInstructorId = new JLabel("Instructor Id");
		lblInstructorId.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblInstructorId.setBounds(114, 108, 86, 14);
		contentPane.add(lblInstructorId);
		
		JLabel lblNewLabel_1_2 = new JLabel("__________________________");
		lblNewLabel_1_2.setBounds(114, 133, 217, 23);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 String del = delins.getText();
				    if (!del.equals("")) {
				        try {
				            // Attempt to parse the input string to a numeric type
				            int insId = Integer.parseInt(del); // Assuming courseId is an integer
				            // If parsing is successful, proceed with deletion
				            String url = "jdbc:mysql://localhost:3306/cms";
				            String username1 = "root";
				            String password1 = "";
				            Connection con = DriverManager.getConnection(url, username1, password1);
				            Statement st = con.createStatement();
				            String sql = "DELETE FROM `instructor` WHERE teacher_id = '" + insId + "'";
				            st.execute(sql);
				            JOptionPane.showMessageDialog(null, "Deleted Successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
				            delins.setText("");
				            con.close(); // Close the connection
				        } catch (NumberFormatException e1) {
				            // If parsing fails, display an error message
				            JOptionPane.showMessageDialog(null, "Invalid Instructor ID: Must be a number", "Error", JOptionPane.ERROR_MESSAGE);
				        } catch (SQLException e2) {
				            e2.printStackTrace();
				            JOptionPane.showMessageDialog(null, "Database Error: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				        }
				    } else {
				        JOptionPane.showMessageDialog(null, "Enter the Instructor Id", "Info", JOptionPane.INFORMATION_MESSAGE);
				    }
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Yu Gothic UI", Font.BOLD, 12));
		btnDelete.setBorder(null);
		btnDelete.setBackground(new Color(0, 128, 0));
		btnDelete.setBounds(114, 176, 217, 31);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Instructdel.class.getResource("/Images/dashboard.png")));
		lblNewLabel.setBounds(396, 11, 32, 32);
		contentPane.add(lblNewLabel);
	}

}

