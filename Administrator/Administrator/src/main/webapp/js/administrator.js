
// Dodavanje nove kategorije.
$(document).ready(function() {
	$('#kategorija-dodaj-button-sacuvaj').on('click', function() {
		var nazivKategorije = document.getElementById("kategorija-naziv").value;
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=dodajKategoriju',
			data: { nazivKategorije: nazivKategorije },
			success: function(data) {
				if (data.dodato) {
					$('#kategorija-naziv').val('');
					location.search = location.search.replace('akcija=prijava', 'akcija=kategorije');
					var modal = document.getElementById("kategorija-modal-dodaj");
					var bootstrapModal = bootstrap.Modal.getInstance(modal);
					bootstrapModal.hide();
				} else {
					alert("Uneseni naziv kategorije već postoji.");
					$('#kategorija-naziv').val('');
					$('.kategorija-naziv-dostupan').empty();
				}
			}
		});
	});
});

// Provjera da li je unesen postojeći naziv za kategoriju pri dodavanju kategorije.
$(document).ready(function() {
	$('#kategorija-naziv').on('keyup', function() {
		var nazivKategorije = document.getElementById("kategorija-naziv").value;
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=provjeraNazivaKategorije',
			data: { nazivKategorije: nazivKategorije },
			success: function(data) {
				if (data.dostupno) {
					$('.kategorija-naziv-dostupan').text('Naziv kategorije je dostupan.');
				} else {
					$('.kategorija-naziv-dostupan').text('Naziv kategorije je zauzet.');
				}
			}
		});
	});
});

// Odustajanje od dodavanja nove kategorije.
$(document).ready(function() {
	$('#kategorija-dodaj-button-zatvori').on('click', function() {
		$('#kategorija-naziv').val('');
		$('.kategorija-naziv-dostupan').empty();

	});
});

// Odustajanje od dodavanja nove kategorije.
$(document).ready(function() {
	$('#kategorija-dodaj-button-zatvori-x').on('click', function() {
		$('#kategorija-naziv').val('');
		$('.kategorija-naziv-dostupan').empty();

	});
});

// Uređivanje kategorije.
$(document).ready(function() {
	$('.kategorija-tabela-kolona-uredi').click(function() {
		var odabraniRed = $(this).closest('tr');
		var idKategorija = odabraniRed.data('id');
		var originalNaziv = odabraniRed.data('naziv');
		$('#kategorija-naziv-uredi').val(originalNaziv);

		$('#kategorija-naziv-uredi').on('keyup', function() {
			var noviNaziv = $(this).val();
			if (noviNaziv === originalNaziv) {
				$('.kategorija-naziv-dostupan').text('Naziv je nepromjenjen.');
			} else {
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=provjeraNazivaKategorije',
					data: { nazivKategorije: noviNaziv },
					success: function(data) {
						if (data.dostupno) {
							$('.kategorija-naziv-dostupan').text('Naziv je dostupan.');
						} else {
							$('.kategorija-naziv-dostupan').text('Naziv je zauzet.');
						}
					}
				});
			}
		});

		$('#kategorija-uredi-button-sacuvaj').on('click', function(event) {
			event.preventDefault();
			var form = document.getElementById("kategorija-uredi-form");
			var inputs = form.querySelectorAll('input');
			var isValidForm = true;
			inputs.forEach(function(input) {
				var errorMessage = $(input).next('.invalid-feedback');
				if (!input.checkValidity()) {
					errorMessage.show();
					$(input).addClass('is-invalid');
					isValidForm = false;
				} else {
					errorMessage.hide();
					$(input).removeClass('is-invalid');
				}
			});

			if (isValidForm) {
				var nazivKategorije = document.getElementById("kategorija-naziv-uredi").value;
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=urediKategoriju',
					data: {
						idKategorija: idKategorija, nazivKategorije: nazivKategorije
					},
					success: function(data) {
						if (data.promjenjeno) {
							$('#kategorija-naziv-uredi').val('');
							$(form).find('.invalid-feedback').hide();
							location.search = location.search.replace('akcija=prijava', 'akcija=kategorije');
							var modal = document.getElementById("kategorija-modal-uredi");
							var bootstrapModal = bootstrap.Modal.getInstance(modal);
							bootstrapModal.hide();
						} else {
							alert("Nije dozvoljeno ažuriranje kategorije navedenom vrijednošću.");
							$('korisnik-ime-uredi').val('');
						}
					}
				});
			}
		});

		$('#kategorija-uredi-button-zatvori').on('click', function() {
			$('#kategorija-naziv-uredi').val('');
			$('.kategorija-naziv-dostupan').empty();
			$('#kategorija-naziv-uredi').off('keyup');
		});
	});
});

