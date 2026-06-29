<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="dto.Korisnik"%>
<%@include file="/WEB-INF/pages/header.jsp"%>

<div class="container-fluid" style="margin-top: 30px">
	<div class="row">
		<%@include file="/WEB-INF/pages/info.jsp"%>
		<div class="col-sm-8 korisnici-kontejner">
			<h2>Korisnici</h2>
			<button type="button" class="btn btn-primary korisnici-dugme-dodaj" data-bs-toggle="modal"
				data-bs-target="#korisnik-modal-dodaj">Dodaj</button>
			<div class="table-responsive">
				<table id="korisnik-tabela" class="table">
					<thead>
						<tr class="table-dark">
							<th scope="col">Id</th>
							<th scope="col">Korisničko ime</th>
							<th scope="col">Svojstva</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Korisnik korisnik : korisnikBean.vratiSveKorisnike()) {
						%>
						<tr data-id="<%=korisnik.getId()%>"
							data-korisnickoime="<%=korisnik.getKorisnickoIme()%>"
							data-email="<%=korisnik.getEmail()%>">
							<td><%=korisnik.getId()%></td>
							<td><%=korisnik.getKorisnickoIme()%></td>
							<td>
								<button type="button"
									class="btn btn-primary korisnik-tabela-kolona-info"
									data-bs-toggle="modal" data-bs-target="#korisnik-modal-info">
									<i class="bi bi-receipt"></i>
								</button>
								<button type="button"
									class="btn btn-success korisnik-tabela-kolona-uredi"
									data-bs-toggle="modal" data-bs-target="#korisnik-modal-uredi">
									<i class="bi bi-pen"></i>
								</button>
								<button type="button"
									class="btn btn-danger korisnik-tabela-kolona-obrisi">
									<i class="bi bi-trash"></i>
								</button>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<div id="korisnik-alert-brisanje-uspjesno"
					class="alert alert-success" style="display: none;">Korisnik
					je uspješno obrisan.</div>
				<div id="korisnik-alert-brisanje-neuspjesno"
					class="alert alert-danger" style="display: none;">Korisnik
					nije uspješno obrisan.</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/footer.jsp"%>

<!-- Modal - dodaj korisnika -->
<div class="modal fade" id="korisnik-modal-dodaj" tabindex="-1"
	aria-labelledby="dodajKorisnikaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="korisnik-dodaj-label">Dodaj
					korisnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="korisnik-dodaj-form" novalidate>
					<div class="mb-3">
						<label for="imeKorisnika" class="form-label">Ime: </label> <input
							type="text" class="form-control" id="korisnik-ime"
							name="imeKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite ime.</div>
					</div>
					<div class="mb-3">
						<label for="prezimeKorisnika" class="form-label">Prezime:
						</label> <input type="text" class="form-control" id="korisnik-prezime"
							name="prezimeKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite prezime.</div>
					</div>
					<div class="mb-3">
						<label for="gradKorisnika" class="form-label">Grad: </label> <input
							type="text" class="form-control" id="korisnik-grad"
							name="gradKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite grad.</div>
					</div>
					<div class="mb-3">
						<label for="emailKorisnika" class="form-label">Email: </label> <input
							type="email" class="form-control" id="korisnik-email"
							name="emailKorisnika" autofocus required /> <span
							class="korisnik-email-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="brojTelefonaKorisnika" class="form-label">Broj
							telefona: </label> <input type="tel" class="form-control"
							id="korisnik-brojTelefona" name="brojTelefonaKorisnika" autofocus
							pattern="[+]{0,1}[0-9]{0,10}"
							title="Unesite ispravan broj telefona." />
						<div class="invalid-feedback">Unesite ispravan broj
							telefona.</div>
					</div>
					<div class="mb-3">
						<label for="korisnickoIme" class="form-label">Korisničko
							ime: </label> <input type="text" class="form-control"
							id="korisnik-korisnickoIme" name="korisnickoIme" autofocus
							required /> <span class="korisnik-korisnickoIme-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="lozinkaKorisnika" class="form-label">Lozinka:
						</label> <input type="text" class="form-control" id="korisnik-lozinka"
							name="lozinkaKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite lozinku.</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="korisnik-dodaj-button-zatvori" type="button"
					class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
				<button id="korisnik-dodaj-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - info korisnik -->
<div class="modal fade" id="korisnik-modal-info" tabindex="-1"
	aria-labelledby="infoKorisnikLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="korisnik-label-info">Profil
					korisnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div id="korisnik-profil" class="modal-body">
				<div class="mb-3">
					<label class="form-label">Ime:</label> <span
						id="korisnik-ime-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Prezime:</label> <span
						id="korisnik-prezime-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Grad:</label> <span
						id="korisnik-grad-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Email:</label> <span
						id="korisnik-email-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Broj telefona:</label> <span
						id="korisnik-brojTelefona-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Korisničko ime:</label> <span
						id="korisnik-korisnickoIme-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Datum pristupa:</label> <span
						id="korisnik-datumPristupa-prikaz" class="fw-normal"></span>
				</div>
				<div class="mb-3">
					<label class="form-label">Status:</label> <span
						id="korisnik-status-prikaz" class="fw-normal"></span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Zatvori</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - uredi korisnika -->
<div class="modal fade" id="korisnik-modal-uredi" tabindex="-1"
	aria-labelledby="urediKorisnikaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="korisnik-label-uredi">Uredi
					korisnika</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="korisnik-uredi-form" novalidate>
					<div class="mb-3">
						<label for="imeKorisnika" class="form-label">Ime: </label> <input
							type="text" class="form-control" id="korisnik-ime-uredi"
							name="imeKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite ime.</div>
					</div>
					<div class="mb-3">
						<label for="prezimeKorisnika" class="form-label">Prezime:
						</label> <input type="text" class="form-control"
							id="korisnik-prezime-uredi" name="prezimeKorisnika" autofocus
							required />
						<div class="invalid-feedback">Unesite prezime.</div>
					</div>
					<div class="mb-3">
						<label for="gradKorisnika" class="form-label">Grad: </label> <input
							type="text" class="form-control" id="korisnik-grad-uredi"
							name="gradKorisnika" autofocus required />
						<div class="invalid-feedback">Unesite grad.</div>
					</div>
					<div class="mb-3">
						<label for="emailKorisnika" class="form-label">Email: </label> <input
							type="email" class="form-control" id="korisnik-email-uredi"
							name="emailKorisnika" autofocus required /> <span
							class="korisnik-email-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="brojTelefonaKorisnika" class="form-label">Broj
							telefona: </label> <input type="tel" class="form-control"
							id="korisnik-brojTelefona-uredi" name="brojTelefonaKorisnika"
							autofocus pattern="[+]{0,1}[0-9]{0,10}"
							title="Unesite ispravan broj telefona." />
						<div class="invalid-feedback">Unesite ispravan broj
							telefona.</div>
					</div>
					<div class="mb-3">
						<label for="korisnickoIme" class="form-label">Korisničko
							ime: </label> <input type="text" class="form-control"
							id="korisnik-korisnickoIme-uredi" name="korisnickoIme" autofocus
							required /> <span class="korisnik-korisnickoIme-dostupan"></span>
					</div>
					<div class="mb-3">
						<label for="lozinkaKorisnika" class="form-label">Lozinka:
						</label> <input type="text" class="form-control"
							id="korisnik-lozinka-uredi" name="lozinkaKorisnika" autofocus />
						<div class="invalid-feedback">Unesite lozinku.</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="korisnik-uredi-button-zatvori" type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Zatvori</button>
				<button id="korisnik-uredi-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>
