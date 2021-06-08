package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable<Adiacenza>{

	private Team t1;
	private Team t2;
	private int differenza;
	public Adiacenza(Team t1, Team t2, int differenza) {
		super();
		this.t1 = t1;
		this.t2 = t2;
		this.differenza = differenza;
	}
	/**
	 * @return the t1
	 */
	public Team getT1() {
		return t1;
	}
	/**
	 * @param t1 the t1 to set
	 */
	public void setT1(Team t1) {
		this.t1 = t1;
	}
	/**
	 * @return the t2
	 */
	public Team getT2() {
		return t2;
	}
	/**
	 * @param t2 the t2 to set
	 */
	public void setT2(Team t2) {
		this.t2 = t2;
	}
	/**
	 * @return the differenza
	 */
	public int getDifferenza() {
		return differenza;
	}
	/**
	 * @param differenza the differenza to set
	 */
	public void setDifferenza(int differenza) {
		this.differenza = differenza;
	}
	@Override
	public String toString() {
		return "Adiacenza [t1=" + t1 + ", t2=" + t2 + ", differenza=" + differenza + "]";
	}
	@Override
	public int compareTo(Adiacenza altra) {
		
		return this.differenza-altra.differenza;
	}
	
	
}
