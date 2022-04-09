package model.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import db.utils.DB;

/*
 * La classe consente di creare degli oggetti per imamgazinare tutti i dati di una determiata query.
 * Per fare una query al database è necessario creare un oggetto di questa classe e passare in input
 * la stringa sql e tutti i parametri. Infine l'oggetto viene passato in input al DB.execute...()
 * Dopo l'esecuzione (attraverso la classe db.utils.DB) vengono memorizzate tutte le informazioni riguardanti
 * l'esito della query, come il risultato (intero nel caso di executeUpdate, ResultSet nel caso di executeQuery()), 
 * lo stato (corretto, errore), e, nel caso si verifichi, l'SQLException.
 * */

public class Query {
	private String query, exc;
	private String[] arguments;
	private List<List<String>> resultSet;
	private int resultInt = -1;
	private int status;
	
	// Quando viene creato un oggetto Query è necessario mandare in input una stringa contentente la query sql
	// seguita da 0 o più stringhe contenenti i parametri della query. Questi vengono gestiti come un varargs
	// e memorizzati nell'array arguments, per poi essere inseriti nel PreparedStasement in db.utils.DB
	public Query(String query, String... strings) {
		this.query = query;
		this.arguments = strings;
		this.status = -1;
		resultSet = new ArrayList<>();
	}

	public String getQuery() {
		return query;
	}
	
	public String[] getArguments() {
		return this.arguments;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getExc() {
		return exc;
	}

	public void setExc(String exc) {
		this.exc = exc;
		setStatus(DB.ERROR);
	}

	public List<List<String>> getResultSet() {
		return resultSet;
	}
	
	public void setResultSet(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				List<String> row = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					row.add(rs.getString(i));
				}
				resultSet.add(row);
			}

			setStatus(DB.RESULT);
		} catch (Exception exception) {
			exc = exception.getMessage();
			setStatus(DB.ERROR);
		}
	}

	public int getResultInt() {
		return resultInt;
	}
	
	public void setResultInt(int res) {
		this.resultInt = res;
		setStatus(DB.RESULT);
	}
}
