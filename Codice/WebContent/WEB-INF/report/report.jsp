<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="/SolaroProject/CentroVaccinale/utilities/report/report.xsl"?>

<%@ page contentType="text/xml" %>
<%@ page import="model.beans.Vaccinato" %>
<%@ page import="model.beans.Vaccinati" %>

<vaccinati xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://localhost:8080/SolaroProject/CentroVaccinale/utilities/report/report.xsd">
	<%  Vaccinati vaccinati = (Vaccinati) request.getAttribute("vaccinati");
		for(Vaccinato v : vaccinati.getVaccinati()) { %>
			<vaccinato codiceFiscale="<%= v.getCodiceFiscale() %>" codicePrenotazione="<%= v.getCodicePrenotazione() %>">
				<provincia id="<%= v.getId_provincia() %>">
					<nome><%= v.getProvincia() %></nome>
				</provincia>
				<regione id="<%= v.getId_regione() %>">
					<nome><%= v.getRegione() %></nome>
				</regione>
				<data><%= v.getData() %></data>
				<ora><%= v.getOra() %></ora>
				<vaccino><%= v.getVaccino() %></vaccino>
				<tipo><%= v.getTipo() %></tipo>
			</vaccinato>
	<% } %>
</vaccinati>