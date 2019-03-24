import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IImagem } from 'app/shared/model/imagem.model';

@Component({
    selector: 'jhi-imagem-detail',
    templateUrl: './imagem-detail.component.html'
})
export class ImagemDetailComponent implements OnInit {
    imagem: IImagem;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imagem }) => {
            this.imagem = imagem;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
