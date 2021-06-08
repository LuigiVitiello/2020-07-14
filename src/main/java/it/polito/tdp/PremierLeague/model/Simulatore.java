package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

public class Simulatore {

	/* N reporter e soglia X
	 * inizio = ogni squadra N reporter ogni reporter segue la squadra a cui è assegnato
	 * 
	 * Simula in ordine cronologico tutte le partite di Matches
	 *  S VINCENTE:
	 *     50% riassegna un reporter a squadra con più punti se c'è
	 *  S PERDENTE: 
	 *     20% un o +(n casuale tra 1 e N) reporter viene bocciato  
	 * PAREGGIO: 
	 *     rimangono uguali
	 *     
	 *stampare:
	 *     n di reporter medi a partita
	 *     n di partite in cui il n totale di reporter era < di x    
	 */
	
	private PriorityQueue<Evento> eventi;
	
	private int N;
	private int X;
	private List<Match> partite;
	private Model model;
	private Map<Team,Integer> reportxTeam;
	private Map<Integer,Team> idMap;
	private int sommaReporter;
	private int inferiori;
	
	public void init(int Nn, int Xx, List<Match> partite, Map<Integer,Team> idMap) {
		this.eventi = new PriorityQueue<>();
		this.N = Nn;
		this.X = Xx;
		this.partite = partite;
		this.idMap = idMap;
		sommaReporter=0;
		inferiori =0;
		this.model = new Model();
		this.model.creaGrafo();
		this.reportxTeam = new HashMap<>();
		for(Team t : this.model.getVertici()) {
			this.reportxTeam.put(t, N);
		}
		
		caricaEventi();
		simulaEventi();
		
	}

	private void caricaEventi() {
		
		Team vincente=null;
		Team perdente = null;
		for(Match m: partite) {
			//System.out.println("val casa: "+this.reportxTeam.get(idMap.get(m.getTeamHomeID())));
			if(m.resultOfTeamHome==1) {
				vincente = idMap.get(m.getTeamHomeID());
				perdente = idMap.get(m.getTeamAwayID());
				double prob = Math.random();
				if(prob<=0.5) {
					//System.out.println("Evento prom creato");
					Evento e = new Evento(EventType.PROMOZIONE,m.date,vincente,m);
					eventi.add(e);
				}
				
				double prob2 = Math.random();
				if(prob2<0.2) {
					//System.out.println("Evento bocc creato");
					Evento e = new Evento (EventType.BOCCIATURA, m.date, perdente,m);
					eventi.add(e);
				}
			} else if(m.resultOfTeamHome==-1) {
				vincente = idMap.get(m.getTeamAwayID());
				perdente = idMap.get(m.getTeamHomeID());
				double prob = Math.random();
				if(prob<=0.5) {
					Evento e = new Evento(EventType.PROMOZIONE,m.date,vincente,m);
					eventi.add(e);
				}
				double prob2 = Math.random();
				if(prob2<0.2) {
					Evento e = new Evento (EventType.BOCCIATURA, m.date, perdente,m);
					eventi.add(e);
				}
			}
		}
		
	}
	private void simulaEventi() {
		while(!this.eventi.isEmpty()) {
			Evento e = this.eventi.poll();
			int sommaPartita=0;
			sommaPartita =  (this.reportxTeam.get(idMap.get(e.getPartita().getTeamHomeID()))+ this.reportxTeam.get(idMap.get(e.getPartita().getTeamAwayID())));
			processaEvento(e);
			System.out.println("La somma di questa partita è: "+sommaPartita+" a fronte di una soglia "+this.X);
			sommaReporter += sommaPartita;
			if(sommaPartita<this.X) {
					inferiori++;
				}
		}
	}

	private void processaEvento(Evento e) {
		
		switch(e.getTipo()) {
		
		case PROMOZIONE:
			//System.out.println("Evento processato");
			this.model.doClassifica(e.getSquadra());
			if(this.model.getMigliori()!=null) {
				//System.out.println("Ci sono migliori");
				if(this.reportxTeam.get(e.getSquadra())>0) {
					this.reportxTeam.put(e.getSquadra(), this.reportxTeam.get(e.getSquadra())-1);
					int prob = (int)(Math.random()*this.model.getMigliori().size());
					int i=0;
					for(Adiacenza a: this.model.getMigliori()) {
						if(i==prob) {
							//System.out.println("Scelta squad casuale");
							this.reportxTeam.put(a.getT1(), this.reportxTeam.get(a.getT1())+1);
							System.out.println("Ora "+a.getT1().toString()+" è salita a  "+this.reportxTeam.get(a.getT1()));
						}
						i++;
					}
					
				} else { System.out.println("La squad che aveva vinto non aveva rporter");}
			}
			break;
			
		case BOCCIATURA:
			if(this.reportxTeam.get(e.getSquadra())>0){
			    int prob = (int) ((Math.random()*this.reportxTeam.get(e.getSquadra()))+1);
				System.out.println("I bocciati sono "+prob);
			    this.reportxTeam.put(e.getSquadra(), this.reportxTeam.get(e.getSquadra())-prob);
				System.out.println("Ora "+e.getSquadra().toString()+" è scesa a "+this.reportxTeam.get(e.getSquadra()));
				this.model.doClassifica(e.getSquadra());
				if(!this.model.getPeggiori().isEmpty()) {
						int prob2 = (int)(Math.random()*this.model.getPeggiori().size());
						System.out.println("Indice squadra scelta: "+prob2);
						int i=0;
						for(Adiacenza a: this.model.getPeggiori()) {
							if(i==prob2) {
								this.reportxTeam.put(a.getT2(), this.reportxTeam.get(a.getT2())+prob);
								System.out.println("Mentre "+a.getT2().toString()+" è salita a "+this.reportxTeam.get(a.getT2()));
							}
							i++;
						}
					}
			}else { System.out.println("La squad che ha perso non aveva rporter");}
			break;
		}
		
    }
	
   public double getMedia() {
	   return (this.sommaReporter/partite.size());
   }
   
   public int getInferiori() {
	   return inferiori;
   }
}
