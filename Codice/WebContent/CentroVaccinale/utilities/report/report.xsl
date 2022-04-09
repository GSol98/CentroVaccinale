<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<style>		
					table {
						width: 100%;
					}
					table, th, td {
						border-collapse: collapse;
						border: 1px solid;
						padding: 5px;
					}
					th {
						text-align: center;
					}
				</style>
			</head>
			
			<body>
				<h1 style="text-align: center">Report Vaccinazioni</h1>
				<table>
						<tr>
							<th colspan="8">Informazioni sui vaccinati</th>
						</tr>
						<tr>
							<th>Codice Prenotazione</th>
							<th>Codice Fiscale</th>
							<th>Provincia</th>
							<th>Regione</th>
							<th>Data</th>
							<th>Ora</th>
							<th>Vaccino</th>
							<th>Tipo Dose</th>
						</tr>
					<xsl:for-each select="vaccinati/vaccinato">
						<tr>
							<td><xsl:value-of select="@codicePrenotazione"></xsl:value-of></td>
							<td><xsl:value-of select="@codiceFiscale"></xsl:value-of></td>
							<td><xsl:value-of select="provincia"></xsl:value-of></td>
							<td><xsl:value-of select="regione"></xsl:value-of></td>
							<td><xsl:value-of select="data"></xsl:value-of></td>
							<td><xsl:value-of select="ora"></xsl:value-of></td>
							<td><xsl:value-of select="vaccino"></xsl:value-of></td>
							<td><xsl:value-of select="tipo"></xsl:value-of></td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>