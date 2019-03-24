import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITelefone } from 'app/shared/model/telefone.model';

@Component({
    selector: 'jhi-telefone-detail',
    templateUrl: './telefone-detail.component.html'
})
export class TelefoneDetailComponent implements OnInit {
    telefone: ITelefone;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ telefone }) => {
            this.telefone = telefone;
        });
    }

    previousState() {
        window.history.back();
    }
}
