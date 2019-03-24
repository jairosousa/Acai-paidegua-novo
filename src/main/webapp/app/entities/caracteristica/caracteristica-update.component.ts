import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from './caracteristica.service';

@Component({
    selector: 'jhi-caracteristica-update',
    templateUrl: './caracteristica-update.component.html'
})
export class CaracteristicaUpdateComponent implements OnInit {
    caracteristica: ICaracteristica;
    isSaving: boolean;

    constructor(protected caracteristicaService: CaracteristicaService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caracteristica }) => {
            this.caracteristica = caracteristica;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.caracteristica.id !== undefined) {
            this.subscribeToSaveResponse(this.caracteristicaService.update(this.caracteristica));
        } else {
            this.subscribeToSaveResponse(this.caracteristicaService.create(this.caracteristica));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaracteristica>>) {
        result.subscribe((res: HttpResponse<ICaracteristica>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
