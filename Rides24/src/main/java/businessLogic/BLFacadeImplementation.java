package businessLogic;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.swing.JFrame;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Reserva;
import domain.Driver;
import domain.Rating;
import exceptions.RideMustBeLaterThanTodayException;
import gui.DriverGUI;
import gui.LoginGUI;
import gui.TravelerGUI;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    this.initializeBD();
		    
		  //dbManager.close();
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    @WebMethod public List<String> getAllDrivers(){
    	dbManager.open();		
		List<String> drivers=dbManager.getAllDrivers();		
		dbManager.close();
		return drivers;
    	
    }
    
    @WebMethod public ArrayList<String> getDriversOfTraveler(Traveler traveler){
    	dbManager.open();		
		ArrayList<String> drivers=dbManager.getDriversOfTraveler(traveler);		
		dbManager.close();
		return drivers;
    	
    }
    
    @WebMethod public ArrayList<Driver> getDriversOfTraveler2(Traveler traveler){
    	dbManager.open();		
		ArrayList<Driver> drivers=dbManager.getDriversOfTraveler2(traveler);		
		dbManager.close();
		return drivers;
    	
    }
    
    
    
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, String driverEmail, float price, boolean asegurar ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, driverEmail, price, asegurar);		
		dbManager.close();
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    
    public boolean login(String name, String password) {
    	dbManager.open();  	
    	User  dbUser =dbManager.login(name, password);
    	dbManager.close();
    	if(dbUser==null) {
    		return false; // no hay usuario con esas caracteristicas
    	}else {
    		// si el user es Driver, le abrimos la interfaz de Driver, y si es Traveler, la de traveler
    		if(dbUser instanceof Driver ) {
        		JFrame driverGUI = new DriverGUI(dbUser);
        		driverGUI.setVisible(true);
    		}else if(dbUser instanceof Traveler) {
    			JFrame travelerGUI = new TravelerGUI(dbUser);
        		travelerGUI.setVisible(true);
    		}
    		

			
    	}
    	return true;
    }


	@Override
	public boolean register(User user) {
		dbManager.open();
		boolean registro =dbManager.register(user);
		dbManager.close();
		return registro;
	}

	@Override
	public boolean guardarReserva(Reserva reserva) {
		dbManager.open();
		boolean guardado = dbManager.guardarReserva(reserva);
		dbManager.close();
		return guardado;
	}
	
	public boolean anularReserva(String idReserva) {
		dbManager.open();
		boolean guardado = dbManager.anularReserva(idReserva);
		dbManager.close();
		return guardado;
	}

	@Override
	public ArrayList<Reserva> getBookingList(Driver driver) {
		dbManager.open();
		ArrayList<Reserva> bookingList = dbManager.getBookingList(driver);
		dbManager.close();
		return bookingList;
	}
	
	public Driver buscarDriver (String email) {
		dbManager.open();
		Driver driver = dbManager.buscarDriver(email);
		dbManager.close();
		return driver;
		
	}
	


	public boolean agregarRating(Driver conductor, int nota, String com) {
		dbManager.open();
		boolean done = dbManager.agregarRating(conductor, nota, com);
		dbManager.close();
		return done;
		
	}
	
	public List<Reserva> getReservationsTraveler(Traveler traveler){
		dbManager.open();		
		List<Reserva> reservations=dbManager.getReservationsTraveler(traveler);		
		dbManager.close();
		return reservations;
		
	}

	public List<Ride> getRidesWithoutTravelers(Driver driver){
		dbManager.open();		
		List<Ride> rides=dbManager.getRidesWithoutTravelers(driver);		
		dbManager.close();
		return rides;
	}
	
	public void removeRide(int numRide) {
		dbManager.open();
		dbManager.removeRide(numRide);
		dbManager.close();
	}
	
	public List<Reserva> getReservasPendientes(Driver driver){
		return dbManager.getReservasPendientes(driver);
	}
	
	@Override
	public void actualizarReserva(Reserva reserva) {
	    dbManager.actualizarReserva(reserva);
	}
	
	@Override
	public void eliminarReserva(Reserva reserva) {
	    dbManager.eliminarReserva(reserva);
	}
	
	public boolean gestionarDinero(User u, int cantidad ,int modo) {
		dbManager.open();
		boolean done = dbManager.gestionarDinero(u,cantidad,modo);
		dbManager.close();
		return done;
		
	}

	public boolean darseDeBaja(String email,String name, String pass, String nTelefono, int modo) {
		dbManager.open();
		boolean done = dbManager.darseDeBaja(email,name,pass,nTelefono, modo);
		dbManager.close();
		return done;
	
	}
	
	@Override
	public Ride getRideByNumber(int rideNumber) {
	    dbManager.open();
	    Ride ride = dbManager.getRide(rideNumber);
	    dbManager.close();
	    return ride;
	}

	public boolean agregarRating(Ride selectedRide, int nota, String com) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ride getRideByNumber(Ride rideNumber) {
		// TODO Auto-generated method stub
		return null;
	}




}

