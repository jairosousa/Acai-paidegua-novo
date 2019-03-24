import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

@Component({
    selector: 'jhi-horario-funcionamento-detail',
    templateUrl: './horario-funcionamento-detail.component.html'
})
export class HorarioFuncionamentoDetailComponent implements OnInit {
    horarioFuncionamento: IHorarioFuncionamento;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horarioFuncionamento }) => {
            this.horarioFuncionamento = horarioFuncionamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
