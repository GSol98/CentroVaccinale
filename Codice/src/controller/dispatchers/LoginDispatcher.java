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
 * 		Servlet implementation class Login
 * Restituisce la pagina di login corretta in base alla scelta dell'utente.
 */
@WebServlet("/LoginDispatcher")
public class LoginDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginDispatcher() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se un utente loggato cerca di accedere nuovamente è necessario che la sessione
		// precedente venga rimossa
		HttpSession session = request.getSession();
		session.invalidate();
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";

		int num = Integer.parseInt(request.getParameter("num"));
		session = request.getSession();
		
		if(num == 1) 
			address = "/WEB-INF/views/login/utenteP1.jsp";
		else if(num == 2) 
			address = "/WEB-INF/views/login/medico.jsp";
		else if(num == 3) 
			address = "/WEB-INF/views/login/asp.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