// Brisanje kategorije.
$(document).ready(function() {
	$('.kategorija-tabela-kolona-obrisi').on('click', function() {
		if (confirm("Da li ste sigurni da želite obrisati odabranu kategoriju?")) {
			var red = $(this).closest('tr');
			var idKategorije = red.find('td:eq(0)').text();
			$.ajax({
				type: 'GET',
				url: 'Administrator?akcija=obrisiKategoriju',
				data: { idKategorije: idKategorije },
				success: function(data) {
					if (data.obrisano) {
						$('#kategorija-alert-brisanje-uspjesno').fadeIn();
						setTimeout(function() {
							$('#kategorija-alert-brisanje-uspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=kategorije');
						}, 2000);
					} else {
						$('#kategorija-alert-brisanje-neuspjesno').fadeIn();
						setTimeout(function() {
							$('#kategorija-alert-brisanje-neuspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=kategorije');
						}, 2000);
					}
				}
			});
		} else {
			return;
		}
	});
});

// Prikaz, dodavanje i brisanje specifikacija.
$(document).ready(function() {
	$('.specifikacija-tabela-kolona').on('click', function() {
		var odabraniRed = $(this).closest('tr');
		var idKategorija = odabraniRed.data('id');
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=specifikacije',
			data: { idKategorija: idKategorija },
			success: function(data) {
				var listaSpecifikacija = document.getElementById("specifikacija-lista");
				listaSpecifikacija.innerHTML = "";
				data.forEach(function(specifikacija) {
					var liElement = document.createElement("li");
					liElement.textContent = specifikacija.naziv;
					liElement.dataset.id = specifikacija.id;
					liElement.dataset.naziv = specifikacija.naziv;
					liElement.dataset.idKategorija = specifikacija.idKategorija;
					listaSpecifikacija.appendChild(liElement);

					var $span = $("<span></span>").addClass("specifikacija-zatvori").html("&times;");
					$(liElement).append($span);
				});

				$(".specifikacija-zatvori").click(function() {
					if (confirm("Da li sigurni da želite obrisati odabranu specifikaciju?")) {
						var idSpecifikacija = $(this).parent().data("id");
						$(this).parent().hide();
						$.ajax({
							type: 'GET',
							url: 'Administrator?akcija=obrisiSpecifikaciju',
							data: { idSpecifikacija: idSpecifikacija },
							success: function(odgovor) {
								if (odgovor.obrisanaSpecifikacija) {
									$('#specifikacija-alert-brisanje-uspjesno').fadeIn();
									setTimeout(function() {
										$('#specifikacija-alert-brisanje-uspjesno').fadeOut();
									}, 2000);
								}
							}
						});
					}
				});

				$(".specifikacija-button-dodaj").click(function() {
					var unesenaSpecifikacija = $("#specifikacija-naziv").val();
					if (unesenaSpecifikacija === '') {
						alert("Morate unijeti naziv specifikacije!");
					} else {
						$.ajax({
							type: 'GET',
							url: 'Administrator?akcija=dodajSpecifikaciju',
							data: { nazivSpecifikacije: unesenaSpecifikacija, idKategorija: idKategorija },
							success: function(odgovor) {
								if (odgovor.dodato) {
									var liElement = $("<li></li>").text(unesenaSpecifikacija);
									$("#specifikacija-lista").append(liElement);
									$("#specifikacija-naziv").val("");
									var span = $("<span></span>").addClass("specifikacija-zatvori").text("\u00D7");
									liElement.append(span);
									$(".specifikacija-zatvori").click(function() {
										$(this).parent().hide();
									});
									$('#specifikacija-alert-dodavanje-uspjesno').fadeIn();
									setTimeout(function() {
										$('#specifikacija-alert-dodavanje-uspjesno').fadeOut();
									}, 2000);
								} else {
									$('#specifikacija-alert-dodavanje-neuspjesno').fadeIn();
									setTimeout(function() {
										$('#specifikacija-alert-dodavanje-neuspjesno').fadeOut();
									}, 2000);
								}
							}
						});
					}
				});
			}
		});
	});
});

