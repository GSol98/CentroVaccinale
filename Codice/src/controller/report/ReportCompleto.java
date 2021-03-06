package controller.report;

import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import db.utils.DB;
import model.beans.Message;
import model.beans.Vaccinati;
import model.beans.Vaccinato;
import model.db.Query;

/**
 * 		Servlet implementation class ReportCompleto
 * Si occupa di recuperare i dati per la creazione di un report completo di vaccinazioni.
 * Questo report ? accessibile soltanto dai dipendenti ASP e contiene tutti i dati
 * relativi agli utenti e alle prenotazioni (con esito positivo) da loro effettuate.
 */
@WebServlet("/ReportCompleto")
public class ReportCompleto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReportCompleto() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("tipo");
		
		Message message = new Message("Non sei autorizzato a visualizzare il report");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Controllo che l'utente sia un dipendente ASP. Il controllo ? necessario in quanto qualsiasi
			// utente, sebbene non direttamente dal frontend, potrebbe effettuare una richiesta per ottenerlo.
			if(user != null && user.compareTo("3") == 0) {
				// Seleziono tutti i dati necesari per la creazione del report
				String sql = "SELECT utenti.codice_fiscale, prenotazioni.codice_prenotazione, province.id, province.nome, regioni.id, "
						+ 			"regioni.nome, posti_totali.data, posti_totali.ora, prenotazioni.vaccino, prenotazioni.tipo "
						+ 	 "FROM utenti, prenotazioni, posti_totali, province, regioni "
						+ 	 "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
						+ 		   "prenotazioni.ref_posto = posti_totali.id_posto AND "
						+ 		   "posti_totali.ref_provincia = province.id AND "
						+ 		   "province.id_regione = regioni.id AND "
						+ 		   "prenotazioni.esito = \"positivo\""
						+ 	 "ORDER BY prenotazioni.codice_prenotazione";
				
				Query query = new Query(sql);
				DB.executeQuery(query);

				if(query.getStatus() == 1) {
					// Inserisco i dati in dei java Beans in modo da gestirne la visualizzazione in una jsp.
					// I Beans utilizzati sono Vaccinato e Vaccinati: il primo ? un oggetto
					// contenente tutti i dati di un utente vaccinato e della sua prenotazione; il secondo 
					// ? un oggetto contenente un arrayList in cui sono memorizzati tutti gli utenti vaccinati
					Vaccinati vaccinati = new Vaccinati();
					for(List<String> list : query.getResultSet()) {
						String codiceFiscale = list.get(0);
						String codicePrenotazione = list.get(1);
						int id_provincia = Integer.parseInt(list.get(2));
						String provincia = list.get(3);
						int id_regione = Integer.parseInt(list.get(4));
						String regione = list.get(5);
						String data = LocalDate.parse(list.get(6)).toString();
						String ora = list.get(7);
						String vaccino = list.get(8);
						String tipo = list.get(9);
						Vaccinato vaccinato = new Vaccinato(codiceFiscale, codicePrenotazione, id_provincia, provincia, id_regione, regione, data, ora, vaccino, tipo);
						vaccinati.addVaccinato(vaccinato);
					}
					request.setAttribute("vaccinati", vaccinati);
					address = "/WEB-INF/report/report.jsp";
				}
			}
		} 
		catch(Exception exc) {
			message.setText("Ops... Si ? verificato un errore");
			address = "/WEB-INF/views/message.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
