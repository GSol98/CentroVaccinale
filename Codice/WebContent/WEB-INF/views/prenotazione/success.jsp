<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
		$("#torna").click(loadInit);
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center" style="height: inherit;">
		<h1 class="titolo-principale w-100" style="height: 2.6em">Prenotazione effettuata con successo</h1>
		<h1 class="text-white mt-5 w-100" style="font-size: 1.5em">Il codice prenotazione �: <strong>${prenotazione.codicePrenotazione}</strong></h1>
		<p id="torna" class="text-white mt-5 bold-hover w-100">Torna alla pagina principale</p>
	</div>
</body>
</html>