package gui;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Reserva;
import domain.Traveler;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyReservationsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JComboBox reservasCB;
	//DefaultComboBoxModel<Reserva> misReservas = new DefaultComboBoxModel<Reserva>();
	private JButton btnCancelar;
	private JButton btnClose;
	private DefaultTableModel tableModel;
	private JTable table;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MyReservationsGUI(Traveler traveler) {
		
		BLFacade facade = MainGUI.getBusinessLogic(); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//reservasCB = new JComboBox();
		//reservasCB.setBounds(10, 45, 416, 56);
		//contentPane.add(reservasCB);
		
		//reservasCB.setModel(misReservas);
		//List<Reserva> reservas = facade.getReservationsTraveler(traveler);
		//for(Reserva res : reservas) {
		//	misReservas.addElement(res);
		//}
		
		String[] columnNames = {"ID", "Viaje", "Numero Asientos"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 416, 103);
		contentPane.add(scrollPane);
		
		List<Reserva> reservas = facade.getReservationsTraveler(traveler);
		for (Reserva res : reservas) {
			tableModel.addRow(new Object[] {
					res.getRide(),
					res.getNumAsientos(),
					res.getId()
				
			});
		}
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String idRes = ((Reserva)reservasCB.getSelectedItem()).getId();
				//boolean done = facade.anularReserva(idRes);
				//if(done) System.out.println("Cancelacion realizada");
				//else System.out.println("No se pudo realizar la cancelacion");
				int row = table.getSelectedRow();
				
				String idRes = reservas.get(row).getId();
				boolean done = facade.anularReserva(idRes);
				if(done) System.out.println("Cancelacion realizada");
				else System.out.println("No se pudo realizar la cancelacion");
				
				
			}
		});
		btnCancelar.setBounds(10, 125, 416, 48);
		contentPane.add(btnCancelar);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(10, 199, 416, 42);
		contentPane.add(btnClose);
		
		
		
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}	
	
}
