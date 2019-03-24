import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';

@Component({
    selector: 'jhi-beneficiamento-detail',
    templateUrl: './beneficiamento-detail.component.html'
})
export class BeneficiamentoDetailComponent implements OnInit {
    beneficiamento: IBeneficiamento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ beneficiamento }) => {
            this.beneficiamento = beneficiamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
