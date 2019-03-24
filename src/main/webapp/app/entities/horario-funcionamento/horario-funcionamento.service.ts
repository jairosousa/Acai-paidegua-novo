import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

type EntityResponseType = HttpResponse<IHorarioFuncionamento>;
type EntityArrayResponseType = HttpResponse<IHorarioFuncionamento[]>;

@Injectable({ providedIn: 'root' })
export class HorarioFuncionamentoService {
    public resourceUrl = SERVER_API_URL + 'api/horario-funcionamentos';

    constructor(protected http: HttpClient) {}

    create(horarioFuncionamento: IHorarioFuncionamento): Observable<EntityResponseType> {
        return this.http.post<IHorarioFuncionamento>(this.resourceUrl, horarioFuncionamento, { observe: 'response' });
    }

    update(horarioFuncionamento: IHorarioFuncionamento): Observable<EntityResponseType> {
        return this.http.put<IHorarioFuncionamento>(this.resourceUrl, horarioFuncionamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHorarioFuncionamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHorarioFuncionamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
