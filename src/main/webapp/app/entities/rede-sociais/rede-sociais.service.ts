import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRedeSociais } from 'app/shared/model/rede-sociais.model';

type EntityResponseType = HttpResponse<IRedeSociais>;
type EntityArrayResponseType = HttpResponse<IRedeSociais[]>;

@Injectable({ providedIn: 'root' })
export class RedeSociaisService {
    public resourceUrl = SERVER_API_URL + 'api/rede-sociais';

    constructor(protected http: HttpClient) {}

    create(redeSociais: IRedeSociais): Observable<EntityResponseType> {
        return this.http.post<IRedeSociais>(this.resourceUrl, redeSociais, { observe: 'response' });
    }

    update(redeSociais: IRedeSociais): Observable<EntityResponseType> {
        return this.http.put<IRedeSociais>(this.resourceUrl, redeSociais, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRedeSociais>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRedeSociais[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
