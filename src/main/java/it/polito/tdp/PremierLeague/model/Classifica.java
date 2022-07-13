package it.polito.tdp.PremierLeague.model;

public class Classifica {
	private Team s;
	private int p;
	public Classifica(Team s, int p) {
		super();
		this.s = s;
		this.p = p;
	}
	public Team getS() {
		return s;
	}
	public void setS(Team s) {
		this.s = s;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	@Override
	public String toString() {
		return "Classifica [s=" + s + ", p=" + p + "]";
	}
	
}
