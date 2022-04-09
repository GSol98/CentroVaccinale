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
 * 		Servlet implementation class Login
 * Si occupa di gestire il login di medici o dipendenti asp
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
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
		String address = "/InitDispatcher";
		Query query = null;
		String sql;
		
		try {
			int num = Integer.parseInt(request.getParameter("num"));
			String codiceFiscale = request.getParameter("codFisc").toUpperCase();

			if(num == 2) {
				// Se l'accesso è effettuato da un medico
				String matricola = request.getParameter("matricola");
				String password = request.getParameter("password");
				sql = "SELECT COUNT(*) FROM medici WHERE codice_fiscale = ? AND matricola = ? AND password = SHA2(?, 512);";
				query = new Query(sql, codiceFiscale, matricola, password);
				DB.executeQuery(query);
			}
			else if(num == 3) {
				// Se l'accesso è effettuato da un dipendente ASP
				String matricola = request.getParameter("matricola");
				String password = request.getParameter("password");
				sql = "SELECT COUNT(*) FROM asp WHERE codice_fiscale = ? AND matricola = ? AND password = SHA2(?, 512);";
				query = new Query(sql, codiceFiscale, matricola, password);
				DB.executeQuery(query);
			}
			else 
				throw new Exception();
			
			if(query != null && query.getStatus() == 1) {
				if(query.getResultSet().get(0).get(0).compareTo("1") != 0) {
					// La variabile "credentials" impostata a "wrong" consente a 
					// InitDispatcher di restituire la pagina di credenziali errate 
					session.setAttribute("credentials", "wrong");
				}
				session.setAttribute("codiceFiscale", codiceFiscale);
				session.setAttribute("tipo", String.valueOf(num));
				session.removeAttribute("login");
			} 
			else
				throw new Exception();
			
		} catch (Exception exc) {
			Message message = new Message("Ops... Si è verificato un errore");
			request.setAttribute("message", message);
			address = "/WEB-INF/views/message.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
}
