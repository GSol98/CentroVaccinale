package controller.prenotazione;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.Query;
import model.beans.Message;
import model.beans.Prenotazione;
import model.beans.Prenotazioni;
import db.utils.DB;

/**
 * 		Servlet implementation class MostraPrenotazioni
 * La servlet si occupa di cercare tutte le prenotazioni effettuate da un utente 
 * e di mandarle a una pagina jsp che ne consente la visualizzazione e gestione
 */
@WebServlet("/MostraPrenotazioni")
public class MostraPrenotazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MostraPrenotazioni() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String codiceFiscale = (String) session.getAttribute("codiceFiscale");
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Seleziono i dati relativi a tutte le prenotazioni effettuate da un utente
			String sql = "SELECT codice_prenotazione, data, ora, esito, vaccino, tipo, province.nome, regioni.nome "
					+	 "FROM utenti, prenotazioni, posti_totali, province, regioni "
					+    "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
					+ 		   "prenotazioni.ref_posto = posti_totali.id_posto AND "
					+ 		   "posti_totali.ref_provincia = province.id AND "
					+ 		   "province.id_regione = regioni.id AND "
					+ 		   "utenti.codice_fiscale = ?;";
			
			Query query = new Query(sql, codiceFiscale);
			DB.executeQuery(query);
			
			if(query.getStatus() == 1) {
				// Inserisco le prenotazioni in dei java Beans in modo da gestirne
				// la visualizzazione in una jsp.
				// I Beans utilizzati sono Prenotazione e Prenotazioni: il primo è un oggetto
				// contenente tutti i dati di una prenotazione; il secondo è un oggetto contenente
				// un arrayList in cui sono memorizzate tutte le prenotazioni
				boolean result = false;
				Prenotazioni prenotazioni = new Prenotazioni();
				DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				for(List<String> list : query.getResultSet()) {
					String codicePrenotazione = list.get(0);
					LocalDate data1 = LocalDate.parse(list.get(1));
					String dataFormat = data1.format(formatterDate);
					LocalTime time1 = LocalTime.parse(list.get(2));
					String timeFormat = time1.truncatedTo(ChronoUnit.MINUTES).toString();
					String esito = list.get(3);
					String vaccino = list.get(4);
					String tipo = list.get(5);
					String provincia = list.get(6);
					String regione = list.get(7); 
					Prenotazione prenotazione = new Prenotazione(codicePrenotazione, dataFormat, timeFormat, esito, vaccino, tipo, provincia, regione); 
					prenotazioni.addPrenotazione(prenotazione);
					result = true;
				}
				
				if(result) {
					request.setAttribute("prenotazioni", prenotazioni);
					address = "/WEB-INF/views/prenotazione/elenco-prenotazioni.jsp";
				}
			}
		} 
		catch(Exception exc) {
			address = "/WEB-INF/views/generic-error.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
