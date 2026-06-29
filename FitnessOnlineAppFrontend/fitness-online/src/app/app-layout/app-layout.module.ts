import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu/menu.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RouterModule } from '@angular/router';
import { AppMaterialModule } from '../app-material/app-material.module';

@NgModule({
  declarations: [
    MenuComponent,
    PageNotFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    AppMaterialModule
  ],
  exports: [
    RouterModule,
    MenuComponent,
    PageNotFoundComponent
  ]

})
export class AppLayoutModule { }
