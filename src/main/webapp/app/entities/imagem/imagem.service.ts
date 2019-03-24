import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImagem } from 'app/shared/model/imagem.model';

type EntityResponseType = HttpResponse<IImagem>;
type EntityArrayResponseType = HttpResponse<IImagem[]>;

@Injectable({ providedIn: 'root' })
export class ImagemService {
    public resourceUrl = SERVER_API_URL + 'api/imagems';

    constructor(protected http: HttpClient) {}

    create(imagem: IImagem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(imagem);
        return this.http
            .post<IImagem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(imagem: IImagem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(imagem);
        return this.http
            .put<IImagem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IImagem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IImagem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(imagem: IImagem): IImagem {
        const copy: IImagem = Object.assign({}, imagem, {
            uploaded: imagem.uploaded != null && imagem.uploaded.isValid() ? imagem.uploaded.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.uploaded = res.body.uploaded != null ? moment(res.body.uploaded) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((imagem: IImagem) => {
                imagem.uploaded = imagem.uploaded != null ? moment(imagem.uploaded) : null;
            });
        }
        return res;
    }
}
