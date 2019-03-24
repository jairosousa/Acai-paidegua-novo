import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRedeSociais } from 'app/shared/model/rede-sociais.model';

@Component({
    selector: 'jhi-rede-sociais-detail',
    templateUrl: './rede-sociais-detail.component.html'
})
export class RedeSociaisDetailComponent implements OnInit {
    redeSociais: IRedeSociais;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ redeSociais }) => {
            this.redeSociais = redeSociais;
        });
    }

    previousState() {
        window.history.back();
    }
}
