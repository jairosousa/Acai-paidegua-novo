import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';
import { HorarioFuncionamentoService } from './horario-funcionamento.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';

@Component({
    selector: 'jhi-horario-funcionamento-update',
    templateUrl: './horario-funcionamento-update.component.html'
})
export class HorarioFuncionamentoUpdateComponent implements OnInit {
    horarioFuncionamento: IHorarioFuncionamento;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected horarioFuncionamentoService: HorarioFuncionamentoService,
        protected estabelecimentoService: EstabelecimentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ horarioFuncionamento }) => {
            this.horarioFuncionamento = horarioFuncionamento;
        });
        this.estabelecimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstabelecimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstabelecimento[]>) => response.body)
            )
            .subscribe((res: IEstabelecimento[]) => (this.estabelecimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.horarioFuncionamento.id !== undefined) {
            this.subscribeToSaveResponse(this.horarioFuncionamentoService.update(this.horarioFuncionamento));
        } else {
            this.subscribeToSaveResponse(this.horarioFuncionamentoService.create(this.horarioFuncionamento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorarioFuncionamento>>) {
        result.subscribe(
            (res: HttpResponse<IHorarioFuncionamento>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEstabelecimentoById(index: number, item: IEstabelecimento) {
        return item.id;
    }
}
