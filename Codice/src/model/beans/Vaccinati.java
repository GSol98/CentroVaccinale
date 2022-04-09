package model.beans;

/*
 * Il Bean memorizza tutti gli utenti vaccinati in un ArrayList
 * */
import java.util.ArrayList;

public class Vaccinati {
	private ArrayList<Vaccinato> vaccinati;
	
	public Vaccinati() {
		this.vaccinati = new ArrayList<>();
	}

	public ArrayList<Vaccinato> getVaccinati() {
		return vaccinati;
	}

	public void setVaccinati(ArrayList<Vaccinato> vaccinati) {
		this.vaccinati = vaccinati;
	}
	
	public void addVaccinato(Vaccinato vaccinato) {
		this.vaccinati.add(vaccinato);
	}
}
