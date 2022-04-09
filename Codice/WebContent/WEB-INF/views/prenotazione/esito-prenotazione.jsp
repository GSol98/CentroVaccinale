<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<style>
	    @media screen and (max-width: 920px){
	        #corpoPrincipale {
	            height: 850px;
	        }
	    }
	</style>
	
	<script>
		loadNavbar();
	
		$("#elenco").hide();
	    $("#button").click(() => {
	    	$("#elenco").slideToggle("fast");
	    });
		
	    $("input[name='esito']").click(esitoChange);
	    $("#vaccino").change(vaccinoChange);
	    
	    $("form").submit(event => {
	        event.preventDefault();
	        $("#corpoPrincipale").load("/SolaroProject/ModificaEsito", {
	            codicePrenotazione: $("#codicePrenotazione").val(),
	            esito: $("input[name='esito']:checked").val(),
	            vaccino: $("#vaccino").val(),
	            tipo: $("#tipo").val()
	        });
	        loading();
	    });
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
		<h1 class="titolo-principale mb-5" style="height: 1.3em;">Benvenuto ${user.nome} ${user.cognome}</h1>
	    <div class="col-sm-12 col-12">
	    	<div class="row justify-content-center mt-5">
	        	<button id="button" class="btn btn-secondary w-75 mt-5">Carica Esito Vaccinazione</button>
	        </div>
	        <form id="elenco" class="mt-5">
	            <div class="row justify-content-center m-0">
	                <div class="form-group col-sm-12 col-12 col-md-8">
	                    <label class="text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="codicePrenotazione">
	                        Codice Prenotazione
	                    </label>
	                    <input 
	                        id="codicePrenotazione" 
	                        name="codicePrenotazione" 
	                        class="form-control" 
	                        type="number"
	                        min="1000000000000"
	                        max="9999999999999"
	                        required
	                    />
	                    <p class="text-white" style="text-shadow: 0 0 5px black; font-size: 0.8em;">
	                        Puoi inserire l'esito solamente nelle vaccinazioni effettuate  
	                        presso la provincia in cui presti servizio
	                    </p>
	                </div>
	                <div class=" justify-content-center m-0">
	                    <div class="col-sm-12 col-12 col-md-12" style="padding-top: 20px;">
	                        <input id="positivo" type="radio" name="esito" value="positivo" required/>
	                        <label for="positivo" class="text-white font-weight-bold">Positivo</label>
	                        <br />
	                        <input id="negativo" type="radio" name="esito" value="negativo" required/>
	                        <label for="negativo" class="text-white font-weight-bold">Negativo</label>
	                    </div>
	                </div>
	            </div>
	            <div class="row justify-content-center m-0">
	                <div class="form-group col-sm-12 col-12 col-md-4">
	                    <label class="text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="vaccino">
	                        Tipo vaccino
	                    </label>
	                    <select id="vaccino" class="form-control" name="vaccino" required disabled>
	                        <option value =""></option>
	                        <option value="pfizer">Pfizer</option>
	                        <option value="moderna">Moderna</option>
	                        <option value="astrazeneca">Vaxzevria (AstraZeneca)</option>
	                        <option value="janssen">Janssen</option>
	                    </select>
	                </div>
	                <div class="form-group col-sm-12 col-12 col-md-4">
	                    <label class="text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="tipo">
	                        Tipo Dose
	                    </label>
						<select id="tipo" class="form-control" name="tipo" required disabled>
							<option value = ""></option>
						</select>
	                </div>
	            </div>
	            <div class="row justify-content-center m-0 pt-3"> 
	                <button id="cerca" class="btn btn-primary">Aggiorna</button>
	            </div>
	        </form>
	    </div>
	</div>
</body>
</html>