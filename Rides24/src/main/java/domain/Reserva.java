package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reserva {
	@Id
	private String id;
	private Driver driver;
	private Traveler traveler;
	private Ride ride;
	private int numAsientos;
	
	public Reserva(Driver driver, Traveler traveler, Ride ride, int numAsientos) {
		super();
		//tomamos los dos atributos ID de cada clase para crear ID de Reserva
		//asi nos aseguramos que no vaya a haber dos iguales
		this.id = ride.getRideNumber() + traveler.getEmail();
		this.driver = driver;
		this.traveler = traveler;
		this.ride = ride;
		this.numAsientos = numAsientos;
	}
	
	
	@Override
	public String toString() {
		return traveler.getName() + " - "+ ride.toString() +" - "+ numAsientos;
	}


	/**
	 * @return the eserlekuKop
	 */
	public int getNumAsientos() {
		return numAsientos;
	}


	/**
	 * @param eserlekuKop the eserlekuKop to set
	 */
	public void setNumAsientos(int numAsientos) {
		this.numAsientos = numAsientos;
	}


	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	/**
	 * @return the traveler
	 */
	public Traveler getTraveler() {
		return traveler;
	}
	/**
	 * @param traveler the traveler to set
	 */
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	/**
	 * @return the ride
	 */
	public Ride getRide() {
		return ride;
	}
	/**
	 * @param ride the ride to set
	 */
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	
	
	
	
}
