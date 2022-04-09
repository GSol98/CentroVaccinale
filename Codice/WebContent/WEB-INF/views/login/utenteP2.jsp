<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
		loadNavbar();
		$("form").submit(event => {
			event.preventDefault();
		    $("#corpoPrincipale").load("/SolaroProject/LoginUtente", {
		    	codPren: $("#codPren").val()
		    });
		    loading();
		});
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
	    <form class="p-5 border rounded" style="background-color: rgb(255, 255, 255, 0.2);">
	        <p class="text-white mb-4" style="font-size: 5vh; font-weight: bold;">Login Utente</p>
	        <div class="form-group mb-0">
	            <label class="text-white" style="font-weight: bold;">Codice Prenotazione</label>
	            <input 
	            	id="codPren"
	                name="codPren" 
	                class="form-control" 
	                type="text" 
	                pattern="[A-Z0-9]{13}"
	                size="40"
	                required 
	            />
	        </div>
	        <p class="text-white mb-3" style="font-size: 0.8em;">Riferito alla prenotazione in corso</p>
	        <button class="btn btn-secondary w-100">Accedi</button>
	    </form>
	</div>
</body>
</html>