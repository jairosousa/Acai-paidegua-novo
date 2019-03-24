import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRedeSociais } from 'app/shared/model/rede-sociais.model';
import { AccountService } from 'app/core';
import { RedeSociaisService } from './rede-sociais.service';

@Component({
    selector: 'jhi-rede-sociais',
    templateUrl: './rede-sociais.component.html'
})
export class RedeSociaisComponent implements OnInit, OnDestroy {
    redeSociais: IRedeSociais[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected redeSociaisService: RedeSociaisService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.redeSociaisService
            .query()
            .pipe(
                filter((res: HttpResponse<IRedeSociais[]>) => res.ok),
                map((res: HttpResponse<IRedeSociais[]>) => res.body)
            )
            .subscribe(
                (res: IRedeSociais[]) => {
                    this.redeSociais = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRedeSociais();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRedeSociais) {
        return item.id;
    }

    registerChangeInRedeSociais() {
        this.eventSubscriber = this.eventManager.subscribe('redeSociaisListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
