<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.beans.Posto" %>
<%@ page import="model.beans.Posti" %>

<! DOCTYPE html>
<html>
<head>
	<style>
		#corpoPrincipale {
		    background-image: none;
			background-color: rgb(59, 119, 116);
			height: inherit;
		}
	</style>
	
	<script>
		$("button").click(function() {
			$("#corpoPrincipale").load("/SolaroProject/Prenota", {
				ora: $(this).val()
			});
			loading();
		});
		
	    $("#indietro").click(loadInit);
	</script>
</head>

<body>
	<div class="d-block pt-3 pl-5">
	    <p id="indietro" class="text-white bold-hover" style="font-size: 1.3em; text-align: left;">&lt; Indietro</p>
	</div>
	<% Posti posti = (Posti) session.getAttribute("listaPosti"); 
		for(Posto p : posti.getPostiSemplici()) { %>
			<div class="row justify-content-center py-3 m-0"> 
			  	<div class="rounded shadow col-8" style="background-color: rgb(255, 255, 255, 0.2);">  
			       	<div class="row">  
			        	<div class="px-0 py-2 col-md-8 col-sm-12 col-12"> 
			          		<p class="m-0 text-white"><%= p.getProvincia() %></p> 
			                <p class="m-0 text-white"><%= p.getRegione() %></p> 
			                <p class="m-0 text-white"><%= p.getData() %> <%= p.getOra() %></p> 
			            </div> 
			            <div class="px-0 py-2 col-md-4 col-sm-12 col-12"> 
			                <div class="row justify-content-center" style="padding-top: 22px;"> 
			               		<button class="btn" value="<%= p.getOra() %>">Prenota</button> 
			               	</div> 
			           </div> 
			       </div> 
			   </div> 
			</div>		
	<% } %>
</body>
</html>