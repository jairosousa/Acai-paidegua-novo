/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { RedeSociaisDetailComponent } from 'app/entities/rede-sociais/rede-sociais-detail.component';
import { RedeSociais } from 'app/shared/model/rede-sociais.model';

describe('Component Tests', () => {
    describe('RedeSociais Management Detail Component', () => {
        let comp: RedeSociaisDetailComponent;
        let fixture: ComponentFixture<RedeSociaisDetailComponent>;
        const route = ({ data: of({ redeSociais: new RedeSociais(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [RedeSociaisDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RedeSociaisDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RedeSociaisDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.redeSociais).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
