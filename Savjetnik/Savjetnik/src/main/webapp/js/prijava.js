
// Dobijanje tokena po uspjesnoj prijavi.
function uspjesnaPrijava(korisnickoIme, lozinka) {
	$(document).ready(function() {
		var url = 'http://localhost:8888/api/autentifikacija/token-savjetnik';
		var kredencijali = {
			korisnickoIme: korisnickoIme,
			lozinka: lozinka
		};
		$.ajax({
			type: 'POST',
			url: url,
			contentType: 'application/json',
			data: JSON.stringify(kredencijali),
			success: function(odgovor) {
				var jwtToken = odgovor.jwtToken;
				localStorage.setItem('jwtToken-savjetnik', jwtToken);
				localStorage.setItem('korisnickoIme-savjetnik', korisnickoIme);
				if (typeof redirekcijaURL !== "undefined") {
					window.location.href = redirekcijaURL;
				}
			}
		});

	});
}


// Poruka o neuspjesnoj prijavi.
function neuspjesnaPrijava() {
	$(document).ready(function() {
		$('#alert-prijava-neuspjesno').fadeIn();
		setTimeout(function() {
			$('#alert-prijava-neuspjesno').fadeOut();
		}, 10000);
	});
}