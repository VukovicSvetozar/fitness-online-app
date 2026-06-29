
<%@include file="stranice/header.jsp"%>

<div class="container-fluid" style="margin-top: 30px">
	<div class="row">

		<%@include file="stranice/info.jsp"%>

		<div class="col-sm-8">
			<div class="container shadow min-vh-10 py-4">
				<div class="row">
					<div class="col-md-5 mx-auto">
						<div class="input-group">
							<input
								class="form-control border-end-0 border rounded-pill pretraga-polje"
								type="search" placeholder="pretraga" id="input-pretraga">
							<span class="input-group-append">
								<button
									class="btn btn-outline-secondary bg-white border-bottom-0 border rounded-pill ms-n5"
									type="button">
									<i class="bi bi-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
				<div class="table-responsive">
					<table id="table-poruke" class="table">
						<thead>
							<tr class="table-dark">
								<th scope="col">id</th>
								<th scope="col">korisnik</th>
								<th scope="col">vrijeme</th>
								<th scope="col">naslov</th>
								<th scope="col">tekst</th>
								<th scope="col">odgovor</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="odgovor-uspjesan" class="alert alert-success" style="display: none;">
					Poruka je uspje&#353;no poslata.
				</div>
				<div id="odgovor-neuspjesan" class="alert alert-danger" style="display: none;">
					Poruka nije uspje&#353;no poslata.
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal prozor za prikaz poruka. -->
<div class="modal" id="modal-poruka-citanje" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Pitanje</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Zatvori</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal prozor za slanje odgovora. -->
<div class="modal" id="modal-poruka-odgovor" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Odgovor</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="forma-odgovor" enctype="multipart/form-data">
					<div class="mb-3">
						<label for="input-email" class="form-label"> Email adresa
						</label> <input type="email" class="form-control" id="input-email"
							placeholder="ime@primjer.com">
					</div>
					<div class="mb-3">
						<label for="input-naslov" class="form-label"> Naslov
							poruke </label> <input type="text" class="form-control" id="input-naslov"
							placeholder="naslov">
					</div>
					<div class="mb-3">
						<label for="textarea-email" class="form-label"> Unesite
							poruku </label>
						<textarea class="form-control" id="textarea-email" rows="3"
							placeholder="tekst poruke...">
					</textarea>
					</div>
					<div class="mb-3">
						<label for="input-prilog" class="form-label"> Prilog </label> <input
							type="file" class="form-control" id="input-prilog">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Odustani</button>
				<button type="button" id="button-odgovor-posalji"
					class="btn btn-primary">Po&#353;alji</button>
			</div>
		</div>
	</div>
</div>

<%@include file="stranice/footer.jsp"%>
