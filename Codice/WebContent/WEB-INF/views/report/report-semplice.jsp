<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<style>
        #corpoPrincipale {
            height: 950px;
        }
	</style>
	
	<script>     
	    $("#accedi").click(event => {loginDispatch(event, 3)});
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center" style="height: inherit;">  
	    <div class="pb-5 w-100">
	        <h1 class="titolo-secondario">Totale Vaccinazioni effettuate</h1>
	        <p style="font-size: 10vh;" class="text-white">${report.totVaccini}</p>
	    </div>
	    <div class="pt-5 row w-100">
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
	    <div class="pt-5 row">
	        <p id="accedi" class="text-white font-italic m-0 bold-hover w-100" style="font-size: 1.5em;">Accedi per visualizzare il report completo</p>
	        <p class="text-white m-0 w-100" style="font-size: 0.8em;">Solo per dipendenti ASP</p>
	    </div>
	</div>
</body>
</html>