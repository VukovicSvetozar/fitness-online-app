import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { AppLayoutModule } from '../app-layout/app-layout.module';
import { ProgramRoutingModule } from './program-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppMaterialModule } from '../app-material/app-material.module';
import { MainComponent } from './personally/main/main.component';
import { CategoryModalComponent } from './personally/category-modal/category-modal.component';
import { ImageModalComponent } from './personally/image-modal/image-modal.component';
import { SpecificationModalComponent } from './personally/specification-modal/specification-modal.component';
import { FeatureModalComponent } from './personally/feature-modal/feature-modal.component';
import { PurchaseModalComponent } from './purchase-modal/purchase-modal.component';
import { CommentModalComponent } from './comment-modal/comment-modal.component';
import { ReviewComponent } from './review/review.component';
import { RecommendationComponent } from './recommendation/recommendation.component';
import { ProgramDetailsComponent } from './program-details/program-details.component';
import { LiveModalComponent } from './live-modal/live-modal.component';
import { OnlineModalComponent } from './online-modal/online-modal.component';
import { NgChartsModule } from 'ng2-charts';


@NgModule({
  declarations: [
    HomeComponent,
    MainComponent,
    CategoryModalComponent,
    ImageModalComponent,
    SpecificationModalComponent,
    FeatureModalComponent,
    PurchaseModalComponent,
    CommentModalComponent,
    ReviewComponent,
    RecommendationComponent,
    ProgramDetailsComponent,
    LiveModalComponent,
    OnlineModalComponent
  ],
  imports: [
    CommonModule,
    AppLayoutModule,
    AppMaterialModule,
    ProgramRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgChartsModule
  ]
})
export class ProgramModule { }
