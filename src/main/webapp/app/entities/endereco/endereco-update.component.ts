import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';
import { GeolocalizacaoService } from 'app/entities/geolocalizacao';

@Component({
    selector: 'jhi-endereco-update',
    templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
    endereco: IEndereco;
    isSaving: boolean;

    gelocalizacaos: IGeolocalizacao[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected enderecoService: EnderecoService,
        protected geolocalizacaoService: GeolocalizacaoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ endereco }) => {
            this.endereco = endereco;
        });
        this.geolocalizacaoService
            .query({ filter: 'endereco-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IGeolocalizacao[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGeolocalizacao[]>) => response.body)
            )
            .subscribe(
                (res: IGeolocalizacao[]) => {
                    if (!this.endereco.gelocalizacaoId) {
                        this.gelocalizacaos = res;
                    } else {
                        this.geolocalizacaoService
                            .find(this.endereco.gelocalizacaoId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IGeolocalizacao>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IGeolocalizacao>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IGeolocalizacao) => (this.gelocalizacaos = [subRes].concat(res)),
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
        if (this.endereco.id !== undefined) {
            this.subscribeToSaveResponse(this.enderecoService.update(this.endereco));
        } else {
            this.subscribeToSaveResponse(this.enderecoService.create(this.endereco));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>) {
        result.subscribe((res: HttpResponse<IEndereco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGeolocalizacaoById(index: number, item: IGeolocalizacao) {
        return item.id;
    }
}
