import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaracteristica } from 'app/shared/model/caracteristica.model';

@Component({
    selector: 'jhi-caracteristica-detail',
    templateUrl: './caracteristica-detail.component.html'
})
export class CaracteristicaDetailComponent implements OnInit {
    caracteristica: ICaracteristica;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caracteristica }) => {
            this.caracteristica = caracteristica;
        });
    }

    previousState() {
        window.history.back();
    }
}
