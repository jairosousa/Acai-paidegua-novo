import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';

type EntityResponseType = HttpResponse<IEstabelecimento>;
type EntityArrayResponseType = HttpResponse<IEstabelecimento[]>;

@Injectable({ providedIn: 'root' })
export class EstabelecimentoService {
    public resourceUrl = SERVER_API_URL + 'api/estabelecimentos';

    constructor(protected http: HttpClient) {}

    create(estabelecimento: IEstabelecimento): Observable<EntityResponseType> {
        return this.http.post<IEstabelecimento>(this.resourceUrl, estabelecimento, { observe: 'response' });
    }

    update(estabelecimento: IEstabelecimento): Observable<EntityResponseType> {
        return this.http.put<IEstabelecimento>(this.resourceUrl, estabelecimento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstabelecimento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstabelecimento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
