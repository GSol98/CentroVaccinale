package controller.report;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.Query;
import model.beans.Message;
import model.beans.ReportS;
import db.utils.DB;

/**
 * 		Servlet implementation class ReportSemplice
 * Si occupa di restituire un report semplice di vaccinazioni, ovvero un report 
 * accessbile a tutti gli utenti in cui sono indicati il numero di vaccinazioni 
 * effettuate, il numero di prime, seconde e uniche dosi.
 */
@WebServlet("/ReportSemplice")
public class ReportSemplice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportSemplice() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String tipo = (String) session.getAttribute("tipo");
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Seleziono i dati necessari per la creazione del report
			String sql1 = "SELECT COUNT(*) FROM prenotazioni WHERE esito = \"positivo\";";
			String sql2 = "SELECT COUNT(*) FROM prenotazioni WHERE esito = \"positivo\" AND tipo = \"prima dose\";";
			String sql3 = "SELECT COUNT(*) FROM prenotazioni WHERE esito = \"positivo\" AND tipo = \"seconda dose\";";
			String sql4 = "SELECT COUNT(*) FROM prenotazioni WHERE esito = \"positivo\" AND tipo = \"unica dose\";";
			
			Query query1 = new Query(sql1);
			Query query2 = new Query(sql2);
			Query query3 = new Query(sql3);
			Query query4 = new Query(sql4);
			
			DB.executeQuery(query1);
			DB.executeQuery(query2);
			DB.executeQuery(query3);
			DB.executeQuery(query4);

			if(query1.getStatus() == 1 && query2.getStatus() == 1 && query3.getStatus() == 1) {
				// Inserisco i dati in un Beans per gestirne la visualizzazione in una jsp
				int tot = Integer.parseInt(query1.getResultSet().get(0).get(0));
				int pd = Integer.parseInt(query2.getResultSet().get(0).get(0));
				int sd = Integer.parseInt(query3.getResultSet().get(0).get(0));
				int ud = Integer.parseInt(query4.getResultSet().get(0).get(0));
				ReportS report = new ReportS(tot, pd, sd, ud);
				request.setAttribute("report", report);
				
				if(tipo != null && tipo.compareTo("3") == 0)
					address = "/WEB-INF/views/report/report-sempliceASP.jsp";
				else
					address = "/WEB-INF/views/report/report-semplice.jsp";
			}
		}
		catch(Exception exc) {
			address = "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
