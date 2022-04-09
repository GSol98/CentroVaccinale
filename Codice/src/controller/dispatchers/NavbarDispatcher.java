package controller.dispatchers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.Message;


/**
 * 		Servlet implementation class NavbarDispatcher
 * Si occupa di restituire la navbar corretta in base al fatto che l'utente sia loggato oppure no.
 */
@WebServlet("/NavbarDispatcher")
public class NavbarDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NavbarDispatcher() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String codiceFiscale = (String) session.getAttribute("codiceFiscale");
		String tipo = (String) session.getAttribute("tipo");
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";

		if(codiceFiscale == null || tipo == null) 
			address = "/WEB-INF/views/navbars/navbar.jsp";
		else if(tipo.compareTo("1") == 0) {
			String codicePrenotazione = (String) session.getAttribute("codicePrenotazione");
			String accesso = (String) session.getAttribute("accesso");

			if(codicePrenotazione == null && (accesso == null || (accesso != null && (accesso.compareTo("p1-2") == 0 || accesso.compareTo("p2") == 0)))) 
				address = "/WEB-INF/views/navbars/navbar.jsp";
			else
				address = "/WEB-INF/views/navbars/navbar-logged.jsp";
		}
		else {
			String credentials = (String) session.getAttribute("credentials");
			if(credentials != null && credentials.compareTo("wrong") == 0)
				address = "/WEB-INF/views/navbars/navbar.jsp";
			else
				address = "/WEB-INF/views/navbars/navbar-logged.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
