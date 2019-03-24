import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';

type EntityResponseType = HttpResponse<IBeneficiamento>;
type EntityArrayResponseType = HttpResponse<IBeneficiamento[]>;

@Injectable({ providedIn: 'root' })
export class BeneficiamentoService {
    public resourceUrl = SERVER_API_URL + 'api/beneficiamentos';

    constructor(protected http: HttpClient) {}

    create(beneficiamento: IBeneficiamento): Observable<EntityResponseType> {
        return this.http.post<IBeneficiamento>(this.resourceUrl, beneficiamento, { observe: 'response' });
    }

    update(beneficiamento: IBeneficiamento): Observable<EntityResponseType> {
        return this.http.put<IBeneficiamento>(this.resourceUrl, beneficiamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBeneficiamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBeneficiamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
