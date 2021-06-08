package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedWeightedGraph<Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao ;
	private Map<Integer,Team> idMap;
	private Map<Team,Integer> mappaPunti;
	private List<Adiacenza> adiacenze;
	private List<Adiacenza> peggiori;
	private List<Adiacenza> migliori;
	
	private List<Match> matchOrdinati;
	private double media;
	private int inf;
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
	}
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.adiacenze = new ArrayList<>();
		this.matchOrdinati = dao.listAllMatches();
		Collections.sort(this.matchOrdinati);
		// vertici
		this.dao.listAllTeams(idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		// archi
		this.mappaPunti = new HashMap<>();
		// CALCOLO PUNTEGGIO SQUADRE
		for(Team t: this.grafo.vertexSet()) {
			int puntVittorie = this.dao.punteggioVittorie(t);
			int pareggi = this.dao.punteggioPareggi(t);
			this.mappaPunti.put(t,puntVittorie+pareggi );
		}
		
		for(Team t: this.grafo.vertexSet()) {
			for(Team t2 : this.grafo.vertexSet()) {
				int pT1 = this.mappaPunti.get(t);
				int pT2 = this.mappaPunti.get(t2);
				if(pT1>pT2) {
					if(!this.grafo.containsEdge(t, t2)) {
					     Graphs.addEdge(this.grafo, t, t2, (double)(pT1-pT2));
					     Adiacenza a = new Adiacenza(t,t2,pT1-pT2);
					     this.adiacenze.add(a);
					}
				}
				if(pT1<pT2) {
					if(!this.grafo.containsEdge(t2, t)) {
					     Graphs.addEdge(this.grafo, t2, t, (double)(pT2-pT1));
					     Adiacenza a = new Adiacenza(t2,t,pT2-pT1);
					     this.adiacenze.add(a);
					}
				}
			}
		}
		
		media =0.0;
		inf =0;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
	return this.grafo.edgeSet().size();
	}

	public Set<Team> getVertici() {
		return this.grafo.vertexSet();
	}
	
	public void doClassifica(Team t) {
		peggiori = new ArrayList<>();
		migliori = new ArrayList<>();
		for(Adiacenza aa: this.adiacenze) {
			if(aa.getT1().equals(t)) {
				peggiori.add(aa);
			}
			
			if(aa.getT2().equals(t)) {
				migliori.add(aa);
			}
		}
		
		Collections.sort(migliori);
		Collections.sort(peggiori);
		
	}
	
	public List<Adiacenza> getMigliori(){
		return this.migliori;
	}
	public List<Adiacenza> getPeggiori(){
		return this.peggiori;
	}
	
	public void simula(int N, int X) {
		Simulatore s = new Simulatore();
		s.init(N,X,this.matchOrdinati,this.idMap);
		inf = s.getInferiori();
		media = s.getMedia();
	}
	
	public int getInf() {
		return inf;
	}
	
	public double getMedia(){
		return media;
	}
}
