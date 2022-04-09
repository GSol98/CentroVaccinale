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
       			codFisc: $("#codFisc").val()
       		});
       		loading();
   		});
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
	    <form class="p-5 border rounded" style="background-color: rgb(255, 255, 255, 0.2);">
	        <p class="text-white mb-4" style="font-size: 5vh; font-weight: bold;">Login Utente</p>
	        <div class="form-group mb-4">
	            <label class="text-white" style="font-weight: bold;">Codice Fiscale</label>
	            <input 
	            	id="codFisc"
	                name="codFisc" 
	                class="form-control" 
	                type="text" 
	                pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"
	                size="40"
	                required 
	            />
	        </div>
	        <button class="btn btn-secondary w-100">Accedi</button>
	    </form>
	</div>
</body>
</html>