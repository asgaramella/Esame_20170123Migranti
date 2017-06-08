package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {
	
	//parametri di simulazione ossia le costanti
	private int INIZIALI =1000;
	private double PERC_STANZIALI=0.5;
	
	//modello del mondo
	private Map<Country,Integer> stanziali;
	private UndirectedGraph<Country,DefaultEdge> graph;
	
	//misure in uscita, essendo una sola puoi farla tornre diretto dal metodo simula 
	private int passi;
	
	//coda degli eventi
	private PriorityQueue<Event> queue;
	
	
	public Simulator(UndirectedGraph<Country,DefaultEdge> graph){
		this.graph=graph;
		this.stanziali=new HashMap<Country,Integer>();
		for(Country c: graph.vertexSet()){
			this.stanziali.put(c, 0);
		}
		
		this.queue=new PriorityQueue<Event>();
		
	}
	
	public void inserisci(Country c){
		Event e= new Event(INIZIALI,c,1);
		queue.add(e);
		
	}
	
	public void run(){
		while(!queue.isEmpty()){
			Event e =queue.poll();
			//elaborando la coda in senso crescento l'ultimo che estraggo è il T max
			this.passi=e.getT();
			
			
			
			//stanziali
			int stanz=(int)(e.getNum()*this.PERC_STANZIALI);
			
			//nomadi
			int confinanti=Graphs.neighborListOf(graph, e.getCountry()).size();
			
			//ottengo i gruppetti nel caso in cui nr di migranti< nr stato confinanti no problema ottengo gruppi pari a 0
			int nomadi=(e.getNum()-stanz)/confinanti;
			
			
			//aggiungiamo i resti della divisione precedente
			stanz=e.getNum()-nomadi*confinanti;
			
			//aggiorno stato del mondo
			stanziali.put(e.getCountry(),stanziali.get(e.getCountry())+stanz );
			
			//condizione di terminazione, non creo più eventi così  coda si svuota ad un certo punto
			if(nomadi>0){
			//aggiungo eventi
			for(Country c: Graphs.neighborListOf(graph,e.getCountry() )){
				Event eNew = new Event(nomadi, c, e.getT()+1);
				queue.add(eNew);
				}
			}
		}
	}
	
	
	
	public int getPassi(){
		return this.passi;
	}
	
	public List<CountryAndNum> getPresenti(){
		
		List<CountryAndNum> list=new ArrayList<>();
		for(Country c: stanziali.keySet()){
			if(stanziali.get(c)>0)
				list.add(new CountryAndNum(c,stanziali.get(c)));
		}
		
		Collections.sort(list);
		return list;
	}


}



