import { Moment } from 'moment';

export interface IImagem {
    id?: number;
    title?: string;
    descricao?: string;
    imagemContentType?: string;
    imagem?: any;
    uploaded?: Moment;
    estabelecimentoId?: number;
}

export class Imagem implements IImagem {
    constructor(
        public id?: number,
        public title?: string,
        public descricao?: string,
        public imagemContentType?: string,
        public imagem?: any,
        public uploaded?: Moment,
        public estabelecimentoId?: number
    ) {}
}
