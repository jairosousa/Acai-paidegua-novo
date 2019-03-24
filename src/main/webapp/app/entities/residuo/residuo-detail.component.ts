import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResiduo } from 'app/shared/model/residuo.model';

@Component({
    selector: 'jhi-residuo-detail',
    templateUrl: './residuo-detail.component.html'
})
export class ResiduoDetailComponent implements OnInit {
    residuo: IResiduo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ residuo }) => {
            this.residuo = residuo;
        });
    }

    previousState() {
        window.history.back();
    }
}
