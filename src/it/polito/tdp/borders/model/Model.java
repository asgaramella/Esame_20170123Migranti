package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private UndirectedGraph<Country,DefaultEdge> graph;
	private List<Country> countries;
	private BordersDAO dao;
	private  CountryIdMap map; 
	
	
	private List<CountryAndNum> stanziali;
	
	
	public Model(){
		dao=new BordersDAO();
		map=new CountryIdMap();
		}
	
	
	
	private  UndirectedGraph<Country, DefaultEdge> getGrafo(int anno){
		if(this.graph==null)
			this.creaGrafo(anno);
		return graph;
	}

	public List<CountryAndNum> creaGrafo(int anno) {
		this.graph=new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(graph, this.getCountry());
		
		List<IntegerPair> confini= dao.getCountryPairs(anno);
		for(IntegerPair p:confini){
			graph.addEdge(this.map.get(p.getN1()), this.map.get(p.getN2()));
		}
		
		List<CountryAndNum> lista= new ArrayList<>();
		for(Country c: this.getCountry()){
			int confinanti= Graphs.neighborListOf(graph, c).size();
			if(confinanti!=0)
				lista.add(new CountryAndNum(c,confinanti));
		}
		 Collections.sort(lista);
		 return lista;
		
	}
	
	public List<Country> getCountry(){
		if(countries==null){
			countries=dao.loadAllCountries();
			for(Country c:countries){
				map.put(c);
			}
		}
		return countries;
	}
	
	public int simula(Country partenza){
		Simulator sim= new Simulator(graph);
		
		//RICORDAA DI LANCIARE SIMULAZIONE
		sim.inserisci(partenza);
		sim.run();
		this.stanziali=sim.getPresenti();
		return sim.getPassi();
		
	}



	public List<CountryAndNum> getStanziali() {
		return this.stanziali;
	}
	
	
	
}
