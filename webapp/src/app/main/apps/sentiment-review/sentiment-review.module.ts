import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRippleModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';

import { FuseSharedModule } from '@fuse/shared.module';
import { FuseConfirmDialogModule, FuseSidebarModule } from '@fuse/components';

import { SentimentReviewComponent } from 'app/main/apps/sentiment-review/sentiment-review.component';
import { SentimentReviewService } from 'app/main/apps/sentiment-review/sentiment-review.service';
import { ContactsContactListComponent } from 'app/main/apps/sentiment-review/contact-list/contact-list.component';
import { ContactsSelectedBarComponent } from 'app/main/apps/sentiment-review/selected-bar/selected-bar.component';
import { ContactsMainSidebarComponent } from 'app/main/apps/sentiment-review/sidebars/main/main.component';
import { ContactsContactFormDialogComponent } from 'app/main/apps/sentiment-review/contact-form/contact-form.component';
import {MatChipsModule, MatDialogModule} from "@angular/material";
import {ChartsModule} from "ng2-charts";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

const routes: Routes = [
    {
        path     : '**',
        component: SentimentReviewComponent,
        resolve  : {
            contacts: SentimentReviewService
        }
    }
];

@NgModule({
    declarations   : [
        SentimentReviewComponent,
        ContactsContactListComponent,
        ContactsSelectedBarComponent,
        ContactsMainSidebarComponent,
        ContactsContactFormDialogComponent
    ],
    imports: [
        RouterModule.forChild(routes),

        MatButtonModule,
        MatCheckboxModule,
        MatDatepickerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMenuModule,
        MatRippleModule,
        MatTableModule,
        MatToolbarModule,

        FuseSharedModule,
        FuseConfirmDialogModule,
        FuseSidebarModule,
        MatChipsModule,
        MatDialogModule,
        ChartsModule,
        FontAwesomeModule
    ],
    providers      : [
        SentimentReviewService
    ],
    entryComponents: [
        ContactsContactFormDialogComponent
    ]
})
export class SentimentReviewModule
{
}
