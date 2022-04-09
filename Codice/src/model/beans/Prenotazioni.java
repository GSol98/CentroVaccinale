package model.beans;

/*
 * Il Bean memorizza tutte le prenotazioni effettuate da un utente in un ArrayList
 * */
import java.util.ArrayList;

public class Prenotazioni {
	private ArrayList<Prenotazione> prenotazioni;
	
	public Prenotazioni() {
		this.prenotazioni = new ArrayList<>();
	}

	public ArrayList<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	public void addPrenotazione(Prenotazione p) {
		this.prenotazioni.add(p);
	}
}
