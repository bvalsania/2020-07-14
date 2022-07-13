package it.polito.tdp.PremierLeague.model;

public class Coppia {
	
	private Team t;
	private Integer peso;
	public Coppia(Team t, Integer peso) {
		super();
		this.t = t;
		this.peso = peso;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  t + ", peso=" + peso;
	}
	
}
