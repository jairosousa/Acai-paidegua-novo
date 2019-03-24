export interface ITabelaPreco {
    id?: number;
    preco?: number;
    estabelecimentoId?: number;
    produtoId?: number;
}

export class TabelaPreco implements ITabelaPreco {
    constructor(public id?: number, public preco?: number, public estabelecimentoId?: number, public produtoId?: number) {}
}
