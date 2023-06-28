package it.polito.tdp.yelp.model;

import java.util.Map;

public class SimulatorResult {

	private Map<Integer, Integer> giornalistaNumIntervistati;
	private int numGiorni;
	
	
	public SimulatorResult(Map<Integer, Integer> giornalistaNumIntervistati, int numGiorni) {
		super();
		this.giornalistaNumIntervistati = giornalistaNumIntervistati;
		this.numGiorni = numGiorni;
	}


	public Map<Integer, Integer> getGiornalistaNumIntervistati() {
		return giornalistaNumIntervistati;
	}


	public void setGiornalistaNumIntervistati(Map<Integer, Integer> giornalistaNumIntervistati) {
		this.giornalistaNumIntervistati = giornalistaNumIntervistati;
	}


	public int getNumGiorni() {
		return numGiorni;
	}


	public void setNumGiorni(int numGiorni) {
		this.numGiorni = numGiorni;
	}
	
	
	
	
}
