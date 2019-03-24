import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';
import { GeolocalizacaoService } from './geolocalizacao.service';

@Component({
    selector: 'jhi-geolocalizacao-update',
    templateUrl: './geolocalizacao-update.component.html'
})
export class GeolocalizacaoUpdateComponent implements OnInit {
    geolocalizacao: IGeolocalizacao;
    isSaving: boolean;

    constructor(protected geolocalizacaoService: GeolocalizacaoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ geolocalizacao }) => {
            this.geolocalizacao = geolocalizacao;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.geolocalizacao.id !== undefined) {
            this.subscribeToSaveResponse(this.geolocalizacaoService.update(this.geolocalizacao));
        } else {
            this.subscribeToSaveResponse(this.geolocalizacaoService.create(this.geolocalizacao));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGeolocalizacao>>) {
        result.subscribe((res: HttpResponse<IGeolocalizacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
