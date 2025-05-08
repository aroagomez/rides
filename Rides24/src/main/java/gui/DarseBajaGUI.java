package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DarseBajaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUser;
	private JTextField textFieldMail;
	private JTextField textFieldPhoneNumber;
	private final JPanel panelButons = new JPanel();
	private JPasswordField passwordField;
	private JButton btnDarseBaja;
	private JLabel lblError;
	private JRadioButton rdbtnDriver;
	private ButtonGroup buttonGroup;
	private JRadioButton rdbtnTraveler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DarseBajaGUI frame = new DarseBajaGUI();
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
	public DarseBajaGUI() {
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
		lblPhoneNumber.setBounds(5, 203, 445, 22);
		contentPane.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(5, 225, 445, 33);
		contentPane.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		panelButons.setBounds(5, 282, 445, 33);
		contentPane.add(panelButons);
		
		rdbtnDriver = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));
		panelButons.add(rdbtnDriver);	
		rdbtnTraveler = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler"));
		panelButons.add(rdbtnTraveler);
		
		buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtnDriver);
        buttonGroup.add(rdbtnTraveler);
		
		lblError = new JLabel("");
		lblError.setBounds(5, 326, 445, 33);
		lblError.setForeground(Color.red);
		contentPane.add(lblError);
		
		btnDarseBaja = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DarseBajaGUI.Baja"));
		btnDarseBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean done;
				ButtonModel tipo = buttonGroup.getSelection();
				String email = textFieldMail.getText();
				String name = textFieldUser.getText();
				String pass = new String(passwordField.getPassword());
				String nTelefono = textFieldPhoneNumber.getText();
						
				if(rdbtnDriver.isSelected()) {
					done = facade.darseDeBaja(email,name,pass,nTelefono, 1);
				}
				else {
					done = facade.darseDeBaja(email,name,pass,nTelefono, 2);
				}
			
				if(done) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DarseBajaGUI.Correcto"));
				}
				else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DarseBajaGUI.Error"));
				}
			}
			
		});
		btnDarseBaja.setBounds(187, 326, 89, 23);
		contentPane.add(btnDarseBaja);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DarseBajaGUI.Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose(e);
			}
		});
		btnClose.setBounds(365, 327, 80, 22);
		contentPane.add(btnClose);
		
	
		
	
		
		
	}
	
	private void jButtonClose(ActionEvent e) {
		this.setVisible(false);
	}
}
