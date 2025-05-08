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
	private JTextField textFieldMoney;
	private JLabel lblError;

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
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.UserName"));
		lblUserName.setBounds(5, 5, 445, 33);
		contentPane.add(lblUserName);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(5, 31, 445, 33);
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
		lblPassword.setBounds(5, 63, 445, 33);
		contentPane.add(lblPassword);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(5, 93, 445, 33);
		contentPane.add(passwordField);
		
		JLabel lblMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Mail"));
		lblMail.setBounds(5, 126, 233, 33);
		contentPane.add(lblMail);
		
		textFieldMail = new JTextField();
		textFieldMail.setBounds(5, 159, 233, 33);
		contentPane.add(textFieldMail);
		textFieldMail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.PhoneNumber"));
		lblPhoneNumber.setBounds(5, 191, 445, 22);
		contentPane.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(5, 213, 445, 33);
		contentPane.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		panelButons.setBounds(5, 246, 445, 33);
		contentPane.add(panelButons);
		
		JRadioButton rdbtnDriver = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
		panelButons.add(rdbtnDriver);	
		JRadioButton rdbtnTraveler = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		panelButons.add(rdbtnTraveler);
		
		ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtnDriver);
        buttonGroup.add(rdbtnTraveler);
		
		lblError = new JLabel("");
		lblError.setBounds(15, 290, 445, 33);
		contentPane.add(lblError);
		
		textFieldMoney = new JTextField();
		textFieldMoney.setBounds(248, 159, 197, 33);
		contentPane.add(textFieldMoney);
		textFieldMoney.setColumns(10);
		
		JLabel lblMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Money")); //$NON-NLS-1$ //$NON-NLS-2$
		lblMoney.setBounds(248, 126, 202, 33);
		contentPane.add(lblMoney);
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Register"));
		btnRegister.setBounds(5, 275, 445, 44);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user=textFieldUser.getText();
				String mail=textFieldMail.getText();
				String phone=textFieldPhoneNumber.getText();
				String password=new String(passwordField.getPassword());
				float money = Float.parseFloat(textFieldMoney.getText());
				
				boolean done=false;
				if(rdbtnDriver.isSelected())
					done=facade.register(new Driver(mail,user,phone,password, money));
				if(rdbtnTraveler.isSelected())
					done=facade.register(new Traveler(mail,user,phone,password, money));
				
				if(done)
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Done"));
				else
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));
					
					
				
			}
		});
		contentPane.add(btnRegister);	
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setBounds(5, 316, 445, 44);
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
