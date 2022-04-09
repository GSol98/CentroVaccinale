package controller.prenotazione;

import java.time.LocalTime;

import db.utils.DB;
import model.db.Query;

public class AggiungiPosti {
	public static void add(String provincia, String data) {
		// Controllo se, per la data in input sono disponibili dei posti. In caso negativo, vengono aggiunti  
		String sql1 = "SELECT COUNT(*) FROM posti_totali WHERE ref_provincia = ? AND data = ?";
		Query query1 = new Query(sql1, provincia, data);
		DB.executeQuery(query1);

		if(query1.getStatus() == 1) {
			if(query1.getResultSet().get(0).get(0).compareTo("0") == 0) {
				// Non sono stati ancora creati posti di prenotazione per quella data, quindi
				// il sistema li crea automaticamente (aggiungo posti liberi)
				String sql2 = "INSERT INTO posti_totali (ref_provincia, data, ora) VALUES ";
				LocalTime ora = LocalTime.parse("09:00:00");
				for(int i = 0; i < 32; i++) {
					if(ora.isBefore(LocalTime.parse("13:00:00")) || ora.isAfter(LocalTime.parse("13:45:00"))) {
						sql2 += "("+provincia+", \""+data+"\", \""+ora+"\")";
						sql2 += i == 31 ? "; " : ", ";
					}
					ora = ora.plusMinutes(15);
				}
				Query query2 = new Query(sql2);
				DB.executeUpdate(query2);
			}
		}
	}
}
