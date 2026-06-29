import { DatePipe } from '@angular/common';
import { Component, ViewChild, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatExpansionPanel } from '@angular/material/expansion';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AktivnostOdgovor } from 'src/app/model/aktivnostOdgovor';
import { AktivnostZahtjev } from 'src/app/model/aktivnostZahtjev';
import { KategorijaOdgovor } from 'src/app/model/kategorijaOdgovor';
import { MasaZahtjev } from 'src/app/model/masaZahtjev';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { LoginService } from 'src/app/services/login.service';

import jsPDF from 'jspdf';
import { AktivnostInfo } from 'src/app/model/aktivnostInfo';


@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  korisnickoIme: string;
  odabraniDatum!: string;
  kategorije: KategorijaOdgovor[] = [];
  aktivnosti: AktivnostOdgovor[] = [];
  infoAktivnosti: AktivnostInfo[] = [];
  aktivnostForm!: FormGroup;
  istorijaForm!: FormGroup;
  masaForm!: FormGroup;
  pdfForm!: FormGroup;

  @ViewChild('panelAktivnost') expansionPanel1!: MatExpansionPanel;
  @ViewChild('panelIstorija') expansionPanel2!: MatExpansionPanel;
  @ViewChild('panelMasa') expansionPanel3!: MatExpansionPanel;
  @ViewChild('panelPdf') expansionPanel4!: MatExpansionPanel;

  aktivnostiDijagramOpcije: any;
  aktivnostiDijagramLabele: string[] = [];
  aktivnostiDijagramLegenda: boolean = true;
  aktivnostiDijagramPodaci: any[] = [];

  kilogramiDijagramOpcije: any;
  kilogramiDijagramLabele: string[] = [];
  kilogramiDijagramLegenda: boolean = true;
  kilogramiDijagramPodaci: any[] = [];

  constructor(private backendService: BackendService, private formBuilder: FormBuilder,
    private loginService: LoginService, private snackBar: MatSnackBar) {
    const podaci: PrijavaOdgovor | null = this.loginService.vratiPodatke();
    if (podaci) {
      this.korisnickoIme = podaci.korisnickoIme;
    }
    else {
      this.korisnickoIme = 'gost';
    }
  }

  ngOnInit(): void {
    this.vratiSveKategorije();
    this.aktivnostForm = this.formBuilder.group({
      nazivVjezbe: [null, [Validators.required, Validators.pattern('^[a-zA-Z0-9_ ]*$'), Validators.minLength(3), Validators.maxLength(30)]],
      kategorija: [null, Validators.required],
      intezitet: [null, Validators.required],
      trajanje: [null, [Validators.required, Validators.pattern('^[0-9]*$')]],
      brojPonavljanja: [null, [Validators.required, Validators.pattern('^[0-9]*$')]]
    });
    this.istorijaForm = this.formBuilder.group({
      datum: [null, Validators.required]
    });
    this.masaForm = this.formBuilder.group({
      masa: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1})?$/)]]
    });
    this.pdfForm = this.formBuilder.group({
    });
    this.prikaziAktivnosti(false);
    this.vratiSveIzmjereneMaseKorisnika();
  }

  vratiSveKategorije(): void {
    this.backendService.vratiSveKategorije().subscribe({
      next: (odgovor) => {
        if (odgovor)
          this.kategorije = Array.from(odgovor);
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom odabira kategorije!' + greska, 'Zatvori', { duration: 4000 });
      }
    })
  }

  dodajAktivnost() {
    const zahtjev: AktivnostZahtjev = {
      naziv: this.aktivnostForm.value.nazivVjezbe,
      kategorijaId: this.aktivnostForm.value.kategorija,
      intezitet: this.aktivnostForm.value.intezitet,
      trajanje: this.aktivnostForm.value.trajanje,
      brojPonavljanja: this.aktivnostForm.value.brojPonavljanja,
      korisnickoIme: this.korisnickoIme
    };
    this.backendService.kreirajAktivnsot(zahtjev).subscribe({
      next: () => {
        this.snackBar.open('Uspješno ste dodali aktivnost!', 'Zatvori', { duration: 4000 });
        this.prikaziAktivnosti(false);
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom kreiranja aktivnosti!', 'Zatvori', { duration: 4000 });
      }
    })
    this.aktivnostForm.reset();
    this.expansionPanel1.close();
  }

  prikaziAktivnosti(istorija: boolean) {
    let datum = this.istorijaForm.value.datum;
    if (datum == null) {
      datum = new Date();
    }
    const datePipe = new DatePipe('en-US');
    datum = datePipe.transform(datum, 'yyyy-MM-dd');
    this.odabraniDatum = datum;

    this.backendService.vratiAktivnostiKorisnikaPoDatumu(this.korisnickoIme, datum).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.aktivnosti = Array.from(odgovor);
        }
        else
          this.aktivnosti = [];
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom vraćanja aktivnosti korisnika!' + greska, 'Zatvori', { duration: 4000 });
      }
    })
    if (istorija) {
      this.istorijaForm.reset();
      this.expansionPanel2.close();
    }
    this.vratiInformacijeOAktivnostimaKorisnika();
  }

  vratiInformacijeOAktivnostimaKorisnika() {
    this.backendService.vratiInformacijeOAktivnostimaKorisnika(this.korisnickoIme).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.infoAktivnosti = Array.from(odgovor);
          this.infoAktivnosti.sort((a, b) => new Date(a.datum).getTime() - new Date(b.datum).getTime());
          this.aktivnostiDijagramOpcije = {
            scaleShowVerticalLines: false,
            responsive: true
          };
          let podaciUkupno: any = [];
          let labelaUkupno: string = 'ukupno';
          let podaciZavrseno: any = [];
          let labelaZavrseno: string = 'zavrseno';
          this.aktivnostiDijagramLabele = [];
          this.infoAktivnosti.forEach(podatak => {
            this.aktivnostiDijagramLabele.push(podatak.datum);
            podaciUkupno.push(podatak.ukupno);
            podaciZavrseno.push(podatak.brojZavrsenih)
          });
          this.aktivnostiDijagramPodaci = [
            { data: podaciUkupno, label: labelaUkupno },
            { data: podaciZavrseno, label: labelaZavrseno }
          ];
        }
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom dobijanja informacija o aktivnostima!' + greska, 'Zatvori', { duration: 4000 });
      }
    })
  }

  unesiMasu() {
    const zahtjev: MasaZahtjev = {
      masa: this.masaForm.value.masa,
      korisnickoIme: this.korisnickoIme
    };
    this.backendService.unesiMasu(zahtjev).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.snackBar.open('Uspješno ste unijeli broj kilograma!', 'Zatvori', { duration: 4000 });
          this.vratiSveIzmjereneMaseKorisnika();
        }
        else
          this.snackBar.open('Danas ste već unijeli broj kilograma!', 'Zatvori', { duration: 4000 });
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom unosa mase korisnika!', 'Zatvori', { duration: 4000 });
      }
    })
    this.masaForm.reset();
    this.expansionPanel3.close();
  }

  vratiSveIzmjereneMaseKorisnika() {
    this.backendService.vratiSveIzmjereneMaseKorisnika(this.korisnickoIme).subscribe({
      next: (odgovor) => {
        let izmjereneMase = Array.from(odgovor);
        izmjereneMase.sort((a, b) => new Date(a.datum).getTime() - new Date(b.datum).getTime());
        this.kilogramiDijagramOpcije = {
          scaleShowVerticalLines: false,
          responsive: true
        };
        let podaci: any = [];
        let labela: string = 'kilogrami';
        this.kilogramiDijagramLabele = [];
        izmjereneMase.forEach(podatak => {
          this.kilogramiDijagramLabele.push(podatak.datum);
          podaci.push(podatak.masa);
        });
        this.kilogramiDijagramPodaci = [
          { data: podaci, label: labela }
        ];
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom vraćanja izmjerenih masa korisnika!', 'Zatvori', { duration: 4000 });
      }
    })
  }

  snimi() {
    this.backendService.vratiDnevnikKorisnika(this.korisnickoIme).subscribe({
      next: (odgovor) => {
        let dnevnik = odgovor;
        const pdf = new jsPDF({
          format: 'letter',
          unit: 'mm',
          orientation: 'portrait'
        });
        const datePipe = new DatePipe('en-US');
        let trenutnoVrijeme = datePipe.transform(new Date(), 'short');
        let naziv: string = "dnevnik_" + this.korisnickoIme + "_" + trenutnoVrijeme + ".pdf";
        const brojRedovaNaStranici = 25;
        const tekstDijelovi = dnevnik.split('\n');
        let trenutnaStranica = 1;
        let redNaStranici = 0;
        for (let i = 0; i < tekstDijelovi.length; i++) {
          if (redNaStranici >= brojRedovaNaStranici) {
            pdf.addPage();
            trenutnaStranica++;
            redNaStranici = 0;
          }
          pdf.text(tekstDijelovi[i], 10, 10 + (redNaStranici * 10));
          redNaStranici++;
        }
        pdf.save(naziv);
        this.snackBar.open('Uspješno ste snimili dnevnika korisnika!', 'Zatvori', { duration: 5000 });
        this.expansionPanel4.close();
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom vraćanja dnevnika korisnika!', 'Zatvori', { duration: 5000 });
      }
    })
  }

  zavrsi(aktivnost: any) {
    this.backendService.zavrsiAktivnost(aktivnost.id).subscribe({
      next: () => {
        this.snackBar.open('Odabrana aktivnost je završena!', 'Zatvori', { duration: 4000 });
        this.prikaziAktivnosti(false);
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom ažuriranja aktivnosti!', 'Zatvori', { duration: 4000 });
      }
    })
  }

}
