import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITabelaPreco } from 'app/shared/model/tabela-preco.model';

@Component({
    selector: 'jhi-tabela-preco-detail',
    templateUrl: './tabela-preco-detail.component.html'
})
export class TabelaPrecoDetailComponent implements OnInit {
    tabelaPreco: ITabelaPreco;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tabelaPreco }) => {
            this.tabelaPreco = tabelaPreco;
        });
    }

    previousState() {
        window.history.back();
    }
}
