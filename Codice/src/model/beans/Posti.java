package model.beans;

/*
 * Il Bean memorizza tutti i posti di vaccinazione all'interno di un ArrayList
 * */

import java.util.ArrayList;

public class Posti {
	private ArrayList<Posto> posti;
	private ArrayList<Posto> postiSemplici;
	
	public Posti() {
		posti = new ArrayList<>();
		postiSemplici = new ArrayList<>();
	}

	public ArrayList<Posto> getPosti() {
		return posti;
	}

	public void setPosti(ArrayList<Posto> posti) {
		this.posti = posti;
	}
	
	public void addPosti(Posto posto) {
		this.posti.add(posto);
	}

	public ArrayList<Posto> getPostiSemplici() {
		return postiSemplici;
	}

	public void setPostiSemplici(ArrayList<Posto> postiSemplici) {
		this.postiSemplici = postiSemplici;
	}
	
	public void addPostiSemplici(Posto posto) {
		this.postiSemplici.add(posto);
	}
}
