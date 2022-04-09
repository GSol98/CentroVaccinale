package controller.prenotazione;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Message;
import model.beans.Posti;
import model.beans.Posto;
import model.beans.Prenotazione;
import model.db.Query;
import db.utils.DB;

/**
 * 		Servlet implementation class Prenota
 * Si occupa di effettuare una prenotazione per un utente.
 * La fase di prenotazione è successiva a quella di visualizzazione dei posti disponibili.
 * In questa fase i posti disponibili per una provincia e data vengono memorizzati in sessione.
 * In questo modo è possibile indicizzarli attraverso l'ora di vaccinazione.
 * Una volta effettuata una prenotazione la servler effettua un dispatching vero InitDispatcher
 * che si occupa di visualizzare tutte le prenotazioni effettuate dall'utente
 */
@WebServlet("/Prenota")
public class Prenota extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Prenota() {
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
		HttpSession session = request.getSession();
		String ora = request.getParameter("ora");
		
		Message message = new Message("E' stato riscontrato un errore nella prenotazione");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Recupero i posti disponibili per una provincia e data dalla sessione
			// (inseriti dalla servlet ElencoPosti) e successivamente recupero
			// l'id del posto che l'utente ha prenotato
			Posti posti = (Posti) session.getAttribute("listaPosti");
			String id_posto = null;
			for(Posto p : posti.getPosti()) {
				if(LocalTime.parse(p.getOra()).truncatedTo(ChronoUnit.HOURS).toString().compareTo(ora) == 0) {
					id_posto = String.valueOf(p.getId_posto());
					break;
				}
			}
			
			// Inserisco una nuova prenotazione per quel posto
			String sql1 = "INSERT INTO prenotazioni (ref_posto) VALUE (?);";
			Query query1 = new Query(sql1, id_posto);
			DB.executeUpdate(query1);
			
			if(query1.getStatus() == 1) {
				// Seleziono il codice della nuova prenotazione. I posti potrebbero già essere associati a prenotazioni
				// con esito negativo (prenotazioni annullate) quindi è necessario quel controllo
				String sql2 = "SELECT codice_prenotazione FROM prenotazioni WHERE ref_posto = ? AND esito <> \"negativo\";";
				Query query2 = new Query(sql2, id_posto);
				DB.executeQuery(query2);

				if(query2.getStatus() == 1) {
					// Collego la nuova prenotazione all'utente che la ha effettuata
					String codiceFiscale = (String) session.getAttribute("codiceFiscale");
					String codicePrenotazione = query2.getResultSet().get(0).get(0);
					String sql3 = "INSERT INTO utenti VALUE (?, ?);";
					Query query3 = new Query(sql3, codiceFiscale, codicePrenotazione);
					DB.executeUpdate(query3);
	
					if(query3.getStatus() == 1) {
						// Inserisco in un Bean la nuova prenotazione in modo da visualizzare in una jsp
						// il codice della nuova prenotazione
						Prenotazione prenotazione = new Prenotazione(codicePrenotazione, "", "", "", "", "", "", "");
						request.setAttribute("prenotazione", prenotazione);
						session.setAttribute("codicePrenotazione", codicePrenotazione);
						address = "/WEB-INF/views/prenotazione/success.jsp";
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
