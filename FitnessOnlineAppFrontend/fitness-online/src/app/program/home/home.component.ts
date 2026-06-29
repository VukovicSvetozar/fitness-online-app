import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { KategorijaOdgovor } from 'src/app/model/kategorijaOdgovor';
import { PretragaZahtjev } from 'src/app/model/pretragaZahtjev';
import { FitnesProgramKarticaOdgovor } from 'src/app/model/fitnesProgramKarticaOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { VijestiOdgovor } from 'src/app/model/vijestiOdgovor';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  listaKarticaFitnesPrograma: FitnesProgramKarticaOdgovor[] = [];
  trenutnaStranica: number = 0;
  ukupnoElemenata: number = 0;
  brojStranica: number = 0;
  brojElemenata: number = 9;
  filtriranjeForm!: FormGroup;
  kategorije: KategorijaOdgovor[] = [];
  vijesti: VijestiOdgovor[] = [];
  ukljucenaPretraga: boolean = false;
  sortiranjeForm!: FormGroup;

  constructor(private backendService: BackendService, private paginatorIntl: MatPaginatorIntl, private formBuilder: FormBuilder,
    private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.ucitajSveKarticeFitnesPrograma(this.trenutnaStranica, this.brojElemenata);
    this.vratiSveKategorije();
    this.vratiNajnovijeVijesti();
    this.paginatorIntl.itemsPerPageLabel = 'Broj programa po stranici:';
    this.paginatorIntl.nextPageLabel = 'Sledeća stranica';
    this.paginatorIntl.previousPageLabel = 'Prethodna stranica';
    this.filtriranjeForm = this.formBuilder.group({
      kategorija: [null],
      korisnickoIme: [null, [Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(3), Validators.maxLength(100)]],
      naziv: [null, [Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(3), Validators.maxLength(30)]],
      tipLokacije: [null, [Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(2), Validators.maxLength(50)]],
      minCijena: [null, Validators.pattern('^[0-9]+$')],
      maxCijena: [null, []],
      datumOd: [''],
      datumDo: [''],
      nivoTezine: [null],
      minTrajanje: [null, Validators.pattern('^[0-9]+$')],
      maxTrajanje: [null, []]
    }, {
      validators: [this.cijenaValidator.bind(this), this.trajanjeValidator.bind(this)]
    } as AbstractControlOptions);
    this.sortiranjeForm = this.formBuilder.group({
      sortiranjeOpcija: ['', Validators.required]
    });
  }

  ucitajSveKarticeFitnesPrograma(trenutnaStranica: number, brojElemenata: number) {
    const sortiranje = 'id,r'
    this.backendService.vratiSveKarticeFitnesPrograma(trenutnaStranica, brojElemenata, sortiranje).subscribe({
      next: (odgovor) => {
        this.listaKarticaFitnesPrograma = odgovor.kartice;
        this.trenutnaStranica = odgovor.trenutnaStranica;
        this.ukupnoElemenata = odgovor.ukupnoElemenata;
        this.brojStranica = odgovor.brojStranica;
      }
    });
  }

  promjenaStranice(event: PageEvent) {
    const novaStranica = event.pageIndex;
    const velicinaStranice = event.pageSize;
    if (this.ukljucenaPretraga)
      this.pretraga(novaStranica, velicinaStranice);
    else
      this.ucitajSveKarticeFitnesPrograma(novaStranica, velicinaStranice);
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

  cijenaValidator(group: FormGroup) {
    const minCijena = group.get('minCijena')?.value;
    const maxCijena = group.get('maxCijena')?.value;
    if (!minCijena && !maxCijena)
      return null;
    if ((minCijena && !maxCijena) || (!minCijena && maxCijena)) {
      group.get('maxCijena')?.setErrors({ required: true });
      return { invalidRange: true };
    }
    if (isNaN(maxCijena)) {
      group.get('maxCijena')?.setErrors({ pattern: true });
      return { invalidRange: true };
    }
    if (+minCijena >= +maxCijena) {
      group.get('maxCijena')?.setErrors({ invalidRange: true });
      return { invalidRange: true };
    }
    group.get('maxCijena')?.setErrors(null);
    return null;
  }

  trajanjeValidator(group: FormGroup) {
    const minTrajanje = group.get('minTrajanje')?.value;
    const maxTrajanje = group.get('maxTrajanje')?.value;
    if (!minTrajanje && !maxTrajanje)
      return null;
    if ((minTrajanje && !maxTrajanje) || (!minTrajanje && maxTrajanje)) {
      group.get('maxTrajanje')?.setErrors({ required: true });
      return { invalidRange: true };
    }
    if (isNaN(maxTrajanje)) {
      group.get('maxTrajanje')?.setErrors({ pattern: true });
      return { invalidRange: true };
    }
    if (+minTrajanje >= +maxTrajanje) {
      group.get('maxTrajanje')?.setErrors({ invalidRange: true });
      return { invalidRange: true };
    }
    group.get('maxTrajanje')?.setErrors(null);
    return null;
  }

  pretraga(trenutnaStranica?: number, brojElemenata?: number): void {
    if (this.filtriranjeForm.invalid) {
      return;
    }

    if (trenutnaStranica == undefined)
      trenutnaStranica = this.trenutnaStranica;
    if (brojElemenata == undefined)
      brojElemenata = this.brojElemenata;

    const datumOdC = this.filtriranjeForm.get('datumOd')?.value;
    const datumDoC = this.filtriranjeForm.get('datumDo')?.value;
    var formatiranDatumOd: string | null = null;
    var formatiranDatumDo: string | null = null;
    const datePipe = new DatePipe('en-US');
    if (datumOdC) {
      formatiranDatumOd = datePipe.transform(datumOdC, 'yyyy-MM-dd');
    }
    if (datumDoC) {
      formatiranDatumDo = datePipe.transform(datumDoC, 'yyyy-MM-dd');
    }

    const zahtjev: PretragaZahtjev = {
      kategorijaId: this.filtriranjeForm.value.kategorija,
      korisnickoIme: this.filtriranjeForm.value.korisnickoIme,
      naziv: this.filtriranjeForm.value.naziv,
      tipLokacije: this.filtriranjeForm.value.tipLokacije,
      minCijena: this.filtriranjeForm.value.minCijena,
      maxCijena: this.filtriranjeForm.value.maxCijena,
      datumOd: formatiranDatumOd!,
      datumDo: formatiranDatumDo!,
      nivoTezine: this.filtriranjeForm.value.nivoTezine,
      minTrajanje: this.filtriranjeForm.value.minTrajanje,
      maxTrajanje: this.filtriranjeForm.value.maxTrajanje
    }
    this.backendService.pretragaKarticaFitnesPrograma(zahtjev, trenutnaStranica, brojElemenata).subscribe({
      next: (odgovor) => {
        this.listaKarticaFitnesPrograma = odgovor.pronadjeneKartice;
        this.trenutnaStranica = odgovor.trenutnaStranica;
        this.ukupnoElemenata = odgovor.ukupnoElemenata;
        this.brojStranica = odgovor.brojStranica;
        this.ukljucenaPretraga = true;
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom pretrage!', 'Zatvori', { duration: 4000 });
      }
    });
  }

  resetujFormu() {
    this.filtriranjeForm.reset();
    this.ukljucenaPretraga = false;
  }

  sortiraj(): void {
    const sortiranje = this.sortiranjeForm.get('sortiranjeOpcija')?.value;
    this.backendService.vratiSveKarticeFitnesPrograma(this.trenutnaStranica, this.brojElemenata, sortiranje).subscribe({
      next: (odgovor) => {
        this.listaKarticaFitnesPrograma = odgovor.kartice;
        this.trenutnaStranica = odgovor.trenutnaStranica;
        this.ukupnoElemenata = odgovor.ukupnoElemenata;
        this.brojStranica = odgovor.brojStranica;
      }
    });
  }

  vratiNajnovijeVijesti(): void {
    this.backendService.vratiNajnovijeVijesti().subscribe({
      next: (odgovor) => {
        if (odgovor)
          this.vijesti = Array.from(odgovor);
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom učitavanja vijesti!' + greska, 'Zatvori', { duration: 10000 });
      }
    })
  }

}
