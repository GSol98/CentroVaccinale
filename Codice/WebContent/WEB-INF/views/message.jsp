<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<! DOCTYPE html>
<html>
<head>
	<script>
		$("#indietro").click(loadInit);
	</script>
</head>

<body>
	<div class="row justify-content-center align-content-center" style="height: inherit;">
		<h1 class="titolo-principale w-100" style="height: 2.6em;">${message.text}</h1>
		<p id="indietro" class="text-white bold-hover w-100 mt-5">Torna indietro</p>
	</div>
</body>
</html>