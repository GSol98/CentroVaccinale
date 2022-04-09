<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
		loadNavbar();
	 		
	   	$("#register").click(() => {
	   		loading();
	   		$("#corpoPrincipale").load("/SolaroProject/RegisterDispatcher?num=2");
	   	});
	   
	   	$("form").submit(event => {
	   		event.preventDefault();
	   		$("#corpoPrincipale").load("/SolaroProject/Login", {
	   			num: 2,
	   			codFisc: $("#codFisc").val(),
	   			matricola: $("#matricola").val(),
	   			password: $("#password").val()
	   		});
	   		loading();
	   	});
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center px-0" style="height: inherit;">
	    <form class="p-5 border rounded" style="background-color: rgb(255, 255, 255, 0.2);">
	        <p class="text-white mb-4" style="font-size: 5vh; font-weight: bold;">Login Medico</p>
	        <div class="form-label-group mb-4">
	            <input 
	            	id="codFisc"
	                name="codFisc" 
	                class="form-control" 
	                type="text" 
	                placeholder="Codice Fiscale" 
	                pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"
	                size="40"
	                required 
	            />
	        </div>
	        <div class="form-label-group mb-4">
	            <input 
	            	id="matricola"
	                name="matricola" 
	                class="form-control" 
	                type="text" 
	                placeholder="Matricola Medico" 
	                pattern="[0-9]{5}"
	                size="40" 
	                required 
	            />
	        </div>
	        <div class="form-label-group mb-4">
	            <input 
	            	id="password"
	                name="password" 
	                class="form-control" 
	                type="password" 
	                placeholder="Password" 
	                size="40"
	                required 
	            />
	        </div>
	
	        <div id="submit" class="form-row px-1">
	            <button class="btn btn-secondary col-6">Accedi</button>
	            <p id="register" class="col-6 pt-2 text-white bold-hover">Registrati</p>
	        </div>
	    </form>
	</div>
</body>
</html>