// Resetovanje modala specifikacije.
$(document).ready(function() {
	$("#specifikacija-button-ok").click(function() {
		$("#specifikacija-lista").empty();
		$("#specifikacija-naziv").val("");
		$('#specifikacija-modal').modal('hide');
		$(".specifikacija-button-dodaj").off("click");
	});
});

// Provjera usklađenosti datuma kod statistike.
$(document).ready(function() {
	$('#datumOd, #datumDo').change(function() {
		var datumOd = document.getElementById("datumOd").value;
		var datumDo = document.getElementById("datumDo").value;
		if (datumOd.length != 0 && datumDo != 0) {
			var ranije = new Date(datumOd);
			var novije = new Date(datumDo);
			if (ranije < novije) {
				var danger = document.getElementById("alert-datum-danger");
				danger.style.display = "none";
				var success = document.getElementById("alert-datum-success");
				success.style.display = "block";
			} else {
				var danger = document.getElementById("alert-datum-danger");
				danger.style.display = "block";
				var success = document.getElementById("alert-datum-success");
				success.style.display = "none";
			}
		}
		else {
			var danger = document.getElementById("alert-datum-danger");
			danger.style.display = "none";
			var success = document.getElementById("alert-datum-success");
			success.style.display = "none";
		}
	});
});

// Prihvatanje log poruka.
$(document).ready(function() {
	$('#trazi-statistiku-button').on('click', function() {
		var parametri = {};
		var startTime = document.getElementById("datumOd").value;
		var endTime = document.getElementById("datumDo").value;
		if (startTime.length != 0 && endTime != 0) {
			var ranije = new Date(startTime);
			var novije = new Date(endTime);
			if (ranije < novije) {
				parametri.startTime = startTime;
				parametri.endTime = endTime;
			} else {
				alert("Vremena nisu uskladjena.");
				return;
			}
		} else {
			alert("Vremena nema.");
			return;
		}
		var logLevelInfo = document.getElementById("switchCheckInfo");
		if (logLevelInfo != null && logLevelInfo.checked) {
			parametri.logLevelInfo = 'info';
		}
		var logLevelTrace = document.getElementById("switchCheckTrace");
		if (logLevelTrace != null && logLevelTrace.checked) {
			parametri.logLevelTrace = 'trace';
		}
		var logLevelDebug = document.getElementById("switchCheckDebug");
		if (logLevelDebug != null && logLevelDebug.checked) {
			parametri.logLevelDebug = 'debug';
		}
		var logLevelWarn = document.getElementById("switchCheckWarn");
		if (logLevelWarn != null && logLevelWarn.checked) {
			parametri.logLevelWarn = 'warn';
		}
		var logLevelError = document.getElementById("switchCheckError");
		if (logLevelError != null && logLevelError.checked) {
			parametri.logLevelError = 'error';
		}
		var logLevelFatal = document.getElementById("switchCheckFatal");
		if (logLevelFatal != null && logLevelFatal.checked) {
			parametri.logLevelFatal = 'fatal';
		}

		var queryString = Object.keys(parametri).map(function(kljuc) {
			return encodeURIComponent(kljuc) + "=" + encodeURIComponent(parametri[kljuc]);
		}).join("&");
		var url = 'http://localhost:8888/api/logovi' + (queryString ? "?" + queryString : "");

		$.ajax({
			type: 'GET',
			url: url,
			data: {},
			success: function(data) {
				var listaLogova = document.getElementById("listaLogova");
				listaLogova.innerHTML = "";
				var stavke = data.split("\n");
				stavke.forEach(function(stavka) {
					if (stavka != "") {
						var elementiStavke = stavka.split("#");
						var logLevel = elementiStavke[1].trim().toLowerCase();
						var li = document.createElement("li");
						li.textContent = stavka;
						li.classList.add("list-group-item");
						if (logLevel === "info") {
							li.classList.add("list-group-item-success");
						} else if (logLevel === "trace") {
							li.classList.add("list-group-item-info");
						} else if (logLevel === "debug") {
							li.classList.add("list-group-item-secondary");
						} else if (logLevel === "warn") {
							li.classList.add("list-group-item-warning");
						} else if (logLevel === "error") {
							li.classList.add("list-group-item-danger");
						} else if (logLevel === "fatal") {
							li.classList.add("list-group-item-dark");
						}
						listaLogova.appendChild(li);
					}
				});
			}
		});
	});
});

