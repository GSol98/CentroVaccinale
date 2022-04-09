package model.beans;

/*
 * Il Bean memorizza tutte le informazioni relative a un posto di vaccinazione 
 * su cui l'utente può effettuare una prenotazione
 * */

public class Posto {
	private String regione, provincia;
	private int id_posto, id_regione, id_provincia;
	private String data, ora;
	
	public Posto(int id_posto, int id_provincia, String provincia, int id_regione, String regione, String data, String ora) {
		this.id_posto = id_posto;
		this.id_provincia = id_provincia;
		this.id_regione = id_regione;
		this.provincia = provincia;
		this.regione = regione;
		this.data = data;
		this.ora = ora;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getId_posto() {
		return id_posto;
	}

	public void setId_posto(int id_posto) {
		this.id_posto = id_posto;
	}

	public int getId_regione() {
		return id_regione;
	}

	public void setId_regione(int id_regione) {
		this.id_regione = id_regione;
	}

	public int getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}
 }
