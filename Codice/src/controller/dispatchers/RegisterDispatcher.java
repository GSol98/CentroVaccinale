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
 * 		Servlet implementation class registerDispatcher
 * Si occupa di restituire il form di registrazione corretto.
 */
@WebServlet("/RegisterDispatcher")
public class RegisterDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RegisterDispatcher() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";

		int path = Integer.parseInt(request.getParameter("num"));
		
		if(path == 2) {
			// Se l'utente è un medico
			session.setAttribute("register", "medico");
			address = "/WEB-INF/views/registrazione/medico.jsp";
		}
		else if(path == 3) {
			// Se l'utente è un dipendente ASO
			session.setAttribute("register", "asp");
			address = "/WEB-INF/views/registrazione/asp.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
