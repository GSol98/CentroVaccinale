<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.beans.Prenotazione" %>
<%@ page import="model.beans.Prenotazioni" %>
   
<! DOCTYPE html>
<html>
<head>
	<style>
	    @media screen and (max-width: 767px){
	        #corpoPrincipale {
	            height: 1100px;
	        }
	    }
	</style>
	<script>
		loadNavbar();
		
		$("#elenco").hide();
	    $("#button").click(() => {
	    	$("#elenco").slideToggle("fast");
	    });
	    
	    $("#cancella").click(function() {
	        if(confirm("Vuoi veramente cancellare la prenotazione?")) {
	       		$("#corpoPrincipale").load("/SolaroProject/CancellaPrenotazione", {
	       			codicePrenotazione: $(this).val()
	       		});
	       		loading();
	        }
	    });
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
		<h1 class="titolo-principale mb-5" style="height: 1.3em">Benvenuto</h1>
        <div class="col-sm-12 col-12">
        	<div class="row justify-content-center mt-5">
            	<button id="button" class="btn btn-secondary w-75 mt-5">Mostra prenotazioni</button>
           	</div>
            <div id="elenco" class="px-5">
            	<% Prenotazioni prenotazioni = (Prenotazioni) request.getAttribute("prenotazioni"); 
					for(Prenotazione p : prenotazioni.getPrenotazioni()) { 
						if(p.getEsito().compareTo("in corso") == 0) { %>
	            			<div class='row justify-content-center py-1 m-0'> 
		           				<div class='border rounded col-12' style='background-color: rgb(255, 255, 255, 0.2);'>   
		            				<div class='row'> 
		           						<div class='px-0 col-md-8 col-sm-12 col-12'> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getProvincia()%></p> 
		           							<p class='m-0 text-white font-weight-bold'><%=p.getRegione()%></p> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getData()%> <%=p.getOra()%></p> 
		            					</div> 
		            					<div class='px-0 pb-3 col-md-4 col-sm-12 col-12'> 
		            						<div class='row justify-content-center' style='padding-top: 22px;'> 
		            							<button id='cancella' class='btn btn-secondary' value='<%=p.getCodicePrenotazione()%>'>Cancella</button> 
		            						</div> 
		            					</div> 
		            				</div>
		            			</div> 
		            		</div>
					<% } else if(p.getEsito().compareTo("positivo") == 0) { %>
	            			<div class='row justify-content-center py-1 m-0'>   
		            			<div class='border rounded col-12' style='background-color: rgb(255, 255, 255, 0.2);'>   
		            				<div class='row'> 
		            					<div class='px-0 col-md-8 col-sm-12 col-12'> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getProvincia()%></p> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getRegione()%></p> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getData()%> <%=p.getOra()%></p> 
		            					</div> 
		            					<div class='px-0 col-md-4 col-sm-12 col-12'> 
		                                 	<p class='m-0 text-white font-weight-bold'>Esito: Positivo 
		            						<p class='m-0 text-white font-weight-bold'>Vaccino: <%=p.getVaccino()%>
		           							<p class='m-0 text-white font-weight-bold'>Tipo: <%=p.getTipo()%>
		            					</div> 
		           					</div> 
		            			</div> 
		            		</div>
					<% } else if(p.getEsito().compareTo("negativo") == 0) { %>
	            			<div class='row justify-content-center py-1 m-0'>   
		           				<div class='border rounded col-12' style='background-color: rgb(255, 255, 255, 0.2);'>   
		           					<div class='row'> 
		            					<div class='px-0 col-md-8 col-sm-12 col-12'> 
		           							<p class='m-0 text-white font-weight-bold'><%=p.getProvincia()%></p> 
		            						<p class='m-0 text-white font-weight-bold'><%=p.getRegione()%></p> 
		           							<p class='m-0 text-white font-weight-bold'><%=p.getData()%> <%=p.getOra()%></p> 
		           						</div> 
	            						<div class='px-0 py-4 col-md-4 col-sm-12 col-12'> 
		            						<p class='m-0 text-white font-weight-bold'>Esito: Negativo 
		           						</div> 
		           					</div> 
		            			</div> 
		            		</div>
					<% }
					} %>
            </div>
        </div>
    </div>
</body>
</html>