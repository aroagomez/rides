package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Traveler;
import domain.User;

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


public class TravelerGUI extends JFrame {
	
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
	private JButton btnErreserba;
	private JButton btnCalificar;
	private JButton btnCancelarRes;
	private JButton btnSignOut;

	private JButton btnGestionarMonedero;
	
	/**
	 * This is the default constructor
	 */
	public TravelerGUI(User dbUser) {
		super();
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$

		Traveler traveler=(Traveler) dbUser;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		
		jContentPane = new JPanel();
		GridBagLayout gbl_jContentPane = new GridBagLayout();
		gbl_jContentPane.columnWidths = new int[]{135, 211, 135, 0};
		gbl_jContentPane.rowHeights = new int[]{33, 43, 43, 40, 43, 0};
		gbl_jContentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_jContentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		jContentPane.setLayout(gbl_jContentPane);
		
		btnGestionarMonedero = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.GestionarMonedero"));
		btnGestionarMonedero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new GestionarMonederoGUI(dbUser);
				a.setVisible(true);
			}
		});
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
		GridBagConstraints gbc_btnGestionarMonedero = new GridBagConstraints();
		gbc_btnGestionarMonedero.anchor = GridBagConstraints.SOUTH;
		gbc_btnGestionarMonedero.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGestionarMonedero.insets = new Insets(0, 0, 5, 5);
		gbc_btnGestionarMonedero.gridx = 0;
		gbc_btnGestionarMonedero.gridy = 0;
		jContentPane.add(btnGestionarMonedero, gbc_btnGestionarMonedero);
		
		btnCancelarRes = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Cancelar"));
		btnCancelarRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MyReservationsGUI(traveler);
				a.setVisible(true);
				
			}
		});
		
		
		btnErreserba = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Erreserba"));
		btnErreserba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindRidesGUI(traveler);

				a.setVisible(true);
			}
		});
		
		btnSignOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.DarseBaja"));
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new DarseBajaGUI();
				a.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnSignOut = new GridBagConstraints();
		gbc_btnSignOut.anchor = GridBagConstraints.SOUTH;
		gbc_btnSignOut.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSignOut.insets = new Insets(0, 0, 5, 0);
		gbc_btnSignOut.gridx = 2;
		gbc_btnSignOut.gridy = 0;
		jContentPane.add(btnSignOut, gbc_btnSignOut);
		GridBagConstraints gbc_btnErreserba = new GridBagConstraints();
		gbc_btnErreserba.fill = GridBagConstraints.BOTH;
		gbc_btnErreserba.insets = new Insets(0, 0, 5, 0);
		gbc_btnErreserba.gridwidth = 3;
		gbc_btnErreserba.gridx = 0;
		gbc_btnErreserba.gridy = 1;
		jContentPane.add(btnErreserba, gbc_btnErreserba);
		GridBagConstraints gbc_btnCancelarRes = new GridBagConstraints();
		gbc_btnCancelarRes.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarRes.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelarRes.gridwidth = 3;
		gbc_btnCancelarRes.gridx = 0;
		gbc_btnCancelarRes.gridy = 2;
		jContentPane.add(btnCancelarRes, gbc_btnCancelarRes);
		
		btnCalificar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Calificar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCalificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new RateGUI(traveler);
				a.setVisible(true);
				
			}
		});
		GridBagConstraints gbc_btnCalificar = new GridBagConstraints();
		gbc_btnCalificar.fill = GridBagConstraints.BOTH;
		gbc_btnCalificar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCalificar.gridwidth = 3;
		gbc_btnCalificar.gridx = 0;
		gbc_btnCalificar.gridy = 3;
		jContentPane.add(btnCalificar, gbc_btnCalificar);
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
} 

