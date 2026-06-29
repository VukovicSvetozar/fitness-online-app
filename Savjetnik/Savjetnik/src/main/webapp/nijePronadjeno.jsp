<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="nalogSavjetnikBean" type="beans.NalogSavjetnik"
	scope="session" />

<!DOCTYPE html>

<html>

<head>

<title>Greška</title>

<meta http-equiv="content-type"
	content="text/html;charset=utf-8 width=device-width, initial-scale=1" />

<link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" />

</head>

<body>

	<div class="d-flex align-items-center justify-content-center vh-100">
		<div class="text-center">
			<h1 class="display-1 fw-bold">404</h1>
			<p class="fs-3">
				<span class="text-danger">Opps!</span> Stranica nije pronadjena.
			</p>
			<p class="lead">Stranica koju tražite ne postoji.</p>
			<%
			if (nalogSavjetnikBean.isPrijavljen()) {
			%>
			<a href="poruke.jsp" class="btn btn-primary">Početna stranica</a>
			<%
			} else {
			%>
			<a href="index.jsp" class="btn btn-primary" id="pocetnaStranica">Početna
				stranica</a>
			<script>
				document.getElementById("pocetnaStranica").addEventListener(
						"click", function(e) {
							e.preventDefault();
							window.location.href = "index.jsp";
						});
			</script>
			<%
			}
			%>
		</div>
	</div>

</body>

</html>
