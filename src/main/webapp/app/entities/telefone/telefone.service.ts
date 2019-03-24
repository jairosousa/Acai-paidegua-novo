import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITelefone } from 'app/shared/model/telefone.model';

type EntityResponseType = HttpResponse<ITelefone>;
type EntityArrayResponseType = HttpResponse<ITelefone[]>;

@Injectable({ providedIn: 'root' })
export class TelefoneService {
    public resourceUrl = SERVER_API_URL + 'api/telefones';

    constructor(protected http: HttpClient) {}

    create(telefone: ITelefone): Observable<EntityResponseType> {
        return this.http.post<ITelefone>(this.resourceUrl, telefone, { observe: 'response' });
    }

    update(telefone: ITelefone): Observable<EntityResponseType> {
        return this.http.put<ITelefone>(this.resourceUrl, telefone, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITelefone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITelefone[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
