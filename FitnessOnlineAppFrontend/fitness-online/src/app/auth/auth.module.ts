import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { InfoComponent } from './info/info.component';
import { RegistrationComponent } from './registration/registration.component';
import { AppLayoutModule } from '../app-layout/app-layout.module';
import { AuthRoutingModule } from './auth-routing.module';
import { AppMaterialModule } from '../app-material/app-material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AvatarModalComponent } from './avatar-modal/avatar-modal.component';
import { ActivationComponent } from './activation/activation.component';

@NgModule({
  declarations: [
    LoginComponent,
    InfoComponent,
    RegistrationComponent,
    AvatarModalComponent,
    ActivationComponent
  ],
  imports: [
    CommonModule,
    AppLayoutModule,
    AuthRoutingModule,
    AppMaterialModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AuthModule { }
