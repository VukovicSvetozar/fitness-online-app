
<%@ page import="beans.NalogSavjetnik"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
  Date trenutnoVrijeme = new Date();
  SimpleDateFormat vrijemeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
  String formatiranoVrijeme = vrijemeFormat.format(trenutnoVrijeme);
%>
<div class="col-sm-3">
	<h2>Informacije:</h2>
	<ul class="nav nav-pills flex-column">
		<li class="nav-item"><span class="info-naslov"> Ime: </span> &nbsp; <%=((NalogSavjetnik) (session.getAttribute("nalogSavjetnikBean"))).getIme()%><br />
		</li>
		<li class="nav-item"><span class="info-naslov">Prezime:</span> &nbsp; <%=((NalogSavjetnik) (session.getAttribute("nalogSavjetnikBean"))).getPrezime()%><br />
		</li>
		<li class="nav-item"><span class="info-naslov">Vrijeme:</span> &nbsp;<%=formatiranoVrijeme%><br />
		</li>

	</ul>
	<hr class="d-sm-none">
</div>