// Dodavanje novog korisnika.
$(document).ready(function() {
	$('#korisnik-dodaj-button-sacuvaj').on('click', function(event) {
		event.preventDefault();
		var form = document.getElementById("korisnik-dodaj-form");
		var inputs = form.querySelectorAll('input');
		var isValidForm = true;
		inputs.forEach(function(input) {
			var errorMessage = $(input).next('.invalid-feedback');
			if (!input.checkValidity()) {
				errorMessage.show();
				$(input).addClass('is-invalid');
				isValidForm = false;
			} else {
				errorMessage.hide();
				$(input).removeClass('is-invalid');
			}
		});
		if (isValidForm) {
			var imeKorisnika = document.getElementById("korisnik-ime").value;
			var prezimeKorisnika = document.getElementById("korisnik-prezime").value;
			var gradKorisnika = document.getElementById("korisnik-grad").value;
			var emailKorisnika = document.getElementById("korisnik-email").value;
			var brojTelefonaKorisnika = document.getElementById("korisnik-brojTelefona").value;
			var korisnickoIme = document.getElementById("korisnik-korisnickoIme").value;
			var lozinkaKorisnika = document.getElementById("korisnik-lozinka").value;
			$.ajax({
				type: 'GET',
				url: 'Administrator?akcija=dodajKorisnika',
				data: {
					imeKorisnika: imeKorisnika, prezimeKorisnika: prezimeKorisnika, gradKorisnika: gradKorisnika,
					emailKorisnika: emailKorisnika, brojTelefonaKorisnika: brojTelefonaKorisnika,
					korisnickoIme: korisnickoIme, lozinkaKorisnika: lozinkaKorisnika
				},
				success: function(data) {
					if (data.dodato) {
						$('korisnik-ime').val('');
						$('korisnik-prezime').val('');
						$('korisnik-grad').val('');
						$('korisnik-email').val('');
						$('korisnik-brojTelefona').val('');
						$('korisnik-korisnickoIme').val('');
						$('korisnik-lozinka').val('');

						$(form).find('.invalid-feedback').hide();
						location.search = location.search.replace('akcija=prijava', 'akcija=korisnici');
						var modal = document.getElementById("korisnik-modal-dodaj");
						var bootstrapModal = bootstrap.Modal.getInstance(modal);
						bootstrapModal.hide();
					} else {
						alert("Došlo je do greške pri dodavanju korisnika.");
						$('korisnik-ime').val('');
						$('korisnik-prezime').val('');
						$('korisnik-grad').val('');
						$('korisnik-email').val('');
						$('korisnik-brojTelefona').val('');
						$('korisnik-korisnickoIme').val('');
						$('korisnik-lozinka').val('');
					}
				}
			});
		}
	});
});

// Odustajanje od dodavanja novog korisnika.
$(document).ready(function() {
	$('#korisnik-dodaj-button-zatvori').on('click', function() {
		var form = $('#korisnik-dodaj-form');
		form.find('input').val('');
		form.find('.invalid-feedback').hide();
		form.find('input').removeClass('is-invalid');
	});
});

// Provjera da li je uneseno postojeće korisničko ime pri dodavanju korisnika.
$(document).ready(function() {
	$('#korisnik-korisnickoIme').on('keyup', function() {
		var korisnickoIme = document.getElementById("korisnik-korisnickoIme").value;
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=provjeraKorisnickogImena',
			data: { korisnickoIme: korisnickoIme },
			success: function(data) {
				if (data.dostupno) {
					$('.korisnik-korisnickoIme-dostupan').text('Korisničko ime je dostupno.');
				} else {
					$('.korisnik-korisnickoIme-dostupan').text('Korisničko ime je zauzeto.');
				}
			}
		});
	});
});

// Provjera da li je unesen postojeći email pri dodavanju korisnika.
$(document).ready(function() {
	$('#korisnik-email').on('keyup', function() {
		var email = document.getElementById("korisnik-email").value;
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=provjeraEmaila',
			data: { email: email },
			success: function(data) {
				if (data.dostupno) {
					$('.korisnik-email-dostupan').text('Email je dostupan.');
				} else {
					$('.korisnik-email-dostupan').text('Email je zauzet.');
				}
			}
		});
	});
});

