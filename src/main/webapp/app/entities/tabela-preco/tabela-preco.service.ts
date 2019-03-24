import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITabelaPreco } from 'app/shared/model/tabela-preco.model';

type EntityResponseType = HttpResponse<ITabelaPreco>;
type EntityArrayResponseType = HttpResponse<ITabelaPreco[]>;

@Injectable({ providedIn: 'root' })
export class TabelaPrecoService {
    public resourceUrl = SERVER_API_URL + 'api/tabela-precos';

    constructor(protected http: HttpClient) {}

    create(tabelaPreco: ITabelaPreco): Observable<EntityResponseType> {
        return this.http.post<ITabelaPreco>(this.resourceUrl, tabelaPreco, { observe: 'response' });
    }

    update(tabelaPreco: ITabelaPreco): Observable<EntityResponseType> {
        return this.http.put<ITabelaPreco>(this.resourceUrl, tabelaPreco, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITabelaPreco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITabelaPreco[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
