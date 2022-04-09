package controller.prenotazione;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Message;
import model.db.Query;
import db.utils.DB;

/**
 * 		Servlet implementation class CancellaPrenotazion
 * Si occupa di annullare una prenotazione quando è richiesto dall'utente
 */
@WebServlet("/CancellaPrenotazione")
public class CancellaPrenotazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CancellaPrenotazione() {
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
		String codicePrenotazione = request.getParameter("codicePrenotazione");
		
		Message message = new Message("Non è stato possibile annullare la prenotazione");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Annullo la prenotazione ponendo l'esito a negativo.
			String sql = "UPDATE prenotazioni SET esito = \"negativo\" where codice_prenotazione = ?;";
			Query query = new Query(sql, codicePrenotazione);
			DB.executeUpdate(query);
			
			if(query.getStatus() == 1) {
				// Effettuo il dispatching a InitDispatcher che restituisce
				// la pagina di prenotazione 
				session.removeAttribute("codicePrenotazione");
				session.setAttribute("accesso", "p1-1");
				address = "/InitDispatcher";
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
