/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { HorarioFuncionamentoService } from 'app/entities/horario-funcionamento/horario-funcionamento.service';
import { IHorarioFuncionamento, HorarioFuncionamento, TipoHorario } from 'app/shared/model/horario-funcionamento.model';

describe('Service Tests', () => {
    describe('HorarioFuncionamento Service', () => {
        let injector: TestBed;
        let service: HorarioFuncionamentoService;
        let httpMock: HttpTestingController;
        let elemDefault: IHorarioFuncionamento;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(HorarioFuncionamentoService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new HorarioFuncionamento(0, TipoHorario.COMERCIAL, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a HorarioFuncionamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new HorarioFuncionamento(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a HorarioFuncionamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipo: 'BBBBBB',
                        diasSemana: 'BBBBBB',
                        hrAbertura: 'BBBBBB',
                        hrFechamento: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of HorarioFuncionamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        tipo: 'BBBBBB',
                        diasSemana: 'BBBBBB',
                        hrAbertura: 'BBBBBB',
                        hrFechamento: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a HorarioFuncionamento', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
