package it.polito.tdp.PremierLeague.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		PROMOZIONE,
		BOCCIATURA	
	}
	
	private EventType tipo;
	private LocalDateTime date;
	private Match partita;
	/**
	 * @return the tipo
	 */
	public EventType getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the squadra
	 */
	public Team getSquadra() {
		return squadra;
	}

	/**
	 * @param squadra the squadra to set
	 */
	public void setSquadra(Team squadra) {
		this.squadra = squadra;
	}

	private Team squadra;
	
	public Evento(EventType tipo, LocalDateTime date, Team squadra, Match m) {
		super();
		this.tipo = tipo;
		this.date = date;
		this.squadra = squadra;
		this.partita = m;
	}

	/**
	 * @return the partita
	 */
	public Match getPartita() {
		return partita;
	}

	/**
	 * @param partita the partita to set
	 */
	public void setPartita(Match partita) {
		this.partita = partita;
	}

	@Override
	public int compareTo(Evento altro) {
		return this.date.compareTo(altro.date);
	}
	
}
