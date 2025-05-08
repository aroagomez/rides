package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.User;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class DriverGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnSignOut;
	private JButton btnGestionarMonedero;
	
	/**
	 * This is the default constructor
	 */
	public DriverGUI(User d) {
		super();
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.this.title")); 

		driver=(Driver)d;
		
		
		this.setSize(495, 290);
		
		jContentPane = new JPanel();
		GridBagLayout gbl_jContentPane = new GridBagLayout();
		gbl_jContentPane.columnWidths = new int[]{114, 268, 99, 0};
		gbl_jContentPane.rowHeights = new int[]{39, 36, 36, 36, 36, 0};
		gbl_jContentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_jContentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		jContentPane.setLayout(gbl_jContentPane);
		
		btnGestionarMonedero = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.GestionarMonedero")); //$NON-NLS-1$ //$NON-NLS-2$
		btnGestionarMonedero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new GestionarMonederoGUI(d);
				a.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnGestionarMonedero = new GridBagConstraints();
		gbc_btnGestionarMonedero.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnGestionarMonedero.insets = new Insets(0, 0, 5, 5);
		gbc_btnGestionarMonedero.gridx = 0;
		gbc_btnGestionarMonedero.gridy = 0;
		jContentPane.add(btnGestionarMonedero, gbc_btnGestionarMonedero);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_jLabelSelectOption = new GridBagConstraints();
		gbc_jLabelSelectOption.anchor = GridBagConstraints.NORTH;
		gbc_jLabelSelectOption.fill = GridBagConstraints.HORIZONTAL;
		gbc_jLabelSelectOption.insets = new Insets(0, 0, 5, 0);
		gbc_jLabelSelectOption.gridwidth = 3;
		gbc_jLabelSelectOption.gridx = 0;
		gbc_jLabelSelectOption.gridy = 0;
		jContentPane.add(jLabelSelectOption, gbc_jLabelSelectOption);
		
		//para ver reservas pendientes
		JButton btnVerPendientes = new JButton("Ver reservas pendientes");
		btnVerPendientes.addActionListener(e -> {
		    new ReservasPendientesGUI(driver).setVisible(true);
		});
		
		btnSignOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.DarseBaja"));
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new DarseBajaGUI();
				a.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnSignOut = new GridBagConstraints();
		gbc_btnSignOut.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnSignOut.insets = new Insets(0, 0, 5, 0);
		gbc_btnSignOut.gridx = 2;
		gbc_btnSignOut.gridy = 0;
		jContentPane.add(btnSignOut, gbc_btnSignOut);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(driver);
				a.setVisible(true);
			}
		});
		GridBagConstraints gbc_jButtonCreateQuery = new GridBagConstraints();
		gbc_jButtonCreateQuery.fill = GridBagConstraints.BOTH;
		gbc_jButtonCreateQuery.insets = new Insets(0, 0, 5, 0);
		gbc_jButtonCreateQuery.gridwidth = 3;
		gbc_jButtonCreateQuery.gridx = 0;
		gbc_jButtonCreateQuery.gridy = 1;
		jContentPane.add(jButtonCreateQuery, gbc_jButtonCreateQuery);
		GridBagConstraints gbc_btnVerPendientes = new GridBagConstraints();
		gbc_btnVerPendientes.fill = GridBagConstraints.BOTH;
		gbc_btnVerPendientes.insets = new Insets(0, 0, 5, 0);
		gbc_btnVerPendientes.gridwidth = 3;
		gbc_btnVerPendientes.gridx = 0;
		gbc_btnVerPendientes.gridy = 2;
		jContentPane.add(btnVerPendientes, gbc_btnVerPendientes);
		
		JButton btnModificarViaje = new JButton("Modificar Viaje"); //$NON-NLS-1$ //$NON-NLS-2$
		btnModificarViaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyRidesGUI(driver);
				a.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnModificarViaje = new GridBagConstraints();
		gbc_btnModificarViaje.fill = GridBagConstraints.BOTH;
		gbc_btnModificarViaje.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificarViaje.gridwidth = 3;
		gbc_btnModificarViaje.gridx = 0;
		gbc_btnModificarViaje.gridy = 3;
		jContentPane.add(btnModificarViaje, gbc_btnModificarViaje);
		GridBagConstraints gbc_jButtonClose = new GridBagConstraints();
		gbc_jButtonClose.fill = GridBagConstraints.BOTH;
		gbc_jButtonClose.gridwidth = 3;
		gbc_jButtonClose.gridx = 0;
		gbc_jButtonClose.gridy = 4;
		jContentPane.add(jButtonClose, gbc_jButtonClose);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		
		
		
		setContentPane(jContentPane);
		
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	

	private void jButtonClose_actionPerformed(ActionEvent e) {
			this.setVisible(false);
	}
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getName());
	}
} 

