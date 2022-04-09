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
 * 		Servlet implementation class ModificaEsito
 * Si occupa di modificare l'esito di una prenotazione ogni volta che un medico la richiede.
 * Vengono inoltre effettuati dei controlli in modo che un medico non possa modificare
 * l'esito di una prenotazione effettuata in una provincia diversa dalla sua, o di una 
 * prenotazione già dichiarata positiva o negativa.
 */
@WebServlet("/ModificaEsito")
public class ModificaEsito extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModificaEsito() {
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
		String codiceFiscale = (String) session.getAttribute("codiceFiscale");
		String codicePrenotazione = request.getParameter("codicePrenotazione");
		String esito = request.getParameter("esito");
		String vaccino = request.getParameter("vaccino");
		String tipo = request.getParameter("tipo");
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		try {
			// Seleziono la provincia in cui il medico presta servizio
			String sql1 = "SELECT ref_provincia FROM medici WHERE codice_fiscale = ?;";
			Query query1 = new Query(sql1, codiceFiscale);
			DB.executeQuery(query1);

			if(query1.getStatus() == 1) {
				// Seleziono i dati relativi all'utente e alla prenotazione. Inoltre viene effettuato il controllo
				// sulla provincia in cui è stata effettuata quella prenotazione e sull'esito della prenotazione
				String provincia = query1.getResultSet().get(0).get(0);

				String sql2 = "SELECT COUNT(*), utenti.codice_fiscale, posti_totali.ref_provincia, posti_totali.data "
						+ 	  "FROM utenti, prenotazioni, posti_totali "
						+ 	  "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
						+ 			"prenotazioni.ref_posto = posti_totali.id_posto AND "
						+ 			"posti_totali.ref_provincia = ? AND "
						+ 			"prenotazioni.codice_prenotazione = ? AND "
						+ 			"prenotazioni.esito = \"in corso\";";
				
				Query query2 = new Query(sql2, provincia, codicePrenotazione);
				DB.executeQuery(query2);

				if(query2.getStatus() == 1) {
					if(Integer.parseInt(query2.getResultSet().get(0).get(0)) == 0) 
						// Se la prenotazione è già stata modificata o è effettuata in una provincia differente
						// da quella in cui il medico presta servizio
						message.setText("Non puoi modificare l'esito di questa prenotazione");
					else {
						String sql3;
						Query query3 = null;
						if(esito.compareTo("negativo") == 0) {
							// Se l'esito della prenotazione è negativo (mancata vaccinazione)
							sql3 = "UPDATE prenotazioni SET esito = \"negativo\" WHERE codice_prenotazione = ?;";
							query3 = new Query(sql3, codicePrenotazione);
						}	
						else {
							// Se l'esito della prenotazione è positivo. In questo caso è necessario che il medico
							// dichiari il tipo di vaccino e la tipologia di dose
							sql3 = "UPDATE prenotazioni SET esito = \"positivo\", vaccino = ?, tipo = ?  WHERE codice_prenotazione = ?;";
							query3 = new Query(sql3, vaccino, tipo, codicePrenotazione);							
						}
						
						DB.executeUpdate(query3);
						if(query3 != null && query3.getStatus() == 1) { 
							if(esito.compareTo("positivo") == 0 && tipo.compareTo("prima dose") == 0) {
								// Nel caso in cui il medico ha inserito un esito di una prima dose viene 
								// richiamata una seconda servlet che si occupa di prenotare automaticamente
								// la seconda dose per quell'utente
								request.setAttribute("codiceFiscale", query2.getResultSet().get(0).get(1));
								request.setAttribute("provincia", query2.getResultSet().get(0).get(2));
								request.setAttribute("data", query2.getResultSet().get(0).get(3));
								address = "/SecondaDose";
							}
							else 
								message.setText("Esito Modificato Correttamente");
						}
					}
				}
			}		
		} catch(Exception exc) {
			address = "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
