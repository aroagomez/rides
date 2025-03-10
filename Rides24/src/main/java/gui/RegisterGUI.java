package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldMail;
	private JTextField textFieldPhoneNumber;
	private final JPanel panelButons = new JPanel();
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		BLFacade facade = MainGUI.getBusinessLogic(); 
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.this.title")); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserName"));
		contentPane.add(lblUserName);
		
		textFieldUser = new JTextField();
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
		contentPane.add(lblPassword);
		
		JPasswordField passwordField = new JPasswordField();
		contentPane.add(passwordField);
		
		JLabel lblMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Mail"));
		contentPane.add(lblMail);
		
		textFieldMail = new JTextField();
		contentPane.add(textFieldMail);
		textFieldMail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PhoneNumber"));
		contentPane.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		contentPane.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		contentPane.add(panelButons);
		
		JRadioButton rdbtnDriver = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
		panelButons.add(rdbtnDriver);	
		JRadioButton rdbtnTraveler = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		panelButons.add(rdbtnTraveler);
		
		ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtnDriver);
        buttonGroup.add(rdbtnTraveler);
		
		JLabel lblError = new JLabel("");
		contentPane.add(lblError);
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register"));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user=textFieldUser.getText();
				String mail=textFieldMail.getText();
				String phone=textFieldPhoneNumber.getText();
				String password=new String(passwordField.getPassword());
				
				boolean done=false;
				if(rdbtnDriver.isSelected())
					done=facade.register(new Driver(mail,user,phone,password));
				if(rdbtnTraveler.isSelected())
					done=facade.register(new Traveler(mail,user,phone,password));
				
				if(done)
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Done"));
				else
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));
					
					
				
			}
		});
		contentPane.add(btnRegister);	
		
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
