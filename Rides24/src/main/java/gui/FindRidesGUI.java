package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class FindRidesGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private JComboBox<String> jComboBoxOrigin = new JComboBox<String>();
	DefaultComboBoxModel<String> ciudadesSalida = new DefaultComboBoxModel<String>();

	private JComboBox<String> jComboBoxDestination = new JComboBox<String>();
	DefaultComboBoxModel<String> ciudadesLlegada = new DefaultComboBoxModel<String>();

	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo"));
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Rides")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private List<Date> datesWithRidesCurrentMonth = new Vector<Date>();

	private JTable tableRides= new JTable();
	
	private DefaultTableModel tableModelRides;


	private String[] columnNamesRides = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Price")
	};


	public FindRidesGUI(User user)
	{
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.FindRides"));
		BLFacade facade = MainGUI.getBusinessLogic();
		List<String> origins=facade.getDepartCities();
		
		for(String location:origins) ciudadesSalida.addElement(location);
		
		jComboBoxOrigin.setModel(ciudadesSalida);
		jComboBoxOrigin.setBounds(new Rectangle(103, 50, 172, 20));
		
		List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
		for(String aciti:aCities) {
			ciudadesLlegada.addElement(aciti);
		}
		jComboBoxDestination.setModel(ciudadesLlegada);
		jComboBoxDestination.setBounds(new Rectangle(103, 80, 172, 20));

		jLabelEventDate.setBounds(new Rectangle(457, 6, 140, 25));
		jLabelEvents.setBounds(172, 229, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(20, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		
		
		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelOrigin);

		getContentPane().add(jLabelDestination);

		
		

		
		jComboBoxOrigin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ciudadesLlegada.removeAllElements();
				BLFacade facade = MainGUI.getBusinessLogic();

				List<String> aCities=facade.getDestinationCities((String)jComboBoxOrigin.getSelectedItem());
				for(String aciti:aCities) {
					ciudadesLlegada.addElement(aciti);
				}
				tableModelRides.getDataVector().removeAllElements();
				tableModelRides.fireTableDataChanged();	
			}
		});


		
		jComboBoxDestination.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,	new Color(210,228,238));

				BLFacade facade = MainGUI.getBusinessLogic();

				datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
				paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
				
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jComboBoxOrigin, null);

		this.getContentPane().add(jComboBoxDestination, null);


		jCalendar1.setBounds(new Rectangle(300, 50, 225, 150));


		// Code for JCalendar
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					

					
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						

						jCalendar1.setCalendar(calendarAct);

					}
					
					try {
						tableModelRides.setDataVector(null, columnNamesRides);
						tableModelRides.setColumnCount(4); // another column added to allocate ride objects

						BLFacade facade = MainGUI.getBusinessLogic();
						List<domain.Ride> rides=facade.getRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),UtilDate.trim(jCalendar1.getDate()));

						if (rides.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoRides")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Rides")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Ride ride:rides){
							Vector<Object> row = new Vector<Object>();
							row.add(ride.getDriver().getName());
							row.add(ride.getnPlaces());
							row.add(ride); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
							tableModelRides.addRow(row);		
						}
						
						datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
						paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);


					} catch (Exception e1) {

						e1.printStackTrace();
					}
					tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
					tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

				}
			} 
			
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(172, 257, 346, 150));

		scrollPaneEvents.setViewportView(tableRides);
		tableModelRides = new DefaultTableModel(null, columnNamesRides);

		tableRides.setModel(tableModelRides);

		tableModelRides.setDataVector(null, columnNamesRides);
		tableModelRides.setColumnCount(4); // another column added to allocate ride objects

		tableRides.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableRides.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPaneEvents, null);
		datesWithRidesCurrentMonth=facade.getThisMonthDatesWithRides((String)jComboBoxOrigin.getSelectedItem(),(String)jComboBoxDestination.getSelectedItem(),jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithRidesCurrentMonth,Color.CYAN);
		
		JButton btnErreserbatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Erreserbatu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnErreserbatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = tableRides.getSelectedRow();
				if(pos == -1) {
					
				}else { 
					Ride ride = (Ride) tableModelRides.getValueAt(pos, 2);
					
					JFrame a = new BookRideGUI(ride, user);
					a.setVisible(true);
					
				}
			}
		});
		btnErreserbatu.setBounds(244, 419, 215, 34);
		if(user instanceof Traveler) { 
			getContentPane().add(btnErreserbatu);
		}
		
	}
	public static void paintDaysWithEvents(JCalendar jCalendar,List<Date> datesWithEventsCurrentMonth, Color color) {


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);


			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(color);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
