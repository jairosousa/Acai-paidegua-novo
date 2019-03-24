export const enum Destino {
    LIXEIRO = 'LIXEIRO',
    MEIO_AMBIENTE = 'MEIO_AMBIENTE',
    OUTROS = 'OUTROS'
}

export interface IResiduo {
    id?: number;
    destino?: Destino;
    outro?: string;
    custo?: number;
    responsavel?: string;
}

export class Residuo implements IResiduo {
    constructor(public id?: number, public destino?: Destino, public outro?: string, public custo?: number, public responsavel?: string) {}
}
