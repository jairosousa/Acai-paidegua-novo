import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRedeSociais } from 'app/shared/model/rede-sociais.model';
import { RedeSociaisService } from './rede-sociais.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';

@Component({
    selector: 'jhi-rede-sociais-update',
    templateUrl: './rede-sociais-update.component.html'
})
export class RedeSociaisUpdateComponent implements OnInit {
    redeSociais: IRedeSociais;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected redeSociaisService: RedeSociaisService,
        protected estabelecimentoService: EstabelecimentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ redeSociais }) => {
            this.redeSociais = redeSociais;
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
        if (this.redeSociais.id !== undefined) {
            this.subscribeToSaveResponse(this.redeSociaisService.update(this.redeSociais));
        } else {
            this.subscribeToSaveResponse(this.redeSociaisService.create(this.redeSociais));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRedeSociais>>) {
        result.subscribe((res: HttpResponse<IRedeSociais>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
