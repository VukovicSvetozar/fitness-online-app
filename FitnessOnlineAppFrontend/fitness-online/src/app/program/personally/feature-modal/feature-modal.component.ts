import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SpecificationModalComponent } from '../specification-modal/specification-modal.component';
import { BackendService } from 'src/app/services/backend.service';
import { LoginService } from 'src/app/services/login.service';
import { KategorijaOdgovor } from 'src/app/model/kategorijaOdgovor';
import { SpecifikacijaInfo } from 'src/app/model/specifikacijaInfo';
import { FitnesProgramZahtjev } from 'src/app/model/fitnesProgramZahtjev';

@Component({
  selector: 'app-feature-modal',
  templateUrl: './feature-modal.component.html',
  styleUrls: ['./feature-modal.component.css']
})
export class FeatureModalComponent {

  svojstvoForm!: FormGroup;
  kategorijaId: number | null = null;
  specifikacijaInfo: SpecifikacijaInfo | null = null;
  prikaziAdresu: boolean = false;
  prikaziUrl: boolean = false;

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<FeatureModalComponent>,
    private backendService: BackendService, private loginService: LoginService,
    private snackBar: MatSnackBar, private dijalog: MatDialog, @Inject(MAT_DIALOG_DATA) public odabranaKategorija: KategorijaOdgovor | null) { }

  ngOnInit(): void {
    this.svojstvoForm = this.formBuilder.group({
      naziv: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      cijena: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      trajanje: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      nivoTezine: ['', Validators.required],
      tipLokacije: ['', Validators.required],
      adresa: [null, [Validators.minLength(3), Validators.maxLength(100)]],
      url: [null, [Validators.pattern(/^(http|https):\/\/[^ "]+$/)]],
      opis: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(300)]],
    });
  }

  promjenaTipaLokacije(odabranTipLokacije: string) {
    this.prikaziAdresu = false;
    this.prikaziUrl = false;
    if (odabranTipLokacije === 'teretana' || odabranTipLokacije === 'park') {
      this.prikaziAdresu = true;
      this.svojstvoForm.value.url = null;
    } else if (odabranTipLokacije === 'online') {
      this.prikaziUrl = true;
      this.svojstvoForm.value.adresa = null;
    }
  }

  odustani() {
    this.kategorijaId = null;
    this.specifikacijaInfo = null;
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od dodavanja fitnes programa!', 'Zatvori', { duration: 5000 });
  }

  nastavi(): void {
    if (this.svojstvoForm.invalid) {
      return;
    }
    if (this.odabranaKategorija && this.odabranaKategorija.id !== undefined) {
      this.kategorijaId = this.odabranaKategorija.id;

      const zahtjev: FitnesProgramZahtjev = {
        naziv: this.svojstvoForm.value.naziv,
        opis: this.svojstvoForm.value.opis,
        cijena: this.svojstvoForm.value.cijena,
        nivoTezine: this.svojstvoForm.value.nivoTezine,
        trajanje: this.svojstvoForm.value.trajanje,
        kategorijaId: this.kategorijaId,
        korisnickoIme: this.loginService.vratiPodatke().korisnickoIme!,
        tipLokacije: this.svojstvoForm.value.tipLokacije,
        adresaLokacije: this.svojstvoForm.value.adresa,
        urlLokacije: this.svojstvoForm.value.url
      };

      this.backendService.kreirajFitnesProgram(zahtjev).subscribe({
        next: (odgovor) => {
          this.specifikacijaInfo = new SpecifikacijaInfo(this.kategorijaId!, odgovor);
          this.otvoriSpecifikacijeDijalog();
          this.dialogRef.close(this.specifikacijaInfo);
        },
        error: () => {
          this.snackBar.open('Došlo je do greške prilikom kreiranja fitnes programa!', 'Zatvori', { duration: 5000 });
        }
      });
    }
  }

  otvoriSpecifikacijeDijalog(): void {
    this.dijalog.open(SpecificationModalComponent, {
      width: '600px',
      data: this.specifikacijaInfo
    });
  }

}
