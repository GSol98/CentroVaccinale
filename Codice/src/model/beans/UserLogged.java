package model.beans;

/*
 * Il Bean contiene nome e cognome di un medico o dipendente ASP che si sono loggati.
 * Viene usato per mostrare questi dati nelle rispettive pagine principali
 * */
public class UserLogged {
	private String nome, cognome;
	
	public UserLogged(String n, String c) {
		this.nome = n;
		this.cognome = c;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
}
