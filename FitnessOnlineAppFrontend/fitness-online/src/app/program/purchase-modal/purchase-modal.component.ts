import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { KupovinaInfo } from 'src/app/model/kupovinaInfo';
import { KupovinaZahtjev } from 'src/app/model/kupovinaZahtjev';
import { BackendService } from 'src/app/services/backend.service';

@Component({
  selector: 'app-purchase-modal',
  templateUrl: './purchase-modal.component.html',
  styleUrls: ['./purchase-modal.component.css']
})
export class PurchaseModalComponent implements OnInit {

  kupovinaForm!: FormGroup;

  constructor(private dialogRef: MatDialogRef<PurchaseModalComponent>, private backendService: BackendService,
    private formBuilder: FormBuilder, private snackBar: MatSnackBar, private router: Router,
    @Inject(MAT_DIALOG_DATA) public kupovinaInfo: KupovinaInfo | null) { }

  ngOnInit(): void {
    this.kupovinaForm = this.formBuilder.group({
      kupovinaOpcija: ['', Validators.required],
      brojKartice: [{ value: '', disabled: true }, [Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(5), Validators.maxLength(30)]],
      payPalRacun: [{ value: '', disabled: true }, [Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(5), Validators.maxLength(30)]]
    });
  }

  kupovinaOpcijaPromjena() {
    const odabranaOpcija = this.kupovinaForm.get('kupovinaOpcija')!.value;
    if (odabranaOpcija === 'licno') {
      this.kupovinaForm.get('brojKartice')!.setValue('');
      this.kupovinaForm.get('brojKartice')!.disable();
      this.kupovinaForm.get('payPalRacun')!.setValue('');
      this.kupovinaForm.get('payPalRacun')!.disable();
    } else if (odabranaOpcija === 'kartica') {
      this.kupovinaForm.get('brojKartice')!.enable();
      this.kupovinaForm.get('payPalRacun')!.setValue('');
      this.kupovinaForm.get('payPalRacun')!.disable();
    } else {
      this.kupovinaForm.get('payPalRacun')!.enable();
      this.kupovinaForm.get('brojKartice')!.setValue('');
      this.kupovinaForm.get('brojKartice')!.disable();
    }
  }

  kupi() {
    if (this.kupovinaForm.invalid) {
      return;
    }

    const kupovinaOpcija = this.kupovinaForm.get('kupovinaOpcija')?.value;
    const licnoPlacanje = kupovinaOpcija == 'licno';
    const kupovinaZahtjev: KupovinaZahtjev = {
      brojKartice: this.kupovinaForm.value.brojKartice,
      payPalRacun: this.kupovinaForm.value.payPalRacun,
      licno: licnoPlacanje,
      fitnesProgramId: this.kupovinaInfo!.fitnesProgramId,
      korisnickoImeKupca: this.kupovinaInfo!.korisnickoImeKupca
    }
    this.backendService.kupi(kupovinaZahtjev!).subscribe({
      next: (odgovor) => {
        this.snackBar.open(odgovor, 'Zatvori', { duration: 5000 });
        this.kupovinaForm.reset();
        this.dialogRef.close();
        this.router.navigate(['/../../personally']);
      },
      error: () => {
        this.kupovinaForm.reset();
        this.dialogRef.close();
        this.snackBar.open('Došlo je do greške prilikom kupovine!', 'Zatvori', { duration: 5000 });
      }
    });
  }

  zatvori() {
    this.dialogRef.close();
  }

}
