import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';
import { AccountService } from 'app/core';
import { HorarioFuncionamentoService } from './horario-funcionamento.service';

@Component({
    selector: 'jhi-horario-funcionamento',
    templateUrl: './horario-funcionamento.component.html'
})
export class HorarioFuncionamentoComponent implements OnInit, OnDestroy {
    horarioFuncionamentos: IHorarioFuncionamento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected horarioFuncionamentoService: HorarioFuncionamentoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.horarioFuncionamentoService
            .query()
            .pipe(
                filter((res: HttpResponse<IHorarioFuncionamento[]>) => res.ok),
                map((res: HttpResponse<IHorarioFuncionamento[]>) => res.body)
            )
            .subscribe(
                (res: IHorarioFuncionamento[]) => {
                    this.horarioFuncionamentos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHorarioFuncionamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHorarioFuncionamento) {
        return item.id;
    }

    registerChangeInHorarioFuncionamentos() {
        this.eventSubscriber = this.eventManager.subscribe('horarioFuncionamentoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
