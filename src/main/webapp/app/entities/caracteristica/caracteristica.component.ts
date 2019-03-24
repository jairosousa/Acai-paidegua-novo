import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { AccountService } from 'app/core';
import { CaracteristicaService } from './caracteristica.service';

@Component({
    selector: 'jhi-caracteristica',
    templateUrl: './caracteristica.component.html'
})
export class CaracteristicaComponent implements OnInit, OnDestroy {
    caracteristicas: ICaracteristica[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected caracteristicaService: CaracteristicaService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.caracteristicaService
            .query()
            .pipe(
                filter((res: HttpResponse<ICaracteristica[]>) => res.ok),
                map((res: HttpResponse<ICaracteristica[]>) => res.body)
            )
            .subscribe(
                (res: ICaracteristica[]) => {
                    this.caracteristicas = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCaracteristicas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICaracteristica) {
        return item.id;
    }

    registerChangeInCaracteristicas() {
        this.eventSubscriber = this.eventManager.subscribe('caracteristicaListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
