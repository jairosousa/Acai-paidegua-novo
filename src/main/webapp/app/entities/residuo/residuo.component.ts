import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResiduo } from 'app/shared/model/residuo.model';
import { AccountService } from 'app/core';
import { ResiduoService } from './residuo.service';

@Component({
    selector: 'jhi-residuo',
    templateUrl: './residuo.component.html'
})
export class ResiduoComponent implements OnInit, OnDestroy {
    residuos: IResiduo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected residuoService: ResiduoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.residuoService
            .query()
            .pipe(
                filter((res: HttpResponse<IResiduo[]>) => res.ok),
                map((res: HttpResponse<IResiduo[]>) => res.body)
            )
            .subscribe(
                (res: IResiduo[]) => {
                    this.residuos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResiduos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResiduo) {
        return item.id;
    }

    registerChangeInResiduos() {
        this.eventSubscriber = this.eventManager.subscribe('residuoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
