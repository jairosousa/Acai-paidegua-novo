import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';

@Component({
    selector: 'jhi-estabelecimento-detail',
    templateUrl: './estabelecimento-detail.component.html'
})
export class EstabelecimentoDetailComponent implements OnInit {
    estabelecimento: IEstabelecimento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estabelecimento }) => {
            this.estabelecimento = estabelecimento;
        });
    }

    previousState() {
        window.history.back();
    }
}
