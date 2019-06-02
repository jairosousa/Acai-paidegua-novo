import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { Conta, IEstabelecimento, Status } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from './estabelecimento.service';
import { IUser, UserService } from 'app/core';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from 'app/entities/caracteristica';
import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';
import { BeneficiamentoService } from 'app/entities/beneficiamento';

@Component({
    selector: 'jhi-estabelecimento-update',
    templateUrl: './estabelecimento-update.component.html'
})
export class EstabelecimentoUpdateComponent implements OnInit {
    estabelecimento: IEstabelecimento;
    isSaving: boolean;

    users: IUser[];

    enderecos: IEndereco[];

    caracteristicas: ICaracteristica[];

    beneficiamentos: IBeneficiamento[];

    currentAction: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected estabelecimentoService: EstabelecimentoService,
        protected userService: UserService,
        protected enderecoService: EnderecoService,
        protected caracteristicaService: CaracteristicaService,
        protected beneficiamentoService: BeneficiamentoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.setCurrentyAction();
        this.activatedRoute.data.subscribe(({ estabelecimento }) => {
            this.estabelecimento = estabelecimento;
            if (!this.estabelecimento.id) {
                this.estabelecimento.status = Status.PENDENTE;
                this.estabelecimento.conta = Conta.FREE;
            }
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.enderecoService
            .query({ filter: 'estabelecimento-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IEndereco[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEndereco[]>) => response.body)
            )
            .subscribe(
                (res: IEndereco[]) => {
                    if (!this.estabelecimento.enderecoId) {
                        this.enderecos = res;
                    } else {
                        this.enderecoService
                            .find(this.estabelecimento.enderecoId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IEndereco>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IEndereco>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IEndereco) => (this.enderecos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.caracteristicaService
            .query({ filter: 'estabelecimento-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICaracteristica[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICaracteristica[]>) => response.body)
            )
            .subscribe(
                (res: ICaracteristica[]) => {
                    if (!this.estabelecimento.caracteristicaId) {
                        this.caracteristicas = res;
                    } else {
                        this.caracteristicaService
                            .find(this.estabelecimento.caracteristicaId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICaracteristica>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICaracteristica>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICaracteristica) => (this.caracteristicas = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.beneficiamentoService
            .query({ filter: 'estabelecimento-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBeneficiamento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBeneficiamento[]>) => response.body)
            )
            .subscribe(
                (res: IBeneficiamento[]) => {
                    if (!this.estabelecimento.beneficiamentoId) {
                        this.beneficiamentos = res;
                    } else {
                        this.beneficiamentoService
                            .find(this.estabelecimento.beneficiamentoId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBeneficiamento>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBeneficiamento>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBeneficiamento) => (this.beneficiamentos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estabelecimento.id !== undefined) {
            this.subscribeToSaveResponse(this.estabelecimentoService.update(this.estabelecimento));
        } else {
            this.subscribeToSaveResponse(this.estabelecimentoService.create(this.estabelecimento));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstabelecimento>>) {
        result.subscribe((res: HttpResponse<IEstabelecimento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }

    trackCaracteristicaById(index: number, item: ICaracteristica) {
        return item.id;
    }

    trackBeneficiamentoById(index: number, item: IBeneficiamento) {
        return item.id;
    }

    private setCurrentyAction() {
        if (this.activatedRoute.snapshot.routeConfig.path === 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
