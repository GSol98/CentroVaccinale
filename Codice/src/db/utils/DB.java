package db.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;


import model.db.Query;

public class DB {
	public static final int RESULT = 1;
	public static final int ERROR = 2;
	
	public static void executeUpdate(Query query) {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/solarodb");
			try (
					Connection connection = ds.getConnection(); 
					PreparedStatement statement = connection.prepareStatement(query.getQuery())
			) {
				// Itero l'array contentente tutti i parametri della query, contenuti nell'ogetto
				// query, e li inserisco nel PreparedStatement
				String[] arguments = query.getArguments();
				for (int i = 0; i < arguments.length; i++) 
					statement.setString(i+1, arguments[i]);
				
				int result = statement.executeUpdate();
				query.setResultInt(result);
			}
		} catch (Exception exc) {
			query.setExc(exc.getMessage());
		}
	}
	
	public static void executeQuery(Query query) {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/solarodb");
			try (
					Connection connection = ds.getConnection(); 
					PreparedStatement statement = connection.prepareStatement(query.getQuery())
			) {
				// Itero l'array contentente tutti i parametri della query, contenuti nell'ogetto
				// query, e li inserisco nel PreparedStatement
				String[] arguments = query.getArguments();
				int argint;
				for (int i = 0; i < arguments.length; i++) {
					// I parametri sono memorizzati come stringhe, ma alcuni vengono richiesti 
					// come interi dal databse. Provo quindi a trasformarli in interi: se non si generano 
					// eccezioni viene inserito il parametro come intero, viceversa viene inserito
					// come stringa
					try {
						argint = Integer.parseInt(arguments[i]);
						statement.setInt(i+1, argint);
					} catch(Exception exc) {
						statement.setString(i+1, arguments[i]);
					}
				}
				
				ResultSet result = statement.executeQuery();
				query.setResultSet(result);
			}
		} catch (Exception exc) {
			query.setExc(exc.getMessage());
		}
	}
}
