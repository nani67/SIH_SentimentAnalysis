import { FuseUtils } from '@fuse/utils';

export class SentimentReview
{
    id: string;
    name: string;
    lastName: string;
    avatar: string;
    department: string;
    risklevelInsta: number;
    accuracyInsta: number;
    risklevelReddit: number;
    accuracyReddit: number;
    risklevelFB: number;
    accuracyFB: number;
    risklevelTwitter: number;
    accuracyTwitter: number;
    risklevelCurrent: number;
    accuracyCurrent: number;

    /**
     * Constructor
     *
     * @param contact
     */
    constructor(contact)
    {
        {
            this.id = contact.id || FuseUtils.generateGUID();
            this.name = contact.name || '';
            this.lastName = contact.lastName || '';
            this.avatar = contact.avatar || 'profile.jpg';
            this.risklevelInsta = contact.risklevelInsta;
            this.department = contact.department;
            this.accuracyInsta = contact.accuracyInsta;
            this.risklevelReddit = contact.risklevelReddit;
            this.accuracyReddit = contact.accuracyReddit;
            this.risklevelFB = contact.risklevelFB;
            this.accuracyFB = contact.accuracyFB;
            this.risklevelTwitter = contact.risklevelTwitter;
            this.accuracyTwitter = contact.accuracyTwitter;
            this.accuracyCurrent = contact.accuracyCurrent || '-1';
            this.risklevelCurrent = contact.risklevelCurrent || '-1';
            // this.nickname = sentimentReview.nickname || '';
            // this.company = sentimentReview.company || '';
            // this.jobTitle = sentimentReview.jobTitle || '';
            // this.email = sentimentReview.email || '';
            // this.phone = sentimentReview.phone || '';
            // this.address = sentimentReview.address || '';
            // this.birthday = sentimentReview.birthday || '';
            // this.notes = sentimentReview.notes || '';
        }
    }
}
