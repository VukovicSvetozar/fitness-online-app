<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="nalogSavjetnikBean" type="beans.NalogSavjetnik"
	scope="session" />

<%
if (!(nalogSavjetnikBean.isPrijavljen()))
	response.sendRedirect("index.jsp");
%>

<!DOCTYPE html>

<html>

<head>

<title>Savjetnik</title>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/savjetnik.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="js/savjetnik.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
	integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
	integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
	crossorigin="anonymous"></script>

</head>

<body>

	<div class="jumbotron text-center" style="margin-bottom: 0">
		<h1 id="zaglavlje-naslov">Savjetnik</h1>
	</div>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark mx-3">
		<div class="container-fluid mx-3">
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav" id="zaglavlje-meni">
					<li class="nav-item"><a class="nav-link" href="poruke.jsp">Poruke</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="#" data-bs-toggle="modal" data-bs-target="#odjavaModal">Odjava</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Modal prozor za odjavu. -->
	<div class="modal" id="odjavaModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Potvrda</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Da li ste sigurni da želite da se odjavite iz aplikacija?</p>
				</div>
				<div class="modal-footer">
					<button id="button-odjava" type="button" class="btn btn-danger">Odjava</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Odustani</button>
				</div>
			</div>
		</div>
	</div>