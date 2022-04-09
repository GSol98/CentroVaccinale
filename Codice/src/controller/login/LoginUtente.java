package controller.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.utils.DB;
import model.beans.Message;
import model.db.Query;

/**
 * 		Servlet implementation class LoginUtente
 * La Servlet gestisce la fase di Login dell'utente.
 */
@WebServlet("/LoginUtente")
public class LoginUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginUtente() {
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
		String codiceFiscale = request.getParameter("codFisc");
		String sql;
		String part = null;
		String address = "/InitDispatcher";
		Query query = null;
		
		HttpSession session = request.getSession();
		try {
			// La fase di login di un utente semplice si divide in due parti.
			// La prima è quella in cui viene chiesto il codice fiscale: se a lui
			// NON sono associate prenotazioni in corso l'accesso si considera effettuato
			// e viene restituita la pagina di prenotazione; viceversa, se a lui SONO 
			// associate prenotazioni in corso comincia la seconda fase di login.
			// La seconda fase è quella in cui viene richiesto l'inserimento del codice di 
			// prenotazione associato al suo codice fiscale: se è corretto viene restituita 
			// la pagina di gestione delle prenotazioni; viceversa viene restituita una pagina di errore
			
			if(codiceFiscale == null) {
				// Seconda fase: l'utente ha inserito il codice prenotazione (quindi la variabile 
				// codiceFiscale nella richiesta non esiste). 
				codiceFiscale = (String) session.getAttribute("codiceFiscale");
				String codicePrenotazione = request.getParameter("codPren");
				sql = "SELECT COUNT(*) "
					+ "FROM utenti, prenotazioni "
					+ "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
					+ 		"codice_fiscale = ? AND "
					+ 	    "ref_prenotazione = ? AND "
					+ 		"prenotazioni.esito = \"in corso\";";
				query = new Query(sql, codiceFiscale, codicePrenotazione);
				DB.executeQuery(query);
				
				if(query.getStatus() == 1) {
					if(query.getResultSet().get(0).get(0).compareTo("1") == 0) 
						session.setAttribute("codicePrenotazione", codicePrenotazione);

					// Il valore di part è un placeholder che consente di ricordare a che punto
					// del login si trova l'utente in modo da evitare errori se questo ricarica la pagina
					part = "p2";
				}
				else
					throw new Exception();
			}
			else {
				// Prima fase: l'utente ha inserito il codice fiscale e viene inviato nella richiesta.
				codiceFiscale = codiceFiscale.toUpperCase();
				sql = "SELECT COUNT(*), prenotazioni.tipo "
					+ "FROM utenti, prenotazioni "
					+ "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
					+ "	     prenotazioni.esito = \"in corso\" AND "
					+ "	 	 utenti.codice_fiscale = ?"
					+ "UNION "
					+ "SELECT COUNT(*), prenotazioni.tipo "
					+ "FROM utenti, prenotazioni "
					+ "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
					+ "	 	 prenotazioni.esito = \"positivo\" AND "
					+ "	 	 utenti.codice_fiscale = ?;";
				
				query = new Query(sql, codiceFiscale, codiceFiscale);
				DB.executeQuery(query);
				
				if(query.getStatus() == 1) {	
					if(Integer.parseInt(query.getResultSet().get(0).get(0)) == 0) {
						try {
							if(Integer.parseInt(query.getResultSet().get(1).get(0)) == 2 || query.getResultSet().get(1).get(1).compareTo("unica dose") == 0)
									part = "p1-3";
							else
								throw new Exception();
						} catch(Exception exc) {
							part = "p1-1";
						}
					}
					else 
						part = "p1-2";
					
					session.setAttribute("codiceFiscale", codiceFiscale);
				}
				else 
					throw new Exception();
				
			}
			session.setAttribute("accesso", part);
			session.setAttribute("tipo", "1");
			session.removeAttribute("login");
		}
		catch(Exception exc) {
			Message message = new Message("Ops... Si è verificato un errore");
			request.setAttribute("message", message);
			address = "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
