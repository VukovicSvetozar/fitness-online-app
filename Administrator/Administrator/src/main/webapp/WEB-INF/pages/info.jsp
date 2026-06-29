
<%@ page import="beans.NalogAdministratorBean"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Date trenutnoVrijeme = new Date();
	SimpleDateFormat vrijemeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	String formatiranoVrijeme = vrijemeFormat.format(trenutnoVrijeme);
%>

<div class="col-sm-3 info-kontejner">
	<h2>Informacije:</h2>

	<ul class="nav nav-pills flex-column info-lista">
		<li class="nav-item">
			<b>Ime: &nbsp;</b> 
			<i><%=((NalogAdministratorBean) (session.getAttribute("nalogBean"))).getNalog().getIme()%></i>
			<br />
		</li>
		<li class="nav-item">
			<b>Prezime: &nbsp;</b> 
			<i><%=((NalogAdministratorBean) (session.getAttribute("nalogBean"))).getNalog().getPrezime()%></i>
			<br />
		</li>
		<li class="nav-item">
			<b>Vrijeme: &nbsp;</b>
			<i><%=formatiranoVrijeme%></i>
			<br />
		</li>
	</ul>
	<hr class="d-sm-none info-linija">
</div>
