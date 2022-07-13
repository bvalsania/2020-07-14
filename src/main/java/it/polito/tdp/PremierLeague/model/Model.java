package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Team,DefaultWeightedEdge> grafo;
	private Map<Integer,Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		this.dao.listAllTeams(idMap);
	}
	
	
	
	public String creg() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getvertici(idMap));
		
		this.dao.getArchi(idMap);
		
		for(Team t1: grafo.vertexSet()) {
			for(Team t2: grafo.vertexSet()) {
				if(!t1.equals(t2) && !this.grafo.edgeSet().contains(this.grafo.getEdge(t1, t2)) && !this.grafo.edgeSet().contains(this.grafo.getEdge(t2, t1))) {
					if(t1.getPoints()>t2.getPoints()) {
						Graphs.addEdge(this.grafo, t1, t2, Math.abs(t1.getPoints()-t2.getPoints()));
					}
					else if(t1.getPoints()<t2.getPoints()) {
						Graphs.addEdge(this.grafo, t2, t1, Math.abs(t1.getPoints()-t2.getPoints()));
					}
				}
			}
		}
			
		return "Il grafo ha vertici e archi : "+this.grafo.vertexSet().size()+ "  " +this.grafo.edgeSet().size();
	}
	
	public List<Team> getTeam(){
		List<Team> res = new ArrayList<>(grafo.vertexSet());
		
		Collections.sort(res, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}
			
		});
		return res;
	}
	
	public String getMigliore(Team t){
			List<Coppia>ordinataMigliori= new ArrayList<>();
		
		Set<DefaultWeightedEdge> verticiIn= this.grafo.incomingEdgesOf(t);
		for(DefaultWeightedEdge e: verticiIn) {
			Coppia ad= new Coppia(this.grafo.getEdgeSource(e), (int)this.grafo.getEdgeWeight(e));
			ordinataMigliori.add(ad);
		}
		
		String string= "SQUADRE MIGLIORI:\n";
		
		Collections.sort(ordinataMigliori, new Comparator<Coppia>() {
			public int compare(Coppia o1, Coppia o2) {
				return o1.getPeso().compareTo(o2.getPeso());
			};
		});
		
		
		for(Coppia a: ordinataMigliori) {
			string+=a.getT().toString()+"("+a.getPeso()+")\n";
		}
		
		List<Coppia> ordinataPeggiori= new ArrayList<>();
		
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(t)) {
			Coppia ad= new Coppia(this.grafo.getEdgeTarget(e), (int)this.grafo.getEdgeWeight(e));
			ordinataPeggiori.add(ad);
		}
		
		string+="\nSQUADRE PEGGIORI:\n";
		
		Collections.sort(ordinataPeggiori, new Comparator<Coppia>() {
			public int compare(Coppia o1, Coppia o2) {
				return o1.getPeso().compareTo(o2.getPeso());
			};
		});
		
		for(Coppia a: ordinataPeggiori) {
			string+=a.getT().toString()+"("+a.getPeso()+")\n";
		}
		
		return string;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	
	private PremierLeagueDAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		
		this.dao.listAllTeams(idMap);
	}
	
	public String creg() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getvertici(idMap));
		
		
		for(Coppia c : this.dao.getArchi(idMap)) {
			
			int pesos1 =0;
			int pesos2 =0;
			
			int peso =0;
			
			if(c.getPeso()==0) {
				pesos1++;
				pesos2++;
			}else if(c.getPeso()==1) {
				pesos1+=3;
			}else {
				pesos2+=3;
			}
			peso = pesos1-pesos2;
			
			
				Graphs.addEdge(this.grafo, c.getS1(), c.getS2(), peso);
			
		}
		
		return "Il grafo creato ha "+this.grafo.vertexSet().size()+" vertici e aerchi "+this.grafo.edgeSet().size();
	}

	public List<Team> gette(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public List<Classifica> getClassific(Team t){
		List<Classifica> result= new ArrayList<>();
		int max = 0;
		
		for(Coppia c : this.dao.getArchi(idMap)) {
		for(DefaultWeightedEdge e: this.grafo.edgesOf(t)) {
			if(grafo.getEdgeWeight(e)>max) {
				max = (int) grafo.getEdgeWeight(e);
				result.add(new Classifica(c.getS2(), max));
			}
		}
		}
		return result;
	}
	*/
}

