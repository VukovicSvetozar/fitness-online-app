<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/header.jsp"%>

<div class="container-fluid" style="margin-top: 30px">
	<div class="row">
		<%@include file="/WEB-INF/pages/info.jsp"%>
		<div class="col-sm-8 kategorija-kontejner">
			<h2>Kategorije</h2>
			<button type="button" class="btn btn-primary kategorija-dugme-dodaj" data-bs-toggle="modal"
				data-bs-target="#kategorija-modal-dodaj">Dodaj</button>
			<div class="table-responsive">
				<table id="kategorija-tabela" class="table w-100">
					<thead>
						<tr class="table-dark">
							<th scope="col">Id</th>
							<th scope="col">Naziv</th>
							<th scope="col">Svojstva</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Kategorija kategorija : kategorijaBean.vratiSveKategorije()) {
						%>
						<tr data-id="<%=kategorija.getId()%>"
							data-naziv="<%=kategorija.getNaziv()%>">
							<td><%=kategorija.getId()%></td>
							<td><%=kategorija.getNaziv()%></td>
							<td>
								<button type="button"
									class="btn btn-primary kategorija-tabela-kolona-uredi"
									data-bs-toggle="modal" data-bs-target="#kategorija-modal-uredi">
									<i class="bi bi-receipt"></i>
								</button>
								<button type="button"
									class="btn btn-success specifikacija-tabela-kolona"
									data-bs-toggle="modal" data-bs-target="#specifikacija-modal">
									<i class="bi bi-pen"></i>
								</button>
								<button type="button"
									class="btn btn-danger kategorija-tabela-kolona-obrisi">
									<i class="bi bi-trash"></i>
								</button>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<div id="kategorija-alert-brisanje-uspjesno"
					class="alert alert-success" style="display: none;">Kategorija
					je uspješno obrisana.</div>
				<div id="kategorija-alert-brisanje-neuspjesno"
					class="alert alert-danger" style="display: none;">Kategorija
					nije uspješno obrisana.</div>
			</div>
		</div>
	</div>
</div>

<%@include file="/WEB-INF/pages/footer.jsp"%>

<!-- Modal - dodaj kategoriju -->
<div class="modal fade" id="kategorija-modal-dodaj" tabindex="-1"
	aria-labelledby="dodajKategorijuLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="kategorija-dodaj-label">Dodaj
					kategoriju</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close" id="kategorija-dodaj-button-zatvori-x"></button>
			</div>
			<div class="modal-body">
				<form id="kategorija-dodaj-form" novalidate>
					<div class="mb-3">
						<label for="nazivKategorije" class="form-label">Naziv: </label> <input
							type="text" class="form-control" id="kategorija-naziv"
							name="nazivKategorije" autofocus required /> <span
							class="kategorija-naziv-dostupan"></span>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="kategorija-dodaj-button-zatvori" type="button"
					class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
				<button id="kategorija-dodaj-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - uredi kategoriju -->
<div class="modal fade" id="kategorija-modal-uredi" tabindex="-1"
	aria-labelledby="urediKategorijuLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="kategorija-label-uredi">Uredi
					kategoriju</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="kategorija-uredi-form">
					<div class="mb-3">
						<label for="nazivKategorije" class="form-label">Naziv: </label> <input
							type="text" class="form-control" id="kategorija-naziv-uredi"
							name="nazivKategorije" /> <span
							class="kategorija-naziv-dostupan"></span>
						<div class="invalid-feedback">Unesite naziv.</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="kategorija-uredi-button-zatvori" type="button"
					class="btn btn-secondary" data-bs-dismiss="modal">Zatvori</button>
				<button id="kategorija-uredi-button-sacuvaj" type="button"
					class="btn btn-primary">Sačuvaj</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal - uredi specifikaciju -->
<div class="modal fade" id="specifikacija-modal" tabindex="-1"
	aria-labelledby="urediSpecifikacijuLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="specifikacija-label">Specifikacije</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="specifikacija-zaglavlje">
					<input type="text" id="specifikacija-naziv"
						placeholder="Naziv specifikacije..."> <span
						class="specifikacija-button-dodaj">Dodaj</span>
				</div>
				<div id="specifikacija-alert-brisanje-uspjesno"
					class="alert alert-success" style="display: none;">Specifikacija
					je uspješno obrisana.</div>
				<div id="specifikacija-alert-dodavanje-uspjesno"
					class="alert alert-success" style="display: none;">Specifikacija
					je uspješno dodata.</div>
				<div id="specifikacija-alert-dodavanje-neuspjesno"
					class="alert alert-danger" style="display: none;">Naziv
					specifikacije je zauzet.</div>
				<ul id="specifikacija-lista" class="list-group">
				</ul>
			</div>
			<div class="modal-footer">
				<button id="specifikacija-button-ok" type="button"
					class="btn btn-primary px-4" data-bs-dismiss="modal">OK</button>
			</div>
		</div>
	</div>
</div>
