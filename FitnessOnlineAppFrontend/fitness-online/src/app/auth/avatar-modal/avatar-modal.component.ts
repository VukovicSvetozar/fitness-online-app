import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-avatar-modal',
  templateUrl: './avatar-modal.component.html',
  styleUrls: ['./avatar-modal.component.css']
})
export class AvatarModalComponent {

  izabraniAvatar: string | null = null;
  listaAvatara: string[] = [
    'assets/avatari/1.png',
    'assets/avatari/2.png',
    'assets/avatari/3.png',
    'assets/avatari/4.png',
    'assets/avatari/5.png',
    'assets/avatari/6.png',
    'assets/avatari/7.png',
    'assets/avatari/8.png',
    'assets/avatari/9.png',
    'assets/avatari/10.png',
    'assets/avatari/11.png',
    'assets/avatari/12.png',
    'assets/avatari/13.png',
    'assets/avatari/14.png',
    'assets/avatari/15.png',
    'assets/avatari/16.png',
    'assets/avatari/17.png',
    'assets/avatari/18.png',
    'assets/avatari/19.png',
    'assets/avatari/20.png',
    'assets/avatari/21.png',
    'assets/avatari/22.png',
    'assets/avatari/23.png',
    'assets/avatari/24.png',
    'assets/avatari/25.png',
    'assets/avatari/26.png',
    'assets/avatari/27.png',
    'assets/avatari/28.png',
    'assets/avatari/29.png',
    'assets/avatari/30.png',
    'assets/avatari/31.png',
    'assets/avatari/32.png',
    'assets/avatari/33.png',
    'assets/avatari/34.png',
    'assets/avatari/35.png',
    'assets/avatari/36.png'
  ];

  constructor(private dialogRef: MatDialogRef<AvatarModalComponent>) { }

  odaberiAvatar(avatar: string) {
    this.izabraniAvatar = avatar;
  }

  odustani() {
    this.dialogRef.close();
  }

  potvrdi() {
    this.dialogRef.close(this.izabraniAvatar);
  }

}