// Profil korisnika
$(document).ready(function() {
	$('.korisnik-tabela-kolona-info').click(function() {
		var idKorisnik = $(this).closest('tr').find('td:nth-child(1)').text();
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=prikaziKorisnika',
			data: { idKorisnik: idKorisnik },
			success: function(data) {
				$('#korisnik-ime-prikaz').text(data.ime);
				$('#korisnik-prezime-prikaz').text(data.prezime);
				$('#korisnik-grad-prikaz').text(data.grad);
				$('#korisnik-email-prikaz').text(data.email);
				$('#korisnik-brojTelefona-prikaz').text(data.brojTelefona);
				$('#korisnik-korisnickoIme-prikaz').text(data.korisnickoIme);
				$('#korisnik-datumPristupa-prikaz').text(data.datumPristupa);
				$('#korisnik-status-prikaz').text(data.uloga);
				$('#korisnik-modal-info').modal('show');

			},
			error: function(error) {
				console.error("Greška tokom prikaza korisnika: ", error);
			}
		});
	});
});

// Uređivanje korisnika.
$(document).ready(function() {
	$('.korisnik-tabela-kolona-uredi').click(function() {
		var odabraniRed = $(this).closest('tr');
		var idKorisnik = odabraniRed.data('id');
		var originalKorisnickoIme = odabraniRed.data('korisnickoime');
		var originalEmail = odabraniRed.data('email');

		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=prikaziKorisnika',
			data: { idKorisnik: idKorisnik },
			success: function(data) {
				$('#korisnik-ime-uredi').val(data.ime);
				$('#korisnik-prezime-uredi').val(data.prezime);
				$('#korisnik-grad-uredi').val(data.grad);
				$('#korisnik-email-uredi').val(data.email);
				$('#korisnik-brojTelefona-uredi').val(data.brojTelefona);
				$('#korisnik-korisnickoIme-uredi').val(data.korisnickoIme);
			},
			error: function(error) {
				console.error("Greška tokom prikaza korisnika: ", error);
			}
		});

		$('#korisnik-korisnickoIme-uredi').on('keyup', function() {
			var noviKorisnickoIme = $(this).val();
			if (noviKorisnickoIme === originalKorisnickoIme) {
				$('.korisnik-korisnickoIme-dostupan').text('Korisničko ime je nepromjenjeno.');
			} else {
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=provjeraKorisnickogImena',
					data: { korisnickoIme: noviKorisnickoIme },
					success: function(data) {
						if (data.dostupno) {
							$('.korisnik-korisnickoIme-dostupan').text('Korisničko ime je dostupno.');
						} else {
							$('.korisnik-korisnickoIme-dostupan').text('Korisničko ime je zauzeto.');
						}
					}
				});
			}
		});

		// Provjera dostupnosti emaila dok korisnik unosi
		$('#korisnik-email-uredi').on('keyup', function() {
			var noviEmail = $(this).val();

			if (noviEmail === originalEmail) {
				$('.korisnik-email-dostupan').text('Email je nepromjenjen');
			} else {
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=provjeraEmaila',
					data: { email: noviEmail },
					success: function(data) {
						if (data.dostupno) {
							$('.korisnik-email-dostupan').text('Email je dostupan.');
						} else {
							$('.korisnik-email-dostupan').text('Email je zauzet.');
						}
					}
				});
			}
		});

		$('#korisnik-uredi-button-sacuvaj').on('click', function(event) {
			event.preventDefault();
			var form = document.getElementById("korisnik-uredi-form");
			var inputs = form.querySelectorAll('input');
			var isValidForm = true;
			inputs.forEach(function(input) {
				var errorMessage = $(input).next('.invalid-feedback');
				if (!input.checkValidity()) {
					errorMessage.show();
					$(input).addClass('is-invalid');
					isValidForm = false;
				} else {
					errorMessage.hide();
					$(input).removeClass('is-invalid');
				}
			});

			if (isValidForm) {
				var imeKorisnika = document.getElementById("korisnik-ime-uredi").value;
				var prezimeKorisnika = document.getElementById("korisnik-prezime-uredi").value;
				var gradKorisnika = document.getElementById("korisnik-grad-uredi").value;
				var emailKorisnika = document.getElementById("korisnik-email-uredi").value;
				var brojTelefonaKorisnika = document.getElementById("korisnik-brojTelefona-uredi").value;
				var korisnickoIme = document.getElementById("korisnik-korisnickoIme-uredi").value;
				var lozinkaKorisnika = document.getElementById("korisnik-lozinka-uredi").value;
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=urediKorisnika',
					data: {
						idKorisnik: idKorisnik, imeKorisnika: imeKorisnika, prezimeKorisnika: prezimeKorisnika, gradKorisnika: gradKorisnika,
						emailKorisnika: emailKorisnika, brojTelefonaKorisnika: brojTelefonaKorisnika,
						korisnickoIme: korisnickoIme, lozinkaKorisnika: lozinkaKorisnika
					},
					success: function(data) {
						if (data.promjenjeno) {
							$('korisnik-ime-uredi').val('');
							$('korisnik-prezime-uredi').val('');
							$('korisnik-grad-uredi').val('');
							$('korisnik-email-uredi').val('');
							$('korisnik-brojTelefona-uredi').val('');
							$('korisnik-korisnickoIme-uredi').val('');
							$('korisnik-lozinka-uredi').val('');

							$(form).find('.invalid-feedback').hide();
							location.search = location.search.replace('akcija=prijava', 'akcija=korisnici');
							var modal = document.getElementById("korisnik-modal-uredi");
							var bootstrapModal = bootstrap.Modal.getInstance(modal);
							bootstrapModal.hide();
						} else {
							alert("Došlo je do greške pri ažuriranju korisnika.");
							$('korisnik-ime-uredi').val('');
							$('korisnik-prezime-uredi').val('');
							$('korisnik-grad-uredi').val('');
							$('korisnik-email-uredi').val('');
							$('korisnik-brojTelefona-uredi').val('');
							$('korisnik-korisnickoIme-uredi').val('');
							$('korisnik-lozinka-uredi').val('');
						}
					}
				});
			}
		});

		$('#korisnik-uredi-button-zatvori').on('click', function() {
			$('#korisnik-ime-uredi').val('');
			$('#korisnik-prezime-uredi').val('');
			$('#korisnik-grad-uredi').val('');
			$('#korisnik-email-uredi').val('');
			$('#korisnik-brojTelefona-uredi').val('');
			$('#korisnik-korisnickoIme-uredi').val('');
			$('#korisnik-lozinka-uredi').val('');

			$('.korisnik-korisnickoIme-dostupan').empty();
			$('.korisnik-email-dostupan').empty();
			$('#korisnik-korisnickoIme-uredi').off('keyup');
			$('#korisnik-email-uredi').off('keyup');
		});
	});
});

