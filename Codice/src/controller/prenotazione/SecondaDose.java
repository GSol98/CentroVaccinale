package controller.prenotazione;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.beans.Message;
import model.beans.Prenotazione;
import model.db.Query;
import db.utils.DB;

/**
 * 		Servlet implementation class SecondaDose
 * Si occupa di prenotare automaticamente una seconda dose ad 
 * almeno 21 gionri di distanza dalla prima
 */
@WebServlet("/SecondaDose")
public class SecondaDose extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SecondaDose() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Il metodo non dovrebbe rispondere a una GET in quando sono necessari dei dati inviati tramite POST.
    	// Nel caso in cui venga effettuata una richiesta di questo tipo, si reindirizza alla doPost, ma dato 
    	// che mancano i dati, in questo metodo viene generata una eccezione che, intercettata, rimanda 
    	// l'utente alla pagina di errore generico
    	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message message = new Message("E' stato riscontrato un errore nella prenotazione");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";

		try {
			String codiceFiscale = (String) request.getAttribute("codiceFiscale");
			String provincia = (String) request.getAttribute("provincia");
			String data = LocalDate.parse((String) request.getAttribute("data")).plusDays(21).toString();

			AggiungiPosti.add(provincia, data);
			
			// Cerco il primo posto disponibile per quella data
			String sql3 = "SELECT id_posto "
					+     "FROM posti_totali "
					+ 	  "WHERE posti_totali.ref_provincia = ? AND "
					+ 			"posti_totali.data >= ? AND "
					+ 			"id_posto NOT IN (SELECT ref_posto "
					+ 							 "FROM prenotazioni "
					+ 							 "WHERE prenotazioni.esito <> \"negativo\");";
			
			Query query3 = new Query(sql3, provincia, data);
			DB.executeQuery(query3);

			if(query3.getStatus() == 1) {
				// Inserisco una nuova prenotazione per quel posto
				String id_posto = query3.getResultSet().get(0).get(0);
				String sql4 = "INSERT INTO prenotazioni (ref_posto) VALUE (?);";
				Query query4 = new Query(sql4, id_posto);
				DB.executeUpdate(query4);

				if(query4.getStatus() == 1) {
					// Ottengo il codice della prenotazione appena inserita
					String sql5 = "SELECT codice_prenotazione "
							+ 	  "FROM prenotazioni "
							+     "WHERE ref_posto = ? AND esito = \"in corso\";";
					Query query5 = new Query(sql5, id_posto);
					DB.executeQuery(query5);

					if(query5.getStatus() == 1) {
						// Collego la nuova prenotazione all'utente corretto
						String nuovo_codicePrenotazione = query5.getResultSet().get(0).get(0);
						String sql7 = "INSERT INTO utenti VALUE (?, ?);";
						Query query7 = new Query(sql7, codiceFiscale, nuovo_codicePrenotazione);
						DB.executeUpdate(query7);
						
						if(query7.getStatus() == 1) {
							// Memorizzo la nuova prenotazione in un Bean che consente di visualizzare in
							// una jsp il codice della nuova prenotazione
							Prenotazione prenotazione = new Prenotazione(nuovo_codicePrenotazione, "", "", "", "", "", "", "");
							request.setAttribute("prenotazione", prenotazione);
							address = "/WEB-INF/views/prenotazione/esito-e-dose.jsp";
						}
					}
				}				
			}
		} 
		catch(Exception exc) {
			message.setText("Ops... Si è verificato un errore");
			address = "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
}
