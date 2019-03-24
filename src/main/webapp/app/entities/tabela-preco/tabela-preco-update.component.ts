import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITabelaPreco } from 'app/shared/model/tabela-preco.model';
import { TabelaPrecoService } from './tabela-preco.service';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from 'app/entities/estabelecimento';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto';

@Component({
    selector: 'jhi-tabela-preco-update',
    templateUrl: './tabela-preco-update.component.html'
})
export class TabelaPrecoUpdateComponent implements OnInit {
    tabelaPreco: ITabelaPreco;
    isSaving: boolean;

    estabelecimentos: IEstabelecimento[];

    produtos: IProduto[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tabelaPrecoService: TabelaPrecoService,
        protected estabelecimentoService: EstabelecimentoService,
        protected produtoService: ProdutoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tabelaPreco }) => {
            this.tabelaPreco = tabelaPreco;
        });
        this.estabelecimentoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstabelecimento[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstabelecimento[]>) => response.body)
            )
            .subscribe((res: IEstabelecimento[]) => (this.estabelecimentos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.produtoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduto[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduto[]>) => response.body)
            )
            .subscribe((res: IProduto[]) => (this.produtos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tabelaPreco.id !== undefined) {
            this.subscribeToSaveResponse(this.tabelaPrecoService.update(this.tabelaPreco));
        } else {
            this.subscribeToSaveResponse(this.tabelaPrecoService.create(this.tabelaPreco));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITabelaPreco>>) {
        result.subscribe((res: HttpResponse<ITabelaPreco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstabelecimentoById(index: number, item: IEstabelecimento) {
        return item.id;
    }

    trackProdutoById(index: number, item: IProduto) {
        return item.id;
    }
}
