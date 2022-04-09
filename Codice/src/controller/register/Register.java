package controller.register;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.utils.DB;
import model.db.Query;
import model.beans.Dispatcher;
import model.beans.Message;

/**
 * 		Servlet implementation class Register
 * Si occupa di effettuare una registrazione di un medico o dipendente ASP.
 * Un utente semplice (colui che deve soltanto effettuare una prenotazione)
 * non ha bisogno di registrarsi.
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register() {
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
		
		Message message = new Message("Utente già registrato");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Recupero i dati forniti dall'utente
			int num = Integer.parseInt(request.getParameter("num"));
			String codFis = request.getParameter("codFisc").toUpperCase();
			String nome = request.getParameter("firstName").toUpperCase();
			String cognome = request.getParameter("lastName").toUpperCase();
			String provincia = request.getParameter("id_provincia");
			String password = request.getParameter("password");
	
			Query query = null;
			
			if(num == 2) {
				// Se l'utente è un medico
				String matricola = request.getParameter("matricola");
				String sql = "INSERT INTO medici VALUE (?, ?, ?, ?, ?, SHA2(?, 512))";
				query = new Query(sql, codFis, matricola, nome, cognome, provincia, password);
				DB.executeUpdate(query);
			}
			else if(num == 3) {
				// Se l'utente è un dipendente ASP
				String matricola = request.getParameter("matricola");
				String sql = "INSERT INTO asp VALUE (?, ?, ?, ?, ?, SHA2(?, 512))";
				query = new Query(sql, codFis, matricola, nome, cognome, provincia, password);
				DB.executeUpdate(query);
			}
			else 
				throw new Exception();

			if(query != null && query.getStatus() == 1)
				message.setText("Registrazione Effettuata");
			else if(!query.getExc().contains("Duplicate"))
				throw new Exception();

			Dispatcher disp = new Dispatcher(num);
			request.setAttribute("dispatcher", disp);
			session.removeAttribute("register");
		} 
		catch (Exception exc) {
			address =  "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
}
