import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FitnesProgramDetaljiOdgovor } from 'src/app/model/fitnesProgramDetaljiOdgovor';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-online-modal',
  templateUrl: './online-modal.component.html',
  styleUrls: ['./online-modal.component.css']
})
export class OnlineModalComponent {

  videoUrl?: any;

  constructor(private _sanitizer: DomSanitizer, @Inject(MAT_DIALOG_DATA) public odabraniProgram: FitnesProgramDetaljiOdgovor | null) {
    this.videoUrl = this._sanitizer.bypassSecurityTrustResourceUrl(odabraniProgram!.urlLokacije);
  }

}