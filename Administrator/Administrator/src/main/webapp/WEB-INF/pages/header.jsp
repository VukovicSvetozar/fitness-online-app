<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dto.Kategorija"%>
<%@ page import="dto.Specifikacija"%>
<%@ page import="dto.Korisnik"%>
<jsp:useBean id="nalogBean" type="beans.NalogAdministratorBean"
	scope="session" />
<jsp:useBean id="kategorijaBean" type="beans.KategorijaBean"
	scope="session" />
<jsp:useBean id="specifikacijaBean" type="beans.SpecifikacijaBean"
	scope="session" />
<jsp:useBean id="korisnikBean" type="beans.KorisnikBean" scope="session" />
<jsp:useBean id="savjetnikBean" type="beans.SavjetnikBean" scope="session" />

<!DOCTYPE html>

<html lang="en">

<head>

<title>Administrator</title>

<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/administrator.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
	integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
	integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
	crossorigin="anonymous"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script type="text/javascript" charset="UTF-8" src="js/administrator.js"></script>

</head>

<body>

	<div class="jumbotron text-center" style="margin-bottom: 0">
		<h1 id="naslov">Administratorska aplikacija</h1>
	</div>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark mx-3">
		<div class="container-fluid mx-3">
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link"
						href="Administrator?akcija=kategorije">Kategorije</a></li>
					<li class="nav-item"><a class="nav-link"
						href="Administrator?akcija=savjetnici">Savjetnici</a></li>
					<li class="nav-item"><a class="nav-link"
						href="Administrator?akcija=korisnici">Korisnici</a></li>
					<li class="nav-item"><a class="nav-link"
						href="Administrator?akcija=statistika">Statiskika</a></li>
					<li class="nav-item"><a id="odjava-link" class="nav-link"
						href="Administrator?akcija=odjava">Odjava</a></li>
				</ul>
			</div>
		</div>
	</nav>