// Brisanje korisnika.
$(document).ready(function() {
	$('.korisnik-tabela-kolona-obrisi').on('click', function() {
		if (confirm("Da li ste sigurni da želite obrisati odabranog korisnika?")) {
			var red = $(this).closest('tr');
			var idKorisnik = red.find('td:eq(0)').text();
			$.ajax({
				type: 'GET',
				url: 'Administrator?akcija=obrisiKorisnika',
				data: { idKorisnik: idKorisnik },
				success: function(data) {
					if (data.obrisano) {
						$('#korisnik-alert-brisanje-uspjesno').fadeIn();
						setTimeout(function() {
							$('#korisnik-alert-brisanje-uspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=korisnici');
						}, 2000);
					} else {
						$('#korisnik-alert-brisanje-neuspjesno').fadeIn();
						setTimeout(function() {
							$('#korisnik-alert-brisanje-neuspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=korisnici');
						}, 2000);
					}
				}
			});
		} else {
			return;
		}
	});
});

// Dodavanje novog savjetnika.
$(document).ready(function() {
	$('#savjetnik-dodaj-button-sacuvaj').on('click', function(event) {
		event.preventDefault();
		var form = document.getElementById("savjetnik-dodaj-form");
		var inputs = form.querySelectorAll('input');
		var isValidForm = true;
		inputs.forEach(function(input) {
			var errorMessage = $(input).next('.invalid-feedback');
			if (!input.checkValidity()) {
				errorMessage.show();
				$(input).addClass('is-invalid');
				isValidForm = false;
			} else {
				errorMessage.hide();
				$(input).removeClass('is-invalid');
			}
		});
		if (isValidForm) {
			var imeSavjetnika = document.getElementById("savjetnik-ime").value;
			var prezimeSavjetnika = document.getElementById("savjetnik-prezime").value;
			var korisnickoIme = document.getElementById("savjetnik-korisnickoIme").value;
			var lozinkaSavjetnika = document.getElementById("savjetnik-lozinka").value;
			$.ajax({
				type: 'GET',
				url: 'Administrator?akcija=dodajSavjetnika',
				data: {
					imeSavjetnika: imeSavjetnika, prezimeSavjetnika: prezimeSavjetnika,
					korisnickoImeSavjetnika: korisnickoIme, lozinkaSavjetnika: lozinkaSavjetnika
				},
				success: function(data) {
					if (data.dodato) {
						$('savjetnik-ime').val('');
						$('savjetnik-prezime').val('');
						$('savjetnik-korisnickoIme').val('');
						$('savjetnik-lozinka').val('');

						$(form).find('.invalid-feedback').hide();
						location.search = location.search.replace('akcija=prijava', 'akcija=savjetnici');
						var modal = document.getElementById("savjetnik-modal-dodaj");
						var bootstrapModal = bootstrap.Modal.getInstance(modal);
						bootstrapModal.hide();
					} else {
						alert("Doslo je do greske pri dodavanju savjetnika.");
						$('savjetnik-ime').val('');
						$('savjetnik-prezime').val('');
						$('savjetnik-korisnickoIme').val('');
						$('savjetnik-lozinka').val('');
					}
				}
			});
		}
	});
});

