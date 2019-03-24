export const enum Periodo {
    SAFRA = 'SAFRA',
    ENTRE_SAFRA = 'ENTRE_SAFRA',
    FORA_SAFRA = 'FORA_SAFRA'
}

export const enum Unidade {
    LATA = 'LATA',
    SACA = 'SACA',
    RAZA = 'RAZA'
}

export interface IBeneficiamento {
    id?: number;
    periodo?: Periodo;
    quantidade?: number;
    unidade?: Unidade;
    total?: number;
    residuoId?: number;
}

export class Beneficiamento implements IBeneficiamento {
    constructor(
        public id?: number,
        public periodo?: Periodo,
        public quantidade?: number,
        public unidade?: Unidade,
        public total?: number,
        public residuoId?: number
    ) {}
}
