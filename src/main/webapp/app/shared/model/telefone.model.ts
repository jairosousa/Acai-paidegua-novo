export const enum Operadora {
    CLARO = 'CLARO',
    OI = 'OI',
    TIM = 'TIM',
    VIVO = 'VIVO'
}

export interface ITelefone {
    id?: number;
    operadora?: Operadora;
    numero?: string;
    estabelecimentoId?: number;
}

export class Telefone implements ITelefone {
    constructor(public id?: number, public operadora?: Operadora, public numero?: string, public estabelecimentoId?: number) {}
}
