import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEndereco } from 'app/shared/model/endereco.model';
import { AccountService } from 'app/core';
import { EnderecoService } from './endereco.service';

@Component({
    selector: 'jhi-endereco',
    templateUrl: './endereco.component.html'
})
export class EnderecoComponent implements OnInit, OnDestroy {
    enderecos: IEndereco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected enderecoService: EnderecoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.enderecoService
            .query()
            .pipe(
                filter((res: HttpResponse<IEndereco[]>) => res.ok),
                map((res: HttpResponse<IEndereco[]>) => res.body)
            )
            .subscribe(
                (res: IEndereco[]) => {
                    this.enderecos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEnderecos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEndereco) {
        return item.id;
    }

    registerChangeInEnderecos() {
        this.eventSubscriber = this.eventManager.subscribe('enderecoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
