import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './app-layout/page-not-found/page-not-found.component';

const routes: Routes = [

  {
    path: 'program',
    loadChildren: () => import('./program/program.module').then(mod => mod.ProgramModule)
  },

  {
    path: 'message',
    loadChildren: () => import('./message/message.module').then(mod => mod.MessageModule)
  },

  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(mod => mod.AuthModule)
  },

  {
    path: '',
    loadChildren: () => import('./program/program.module').then(mod => mod.ProgramModule)
  },

  { path: '**', component: PageNotFoundComponent },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
