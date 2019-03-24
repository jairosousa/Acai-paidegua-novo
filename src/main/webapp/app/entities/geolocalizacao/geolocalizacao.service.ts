import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';

type EntityResponseType = HttpResponse<IGeolocalizacao>;
type EntityArrayResponseType = HttpResponse<IGeolocalizacao[]>;

@Injectable({ providedIn: 'root' })
export class GeolocalizacaoService {
    public resourceUrl = SERVER_API_URL + 'api/geolocalizacaos';

    constructor(protected http: HttpClient) {}

    create(geolocalizacao: IGeolocalizacao): Observable<EntityResponseType> {
        return this.http.post<IGeolocalizacao>(this.resourceUrl, geolocalizacao, { observe: 'response' });
    }

    update(geolocalizacao: IGeolocalizacao): Observable<EntityResponseType> {
        return this.http.put<IGeolocalizacao>(this.resourceUrl, geolocalizacao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGeolocalizacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGeolocalizacao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
