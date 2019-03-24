import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEmail } from 'app/shared/model/email.model';
import { AccountService } from 'app/core';
import { EmailService } from './email.service';

@Component({
    selector: 'jhi-email',
    templateUrl: './email.component.html'
})
export class EmailComponent implements OnInit, OnDestroy {
    emails: IEmail[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected emailService: EmailService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.emailService
            .query()
            .pipe(
                filter((res: HttpResponse<IEmail[]>) => res.ok),
                map((res: HttpResponse<IEmail[]>) => res.body)
            )
            .subscribe(
                (res: IEmail[]) => {
                    this.emails = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEmail) {
        return item.id;
    }

    registerChangeInEmails() {
        this.eventSubscriber = this.eventManager.subscribe('emailListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
