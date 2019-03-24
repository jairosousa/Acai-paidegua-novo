import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';

@Component({
    selector: 'jhi-geolocalizacao-detail',
    templateUrl: './geolocalizacao-detail.component.html'
})
export class GeolocalizacaoDetailComponent implements OnInit {
    geolocalizacao: IGeolocalizacao;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ geolocalizacao }) => {
            this.geolocalizacao = geolocalizacao;
        });
    }

    previousState() {
        window.history.back();
    }
}
