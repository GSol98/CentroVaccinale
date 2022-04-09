package model.beans;

/*
 * Il Bean contiene le informazioni relative a un utente vaccinato in modo che possano 
 * essere visualizzate nel report completo
 * */
public class Vaccinato {
	private String codiceFiscale;
	private String codicePrenotazione;
	private int id_provincia;
	private String provincia;
	private int id_regione;
	private String regione;
	private String data;
	private String ora;
	private String vaccino;
	private String tipo;
	
	public Vaccinato(String cf, String cp, int id_p, String prov, int id_r, String reg, String data, String ora, String vac, String tipo) {
		this.codiceFiscale = cf;
		this.codicePrenotazione = cp;
		this.id_provincia = id_p;
		this.provincia = prov;
		this.id_regione = id_r;
		this.regione = reg;
		this.data = data;
		this.ora = ora;
		this.vaccino = vac;
		this.tipo = tipo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodicePrenotazione() {
		return codicePrenotazione;
	}

	public void setCodicePrenotazione(String codicePrenotazione) {
		this.codicePrenotazione = codicePrenotazione;
	}

	public int getId_provincia() {
		return id_provincia;
	}

	public void setId_provincia(int id_provincia) {
		this.id_provincia = id_provincia;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getId_regione() {
		return id_regione;
	}

	public void setId_regione(int id_regione) {
		this.id_regione = id_regione;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
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
	
}
