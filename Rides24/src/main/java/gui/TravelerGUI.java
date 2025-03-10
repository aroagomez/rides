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


public class TravelerGUI extends JFrame {
	
    private Traveler traveler;
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
	
	/**
	 * This is the default constructor
	 */
	public TravelerGUI(User dbUser) {
		super();
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$

		traveler=(Traveler) dbUser;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(5, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);
		
		
		btnErreserba = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TravelerGUI.Erreserba")); //$NON-NLS-1$ //$NON-NLS-2$
		btnErreserba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindRidesGUI(traveler);

				a.setVisible(true);
			}
		});
		jContentPane.add(btnErreserba);
		
		
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jContentPane.add(jButtonClose);
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

