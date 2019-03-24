export const enum Status {
    PENDENTE = 'PENDENTE',
    RECEBIDO = 'RECEBIDO',
    ANALISE = 'ANALISE'
}

export const enum Conta {
    FREE = 'FREE',
    PREMIUM = 'PREMIUM'
}

export interface IEstabelecimento {
    id?: number;
    nome?: string;
    responsavel?: string;
    status?: Status;
    conta?: Conta;
    userLogin?: string;
    userId?: number;
    enderecoId?: number;
    caracteristicaId?: number;
    beneficiamentoId?: number;
}

export class Estabelecimento implements IEstabelecimento {
    constructor(
        public id?: number,
        public nome?: string,
        public responsavel?: string,
        public status?: Status,
        public conta?: Conta,
        public userLogin?: string,
        public userId?: number,
        public enderecoId?: number,
        public caracteristicaId?: number,
        public beneficiamentoId?: number
    ) {}
}
