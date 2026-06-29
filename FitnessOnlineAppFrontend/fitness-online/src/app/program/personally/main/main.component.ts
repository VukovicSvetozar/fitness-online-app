import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CategoryModalComponent } from '../category-modal/category-modal.component';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { FitnesProgramKarticaOdgovor } from 'src/app/model/fitnesProgramKarticaOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { LoginService } from 'src/app/services/login.service';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { KategorijaOdgovor } from 'src/app/model/kategorijaOdgovor';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  listaKarticaAktivnihFitnesPrograma: FitnesProgramKarticaOdgovor[] = [];
  listaKarticaZavrsenihFitnesPrograma: FitnesProgramKarticaOdgovor[] = [];
  listaKarticaKupljenihFitnesPrograma: FitnesProgramKarticaOdgovor[] = [];
  trenutnaStranicaAktivno: number = 0;
  ukupnoElemenataAktivno: number = 0;
  brojStranicaAktivno: number = 0;
  brojElemenataAktivno: number = 4;
  trenutnaStranicaZavrseno: number = 0;
  ukupnoElemenataZavrseno: number = 0;
  brojStranicaZavrseno: number = 0;
  brojElemenataZavrseno: number = 4;
  trenutnaStranicaKupljeno: number = 0;
  ukupnoElemenataKupljeno: number = 0;
  brojStranicaKupljeno: number = 0;
  brojElemenataKupljeno: number = 4;
  korisnickoIme: string;
  statistikaUkupnoElemenataAktivno: number = 0;
  statistikaUkupnoElemenataZavrseno: number = 0;
  statistikaUkupnoElemenataKupljeno: number = 0;
  kategorije: KategorijaOdgovor[] = [];
  pretplataForm!: FormGroup;

  constructor(private dijalog: MatDialog, private backendService: BackendService, private formBuilder: FormBuilder,
    private paginatorIntl: MatPaginatorIntl, private loginService: LoginService, private snackBar: MatSnackBar) {
    const podaci: PrijavaOdgovor | null = this.loginService.vratiPodatke();
    if (podaci) {
      this.korisnickoIme = podaci.korisnickoIme;
    }
    else {
      this.korisnickoIme = 'gost';
    }
  }

  ngOnInit(): void {
    this.ucitajKarticeAktivnihFitnesProgramaKorisnika(this.korisnickoIme, this.trenutnaStranicaAktivno, this.brojElemenataAktivno);
    this.ucitajKarticeZavrsenihFitnesProgramaKorisnika(this.korisnickoIme, this.trenutnaStranicaZavrseno, this.brojElemenataZavrseno);
    this.ucitajKarticeKupljenihFitnesProgramaKorisnika(this.korisnickoIme, this.trenutnaStranicaKupljeno, this.brojElemenataKupljeno);
    this.vratiStatistikuFitnesPrograma(this.korisnickoIme);
    this.paginatorIntl.itemsPerPageLabel = 'Broj elemenata po stranici:';
    this.paginatorIntl.nextPageLabel = 'Sledeća stranica';
    this.paginatorIntl.previousPageLabel = 'Prethodna stranica';
    this.vratiSveKategorije();
    this.pretplataForm = this.formBuilder.group({
      kategorija: [null, Validators.required]
    });
  }

  otvoriDodajFitnesProgramDijalog(): void {
    const dijalogRef = this.dijalog.open(CategoryModalComponent, {
      width: '400px'
    });
    dijalogRef.afterClosed().subscribe((result: string) => { });
  }

  ucitajKarticeAktivnihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    this.backendService.vratiKarticeAktivnihFitnesProgramaKorisnika(korisnickoIme, trenutnaStranica, brojElemenata).subscribe({
      next: (odgovor) => {
        this.listaKarticaAktivnihFitnesPrograma = odgovor.aktivneKartice;
        this.trenutnaStranicaAktivno = odgovor.trenutnaStranica;
        this.ukupnoElemenataAktivno = odgovor.ukupnoElemenata;
        this.brojStranicaAktivno = odgovor.brojStranica;
      }
    });
  }

  ucitajKarticeZavrsenihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    this.backendService.vratiKarticeZavrsenihFitnesProgramaKorisnika(korisnickoIme, trenutnaStranica, brojElemenata).subscribe({
      next: (odgovor) => {
        this.listaKarticaZavrsenihFitnesPrograma = odgovor.zavrseneKartice;
        this.trenutnaStranicaZavrseno = odgovor.trenutnaStranica;
        this.ukupnoElemenataZavrseno = odgovor.ukupnoElemenata;
        this.brojStranicaZavrseno = odgovor.brojStranica;
      }
    });
  }

  ucitajKarticeKupljenihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    this.backendService.vratiKarticeKupljenihFitnesProgramaKorisnika(korisnickoIme, trenutnaStranica, brojElemenata).subscribe({
      next: (odgovor) => {
        this.listaKarticaKupljenihFitnesPrograma = odgovor.kupljeneKartice;
        this.trenutnaStranicaKupljeno = odgovor.trenutnaStranica;
        this.ukupnoElemenataKupljeno = odgovor.ukupnoElemenata;
        this.brojStranicaKupljeno = odgovor.brojStranica;
      }
    });
  }

  promjenaStraniceAktivniFitnesProgramiKorisnika(event: PageEvent) {
    const novaStranica = event.pageIndex;
    const velicinaStranice = event.pageSize;
    this.ucitajKarticeAktivnihFitnesProgramaKorisnika(this.korisnickoIme, novaStranica, velicinaStranice);
  }

  promjenaStraniceZavrseniFitnesProgramiKorisnika(event: PageEvent) {
    const novaStranica = event.pageIndex;
    const velicinaStranice = event.pageSize;
    this.ucitajKarticeZavrsenihFitnesProgramaKorisnika(this.korisnickoIme, novaStranica, velicinaStranice);
  }

  promjenaStraniceKupljeniFitnesProgramiKorisnika(event: PageEvent) {
    const novaStranica = event.pageIndex;
    const velicinaStranice = event.pageSize;
    this.ucitajKarticeKupljenihFitnesProgramaKorisnika(this.korisnickoIme, novaStranica, velicinaStranice);
  }

  vratiStatistikuFitnesPrograma(korisnickoIme: string) {
    this.backendService.vratiStatistikuFitnesPrograma(korisnickoIme).subscribe({
      next: (odgovor) => {
        this.statistikaUkupnoElemenataAktivno = odgovor.brojAktivnih;
        this.statistikaUkupnoElemenataZavrseno = odgovor.brojZavrsenih;
        this.statistikaUkupnoElemenataKupljeno = odgovor.brojKupljenih;
      }
    });
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

  pretplata() {
    let kategorijaId = this.pretplataForm.value.kategorija;
    this.backendService.pretplatiKorisnika(this.korisnickoIme, kategorijaId).subscribe({
      next: (odgovor) => {
        if (odgovor)
          this.snackBar.open("Uspješno ste se pretplatili na odabranu kategoriju!", 'Zatvori', { duration: 7000 });
        else
          this.snackBar.open("Već ste pretplatnik!", 'Zatvori', { duration: 7000 });
          this.pretplataForm.reset();
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom pretplate!' + greska, 'Zatvori', { duration: 5000 });
      }
    })
  }

}