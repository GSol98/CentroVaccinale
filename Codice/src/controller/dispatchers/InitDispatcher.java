package controller.dispatchers;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.utils.DB;
import model.db.Query;
import model.beans.UserLogged;
import model.beans.Dispatcher;
import model.beans.Message;
import model.beans.Posto;

/**
 * 		Servlet implementation class InitDispatcher
 * Si occupa di inviare le pagine principali in base al tipo di utente che le ha richieste. 
 * La servlet si occupa di ricordare, attraverso parametri inseriti in sessione in momenti
 * opportuni, che tipo di navigazione sta svolgendo l'utente e, di conseguenza mostrare
 * contenuti differenti in base a questa.
 */
@WebServlet("/InitDispatcher")
public class InitDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public InitDispatcher() {
        super();
    }

    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String codiceFiscale = (String) session.getAttribute("codiceFiscale");
		String tipo = (String) session.getAttribute("tipo");
		
		Message message = new Message("Ops... Si è verificato un errore");
		request.setAttribute("message", message);
		String address = "/WEB-INF/views/message.jsp";
		
		Query query = null;

		try {
			// Se codiceFiscale o tipo non sono memorizzati in sessione significa che 
			// l'utente non ha ancora fatto un accesso. Viene quindi visualizzata
			// la pagina di benvenuto.
			if(codiceFiscale == null || tipo == null) {
				
				// Il parametro è utilizzato quando si richede la visualizzazione 
				// della pagina di benvenuto anche dopo che l'utente abbia effettuato
				// delle operazioni
				String welcome = request.getParameter("welcome");
				if(welcome != null && welcome.compareTo("yes") == 0) {
					session.removeAttribute("register");
					session.removeAttribute("login");
				}
				
				String register = (String) session.getAttribute("register");
				String login = (String) session.getAttribute("login");
				
				// Tutti i seguenti sono dei 'checkpoint' che consentono di memorizzare
				// dove l'utente è arrivato nella navigazione: se questo sta visualizzando 
				// un certo contenuto e ricarica la pagina viene riportato nello stesso punto
				// in cui si trovava
				if(register != null) {
					if(register.compareTo("medico") == 0)
						address = "/WEB-INF/views/registrazione/medico.jsp";
					else if(register.compareTo("asp") == 0)
						address = "/WEB-INF/views/registrazione/asp.jsp";
				}
				else if(login != null) {
					if(login.compareTo("utente") == 0)
						address = "/WEB-INF/views/login/utenteP1.jsp";
					else if(login.compareTo("medico") == 0)
						address = "/WEB-INF/views/login/medico.jsp";
					else if(login.compareTo("asp") == 0)
						address = "/WEB-INF/views/login/asp.jsp";
				}
				else
					address = "/WEB-INF/views/welcome.jsp";
			}
			else { 
				// Se l'utente è un "utente semplice" (colui che deve prenotare) si deve controllare
				// se nel database esistono prenotazioni in corso a lui associate attraverso
				// la variabile codicePrenotazione memorizzata in sessione: se è null non sono associate
				// prenotazioni in corso.
				if(tipo.compareTo("1") == 0) {
					String codicePrenotazione = (String) session.getAttribute("codicePrenotazione");
					String accesso = (String) session.getAttribute("accesso");
					
					if(codicePrenotazione == null) {
						// Anche in questo caso, i controlli successivi sono 'checkpoint'
						if(accesso.compareTo("p1-1") == 0) {
							// La query e le operazioni successive consentono di controllare che la data della
							// seconda dose sia ad almento 21 giorni di distanza da quella della prima dose.
							// Il dato viene memorizzato in un bean a una jsp, dove tramite javascript viene 
							// utilizzato per bloccare le prenotazioni che non rispettano il vincolo
							String sql1 = "SELECT COUNT(*), posti_totali.* "
									+ 	  "FROM utenti, prenotazioni, posti_totali "
									+	  "WHERE utenti.ref_prenotazione = prenotazioni.codice_prenotazione AND "
									+ 			"prenotazioni.ref_posto = posti_totali.id_posto AND "
									+ 			"prenotazioni.tipo = \"prima dose\" AND "
									+ 			"prenotazioni.esito = \"positivo\" AND "
									+ 			"utenti.codice_fiscale = ?;";
							
							Query query1 = new Query(sql1, codiceFiscale);
							DB.executeQuery(query1);
							
							if(query1.getStatus() == 1) {
								if(Integer.parseInt(query1.getResultSet().get(0).get(0)) == 1) {
									int id_posto = Integer.parseInt(query1.getResultSet().get(0).get(1));
									LocalDate data = LocalDate.parse(query1.getResultSet().get(0).get(3));
									data = data.plusDays(21);
									String dataS = data.toString();
									String ora = query1.getResultSet().get(0).get(4);
									Posto posto = new Posto(id_posto, -1, "", -1, "", dataS, ora);
									request.setAttribute("posto", posto);
								}
							}
							
							address = "/WEB-INF/views/prenotazione/prenota-vaccino.jsp";
						}
						else if(accesso.compareTo("p1-2") == 0)
							// L'utente ha delle prenotazioni in corso a lui associate. Viene quindi rimandato
							// alla pagina di inserimento del codice di prenotazione. 
							address = "/WEB-INF/views/login/utenteP2.jsp";
						
						else if(accesso.compareTo("p1-3") == 0)
							// L'utente ha completato il ciclo di vaccinazione 
							address = "/MostraPrenotazioni";
						
						else if(accesso.compareTo("p2") == 0) 
							// L'utente ha inserito delle credenziali errate. Il caso si può verificare
							// solamente quando il codicePrenotazione non è corretto.
							address = "/WEB-INF/views/login/wrong-credentials.jsp";
					}
					else 
						address = "/MostraPrenotazioni";
					
				}
				else { 
					// Un medico o un dipendente ASP hanno effettuato l'accesso
					String table = "";
					String credentials = (String) session.getAttribute("credentials");
					
					if(credentials != null && credentials.compareTo("wrong") == 0) 
						address = "/WEB-INF/views/login/wrong-credentials.jsp";
					else { 
						if(tipo.compareTo("2") == 0) {
							// Se l'utente è un medico 
							table = "medici";
							address = "/WEB-INF/views/prenotazione/esito-prenotazione.jsp";
						}
						else if(tipo.compareTo("3") == 0) {
							// Se l'utente è un dipendente ASP 
							table = "asp";
							address = "/WEB-INF/views/report/principale-asp.jsp";
						}
						// La query consente di ottenere nome e cognome dell'utente. Il risultato viene
						// memorizzato in un bean e inviato alle jsp che si occupano della visualizzazione
						String sql = "SELECT nome, cognome FROM "+ table +" WHERE codice_fiscale = ?";
						query = new Query(sql, codiceFiscale);
						DB.executeQuery(query);
						
						if(query != null && query.getStatus() == 1) {
							String nome = query.getResultSet().get(0).get(0);
							String cognome = query.getResultSet().get(0).get(1);
							UserLogged user = new UserLogged(nome, cognome);
							request.setAttribute("user", user);
						}
					}
				}
				Dispatcher disp = new Dispatcher(Integer.parseInt(tipo));
				request.setAttribute("dispatcher", disp);
			}
		} 
		catch (Exception exc) {
			// Nel caso in cui si verifica un qualsiasi errore, anche nell'esecuzione della query,
			// l'utente viene rimandato a una semplice pagina di errore generico
			address = "/WEB-INF/views/message.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
