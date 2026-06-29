
// Odjava iz aplikacije.
$(document).ready(function() {
	$('#button-odjava').on('click', function() {
		window.location.href = 'logout.jsp';
		localStorage.removeItem('jwtToken-savjetnik');
		localStorage.removeItem('korisnickoIme-savjetnik');
	});
});

// Rad sa porukama savjetnika.
$(document).ready(function() {
	var jwtToken = localStorage.getItem('jwtToken-savjetnik');
	var imeSavjetnika = localStorage.getItem('korisnickoIme-savjetnik');
	var url = 'http://localhost:8888/api/porukeSavjetnika/poruke/' + imeSavjetnika;
	$.ajax({
		type: 'GET',
		url: url,
		headers: {
			'Authorization': 'Bearer ' + jwtToken
		},
		data: {},
		success: function(data) {
			$('#table-poruke tbody').empty();
			// Dodaj nove redove sa podacima.
			for (var i = 0; i < data.length; i++) {
				var procitanaClass = data[i].procitana ? 'table-success' : 'table-danger';
				var red = '<tr class="' + procitanaClass + '">' +
					'<td>' + data[i].id + '</td>' +
					'<td>' + data[i].korisnickoIme + '</td>' +
					'<td>' + data[i].vrijemePoruke + '</td>' +
					'<td>' + data[i].naslovPoruke + '</td>' +
					'<td>' +
					'<button class="button-poruka-tekst btn btn-primary" data-tekst="' + data[i].tekstPoruke + '" data-poruka-id="' + data[i].id + '">Pro&#269;itaj</button>' +
					'<td>' +
					'<button class="button-poruka-posalji btn btn-primary" data-email="' + data[i].email + '">Po&#353;alji</button>' +

					'</td>' +
					'</tr>';
				$('#table-poruke tbody').append(red);
			}

			// Prikaz modala za citanje poruka.
			$('.button-poruka-tekst').on('click', function() {
				var poruka = $(this).data('tekst');
				var porukaId = $(this).data('poruka-id');
				$('#modal-poruka-citanje .modal-body').text(poruka);
				$('#modal-poruka-citanje').data('poruka-id', porukaId);
				$('#modal-poruka-citanje').modal('show');
			});

			// Oznaci poruku kao procitanu.
			$('#modal-poruka-citanje').on('shown.bs.modal', function() {
				var porukaId = $('#modal-poruka-citanje').data('poruka-id');
				var procitanaUrl = 'http://localhost:8888/api/porukeSavjetnika/' + porukaId + '/procitana';
				$.ajax({
					type: 'PUT',
					url: procitanaUrl,
					headers: {
						'Authorization': 'Bearer ' + jwtToken
					},
					data: {},
					success: function() {
					}
				});
			});

			// Osvjezi stranicu poslije zatvaranja modala za citanje poruke.
			$('#modal-poruka-citanje').on('hidden.bs.modal', function() {
				location.reload();
			});

			// Prikaz modala za slanje odgovora.
			$('.button-poruka-posalji').on('click', function() {
				var mojEmail = $(this).data('email');
				$('#input-email').val(mojEmail).prop('readonly', true);
				$('#modal-poruka-odgovor').modal('show');
				var porukaId = $(this).closest('tr').find('td:first-child').text();
				$('#button-odgovor-posalji').data('poruka-id', porukaId);
			});

			// Odgovaranje putem email-a.
			$('#button-odgovor-posalji').on('click', function() {
				var naslovOdgovora = $('#input-naslov').val();
				var tekstOdgovora = $('#textarea-email').val();
				var emailPrimaoca = $('#input-email').val();
				var porukaId = $(this).data('poruka-id');
				var prilog = document.getElementById('input-prilog').files[0];
				
				var podaciForme = new FormData($('#forma-odgovor')[0]);
				podaciForme.append('naslov', naslovOdgovora);
				podaciForme.append('tekst', tekstOdgovora);
				podaciForme.append('email', emailPrimaoca);
				podaciForme.append('prilog', prilog);
				var odgovorUrl = 'http://localhost:8888/api/porukeSavjetnika/' + porukaId + '/odgovor';
				var modal = document.getElementById("modal-poruka-odgovor");
				var bootstrapModal = bootstrap.Modal.getInstance(modal);
				bootstrapModal.hide();
				$.ajax({
					type: 'PUT',
					url: odgovorUrl,
					headers: {
						'Authorization': 'Bearer ' + jwtToken
					},
					processData: false,
					contentType: false,
					data: podaciForme,
					success: function(odgovor) {
						if (odgovor) {
							$('#odgovor-uspjesan').fadeIn();
							setTimeout(function() {
								$('#odgovor-uspjesan').fadeOut();
							}, 5000);
						} else {
							$('#odgovor-neuspjesan').fadeIn();
							setTimeout(function() {
								$('#odgovor-neuspjesan').fadeOut();
							}, 5000);
						}
					}
				});
			});

			// Osvjezi stranicu poslije zatvaranja modala za odgovaranje na poruku.
			$('#modal-poruka-odgovor').on('hidden.bs.modal', function() {
				$('#input-naslov').val("");
				$('#textarea-email').val("");
				$('#input-prilog').val("");
			});

			// Realizovanje pretrage na osnovu teksta poruke.
			$('#input-pretraga').on('keyup', function() {
				var vrijednostPretrage = $(this).val().toLowerCase();
				$('#table-poruke').find('tbody tr').filter(function() {
					var celijaTekstPoruke = $(this).find('td:nth-child(5) .button-poruka-tekst').data('tekst');
					$(this).toggle(celijaTekstPoruke.toLowerCase().indexOf(vrijednostPretrage) > -1);
				});
			});
		}
	});
});
