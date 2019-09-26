import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { SentimentReviewService } from 'app/main/apps/sentiment-review/sentiment-review.service';
import {faInstagram, faRedditAlien, faTwitter} from "@fortawesome/free-brands-svg-icons";
import {faFacebook} from "@fortawesome/free-brands-svg-icons/faFacebook";
import {FormGroup} from "@angular/forms";

@Component({
    selector   : 'contacts-main-sidebar',
    templateUrl: './main.component.html',
    styleUrls  : ['./main.component.scss']
})
export class ContactsMainSidebarComponent implements OnInit, OnDestroy
{
    user: any;
    filterBy: string;
    faInstagram = faInstagram;
    faFacebook = faFacebook;
    faReddit = faRedditAlien;
    faTwitter = faTwitter;
    contactForm: FormGroup;

    // Private
    private _unsubscribeAll: Subject<any>;

    /**
     * Constructor
     *
     * @param {SentimentReviewService} _contactsService
     */
    constructor(
        private _contactsService: SentimentReviewService
    )
    {
        // Set the private defaults
        this._unsubscribeAll = new Subject();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Lifecycle hooks
    // -----------------------------------------------------------------------------------------------------

    /**
     * On init
     */
    ngOnInit(): void
    {
        this.filterBy = this._contactsService.filterBy || 'all';

        this._contactsService.onUserDataChanged
            .pipe(takeUntil(this._unsubscribeAll))
            .subscribe(user => {
                this.user = user;
            });
    }

    /**
     * On destroy
     */
    ngOnDestroy(): void
    {
        // Unsubscribe from all subscriptions
        this._unsubscribeAll.next();
        this._unsubscribeAll.complete();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Change the filter
     *
     * @param filter
     */
    changeFilter(filter): void
    {
        this.filterBy = filter;
        this._contactsService.onFilterChanged.next(this.filterBy);
    }
}
