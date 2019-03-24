export interface IEmail {
    id?: number;
    endereco?: string;
    estabelecimentoId?: number;
}

export class Email implements IEmail {
    constructor(public id?: number, public endereco?: string, public estabelecimentoId?: number) {}
}
