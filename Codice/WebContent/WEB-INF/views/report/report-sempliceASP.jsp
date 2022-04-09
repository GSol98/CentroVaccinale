<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<style>
	    @media screen and (min-width: 768px){
	        #corpoPrincipale {
	            height: 1000px;
	        }
	    }
	    @media screen and (max-width: 767px){
	        #corpoPrincipale {
	            height: 900px;
	        }
	    }
	</style>
	
	<script>
		$("#reportC").click(() => {
			$("form").attr("action", "/SolaroProject/ReportCompleto");
		});
		
		$("#reportP").click(() => {
			$("form").attr("action", "/SolaroProject/ReportProvincia");
		});
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center" style="height: inherit;">  
	    <div class="pb-5">
	        <h1 class="titolo-secondario">Totale Vaccinazioni effettuate</h1>
	        <p style="font-size: 10vh;" class="text-white">${report.totVaccini}</p>
	    </div>
	    <div class="pt-5 row">
	        <div class="col-sm-12 col-12 col-md-4 pb-3">
	            <h1 class="titolo-secondario">Prime Dosi</h1>
	            <p id="nPrimeDosi" style="font-size: 6vh;" class="text-white">${report.numPD}</p>
	        </div>
	        <div class="col-sm-12 col-12 col-md-4 pb-3">
	            <h1 class="titolo-secondario">Seconde Dosi</h1>
	            <p id="nSecondeDosi" style="font-size: 6vh;" class="text-white">${report.numSD}</p>
	        </div>
	        <div class="col-sm-12 col-12 col-md-4 pb-3">
	            <h1 class="titolo-secondario">Uniche Dosi</h1>
	            <p id="nUnicheDosi" style="font-size: 6vh;" class="text-white">${report.numUD}</p>
	        </div>
	    </div>
	    <form method="GET" action="/SolaroProject/ReportCompleto" target="_blank">
		    <div class="pt-5">
		        <div class="container">
				  	<div class="row">
				    	<div class="my-2 mx-5">
				      		<button id="reportC" class="btn btn-secondary">Report Completo</button>
				  		</div>
				  		<div class="my-2 mx-5">
				      		<button id="reportP" class="btn btn-secondary">Report Provincia</button>
				  		</div>
					</div>
				</div>
		    </div>
	    </form>
	</div>
</body>
</html>