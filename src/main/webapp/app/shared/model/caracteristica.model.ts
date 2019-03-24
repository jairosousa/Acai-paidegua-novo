export interface ICaracteristica {
    id?: number;
    possuiSelo?: boolean;
    possuiRestaurante?: boolean;
    areaProducaoIsolada?: boolean;
    cobraTaxaEntrega?: boolean;
    valor?: number;
    observacao?: string;
}

export class Caracteristica implements ICaracteristica {
    constructor(
        public id?: number,
        public possuiSelo?: boolean,
        public possuiRestaurante?: boolean,
        public areaProducaoIsolada?: boolean,
        public cobraTaxaEntrega?: boolean,
        public valor?: number,
        public observacao?: string
    ) {
        this.possuiSelo = this.possuiSelo || false;
        this.possuiRestaurante = this.possuiRestaurante || false;
        this.areaProducaoIsolada = this.areaProducaoIsolada || false;
        this.cobraTaxaEntrega = this.cobraTaxaEntrega || false;
    }
}
