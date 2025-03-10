package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;

import java.awt.GridLayout;
import java.awt.List;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class BookRideGUI extends JFrame {

	private JPanel panelReserva;
	private JTextField area;
	private JTextField titulo;
	private JTextField tituloAsientos;
	private JComboBox<Integer> listaAsientos;
	private JTextField Salida;
	private JTextField Llegada;
	private JTextField Date;

	private JButton jButtonClose;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookRideGUI frame = new BookRideGUI(null, null);
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
	public BookRideGUI(Ride ride, User user) {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 339);
		panelReserva = new JPanel();
		panelReserva.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelReserva);
		panelReserva.setLayout(null);
		
		JButton btnErreserbatu = new JButton();
		btnErreserbatu.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Erreserbatu"));
		btnErreserbatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int eserlekuKop = (int) listaAsientos.getSelectedItem();
				Reserva erreserba = new Reserva(ride.getDriver(), (Traveler) user, ride, eserlekuKop);
				
				BLFacade facade = MainGUI.getBusinessLogic(); 
				Boolean gordeDa = facade.guardarReserva(erreserba);
				
				if(gordeDa) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.gordeDa"));
				}else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.EzDaGorde"));
				}
			}
		});
		btnErreserbatu.setBounds(75, 202, 126, 51);
		panelReserva.add(btnErreserbatu);
		
		area = new JTextField();
		area.setBounds(33, 131, 378, 63);
		panelReserva.add(area);
		area.setColumns(10);
		
		titulo = new JTextField();
		titulo.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.OharraTitle"));
		titulo.setEditable(false);
		titulo.setBounds(33, 102, 96, 19);
		panelReserva.add(panelReserva);
		titulo.setColumns(10);
		

		titulo = new JTextField();
		titulo.setEnabled(true);
		titulo.setEditable(false);
		titulo.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.EserlekuTitle"));
		titulo.setBounds(33, 71, 125, 21);
		panelReserva.add(titulo);
		titulo.setColumns(10);
		
		Salida = new JTextField();
		Salida.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Irteera") + ": " + ride.getFrom());
		Salida.setEditable(false);
		Salida.setBounds(47, 10, 153, 19);
		panelReserva.add(Salida);
		Salida.setColumns(10);
		
		Llegada = new JTextField();
		Llegada.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Helmuga") + ": " + ride.getTo());
		Llegada.setEditable(false);
		Llegada.setBounds(242, 10, 156, 19);
		panelReserva.add(Llegada);
		Llegada.setColumns(10);
		
		Date = new JTextField();
		Date.setEditable(false);
		Date.setText(ResourceBundle.getBundle("Etiquetas").getString("BookRideGUI.Data") + ": " + ride.getDate());
		Date.setBounds(252, 39, 146, 19);
		panelReserva.add(Date);
		Date.setColumns(10);
				
		ArrayList<Integer> eserlekuKop = new ArrayList();
		for(int i=1; i<=(int)ride.getnPlaces(); i++) {
			eserlekuKop.add(i);
		}
		DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel(eserlekuKop.toArray());
		listaAsientos = new JComboBox<>(comboBoxModel);
		listaAsientos.setBounds(183, 71, 46, 21);
		panelReserva.add(listaAsientos);
		
		jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(225, 202, 146, 51);
		panelReserva.add(jButtonClose);
		
		lblError = new JLabel("");
		lblError.setBounds(36, 263, 335, 29);
		panelReserva.add(lblError);

	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
