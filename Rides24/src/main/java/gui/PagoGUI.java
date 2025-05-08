package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PagoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumeroCuenta;
	private JTextField textFieldFechaCaducidad;
	private JTextField textFieldCVV;
	private JLabel lblMensaje;
	private JButton btnClose;
	private JLabel lblNombreTitular;
	private JLabel lblNumeroCuenta;
	private JLabel lblFechaCaducidad;
	private JLabel lblCVV;
	private JTextField textFieldNombreDeTitular;
	private boolean pagoRealizado = false;

	public boolean isPagoRealizado() {
		return pagoRealizado;
	}

	public void setPagoRealizado(boolean pagoRealizado) {
		this.pagoRealizado = pagoRealizado;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PagoGUI frame = new PagoGUI();
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
	public PagoGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldNombreDeTitular = new JTextField();
		textFieldNombreDeTitular.setBounds(10, 38, 295, 20);
		contentPane.add(textFieldNombreDeTitular);
		textFieldNombreDeTitular.setColumns(10);
		
		
		lblNombreTitular = new JLabel("New label");
		lblNombreTitular.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.NombreTitular"));
		lblNombreTitular.setBounds(10, 13, 391, 14);
		contentPane.add(lblNombreTitular);
		
		textFieldNumeroCuenta = new JTextField();
		textFieldNumeroCuenta.setBounds(10, 90, 295, 20);
		contentPane.add(textFieldNumeroCuenta);
		textFieldNumeroCuenta.setColumns(10);
		
		textFieldFechaCaducidad = new JTextField();
		textFieldFechaCaducidad.setBounds(10, 152, 96, 20);
		contentPane.add(textFieldFechaCaducidad);
		textFieldFechaCaducidad.setColumns(10);
		
		textFieldCVV = new JTextField();
		textFieldCVV.setBounds(166, 152, 96, 20);
		contentPane.add(textFieldCVV);
		textFieldCVV.setColumns(10);
		
		lblNumeroCuenta = new JLabel("New label");
		lblNumeroCuenta.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.NumeroCuenta"));
		lblNumeroCuenta.setBounds(10, 69, 340, 14);
		contentPane.add(lblNumeroCuenta);
		
		lblFechaCaducidad = new JLabel("New label");
		lblFechaCaducidad.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.FechaCaducidad"));
		lblFechaCaducidad.setBounds(10, 127, 49, 14);
		contentPane.add(lblFechaCaducidad);
		
		lblCVV = new JLabel("New label");
		lblCVV.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.CVV"));
		lblCVV.setBounds(166, 127, 49, 14);
		contentPane.add(lblCVV);
		
		lblMensaje = new JLabel();
		lblMensaje.setForeground(Color.red);
		lblMensaje.setBounds(24, 200, 402, 14);
		contentPane.add(lblMensaje);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pagoRealizado)jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(166, 225, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldNumeroCuenta.getText().trim().isEmpty()&& !textFieldNumeroCuenta.getText().trim().isEmpty()&&!textFieldFechaCaducidad.getText().trim().isEmpty()&&!textFieldCVV.getText().trim().isEmpty()){
					lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.PagoRealizado"));
					pagoRealizado = true;
				}
				else {
					lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.NoValido"));
				}
				
			}
		});
		btnPagar.setBounds(312, 151, 89, 23);
		contentPane.add(btnPagar);
		
	
		
		
		
	
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
