package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.EventType;


public class Simulator {

	/*
	 * Ogni intervistatore intervista al massimo 1 utente al giorno, 1 utente al massmo una volta in tutto il periodo
	 * 
	 * ogni intervistatore un utente a caso
	 * 
	 * 60% intervistatore porta a termine lavoro e agenzia assegna uno nuovo
	 * nuovo utente simile, se no a caso
	 * 
	 * 20% intervistatore porta a termine lavoro, chiede un giorno di ferie
	 * 
	 * 
	 * 20% non riuscito a portare a termine, stesso utente, giorno successivo
	 * 
	 * 
	 * OUTPUT
	 * numero di utenti intervistati da ogni intervistatore
	 * numero di giorni che servono
	 * 
	 */
	
	
	// Parametri di input
	private int numIntervistatori;
	private int numUtenti;
	
	
	// Stato del sistema
	private Graph<User, DefaultWeightedEdge> grafo;
	private Map<Integer, Integer> numShare;
	private List<User> vertici;
	
	private List<User> intervistati;
	
	
	// Output
	private Map<Integer, Integer> numIntervistatiPerIntervistatore;
	private int numGiorni;
	
	
	// Eventi
	private PriorityQueue<Event> queue;

	
	public Simulator(int numIntervistatori, int numUtenti, Graph<User, DefaultWeightedEdge> grafo) {
		super();
		this.numIntervistatori = numIntervistatori;
		this.numUtenti = numUtenti;
		this.grafo = grafo;
		
		this.vertici = new ArrayList<>(this.grafo.vertexSet());
	}
	
	
	public void init() {
		
		this.numIntervistatiPerIntervistatore = new HashMap<>();
		this.numGiorni = 0;
		
		this.intervistati = new ArrayList<>();
		
		this.queue = new PriorityQueue<Event>();
		
		
		// creo eventi iniziali
		for(int i=1; i <= this.numIntervistatori; i++) {
			
			int posU = (int)Math.random()*this.vertici.size();
				
			User u = this.vertici.get(posU);
			
			vertici.remove(posU);
			
			this.queue.add(new Event(EventType.INTERVISTA, 1, i, u));
			
			this.numIntervistatiPerIntervistatore.put(i, 1);
			
			this.intervistati.add(u);
				
				
		}
			
	}
		
		
	
	
	public void run() {
		
		
		while(!this.queue.isEmpty()) { 
			
			Event e = this.queue.poll();
			
			System.out.println(e);
			
			int t = e.getT();
			int giornalista = e.getGiornalista();
			User user = e.getUtente();
			
			this.numGiorni = t-1;
			
			if(this.intervistati.size() == this.numUtenti) { // fine 
				
				break;
			}
			
			
			
			switch(e.getType()) {
			case INTERVISTA:
				
				double prob = Math.random();
				
				if(prob < 0.6) { // ASSEGNA UNO NUOVO
					
					this.queue.add(new Event(EventType.ASSEGNA, t+1, giornalista, user));
					
					
				} else if(prob < 0.8) { // Ferie
					
					this.queue.add(new Event(EventType.FERIE, t+1, giornalista, user));
				
					
				} else { // Non ha terminato di intervistare
					
					this.queue.add(new Event(EventType.INTERVISTA, t+1, giornalista, user));
					
				}
				
				break;
				
			case ASSEGNA:
				
				List<User> simili = this.utentiPiuSimili(user);
				
				if(!simili.isEmpty()) { // Se ci sono simili
					
					int posU = (int)Math.random()*simili.size();
					
					User u = simili.get(posU);
					
					vertici.remove(u);
					
					this.queue.add(new Event(EventType.INTERVISTA, t, giornalista, u));
					
					this.numIntervistatiPerIntervistatore.put(giornalista, this.numIntervistatiPerIntervistatore.get(giornalista) + 1);
					
					this.intervistati.add(u);
					
					
				} else {
					
					int posU = (int)Math.random()*this.vertici.size();
					
					User u = this.vertici.get(posU);
					
					vertici.remove(posU);
					
					this.queue.add(new Event(EventType.INTERVISTA, t, giornalista, u));
					
					this.numIntervistatiPerIntervistatore.put(giornalista, this.numIntervistatiPerIntervistatore.get(giornalista) + 1);
				
					
					this.intervistati.add(u);
				}
				
				
				break;
				
				
			case FERIE:
				
				this.queue.add(new Event(EventType.ASSEGNA, t+1, giornalista, user));
				
				break;
			
			
			}
			
			
			
		}
		
		
	}
	
	
	public List<User> utentiPiuSimili(User u) {
		int max = -1 ;
		
		for(DefaultWeightedEdge e: this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e) > max) {
				max = (int)this.grafo.getEdgeWeight(e);
			}
		}
		
		List<User> result = new ArrayList<>();
		for(DefaultWeightedEdge e: this.grafo.edgesOf(u)) {
			
			if((int)this.grafo.getEdgeWeight(e) == max) {
				User u2 = Graphs.getOppositeVertex(this.grafo, e, u);
				result.add(u2);
			}
		}
		
		return result ;
	}
	
	public void messaggioProva() {
		
		System.out.println("Giorni: " + this.numGiorni);
		
		System.out.println("Utenti intervistati: " + this.intervistati.size());
		
		for(Integer i : this.numIntervistatiPerIntervistatore.keySet()) {
			
			System.out.println("Giornalista " + i + "  =  " + this.numIntervistatiPerIntervistatore.get(i));
			
		}
		
		
	}


	public Map<Integer, Integer> getNumIntervistatiPerIntervistatore() {
		return numIntervistatiPerIntervistatore;
	}


	public int getNumGiorni() {
		return numGiorni;
	}

	

	
}
