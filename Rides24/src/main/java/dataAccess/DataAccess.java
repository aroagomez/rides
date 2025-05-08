package dataAccess;

import java.io.File;





import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Reserva;
import domain.Driver;
import domain.Rating;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.io.*;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez", "123", "bat", 100);
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga", "123", "bat", 100);
			Driver driver3=new Driver("driver3@gmail.com","Test driver", "123", "bat", 100);
			Driver driver4=new Driver("driver4@gmail.com","Test", "123", "bat", 100);

			//Create Travelers
			Traveler traveler1=new Traveler("traveler1@gmail.com","traveler", "123", "bat", 100);
			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4 , 20);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4 , 20);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4 , 20);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 20);
			
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 20);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 20);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 20);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 20);
			
						
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);
			db.persist(driver4);
			db.persist(traveler1);
	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * @param asegurar 
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, String driverEmail,float price, boolean asegurar) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date +" price " + price);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			if (asegurar) {
				if (driver.getMoney() < 2) {
					asegurar = false;
				}else {
					driver.setMoney(driver.getMoney()-2);
				}
			}
			
			Ride ride = driver.addRide(from, to, date, nPlaces, price, asegurar);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
	}
	
	public Ride createRide(String from, String to, Date date, int nPlaces, String driverEmail,float price) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date +" price " + price);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	

	public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public User login (String name, String password) {
		// como el ID es el email, no podemos acer find, porque en nel metodo no pedimos el email
		// luego tenemos que realizar una consulta JPQL
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.name=?1 AND u.password=?2", User.class);   
		query.setParameter(1, name);
		query.setParameter(2, password);
		
		try {
	        User user = query.getSingleResult();
	        return user;
	    } catch (Exception e) { // si no se encuentra usuario, un mensaje de error
	        System.out.println("ERROR. No se encuentra usuario con esos datos");
	        return null;
	    }
	}


	public boolean register(User user) {
		try {
			db.getTransaction().begin();
			db.persist(user);
			db.getTransaction().commit();			
		}
		catch(Exception e) {
			System.out.println("No se ha podido registrar el usuario");
			return false;
		}
		
		return true;
	}

	public boolean guardarReserva(Reserva reserva) {
		try {
			db.getTransaction().begin();
			db.persist(reserva);
			db.getTransaction().commit();
			return true;
		}catch(Exception e) {
			System.out.println("ERROR. No se ha podido guardar la reserva");
			return false;
		}
	}
	
	public boolean anularReserva(String idReserva) {
		try {
			db.getTransaction().begin();
			Reserva reserva = db.find(Reserva.class, idReserva);
			
			Driver driver = db.find(Driver.class, reserva.getDriver().getEmail());
			Traveler traveler = db.find(Traveler.class, reserva.getTraveler().getEmail());
			
			Ride ride = db.find(Ride.class, reserva.getRide());
            //ride.setnPlaces(ride.getnPlaces() + reserva.getNumAsientos());
            db.merge(ride);
            
			traveler.setNumRides(traveler.getNumRides()+1);
			traveler.setMoney(traveler.getMoney()+ (reserva.getNumAsientos()*ride.getPrice()/2));
			
			driver.setMoney(driver.getMoney() - (reserva.getNumAsientos()*ride.getPrice()/2));
			
			db.merge(traveler);
			db.merge(driver);
			
			db.remove(reserva);
			db.getTransaction().commit();
			return true;
		}catch(Exception e) {
			System.out.println("ERROR. No se ha podido anular la reserva");
			return false;
		}
	}

	public ArrayList<Reserva> getBookingList(Driver driver) {
		TypedQuery<Reserva> query = db.createQuery("SELECT b FROM Booking b WHERE b.driver=?1", Reserva.class);   
		query.setParameter(1, driver);
		
		ArrayList<Reserva> bookingList = (ArrayList<Reserva>) query.getResultList();
		return bookingList;
	}

	public List<String> getAllDrivers(){
		TypedQuery<String> query = db.createQuery("SELECT email FROM Driver",String.class);
		List<String> drivers = query.getResultList(); 
		return drivers;
	}
	
	public ArrayList<String> getDriversOfTraveler(Traveler traveler){
		TypedQuery<Reserva> query = db.createQuery("SELECT r FROM  Reserva r WHERE r.traveler = ?1 and r.aceptada = ?2 ",Reserva.class);
		query.setParameter(1, traveler);
		query.setParameter(2, true);
		List<Reserva> reservas = query.getResultList(); //reservas hechas por el viajero
		ArrayList<Driver> conductores = new ArrayList <Driver>();
		ArrayList<String>emailConductores = new ArrayList<String>();
		
		for (Reserva reserva : reservas) {
			conductores.add(reserva.getDriver());
		}
		
		for( Driver conductor : conductores) {
			emailConductores.add(conductor.getEmail());
		}
		
		return emailConductores;
		
		
		
		
	}
	
	public ArrayList<Driver> getDriversOfTraveler2(Traveler traveler){
		TypedQuery<Reserva> query = db.createQuery("SELECT r FROM  Reserva r WHERE r.traveler = ?1 and r.aceptada = ?2 ",Reserva.class);
		query.setParameter(1, traveler);
		query.setParameter(2, true);
		List<Reserva> reservas = query.getResultList(); //reservas hechas por el viajero
		ArrayList<Driver> conductores = new ArrayList <Driver>();
		ArrayList<String>emailConductores = new ArrayList<String>();
		
		for (Reserva reserva : reservas) {
			conductores.add(reserva.getDriver());
		}
		
		
		
		return conductores;
		
		
		
		
	}
	
	public Driver buscarDriver(String email) {
		db.getTransaction().begin();
		 Driver driver = db.find(Driver.class, email);
		 db.getTransaction().commit();
		 return driver;
	}



	public boolean agregarRating(Driver conductor, int nota, String com) {
		try {
	        db.getTransaction().begin();
			Rating newRating = new Rating(nota, com);
			db.persist(newRating);
			conductor.getCalificaciones().add(newRating);
			conductor.updateCalificacionMedia();
			db.merge(conductor);
	        db.getTransaction().commit();
	        return true;
			
		}catch(Exception e) {
		     e.printStackTrace();
			 System.out.println("ERROR: No se pudo guardar la calificación.");
		      return false;
		}
		
	}
	
	public List<Reserva> getReservationsTraveler(Traveler traveler){
		TypedQuery<Reserva> query = db.createQuery("SELECT r FROM  Reserva r WHERE r.traveler = ?1 ",Reserva.class);
		query.setParameter(1, traveler);
		List<Reserva> reservas = query.getResultList(); //reservas hechas por el viajero
		return reservas;
		
		
	}
	
	
	public List<Ride> getRidesWithoutTravelers(Driver driver){
		List<Ride> viajesDriver = driver.getRides();
		List<Ride> viajesSinReservas = new ArrayList<Ride>();
		for(Ride viaje : viajesDriver) {
			TypedQuery<Reserva> query = db.createQuery("SELECT r FROM  Reserva r WHERE r.ride = ?1 ",Reserva.class);
			query.setParameter(1, viaje);
			List<Reserva> reservas = query.getResultList();
			if(reservas.isEmpty()) viajesSinReservas.add(viaje);
		}		 
		return viajesSinReservas;
	}
	
	public void removeRide(int numRide) {
		db.getTransaction().begin();
		Ride ride = db.find(Ride.class,numRide);
		db.remove(ride);
		db.getTransaction().commit();
		
	}
	
	public List<Reserva> getReservasPendientes(Driver driver){
		open();
		
		db.getTransaction().begin();
		TypedQuery<Reserva> query=db.createQuery("SELECT r FROM Reserva r WHERE r.driver = :driver AND r.aceptada = false", Reserva.class);
		query.setParameter("driver", driver);
		List<Reserva> resultados=query.getResultList();
		db.getTransaction().commit();
		
		close();
		return resultados;
	}
	
	public void actualizarReserva(Reserva reserva) {
	    try {
	    	open();
	    
	        db.getTransaction().begin();
	        db.merge(reserva); // actualiza la entidad en la base de datos

	        // Si ahora está aceptada, actualizamos asientos
	        if (reserva.isAceptada()) {
	            Ride ride = db.find(Ride.class, reserva.getRide());
	            ride.setnPlaces(ride.getnPlaces() - reserva.getNumAsientos());
	            db.merge(ride);
	            Driver driver = db.find(Driver.class, reserva.getDriver().getEmail());
				Traveler traveler = db.find(Traveler.class, reserva.getTraveler().getEmail());
			
				traveler.setNumRides(traveler.getNumRides()+1);
				
				System.out.println("Driver antes: " + driver.getMoney());
				System.out.println("Traveler antes: " + traveler.getMoney());

				float ingreso = reserva.getNumAsientos() * ride.getPrice();

				driver.setMoney(driver.getMoney() + ingreso);
				if ((traveler.getNumRides() % 10 == 0) && (traveler.getNumRides() != 0)) {
					//Como se hace para imprimir con lo de las etiquetas (poner que se le cobra la mitad por haber realizado 10 viajes consecutivos
					  System.out.println("¡Descuento especial! Se le cobra la mitad por haber realizado " + traveler.getNumRides() + " viajes.");
					  traveler.setMoney(traveler.getMoney() - ingreso / 2);
				}else {
					traveler.setMoney(traveler.getMoney() - ingreso);
				}
				
				System.out.println("Driver después: " + driver.getMoney());
				System.out.println("Traveler después: " + traveler.getMoney());
				
				db.merge(driver);
				db.merge(traveler);
				
				//ENVIAR CORREO AL TRAVELER
				System.out.println("Enviando email al viajero: ");
				String emailTraveler = reserva.getTraveler().getEmail();
				enviarEmail(emailTraveler);
				
	        }

	        db.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        db.getTransaction().rollback();
	    }
	    finally {
	    	close();
	    }
	}

	public void eliminarReserva(Reserva reserva) {
	    try {
	    	open();
	    	
	        db.getTransaction().begin();
	        Reserva r = db.merge(reserva);
	        db.remove(r);
	        db.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        db.getTransaction().rollback();
	    }
	    finally {
	    	close();
	    }
	}
	
	
	
	public boolean gestionarDinero(User u,int cantidad, int modo) {
		boolean done = true;
		if(modo ==1) { //extraer
			if(u.getMoney()>= u.getMoney()-cantidad) {
				db.getTransaction().begin();
				User user = db.find(domain.User.class, u.getEmail());
				user.setMoney(user.getMoney()-cantidad);
				db.getTransaction().commit();
			}
			else {
				done = false;
			}
			
		}
		else { 
				db.getTransaction().begin();
				User user = db.find(domain.User.class, u.getEmail());
				user.setMoney(user.getMoney()+cantidad);
				db.getTransaction().commit();
			
		}
		
		return done;
	}
	
	public boolean darseDeBaja(String email,String name, String pass, String nTelefono, int modo) {
		boolean done=true;
		if(modo==1) { //Driver
			db.getTransaction().begin();
			Driver driver = db.find(domain.Driver.class, email);
			if(driver.getName().equals(name)&&driver.getPassword().equals(pass)&&driver.getPhone().equals(nTelefono)) {
				List<Ride>viajes = driver.getRides();
				for(Ride viaje : viajes) {
					db.remove(viaje);
				}
				db.remove(driver);
				
			}
			else {
				done = false;
			}
			db.getTransaction().commit();
			
		}
		else if(modo==2) { //Traveler
			db.getTransaction().begin();
			Traveler traveler = db.find(domain.Traveler.class, email);
				if(traveler.getName().equals(name)&&traveler.getPassword().equals(pass)&&traveler.getPhone().equals(nTelefono)) {
					Query query = db.createQuery("SELECT r FROM Reserva r WHERE r.traveler =  traveler");
					List<Reserva> reservas = query.getResultList();
					for(Reserva res:reservas) {
						db.remove(res);
					}
					db.remove(traveler);
					
				}
				else {
					done = false;
				}
				db.getTransaction().commit();
			}
				
		return done;
	}
	
	public void enviarEmail(String emailReceptor) {
		final String receptor = emailReceptor;
		try{
            
			Properties props = new Properties();
//			props.put("mail.smtp.auth", "true");  // Si activamos, entonces hay que autenticarse
//			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.ehu.es");
		//	props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props);
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(emailReceptor));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receptor));
				message.setSubject("Confirmacion de Reserva");
				message.setText("Estimado Viajero. Se le envia este mensaje para confirmar la reserva de su viaje. Buen viaje y nos vemos a la siguiente!");
	 
				Transport.send(message);
	 
				System.out.println("Hecho");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	catch (Exception e) {System.out.println("Error: "+e.getMessage());}
	}
	
	public Ride getRide(int numRide) {
	    return db.find(Ride.class, numRide);
	}

}


