<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>    
	    $("#logout").click(() => {
	    	loading();
	    	$("#corpoPrincipale").load("/SolaroProject/Logout");
	    });
	    
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
	            <li class="nav-item my-2 col-md-6">
	                <a id="logout" class="nav-link navbarButton text-dark">Logout</a>
	            </li>
	            <li class="nav-item my-2 col-md-6">
	                <a id="reportSemplice" class="nav-link navbarButton text-dark">Report Vaccinazioni</a>
	            </li>
	        </ul>
	    </div>
	</nav>
</body>
</html>