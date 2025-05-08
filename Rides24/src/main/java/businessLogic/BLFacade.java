package businessLogic;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Reserva;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, String driverEmail, float price, boolean asegurado) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	public boolean register(User u);
	
	public boolean login(String name, String password);

	public boolean guardarReserva(Reserva reserva);
	
	public boolean anularReserva(String idReserva);

	public ArrayList<Reserva> getBookingList(Driver driver);
	
	public List<String> getAllDrivers();
	
	public ArrayList<String> getDriversOfTraveler(Traveler travel);
	public ArrayList<Driver> getDriversOfTraveler2(Traveler travel);
	
	public List<Reserva> getReservationsTraveler(Traveler travel);
	
	public List<Ride> getRidesWithoutTravelers(Driver driver);
	
	public void removeRide(int numRide);
		
	public Driver buscarDriver(String email);
	
	public boolean agregarRating(Driver conductor, int nota, String com);
	
	public List<Reserva> getReservasPendientes(Driver driver);
	
	public void actualizarReserva(Reserva reserva);
	
	public void eliminarReserva(Reserva reserva);
	
	//MIRAR 
	public boolean gestionarDinero(User user,int cantidad, int modo); //1 extraer, 2 ingresar

	public boolean darseDeBaja(String email,String name, String pass, String nTelefono, int modo); //1 Driver, 2 Traveler

	Ride getRideByNumber(Ride rideNumber);

	Ride getRideByNumber(int rideNumber);
	

	
	
}