// Odustajanje od dodavanja novog savjetnika.
$(document).ready(function() {
	$('#savjetnik-dodaj-button-zatvori').on('click', function() {
		var form = $('#savjetnik-dodaj-form');
		form.find('input').val('');
		form.find('.invalid-feedback').hide();
		form.find('input').removeClass('is-invalid');
	});
});

// Provjera da li je uneseno postojeće korisničko ime pri dodavanju savjetnika.
$(document).ready(function() {
	$('#savjetnik-korisnickoIme').on('keyup', function() {
		var korisnickoIme = document.getElementById("savjetnik-korisnickoIme").value;
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=provjeraKorisnickogImenaSavjetnika',
			data: { korisnickoImeSavjetnika: korisnickoIme },
			success: function(data) {
				if (data.dostupno) {
					$('.savjetnik-korisnickoIme-dostupan').text('Korisničko ime je dostupno.');
				} else {
					$('.savjetnik-korisnickoIme-dostupan').text('Korisničko ime je zauzeto.');
				}
			}
		});
	});
});

// Profil savjetnika
$(document).ready(function() {
	$('.savjetnik-tabela-kolona-info').click(function() {
		var idSavjetnik = $(this).closest('tr').find('td:nth-child(1)').text();
		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=prikaziSavjetnika',
			data: { idSavjetnik: idSavjetnik },
			success: function(data) {
				$('#savjetnik-ime-prikaz').text(data.ime);
				$('#savjetnik-prezime-prikaz').text(data.prezime);
				$('#savjetnik-korisnickoIme-prikaz').text(data.korisnickoIme);
				$('#savjetnik-modal-info').modal('show');

			},
			error: function(error) {
				console.error("Greška tokom prikaza savjetnika: ", error);
			}
		});
	});
});

