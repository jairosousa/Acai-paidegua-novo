import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITelefone } from 'app/shared/model/telefone.model';
import { AccountService } from 'app/core';
import { TelefoneService } from './telefone.service';

@Component({
    selector: 'jhi-telefone',
    templateUrl: './telefone.component.html'
})
export class TelefoneComponent implements OnInit, OnDestroy {
    telefones: ITelefone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected telefoneService: TelefoneService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.telefoneService
            .query()
            .pipe(
                filter((res: HttpResponse<ITelefone[]>) => res.ok),
                map((res: HttpResponse<ITelefone[]>) => res.body)
            )
            .subscribe(
                (res: ITelefone[]) => {
                    this.telefones = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTelefones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITelefone) {
        return item.id;
    }

    registerChangeInTelefones() {
        this.eventSubscriber = this.eventManager.subscribe('telefoneListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
