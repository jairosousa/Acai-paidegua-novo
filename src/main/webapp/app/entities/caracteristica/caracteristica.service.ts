import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';

type EntityResponseType = HttpResponse<ICaracteristica>;
type EntityArrayResponseType = HttpResponse<ICaracteristica[]>;

@Injectable({ providedIn: 'root' })
export class CaracteristicaService {
    public resourceUrl = SERVER_API_URL + 'api/caracteristicas';

    constructor(protected http: HttpClient) {}

    create(caracteristica: ICaracteristica): Observable<EntityResponseType> {
        return this.http.post<ICaracteristica>(this.resourceUrl, caracteristica, { observe: 'response' });
    }

    update(caracteristica: ICaracteristica): Observable<EntityResponseType> {
        return this.http.put<ICaracteristica>(this.resourceUrl, caracteristica, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICaracteristica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICaracteristica[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
