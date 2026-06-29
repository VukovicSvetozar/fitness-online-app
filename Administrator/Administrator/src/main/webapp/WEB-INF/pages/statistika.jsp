<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/header.jsp"%>

<div class="container-fluid" style="margin-top: 30px">

	<div class="statistika-kontejner">

		<div class="statistika-red-1">

			<%@include file="/WEB-INF/pages/info.jsp"%>

			<div class="statistika-filtriranje-kontejner">

				<div class="statistika-filtriranje-naslov">
					<h2>Filtriraj</h2>
				</div>

				<div class="statistika-filtriranje-pretraga">
					<div class="statistika-filtriranje-datum">
						<div>
							<label for="datumOd">Datum od:</label> <input
								type="datetime-local" id="datumOd" name="datumOd">
						</div>
						<div>
							<label for="datumDo">Datum do:</label> <input
								type="datetime-local" id="datumDo" name="datumDo">
						</div>
						<div>
							<div id="alert-datum-danger" class="alert alert-danger"
								style="display: none;" role="alert">Odabrana vremena nisu
								pravilno usklađena.</div>
							<div id="alert-datum-success" class="alert alert-success"
								style="display: none;" role="alert">Odabrana vremena su
								pravilno usklađena.</div>
						</div>
					</div>

					<div class="statistika-tipovi">
						<div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckInfo"> <label class="form-check-label"
									for="flexSwitchCheckDefault">INFO</label>
							</div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckTrace"> <label class="form-check-label"
									for="flexSwitchCheckDefault">TRACE</label>
							</div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckDebug"> <label class="form-check-label"
									for="flexSwitchCheckDefault">DEBUG</label>
							</div>
						</div>
						<div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckWarn"> <label class="form-check-label"
									for="flexSwitchCheckDefault">WARN</label>
							</div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckError"> <label class="form-check-label"
									for="flexSwitchCheckDefault">ERROR</label>
							</div>
							<div class="form-check form-switch">
								<input class="form-check-input" type="checkbox"
									id="switchCheckFatal"> <label class="form-check-label"
									for="flexSwitchCheckDefault">FATAL</label>
							</div>
						</div>
					</div>
					<div class="statistika-filtriranje-dugme">
						<button id="trazi-statistiku-button" type="button"
							class="btn btn-primary">Pošalji</button>
					</div>
				</div>


			</div>

		</div>

		<div class="statistika-lista-logovi">
			<ul id="listaLogova" class="list-group">
			</ul>
		</div>

	</div>
</div>

<%@include file="/WEB-INF/pages/footer.jsp"%>