// Uredjivanje savjetnika.
$(document).ready(function() {
	$('.savjetnik-tabela-kolona-uredi').click(function() {
		var odabraniRed = $(this).closest('tr');
		var idSavjetnik = odabraniRed.data('id');
		var originalKorisnickoIme = odabraniRed.data('korisnickoime');

		$.ajax({
			type: 'GET',
			url: 'Administrator?akcija=prikaziSavjetnika',
			data: { idSavjetnik: idSavjetnik },
			success: function(data) {
				$('#savjetnik-ime-uredi').val(data.ime);
				$('#savjetnik-prezime-uredi').val(data.prezime);
				$('#savjetnik-korisnickoIme-uredi').val(data.korisnickoIme);
			},
			error: function(error) {
				console.error("Greška tokom prikaza savjetnika: ", error);
			}
		});

		$('#savjetnik-korisnickoIme-uredi').on('keyup', function() {
			var novoKorisnickoIme = $(this).val();
			if (novoKorisnickoIme === originalKorisnickoIme) {
				$('.savjetnik-korisnickoIme-dostupan').text('Korisnicko ime je nepromjenjeno.');
			} else {
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=provjeraKorisnickogImenaSavjetnika',
					data: { korisnickoImeSavjetnika: novoKorisnickoIme },
					success: function(data) {
						if (data.dostupno) {
							$('.savjetnik-korisnickoIme-dostupan').text('Korisničko ime je dostupno.');
						} else {
							$('.savjetnik-korisnickoIme-dostupan').text('Korisničko ime je zauzeto.');
						}
					}
				});
			}
		});

		$('#savjetnik-uredi-button-sacuvaj').on('click', function(event) {
			event.preventDefault();
			var form = document.getElementById("savjetnik-uredi-form");
			var inputs = form.querySelectorAll('input');
			var isValidForm = true;
			inputs.forEach(function(input) {
				var errorMessage = $(input).next('.invalid-feedback');
				if (!input.checkValidity()) {
					errorMessage.show();
					$(input).addClass('is-invalid');
					isValidForm = false;
				} else {
					errorMessage.hide();
					$(input).removeClass('is-invalid');
				}
			});

			if (isValidForm) {
				var imeSavjetnika = document.getElementById("savjetnik-ime-uredi").value;
				var prezimeSavjetnika = document.getElementById("savjetnik-prezime-uredi").value;
				var korisnickoIme = document.getElementById("savjetnik-korisnickoIme-uredi").value;
				var lozinkaSavjetnika = document.getElementById("savjetnik-lozinka-uredi").value;
				$.ajax({
					type: 'GET',
					url: 'Administrator?akcija=urediSavjetnika',
					data: {
						idSavjetnik: idSavjetnik, imeSavjetnika: imeSavjetnika, prezimeSavjetnika: prezimeSavjetnika,
						korisnickoImeSavjetnika: korisnickoIme, lozinkaSavjetnika: lozinkaSavjetnika
					},
					success: function(data) {
						if (data.promjenjeno) {
							$('savjetnik-ime-uredi').val('');
							$('savjetnik-prezime-uredi').val('');
							$('savjetnik-korisnickoIme-uredi').val('');
							$('savjetnik-lozinka-uredi').val('');

							$(form).find('.invalid-feedback').hide();
							location.search = location.search.replace('akcija=prijava', 'akcija=savjetnici');
							var modal = document.getElementById("savjetnik-modal-uredi");
							var bootstrapModal = bootstrap.Modal.getInstance(modal);
							bootstrapModal.hide();
						} else {
							alert("Doslo je do greske pri azuriranju savjetnika.");
							$('savjetnik-ime-uredi').val('');
							$('savjetnik-prezime-uredi').val('');
							$('savjetnik-korisnickoIme-uredi').val('');
							$('savjetnik-lozinka-uredi').val('');
						}
					}
				});
			}
		});

		$('#savjetnik-uredi-button-zatvori').on('click', function() {
			$('#savjetnik-ime-uredi').val('');
			$('#savjetnik-prezime-uredi').val('');
			$('#savjetnik-korisnickoIme-uredi').val('');
			$('#savjetnik-lozinka-uredi').val('');

			$('.savjetnik-korisnickoIme-dostupan').empty();
			$('#savjetnik-korisnickoIme-uredi').off('keyup');
		});
	});
});

// Brisanje savjetnika.
$(document).ready(function() {
	$('.savjetnik-tabela-kolona-obrisi').on('click', function() {
		if (confirm("Da li ste sigurni da želite obrisati odabranog savjetnika?")) {
			var red = $(this).closest('tr');
			var idSavjetnik = red.find('td:eq(0)').text();
			$.ajax({
				type: 'GET',
				url: 'Administrator?akcija=obrisiSavjetnika',
				data: { idSavjetnik: idSavjetnik },
				success: function(data) {
					if (data.obrisano) {
						$('#savjetnik-alert-brisanje-uspjesno').fadeIn();
						setTimeout(function() {
							$('#savjetnik-alert-brisanje-uspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=savjetnici');
						}, 2000);
					} else {
						$('#savjetnik-alert-brisanje-neuspjesno').fadeIn();
						setTimeout(function() {
							$('#savjetnik-alert-brisanje-neuspjesno').fadeOut();
							location.search = location.search.replace('akcija=prijava', 'akcija=savjetnici');
						}, 2000);
					}
				}
			});
		} else {
			return;
		}
	});
});
