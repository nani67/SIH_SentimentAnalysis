import {Component, Inject, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {SentimentReview} from 'app/main/apps/sentiment-review/sentiment-review.model';
import {Label} from 'ng2-charts';
import {ChartDataSets, ChartType, RadialChartOptions} from 'chart.js';


@Component({
    selector     : 'contacts-contact-form-dialog',
    templateUrl  : './contact-form.component.html',
    styleUrls    : ['./contact-form.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class ContactsContactFormDialogComponent
{
    action: string;
    sentimentReview: SentimentReview;
    radarChartOptions: RadialChartOptions = {
        responsive: true,
    };

    dialogTitle: string;
    sentiChartLabels: Label[] = ['Instagram', 'Facebook', 'Reddit', 'Twitter'];
    radarChartData: ChartDataSets[];
    radarChartType: ChartType = 'radar';

    /**
     * Constructor
     *
     * @param {MatDialogRef<ContactsContactFormDialogComponent>} matDialogRef
     * @param _data
     * @param {FormBuilder} _formBuilder
     */
    constructor(
        public matDialogRef: MatDialogRef<ContactsContactFormDialogComponent>,
        @Inject(MAT_DIALOG_DATA) private _data: any,
        private _formBuilder: FormBuilder
    )
    {
        // Set the defaults
        this.action = _data.action;

        console.log(_data);

        this.sentimentReview = _data.sentimentReview;
        console.log(this.sentimentReview);

        const {risklevelInsta, risklevelFB, risklevelReddit, risklevelTwitter} = this.sentimentReview;
        this.radarChartData =  [
        { data: [risklevelInsta,
                risklevelFB,
                risklevelReddit,
                risklevelTwitter
            ], lineTension: 0.4, label: 'Sentiment Plot'}
            ];
        console.log(this.radarChartData);

    }



}
