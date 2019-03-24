export const enum RedeSocial {
    LINKEDIN = 'LINKEDIN',
    FACEBOOK = 'FACEBOOK',
    TWITTER = 'TWITTER',
    YOUTUBE = 'YOUTUBE',
    INSTAGRAM = 'INSTAGRAM',
    GITHUB = 'GITHUB'
}

export interface IRedeSociais {
    id?: number;
    url?: string;
    nome?: RedeSocial;
    estabelecimentoId?: number;
}

export class RedeSociais implements IRedeSociais {
    constructor(public id?: number, public url?: string, public nome?: RedeSocial, public estabelecimentoId?: number) {}
}
