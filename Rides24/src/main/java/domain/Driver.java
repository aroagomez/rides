package domain;

import java.io.Serializable; 

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Rating> calificaciones ;
	private float calificacionMedia;




	public Driver() {
		super();
		calificaciones = new ArrayList<Rating>();
	}
	

	public Driver(String email, String name, String phone, String password, float money) {
		super(email,name,phone,password, money);
		calificaciones = new ArrayList<Rating>();
	}


	
	public List<Ride> getRides() {
		return rides;
	}


	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}


	public ArrayList<Rating> getCalificaciones() {
		return calificaciones;
	}


	public void setCalificaciones(ArrayList<Rating> calificaciones) {
		this.calificaciones = calificaciones;
	}
	
	//CAMBIAR
	


	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces,float price, boolean asegurar)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this, asegurar);
        rides.add(ride);
        return ride;
	}
	
	public Ride addRide(String from, String to, Date date, int nPlaces,float price)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (this.getEmail() != other.getEmail())
			return false;
		return true;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
	


	public float getCalificacionMedia() {
		return calificacionMedia;
	}


	public void setCalificacionMedia(float calificacionMedia) {
		this.calificacionMedia = calificacionMedia;
	}
	
	public void updateCalificacionMedia() {
        float suma = 0;
        for (Rating rating : calificaciones) {
            suma += rating.getNota(); // Sumar las notas
        }  
            this.calificacionMedia= suma / calificaciones.size();  // Calcular la media 
    }

	
	
}
