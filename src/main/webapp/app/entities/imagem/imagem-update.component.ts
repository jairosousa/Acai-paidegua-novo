import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IImagem } from 'app/shared/model/imagem.model';
import { ImagemService } from './imagem.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';

@Component({
    selector: 'jhi-imagem-update',
    templateUrl: './imagem-update.component.html'
})
export class ImagemUpdateComponent implements OnInit {
    imagem: IImagem;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];
    uploaded: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected imagemService: ImagemService,
        protected estabelecimentoService: EstabelecimentoService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ imagem }) => {
            this.imagem = imagem;
            this.uploaded = this.imagem.uploaded != null ? this.imagem.uploaded.format(DATE_TIME_FORMAT) : null;
        });
        this.estabelecimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstabelecimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstabelecimento[]>) => response.body)
            )
            .subscribe((res: IEstabelecimento[]) => (this.estabelecimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.imagem, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.imagem.uploaded = this.uploaded != null ? moment(this.uploaded, DATE_TIME_FORMAT) : null;
        if (this.imagem.id !== undefined) {
            this.subscribeToSaveResponse(this.imagemService.update(this.imagem));
        } else {
            this.subscribeToSaveResponse(this.imagemService.create(this.imagem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IImagem>>) {
        result.subscribe((res: HttpResponse<IImagem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
