package gui;

import java.awt.EventQueue;

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
import domain.Ride;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class MyRidesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JComboBox cbRides;
	private JTable ridesTable;
	private DefaultTableModel tableModel;
	private List<Ride> rides;
	private JButton btnModificar;
	private JButton btnClose;
	//DefaultComboBoxModel<Ride> modeloRides = new DefaultComboBoxModel<Ride>();

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public MyRidesGUI( Driver d) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//cbRides = new JComboBox();
		//cbRides.setBounds(10, 29, 416, 42);
		//contentPane.add(cbRides);
		
		
		//List<Ride> rides = facade.getRidesWithoutTravelers(d);
		//for(Ride ride : rides) {
			//modeloRides.addElement(ride);
		//}
		//cbRides.setModel(modeloRides);
		
		
		// Configurar tabla de viajes
			String[] columnNames = {"ID", "Origen", "Destino", "Fecha", "Plazas", "Precio"};
			tableModel = new DefaultTableModel(columnNames, 0) {
				@Override public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			ridesTable = new JTable(tableModel);
			ridesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(ridesTable);
			scrollPane.setBounds(10, 10, 416, 150);
			contentPane.add(scrollPane);

			// Cargar datos
			rides = facade.getRidesWithoutTravelers(d);
			for (Ride ride : rides) {
				tableModel.addRow(new Object[] {
					ride.getRideNumber(),
					ride.getFrom(),
					ride.getTo(),
					ride.getDate(),
					ride.getnPlaces(),
					ride.getPrice()
				});
			}
		
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = ridesTable.getSelectedRow();
				if (row >= 0) {
					int numRide = rides.get(row).getRideNumber();
					facade.removeRide(numRide);
					JFrame edit = new CreateRideGUI(d);
					edit.setVisible(true);
					dispose();
				}
			}
		});
		btnModificar.setBounds(10, 170, 416, 36);
		contentPane.add(btnModificar);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(10, 210, 416, 36);
		contentPane.add(btnClose);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

}
