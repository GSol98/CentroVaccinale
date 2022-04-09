<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
<! DOCTYPE html>
<html>
<head>
	<style>
	    @media screen and (min-width: 768px){
	        #corpoPrincipale {
	            height: 900px;
	        }
	    }
	
	    @media screen and (max-width: 767px) and (min-width: 577px){
	        #corpoPrincipale {
	            height: 1150px;
	        }
	    }
	    
	    @media screen and (max-width: 576px){
	        #corpoPrincipale {
	            height: 1250px;
	        }
	    }
	</style>
	
	<script>
		loadNavbar();
		loadRegioni();
	 	$("#regione").change(loadProvince);
	 	$("#password").keyup(checkPassword);
	 
		$("form").submit(event => {
			event.preventDefault();
			$("#corpoPrincipale").load("/SolaroProject/Register", {
				num: 3,
				firstName: $("#name").val(),
				lastName: $("#lastName").val(),
				id_provincia: $("#provincia").val(),
				codFisc: $("#cf").val(),
				matricola: $("#matricola").val(),
				password: $("#password").val(),
				confermaPassword: $("#confermaPassword").val()
			});
			loading();
		});
	</script>
</head>

<body>
	<form class="container was-validated">
		<h1 class="pt-5 titolo-secondario">Registrazione Dipendente ASP</h1>
	    <div class="form-row pt-5 pb-3">
	        <div class="form-group col-md-6 col-sm-12 px-3">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="name">
	                Nome
	            </label>
	            <input 
	                id="name" 
	                name="firstName" 
	                type="text" 
	                class="form-control" 
	                maxlength="40" 
	                required
	            />
	        </div>
	
	        <div class="form-group col-md-6 col-sm-12 px-3">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="lastName">
	                Cognome
	            </label>
	            <input 
	                id="lastName" 
	                name="lastName" 
	                type="text" 
	                class="form-control" 
	                maxlength="40" 
	                required
	            />
	        </div>
	    </div>
	
	    <div class="py-4">
	        <label class="lead text-white ml-3" style="text-shadow: 0 0 5px black; font-weight: bold;">
	            Locazione ASP
	        </label>
	        <div class="input-group py-3">
	            <div class="input-group-prepend col-sm-3 col-12 pr-0">
	                <span class="input-group-text w-100" style="font-weight: bold;">
	                    Regione
	                </span>
	            </div>
	            <select id="regione" name="id_regione" class="custom-select col-sm-9 col-12" required>
	                <option value="" selected></option>
	            </select>
	        </div>
	
	        <div class="input-group py-3">
	            <div class="input-group-prepend col-sm-3 col-12 pr-0">
	                <span class="input-group-text w-100" style="font-weight: bold;">Provincia</span>
	            </div>
	            <select id="provincia" name="id_provincia" class="custom-select col-sm-9 col-12" required>
	                <option value="" selected></option>
	            </select>
	        </div>             
	    </div>
	
	    <div class="form-row py-3">
	        <div class="form-group col-md-6 col-sm-12 px-4">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="cf">
	                Codice Fiscale
	            </label>
	            <input 
	                id="cf" 
	                name="codFisc" 
	                type="text" 
	                class="form-control" 
	                maxlength="40" 
	                pattern="^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$"
	                required
	            />
	        </div>
	
	        <div class="form-group col-md-6 col-sm-12 px-4">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="matricola">
	                Matricola Dipendente
	            </label>
	            <input 
	                id="matricola" 
	                name="matricola" 
	                type="text" 
	                class="form-control" 
	                maxlength="40" 
	                pattern="[0-9]{5}"
	                required
	            />
	        </div>
	    </div>
	
	    <div class="form-row pt-3 pb-5">
	        <div class="form-group col-md-6 col-sm-12 px-4">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="password">
	                Password
	            </label>
	            <input 
	                id="password" 
	                name="password" 
	                type="password" 
	                class="form-control"  
	                required
	            />
	        </div>
	
	        <div class="form-group col-md-6 col-sm-12 px-4">
	            <label class="lead text-white" style="text-shadow: 0 0 5px black; font-weight: bold;" for="confermaPassword">
	                Conferma Password
	            </label>
	            <input 
	                id="confermaPassword" 
	                name="confermaPassword" 
	                type="password" 
	                class="form-control"
	                pattern=""
	                required
	            />
	        </div>
	    </div>
	    
	    <button class="btn btn-primary float-right mr-3">Registrati</button>
	</form>
</body>
</html>