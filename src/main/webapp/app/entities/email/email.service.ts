import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmail } from 'app/shared/model/email.model';

type EntityResponseType = HttpResponse<IEmail>;
type EntityArrayResponseType = HttpResponse<IEmail[]>;

@Injectable({ providedIn: 'root' })
export class EmailService {
    public resourceUrl = SERVER_API_URL + 'api/emails';

    constructor(protected http: HttpClient) {}

    create(email: IEmail): Observable<EntityResponseType> {
        return this.http.post<IEmail>(this.resourceUrl, email, { observe: 'response' });
    }

    update(email: IEmail): Observable<EntityResponseType> {
        return this.http.put<IEmail>(this.resourceUrl, email, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmail[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
