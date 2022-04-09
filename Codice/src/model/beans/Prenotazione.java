package model.beans;

/*
 * Il Bean memorizza tutte le informazioni relative a una prenotazione svolta da un utente
 * */
public class Prenotazione {
	private String codicePrenotazione;
	private String data;
	private String ora;
	private String esito;
	private String vaccino;
	private String tipo;
	private String provincia;
	private String regione;
	
	public Prenotazione(String cod, String data, String ora, String esito, String vacc, String tipo, String prov, String reg) {
		this.codicePrenotazione = cod;
		this.data = data;
		this.ora = ora;
		this.esito = esito;
		this.vaccino = vacc;
		this.tipo = tipo;
		this.provincia = prov;
		this.regione = reg;
	}

	public String getCodicePrenotazione() {
		return codicePrenotazione;
	}

	public void setCodicePrenotazione(String codicePrenotazione) {
		this.codicePrenotazione = codicePrenotazione;
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

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getVaccino() {
		return vaccino;
	}

	public void setVaccino(String vaccino) {
		this.vaccino = vaccino;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

}
