package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.this.title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UserName"));
		contentPane.add(lblUserName);
		
		textFieldUser = new JTextField();
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		contentPane.add(lblPassword);
		
		JPasswordField passwordField = new JPasswordField();
		contentPane.add(passwordField);
		
		JLabel lblError = new JLabel(" ");
		contentPane.add(lblError);
		
		JButton btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textFieldUser.getText();
				String password =new String(passwordField.getPassword());
				BLFacade facade = MainGUI.getBusinessLogic();	
				boolean done =facade.login(user, password);
				if(!done) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Error"));
				}
				else {
					lblError.setText("");
					jButtonClose_actionPerformed(e);
				}
				}
		});
		contentPane.add(btnLogin);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(jButtonClose);
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
}
}
