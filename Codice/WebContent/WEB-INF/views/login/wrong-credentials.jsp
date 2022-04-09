<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
		loadNavbar();
		$("#linkLogin").click(event => { loginDispatch(event, ${dispatcher.num}) });
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center" style="height: inherit;">
		<h1 class="titolo-principale w-100" style="height: 2.6em;">Credenziali Errate</h1><br /><br />
		<p id="linkLogin" class="text-white bold-hover w-100">Riprova</p>
	</div>
</body>
</html>