import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IResiduo } from 'app/shared/model/residuo.model';
import { ResiduoService } from './residuo.service';

@Component({
    selector: 'jhi-residuo-update',
    templateUrl: './residuo-update.component.html'
})
export class ResiduoUpdateComponent implements OnInit {
    residuo: IResiduo;
    isSaving: boolean;

    constructor(protected residuoService: ResiduoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ residuo }) => {
            this.residuo = residuo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.residuo.id !== undefined) {
            this.subscribeToSaveResponse(this.residuoService.update(this.residuo));
        } else {
            this.subscribeToSaveResponse(this.residuoService.create(this.residuo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResiduo>>) {
        result.subscribe((res: HttpResponse<IResiduo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
