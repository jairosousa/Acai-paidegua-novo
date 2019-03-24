export const enum TipoHorario {
    COMERCIAL = 'COMERCIAL',
    DELIVERY = 'DELIVERY'
}

export interface IHorarioFuncionamento {
    id?: number;
    tipo?: TipoHorario;
    diasSemana?: string;
    hrAbertura?: string;
    hrFechamento?: string;
    estabelecimentoId?: number;
}

export class HorarioFuncionamento implements IHorarioFuncionamento {
    constructor(
        public id?: number,
        public tipo?: TipoHorario,
        public diasSemana?: string,
        public hrAbertura?: string,
        public hrFechamento?: string,
        public estabelecimentoId?: number
    ) {}
}
