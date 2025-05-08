package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class GestionarMonederoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCantidad;
	private JButton btnClose;
	private JRadioButton rdbtnExtraer;
	private JRadioButton rdbtnIngresar;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblIntroduzca;
	private JButton btnGuardar;
	private JLabel lblRespuesta;
	private JLabel lblTitulo;
	private JLabel lblCuantoDinero;



	/**
	 * Create the frame.
	 */
	public GestionarMonederoGUI(User dbUser) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rdbtnIngresar = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Ingresar")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(rdbtnIngresar);
		rdbtnIngresar.setBounds(84, 154, 111, 23);
		contentPane.add(rdbtnIngresar);
		
		rdbtnExtraer = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Extraer")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(rdbtnExtraer);
		rdbtnExtraer.setBounds(259, 154, 111, 23);
		contentPane.add(rdbtnExtraer);
		
		lblRespuesta = new JLabel("");
		lblRespuesta.setBounds(36, 207, 206, 33);
		lblRespuesta.setForeground(Color.red);
		contentPane.add(lblRespuesta);
		
		lblTitulo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Titulo")); //$NON-NLS-1$ //$NON-NLS-2$
		lblTitulo.setBounds(84, 33, 342, 43);
		contentPane.add(lblTitulo);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(187, 229, 89, 23);
		contentPane.add(btnClose);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBounds(251, 87, 96, 20);
		contentPane.add(textFieldCantidad);
		textFieldCantidad.setColumns(10);
		
		lblIntroduzca = new JLabel();
		lblIntroduzca.setBounds(84, 81, 121, 32);
		lblIntroduzca.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Label"));
		contentPane.add(lblIntroduzca);
		
		btnGuardar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Guardar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean done = false;
				ButtonModel selected = buttonGroup.getSelection();
				int cantidad = Integer.parseInt(textFieldCantidad.getText());
				
				if(rdbtnExtraer.isSelected()){
					JFrame a = new PagoGUI(); 
					a.setVisible(true);
					done = facade.gestionarDinero(dbUser,cantidad,1);
				}
					
				if(rdbtnIngresar.isSelected()) {
					JFrame a = new PagoGUI(); 
					a.setVisible(true);
					done = facade.gestionarDinero(dbUser,cantidad,2);
				}
				
				if(done) {
					lblRespuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.OperacionCorrecta"));
				}
				else {
					lblRespuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.OperacionIncorrecta"));
				}
				
			}
		});
		btnGuardar.setBounds(187, 195, 89, 23);
		contentPane.add(btnGuardar);
		
		lblCuantoDinero = new JLabel();
		lblCuantoDinero.setBounds(84, 11, 258, 14);
		contentPane.add(lblCuantoDinero);
		lblCuantoDinero.setText(ResourceBundle.getBundle("Etiquetas").getString("GestionarMonederoGUI.Dinero")+" "+dbUser.getMoney());
		
		
		
	}
	
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}	
}
