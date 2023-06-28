package it.polito.tdp.yelp.model;

public class Event implements Comparable<Event> {

	public enum EventType {
		
		INTERVISTA,
		FERIE,
		ASSEGNA
		
	}
	
	private EventType type;
	private Integer t;
	private Integer giornalista;
	private User utente;
	public Event(EventType type, Integer t, Integer giornalista, User utente) {
		super();
		this.type = type;
		this.t = t;
		this.giornalista = giornalista;
		this.utente = utente;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public Integer getT() {
		return t;
	}
	public void setT(Integer t) {
		this.t = t;
	}
	public Integer getGiornalista() {
		return giornalista;
	}
	public void setGiornalista(Integer giornalista) {
		this.giornalista = giornalista;
	}
	public User getUtente() {
		return utente;
	}
	public void setUtente(User utente) {
		this.utente = utente;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.t.compareTo(o.getT());
	}
	@Override
	public String toString() {
		return "Event [type=" + type + ", t=" + t + ", giornalista=" + giornalista + ", utente=" + utente + "]";
	}
	
	
	
	
}
