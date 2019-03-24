export interface IProduto {
    id?: number;
    nome?: string;
    descricao?: string;
}

export class Produto implements IProduto {
    constructor(public id?: number, public nome?: string, public descricao?: string) {}
}
