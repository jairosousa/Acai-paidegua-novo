import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';
import { AccountService } from 'app/core';
import { GeolocalizacaoService } from './geolocalizacao.service';

@Component({
    selector: 'jhi-geolocalizacao',
    templateUrl: './geolocalizacao.component.html'
})
export class GeolocalizacaoComponent implements OnInit, OnDestroy {
    geolocalizacaos: IGeolocalizacao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected geolocalizacaoService: GeolocalizacaoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.geolocalizacaoService
            .query()
            .pipe(
                filter((res: HttpResponse<IGeolocalizacao[]>) => res.ok),
                map((res: HttpResponse<IGeolocalizacao[]>) => res.body)
            )
            .subscribe(
                (res: IGeolocalizacao[]) => {
                    this.geolocalizacaos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGeolocalizacaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGeolocalizacao) {
        return item.id;
    }

    registerChangeInGeolocalizacaos() {
        this.eventSubscriber = this.eventManager.subscribe('geolocalizacaoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
