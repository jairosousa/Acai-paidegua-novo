export interface IGeolocalizacao {
    id?: number;
    latitude?: string;
    longitude?: string;
}

export class Geolocalizacao implements IGeolocalizacao {
    constructor(public id?: number, public latitude?: string, public longitude?: string) {}
}
