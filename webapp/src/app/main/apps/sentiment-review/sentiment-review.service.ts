import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { FuseUtils } from '@fuse/utils';

import { SentimentReview } from 'app/main/apps/sentiment-review/sentiment-review.model';
import {AngularFirestore} from "@angular/fire/firestore";

@Injectable()
export class SentimentReviewService implements Resolve<any>
{
    onContactsChanged: BehaviorSubject<any>;
    onSelectedContactsChanged: BehaviorSubject<any>;
    onUserDataChanged: BehaviorSubject<any>;
    onSearchTextChanged: Subject<any>;
    onFilterChanged: Subject<any>;

    sentimentReviews: SentimentReview[];
    user: any;
    selectedContacts: string[] = [];

    searchText: string;
    filterBy = 'insta';

    /**
     * Constructor
     *
     * @param {HttpClient} _httpClient
     * @param firestore
     */
    constructor(
        private _httpClient: HttpClient,
        private firestore: AngularFirestore
    )
    {
        // Set the defaults
        this.onContactsChanged = new BehaviorSubject([]);
        this.onSelectedContactsChanged = new BehaviorSubject([]);
        this.onUserDataChanged = new BehaviorSubject([]);
        this.onSearchTextChanged = new Subject();
        this.onFilterChanged = new Subject();
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Resolver
     *
     * @param {ActivatedRouteSnapshot} route
     * @param {RouterStateSnapshot} state
     * @returns {Observable<any> | Promise<any> | any}
     */
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any
    {
        return new Promise((resolve, reject) => {

            Promise.all([
                this.getContacts(),
                this.getUserData()
            ]).then(
                ([files]) => {

                    this.onSearchTextChanged.subscribe(searchText => {
                        this.searchText = searchText;
                        this.getContacts();
                    });

                    this.onFilterChanged.subscribe(filter => {
                        this.filterBy = filter;
                        this.getContacts();
                    });

                    resolve();

                },
                reject
            );
        });
    }

    /**
     * Get sentimentReviews
     *
     * @returns {Promise<any>}
     */
    getContacts(): Promise<any>
    {
        return new Promise((resolve, reject) => {
                // this._httpClient.get('api/contacts-contacts')
                    this.firestore.collection('sentiment-store').valueChanges()
                    .subscribe((response: any) => {

                        console.log(response)

                        this.sentimentReviews = response;

                        if ( this.filterBy === 'insta' )
                        {

                            this.sentimentReviews = this.sentimentReviews.map(_sentiment => {
                                _sentiment.risklevelCurrent = _sentiment.risklevelInsta;
                                _sentiment.accuracyCurrent = _sentiment.accuracyInsta;
                                return _sentiment;
                            });
                        }
                        else if ( this.filterBy === 'fb' )
                        {

                            this.sentimentReviews = this.sentimentReviews.map(_sentiment => {
                                _sentiment.risklevelCurrent = _sentiment.risklevelFB;
                                _sentiment.accuracyCurrent = _sentiment.accuracyFB;
                                return _sentiment;
                            });
                        }
                        else if ( this.filterBy === 'reddit' )
                        {

                            this.sentimentReviews = this.sentimentReviews.map(_sentiment => {
                                _sentiment.risklevelCurrent = _sentiment.risklevelReddit;
                                _sentiment.accuracyCurrent = _sentiment.accuracyReddit;
                                return _sentiment;
                            });
                        }
                        else if ( this.filterBy === 'twitter' )
                        {

                            this.sentimentReviews = this.sentimentReviews.map(_sentiment => {
                                _sentiment.risklevelCurrent = _sentiment.risklevelTwitter;
                                _sentiment.accuracyCurrent = _sentiment.accuracyTwitter;
                                return _sentiment;
                            });
                        }

                        if ( this.searchText && this.searchText !== '' )
                        {
                            this.sentimentReviews = FuseUtils.filterArrayByString(this.sentimentReviews, this.searchText);
                        }

                        this.sentimentReviews.sort((a, b) => b.risklevelCurrent - a.risklevelCurrent );

                        this.sentimentReviews = this.sentimentReviews.map(sentiment => {
                            sentiment.avatar = sentiment.name + '.jpg';
                            return new SentimentReview(sentiment);
                        });

                        console.log(this.sentimentReviews);

                        this.onContactsChanged.next(this.sentimentReviews);
                        resolve(this.sentimentReviews);
                    }, reject);
            }
        );
    }

    /**
     * Get user data
     *
     * @returns {Promise<any>}
     */
    getUserData(): Promise<any>
    {
        return new Promise((resolve, reject) => {
                this._httpClient.get('api/contacts-user/5725a6802d10e277a0f35724')
                    .subscribe((response: any) => {
                        this.user = response;
                        this.onUserDataChanged.next(this.user);
                        resolve(this.user);
                    }, reject);
            }
        );
    }

    /**
     * Toggle selected sentimentReview by id
     *
     * @param id
     */
    toggleSelectedContact(id): void
    {
        // First, check if we already have that sentimentReview as selected...
        if ( this.selectedContacts.length > 0 )
        {
            const index = this.selectedContacts.indexOf(id);

            if ( index !== -1 )
            {
                this.selectedContacts.splice(index, 1);

                // Trigger the next event
                this.onSelectedContactsChanged.next(this.selectedContacts);

                // Return
                return;
            }
        }

        // If we don't have it, push as selected
        this.selectedContacts.push(id);

        // Trigger the next event
        this.onSelectedContactsChanged.next(this.selectedContacts);
    }

    /**
     * Toggle select all
     */
    toggleSelectAll(): void
    {
        if ( this.selectedContacts.length > 0 )
        {
            this.deselectContacts();
        }
        else
        {
            this.selectContacts();
        }
    }

    /**
     * Select sentimentReviews
     *
     * @param filterParameter
     * @param filterValue
     */
    selectContacts(filterParameter?, filterValue?): void
    {
        this.selectedContacts = [];

        // If there is no filter, select all sentimentReviews
        if ( filterParameter === undefined || filterValue === undefined )
        {
            this.selectedContacts = [];
            this.sentimentReviews.map(contact => {
                this.selectedContacts.push(contact.id);
            });
        }

        // Trigger the next event
        this.onSelectedContactsChanged.next(this.selectedContacts);
    }

    /**
     * Update sentimentReview
     *
     * @param contact
     * @returns {Promise<any>}
     */
    updateContact(contact): Promise<any>
    {
        return new Promise((resolve, reject) => {

            this._httpClient.post('api/sentimentReviews-sentimentReviews/' + contact.id, {...contact})
                .subscribe(response => {
                    this.getContacts();
                    resolve(response);
                });
        });
    }

    /**
     * Update user data
     *
     * @param userData
     * @returns {Promise<any>}
     */
    updateUserData(userData): Promise<any>
    {
        return new Promise((resolve, reject) => {
            this._httpClient.post('api/sentimentReviews-user/' + this.user.id, {...userData})
                .subscribe(response => {
                    this.getUserData();
                    this.getContacts();
                    resolve(response);
                });
        });
    }

    /**
     * Deselect sentimentReviews
     */
    deselectContacts(): void
    {
        this.selectedContacts = [];

        // Trigger the next event
        this.onSelectedContactsChanged.next(this.selectedContacts);
    }

    /**
     * Delete sentimentReview
     *
     * @param contact
     */
    deleteContact(contact): void
    {
        const contactIndex = this.sentimentReviews.indexOf(contact);
        this.sentimentReviews.splice(contactIndex, 1);
        this.onContactsChanged.next(this.sentimentReviews);
    }

    /**
     * Delete selected sentimentReviews
     */
    deleteSelectedContacts(): void
    {
        for ( const contactId of this.selectedContacts )
        {
            const contact = this.sentimentReviews.find(_contact => {
                return _contact.id === contactId;
            });
            const contactIndex = this.sentimentReviews.indexOf(contact);
            this.sentimentReviews.splice(contactIndex, 1);
        }
        this.onContactsChanged.next(this.sentimentReviews);
        this.deselectContacts();
    }

}
