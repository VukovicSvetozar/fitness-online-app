import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BackendService } from 'src/app/services/backend.service';
import { ImageModalComponent } from '../image-modal/image-modal.component';
import { FitnesProgramSpecifikacijaZahtjev } from 'src/app/model/fitnesProgramSpecifikacijaZahtjev';
import { SpecifikacijaInfo } from 'src/app/model/specifikacijaInfo';
import { SpecifikacijaOdgovor } from 'src/app/model/specifikacijaOdgovor';

@Component({
  selector: 'app-specification-modal',
  templateUrl: './specification-modal.component.html',
  styleUrls: ['./specification-modal.component.css']
})
export class SpecificationModalComponent implements OnInit {

  specifikacijaForm!: FormGroup;
  listaSpecifikacija: SpecifikacijaOdgovor[] = [];

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<SpecificationModalComponent>,
    private backendService: BackendService, private dijalog: MatDialog,
    private snackBar: MatSnackBar, @Inject(MAT_DIALOG_DATA) public specifikacijaInfo: SpecifikacijaInfo | null) { }

  ngOnInit(): void {
    this.specifikacijaForm = this.formBuilder.group({});
    this.vratiSpecifikacijePoIdKategorije();
  }

  vratiSpecifikacijePoIdKategorije(): void {
    this.backendService.vratiSpecifikacijePoIdKategorije(this.specifikacijaInfo!.kategorijaId).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.listaSpecifikacija = Array.from(odgovor);
          this.konfigurisiFormu();
        }
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom dobijanja specifikacija!', 'Zatvori', { duration: 4000 });
      }
    })
  }

  konfigurisiFormu(): void {
    this.listaSpecifikacija.forEach(specifikacija => {
      this.specifikacijaForm.addControl(String(specifikacija.naziv), new FormControl('', [Validators.required, Validators.maxLength(50)]));
    });
  }

  odustani() {
    this.listaSpecifikacija = [];
    this.backendService.odustaniOdKreiranjaFitnesPrograma(this.specifikacijaInfo!.fitnesProgramId).subscribe({
      next: () => {
        this.snackBar.open('Odustali ste od kreiranja fitnes programa!', 'Zatvori', { duration: 5000 });
      },
      error: () => {
        this.snackBar.open('Došlo je do greške!', 'Zatvori', { duration: 5000 });
      }
    })
    this.dialogRef.close();
  }

  nastavi() {
    this.listaSpecifikacija.forEach(specifikacija => {
      const unesenaVrijednost = this.specifikacijaForm.get(specifikacija.naziv)?.value;
      const zahtjev: FitnesProgramSpecifikacijaZahtjev = {
        vrijednost: unesenaVrijednost,
        fitnesProgramId: this.specifikacijaInfo!.fitnesProgramId,
        specifikacijaId: specifikacija.id
      };
      this.dodajFitnesProgramSpecifikaciju(zahtjev);
    });
    this.otvoriSlikaDijalog();
    this.dialogRef.close();
  }

  dodajFitnesProgramSpecifikaciju(zahtjev: FitnesProgramSpecifikacijaZahtjev): void {
    this.backendService.dodajFitnesProgramSpecifikaciju(zahtjev).subscribe({
      next: () => {
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom dodavanja specifikacija fitnes programa!', 'Zatvori', { duration: 4000 });
      }
    })
  }

  otvoriSlikaDijalog(): void {
    const dijalogRef = this.dijalog.open(ImageModalComponent, {
      width: '600px',
      data: this.specifikacijaInfo?.fitnesProgramId
    });
  }

}
