<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<style>
		@media screen and (min-width: 751px){
	        #corpoPrincipale {
	            height: 800px;
	        }
	    }
		@media screen and (max-width: 750px){
	        #corpoPrincipale {
	            height: 700px;
	        }
	    }
	</style>
	
	<script>
		loadNavbar();
		
		$("#reportC").click(() => {
			$("form").attr("action", "/SolaroProject/ReportCompleto");
		});
		
		$("#reportP").click(() => {
			$("form").attr("action", "/SolaroProject/ReportProvincia");
		});
	</script>
</head>

<body>
	<div style="padding-top: 30vh">
	    <h1 class="titolo-principale mb-5" style="height: 1.3em;">Benvenuto ${user.nome} ${user.cognome}</h1>
	    <form method="GET" target="_blank">
			<div class="container">
			<div class="row justify-content-center" style="margin-top: 25vh;">
			  	<div class="row col-md-6 col-sm-12 col-12 px-4 my-2">
			    	<div class="col text-center pr-1">
			      		<button id="reportC" class="btn btn-secondary w-100">Report Completo</button>
			  		</div>
			  		<div>
			  			<a href="/SolaroProject/ReportCompleto" download="report_completo.xml" class="btn btn-secondary">
			  				<img src="./images/download-icon.png" alt="downalod"> 
			  			</a>
			  		</div>
				</div>
				<div class="row col-md-6 col-sm-12 col-12 px-4 my-2">
			    	<div class="col text-center pr-1">
			      		<button id="reportP" class="btn btn-secondary w-100">Report Provincia</button>
			  		</div>
			  		<div>
			  			<a href="/SolaroProject/ReportProvincia" download="report_provincia.xml" class="btn btn-secondary">
			  				<img src="./images/download-icon.png" alt="download"> 
			  			</a>
			  		</div>
				</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>