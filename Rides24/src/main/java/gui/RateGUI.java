package gui;

import java.awt.EventQueue;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import domain.Rating;
import domain.Ride;

import java.awt.Label;

public class RateGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JComboBox elegirConductor;
	private JComboBox elegirNota;
	private JButton btnGuardar;
	//DefaultComboBoxModel<String> modeloConductores = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<Integer> calificaciones = new DefaultComboBoxModel<Integer>();
	private JTextArea comentarios;
	private DefaultTableModel tableModel;
	private JTable table;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public RateGUI(Traveler traveler) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblError = new JLabel("");
		lblError.setBounds(135, 155, 163, 0);
		contentPane.add(lblError);
		
		//elegirConductor = new JComboBox();
		//elegirConductor.setBounds(10, 27, 416, 37);
	//	contentPane.add(elegirConductor);
		
		elegirNota = new JComboBox();
		elegirNota.setBounds(10, 90, 416, 37);
		contentPane.add(elegirNota);
		
		//elegirConductor.setModel(modeloConductores);
		//List<String> conductores = facade.getDriversOfTraveler(traveler);
		//for(String conductor : conductores)modeloConductores.addElement(conductor);
		
		String[] columnNames = {"Nombre", "CalificacionMedia", "Email"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 416, 68);
		contentPane.add(scrollPane);
		
		List<Driver> conductores = facade.getDriversOfTraveler2(traveler);
		for (Driver con : conductores) {
			tableModel.addRow(new Object[] {
					con.getName(),
					con.getCalificacionMedia(),
					con.getEmail()
				
			});
		}
	

		
		elegirNota.setModel(calificaciones);
		
		for(int i=0; i<=10; i++) {
			calificaciones.addElement(i);
		}
		
		btnGuardar = new JButton("Rate");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int nota = (int) elegirNota.getSelectedItem();
				String com;
				if(comentarios.getText().isEmpty()) com = null;
				else com = comentarios.getText();
				
				int row = table.getSelectedRow();
				String emailConductor = conductores.get(row).getEmail();
				//String emailConductor = (String) elegirConductor.getSelectedItem();
				
				
				Driver conductor = facade.buscarDriver(emailConductor);	
				
				
				
				boolean done = facade.agregarRating(conductor, nota,com);
				
				
				if(done)
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Done"));
				else
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Error"));
				
				
			}
		});
		btnGuardar.setBounds(10, 195, 416, 23);
		contentPane.add(btnGuardar);
		
		comentarios = new JTextArea();
		comentarios.setBounds(10, 147, 416, 37);
		contentPane.add(comentarios);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					jButtonClose_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(10, 229, 416, 23);
		contentPane.add(jButtonClose);
		
	
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}	
}

