import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FitnesProgramDetaljiOdgovor } from 'src/app/model/fitnesProgramDetaljiOdgovor';

@Component({
  selector: 'app-live-modal',
  templateUrl: './live-modal.component.html',
  styleUrls: ['./live-modal.component.css']
})
export class LiveModalComponent {

  constructor( @Inject(MAT_DIALOG_DATA) public odabraniProgram: FitnesProgramDetaljiOdgovor | null) { }

}
