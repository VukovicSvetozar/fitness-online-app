import { NgModule } from '@angular/core';
import { Routes, RouterModule, mapToCanActivate } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from '../app-layout/menu/menu.component';
import { MainComponent } from './personally/main/main.component';
import { ProgramDetailsComponent } from './program-details/program-details.component';
import { GuardService } from '../services/guard.service';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { ReviewComponent } from './review/review.component';

const routes: Routes = [

    {
        path: '',
        component: HomeComponent,
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    },
    {
        path: 'home',
        component: HomeComponent,
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    },
    {
        path: 'personally',
        component: MainComponent,
        canActivate: mapToCanActivate([GuardService]),
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    },
    {
        path: 'recommendation',
        component: RecommendationComponent,
        canActivate: mapToCanActivate([GuardService]),
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    },
    {
        path: 'review',
        component: ReviewComponent,
        canActivate: mapToCanActivate([GuardService]),
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    },
    {
        path: 'details/:id',
        component: ProgramDetailsComponent,
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    }

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProgramRoutingModule { }
