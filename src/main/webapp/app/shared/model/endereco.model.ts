export interface IEndereco {
    id?: number;
    cep?: string;
    logradouro?: string;
    complemento?: string;
    numero?: string;
    bairro?: string;
    cidade?: string;
    uf?: string;
    gelocalizacaoId?: number;
}

export class Endereco implements IEndereco {
    constructor(
        public id?: number,
        public cep?: string,
        public logradouro?: string,
        public complemento?: string,
        public numero?: string,
        public bairro?: string,
        public cidade?: string,
        public uf?: string,
        public gelocalizacaoId?: number
    ) {}
}
