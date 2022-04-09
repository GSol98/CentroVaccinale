<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
	    $("#accessoUtente").click(event => { loginDispatch(event, 1) });
	    $("#accessoMedico").click(event => { loginDispatch(event, 2) });
	    $("#accessoASP").click(event => { loginDispatch(event, 3) });
	    
	    $("#reportSemplice").click(loadReportS);
	</script>
</head>

<body>
	<nav class="navbar navbar-expand-sm navbar-light">
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" 
	            aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="navbar-toggler-icon"></span>
	    </button>
	
	    <div class="collapse navbar-collapse" id="navbarNavDropdown">
	        <ul class="navbar-nav container p-0" id="lista-navbar">
	            <li class="nav-item dropdown my-2 col-md-6">
	                <a class="nav-link dropdown-toggle navbarButton text-dark px-0" id="navbarDropdownMenuLink" 
	                   role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                    Area Riservata
	                </a>
	                <div id="dropdown" class="dropdown-menu col-md-12" aria-labelledby="navbarDropdownMenuLink">
	                    <a id="accessoUtente" class="dropdown-item navbarDropdownButton mb-1">Accesso Utente</a>
	                    <a id="accessoMedico" class="dropdown-item navbarDropdownButton mb-1">Accesso Medico</a>
	                    <a id="accessoASP" class="dropdown-item navbarDropdownButton mb-1">Accesso Dipendente APS</a>
	                </div>
	            </li>
	            <li class="nav-item my-2 col-md-6">
	                <a id="reportSemplice" class="nav-link navbarButton text-dark">Report Vaccinazioni</a>
	            </li>
	        </ul>
	    </div>
	</nav>
</body>
</html>