package controller.prenotazione;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.Query;
import db.utils.DB;
import model.beans.Posto;
import model.beans.Message;
import model.beans.Posti;

/**
 * 		Servlet implementation class ElencoPrenotazioni
 * Si occupa di cercare i posti di vaccinazione disponibili per una determinata provincia
 * e in una determinata data (fornite dall'utente)
 */
@WebServlet("/ElencoPosti")
public class ElencoPosti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ElencoPosti() {
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
		String provincia = request.getParameter("provincia");
		String data = request.getParameter("data");
		
		Message message = new Message("Nessun posto disponibile in questa provincia");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			
			if(LocalDate.parse(data).isBefore(LocalDate.parse("2022-01-01"))) 
				AggiungiPosti.add(provincia, data);
			
			
			// Seleziono i posti liberi per effettuare una prenotazione nella provincia e data specificate.
			// Se una prenotazione è stata dichiarata negativa il posto viene considerato libero
			// Le date precedenti al giorno corrente dovrebbero essere considerate negative. In realtà, 
			// in questa servlet, non viene effettuato alcun controllo in quanto questi stati già gestiti
			// tramite javascript nel frontend (viene negata la possibilità a un utente di cercare posti per
			// date precedenti a quella corrente)
			String sql1 = "SELECT posti_totali.id_posto, province.id, province.nome, regioni.id, regioni.nome, posti_totali.data, posti_totali.ora "
					+ 	  "FROM posti_totali, province, regioni "
					+ 	  "WHERE posti_totali.ref_provincia = province.id AND "
					+ 	   		"province.id_regione = regioni.id AND "
					+ 			"posti_totali.ref_provincia = ? AND "
					+ 			"posti_totali.data = ? AND "
					+ 			"posti_totali.id_posto NOT IN (SELECT prenotazioni.ref_posto "
					+ 										  "FROM prenotazioni "
					+ 										  "WHERE esito <> \"negativo\")"
					+ 	  "ORDER BY posti_totali.ora;";
			
			Query query1 = new Query(sql1, provincia, data);
			DB.executeQuery(query1);

			boolean results = false;
			if(query1.getStatus() == 1) {
				// Inserisco i posti in dei java Beans in modo da gestirne la visualizzazione in una jsp.
				// I Beans utilizzati sono Posto e Posti: il primo è un oggetto
				// contenente tutti i dati di una posto; il secondo è un oggetto contenente
				// un arrayList in cui sono memorizzati tutti i posti
				List<List<String>> list1 = query1.getResultSet();
				Posti posti = new Posti();
				DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				ArrayList<String> alreadyIn = new ArrayList<>();
				
				for(List<String> p : list1) {
					int id_posto = Integer.parseInt(p.get(0));
					int id_provincia = Integer.parseInt(p.get(1));
					int id_regione = Integer.parseInt(p.get(3));
					LocalDate data1 = LocalDate.parse(p.get(5));
					String dataFormat = data1.format(formatterDate);
					LocalTime time1 = LocalTime.parse(p.get(6));
					String timeFormat = time1.truncatedTo(ChronoUnit.MINUTES).toString();
					Posto posto = new Posto(id_posto, id_provincia, p.get(2), id_regione, p.get(4), dataFormat, timeFormat);
					posti.addPosti(posto);
					
					String hours = time1.truncatedTo(ChronoUnit.HOURS).toString();
					if(!alreadyIn.contains(hours)) {
						Posto postoS = new Posto(-1, id_provincia, p.get(2), id_regione, p.get(4), dataFormat, hours);
						posti.addPostiSemplici(postoS);
						alreadyIn.add(hours);
					}
						
					results = true;
				}

				if(results) {
					// Inserisco la lista dei posti in sessione in modo da evitare una successiva 
					// query in fase di prenotazione (quando l'utente ne ha scelto esplicitamente uno).
					// Questa fase è gestita dalla servlet Prenota.java
					HttpSession session = request.getSession();
					address = "/WEB-INF/views/prenotazione/elenco-posti.jsp";
					session.setAttribute("listaPosti", posti);
				}
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
