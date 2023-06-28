package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	
	private YelpDao dao;
	
	//private List<Integer> lista;
	
	private Graph<User, DefaultWeightedEdge> grafo;
	
	private List<User> vertici;
	private Map<String, User> userIdMap;
	
	private int max;
	

	public Model() {
		
		this.dao = new YelpDao();
		
		//this.lista = new ArrayList<>();
		
	}
	
	
	public void creaGrafo(int n, int anno) {

		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
		
		this.vertici = new ArrayList<>(this.dao.getAllVertici(n));
		
		this.userIdMap = new HashMap<>();
		for(User u : this.vertici) {
			
			this.userIdMap.put(u.getUserId(), u);
			
		}
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		
		
		// Archi
		for(Arco arco : this.dao.getArchi(anno, userIdMap)) {
			
			Graphs.addEdgeWithVertices(this.grafo, arco.getUser1(), arco.getUser2(), arco.getPeso());
			
			
		}
		
		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}


	public List<User> getVertici() {
		
		List<User> result = new ArrayList<>(this.vertici);
		
		Collections.sort(result);
		
		return result;
	}
	
	public List<User> utentiPiuSimili(User u) {
		max = -1 ;
		
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
	
	
	public int getMax() {
		
		return this.max;
		
	}
	
	
	public SimulatorResult Simula(int x1, int x2) {
		
		Simulator sim = new Simulator(x1, x2, this.grafo);
		
		sim.init();
		sim.run();
		
		sim.messaggioProva();
		
		return new SimulatorResult(sim.getNumIntervistatiPerIntervistatore(), sim.getNumGiorni());
		
	}
	
}
