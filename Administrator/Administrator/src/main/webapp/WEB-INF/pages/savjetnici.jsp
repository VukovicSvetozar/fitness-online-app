<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="beans.SavjetnikBean"%>
<%@page import="dto.Savjetnik"%>
<%@include file="/WEB-INF/pages/header.jsp"%>

<div class="container-fluid" style="margin-top: 30px">
	<div class="row">
		<%@include file="/WEB-INF/pages/info.jsp"%>
		<div class="col-sm-8 savjetnici-kontejner">
			<h2>Savjetnici</h2>
			<button type="button" class="btn btn-primary savjetnici-dugme-dodaj"
				data-bs-toggle="modal" data-bs-target="#savjetnik-modal-dodaj">Dodaj</button>
			<div class="table-responsive">
				<table id="savjetnik-tabela" class="table">
					<thead>
						<tr class="table-dark">
							<th scope="col">Id</th>
							<th scope="col">Korisničko ime</th>
							<th scope="col">Svojstva</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Savjetnik savjetnik : savjetnikBean.vratiSveSavjetnike()) {
						%>
						<tr data-id="<%=savjetnik.getId()%>"
							data-korisnickoime="<%=savjetnik.getKorisnickoIme()%>">
							<td><%=savjetnik.getId()%></td>
							<td><%=savjetnik.getKorisnickoIme()%></td>
							<td>
								<button type="button"
									class="btn btn-primary savjetnik-tabela-kolona-info"
									data-bs-toggle="modal" data-bs-target="#savjetnik-modal-info">
									<i class="bi bi-receipt"></i>
								</button>
								<button type="button"
									class="btn btn-success savjetnik-tabela-kolona-uredi"
									data-bs-toggle="modal" data-bs-target="#savjetnik-modal-uredi">
									<i class="bi bi-pen"></i>
								</button>
								<button type="button"
									class="btn btn-danger savjetnik-tabela-kolona-obrisi">
									<i class="bi bi-trash"></i>
								</button>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<div id="savjetnik-alert-brisanje-uspjesno"
					class="alert alert-success" style="display: none;">Savjetnik
					je uspješno obrisan.</div>
				<div id="savjetnik-alert-brisanje-neuspjesno"
					class="alert alert-danger" style="display: none;">Savjetnik
					nije uspješno obrisan.</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/footer.jsp"%>

<!-- Modal - dodaj savjetnika -->
<div class="modal fade" id="savjetnik-modal-dodaj" tabindex="-1"
	aria-labelledby="dodajSavjetnikaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="savjetnik-dodaj-label">Dodaj
					savjetnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="savjetnik-dodaj-form" novalidate>
					<div class="mb-3">
						<label for="imeSavjetnika" class="form-label">Ime: </label> <input
							type="text" class="form-control" id="savjetnik-ime"
							name="imeSavjetnika" autofocus required />
						<div class="invalid-feedback">Unesite ime.</div>
					</div>
					<div class="mb-3">
						<label for="prezimeSavjetnika" class="form-label">Prezime:
						</label> <input type="text" class="form-control" id="savjetnik-prezime"
							name="prezimeSavjetnika" autofocus required />
						<div class="invalid-feedback">Unesite prezime.</div>
					</div>
					<div class="mb-3">
						<label for="korisnickoIme" class="form-label">Korisničko
							ime: </label> <input type="text" class="form-control"
							id="savjetnik-korisnickoIme" name="korisnickoIme" autofocus
							required /> <span class="savjetnik-korisnickoIme-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="lozinkaSavjetnika" class="form-label">Lozinka:
						</label> <input type="text" class="form-control" id="savjetnik-lozinka"
							name="lozinkaSavjetnika" autofocus required />
						<div class="invalid-feedback">Unesite lozinku.</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="savjetnik-dodaj-button-zatvori" type="button"
					class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
				<button id="savjetnik-dodaj-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - info savjetnik -->
<div class="modal fade" id="savjetnik-modal-info" tabindex="-1"
	aria-labelledby="infoSavjetnikLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="savjetnik-label-info">Profil
					savjetnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div id="savjetnik-profil" class="modal-body">
				<div class="mb-3">
					<label class="form-label">Ime:</label> <span
						id="savjetnik-ime-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Prezime:</label> <span
						id="savjetnik-prezime-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Korisničko ime:</label> <span
						id="savjetnik-korisnickoIme-prikaz" class="fw-normal"></span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Zatvori</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - uredi savjetnika -->
<div class="modal fade" id="savjetnik-modal-uredi" tabindex="-1"
	aria-labelledby="urediSavjetnikaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="savjetnik-label-uredi">Uredi
					savjetnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="savjetnik-uredi-form" novalidate>
					<div class="mb-3">
						<label for="imeSavjetnika" class="form-label">Ime: </label> <input
							type="text" class="form-control" id="savjetnik-ime-uredi"
							name="imeSavjetnika" autofocus required />
						<div class="invalid-feedback">Unesite ime.</div>
					</div>
					<div class="mb-3">
						<label for="prezimeSavjetnika" class="form-label">Prezime:
						</label> <input type="text" class="form-control"
							id="savjetnik-prezime-uredi" name="prezimeSavjetnika" autofocus
							required />
						<div class="invalid-feedback">Unesite prezime.</div>
					</div>
					<div class="mb-3">
						<label for="korisnickoIme" class="form-label">Korisničko
							ime: </label> <input type="text" class="form-control"
							id="savjetnik-korisnickoIme-uredi" name="korisnickoIme" autofocus
							required /> <span class="savjetnik-korisnickoIme-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="lozinkaSavjetnika" class="form-label">Lozinka:
						</label> <input type="text" class="form-control"
							id="savjetnik-lozinka-uredi" name="lozinkaSavjetnika" autofocus />
						<div class="invalid-feedback">Unesite lozinku.</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="savjetnik-uredi-button-zatvori" type="button"
					class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
				<button id="savjetnik-uredi-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>
