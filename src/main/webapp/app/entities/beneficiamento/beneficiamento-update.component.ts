import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';
import { BeneficiamentoService } from './beneficiamento.service';
import { IResiduo } from 'app/shared/model/residuo.model';
import { ResiduoService } from 'app/entities/residuo';

@Component({
    selector: 'jhi-beneficiamento-update',
    templateUrl: './beneficiamento-update.component.html'
})
export class BeneficiamentoUpdateComponent implements OnInit {
    beneficiamento: IBeneficiamento;
    isSaving: boolean;

    residuos: IResiduo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected beneficiamentoService: BeneficiamentoService,
        protected residuoService: ResiduoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ beneficiamento }) => {
            this.beneficiamento = beneficiamento;
        });
        this.residuoService
            .query({ filter: 'beneficiamento-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IResiduo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResiduo[]>) => response.body)
            )
            .subscribe(
                (res: IResiduo[]) => {
                    if (!this.beneficiamento.residuoId) {
                        this.residuos = res;
                    } else {
                        this.residuoService
                            .find(this.beneficiamento.residuoId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IResiduo>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IResiduo>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IResiduo) => (this.residuos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.beneficiamento.id !== undefined) {
            this.subscribeToSaveResponse(this.beneficiamentoService.update(this.beneficiamento));
        } else {
            this.subscribeToSaveResponse(this.beneficiamentoService.create(this.beneficiamento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiamento>>) {
        result.subscribe((res: HttpResponse<IBeneficiamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResiduoById(index: number, item: IResiduo) {
        return item.id;
    }
}
