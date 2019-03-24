import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITelefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';

@Component({
    selector: 'jhi-telefone-update',
    templateUrl: './telefone-update.component.html'
})
export class TelefoneUpdateComponent implements OnInit {
    telefone: ITelefone;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected telefoneService: TelefoneService,
        protected estabelecimentoService: EstabelecimentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ telefone }) => {
            this.telefone = telefone;
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
        if (this.telefone.id !== undefined) {
            this.subscribeToSaveResponse(this.telefoneService.update(this.telefone));
        } else {
            this.subscribeToSaveResponse(this.telefoneService.create(this.telefone));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITelefone>>) {
        result.subscribe((res: HttpResponse<ITelefone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
