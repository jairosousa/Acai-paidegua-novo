/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EmailUpdateComponent } from 'app/entities/email/email-update.component';
import { EmailService } from 'app/entities/email/email.service';
import { Email } from 'app/shared/model/email.model';

describe('Component Tests', () => {
    describe('Email Management Update Component', () => {
        let comp: EmailUpdateComponent;
        let fixture: ComponentFixture<EmailUpdateComponent>;
        let service: EmailService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EmailUpdateComponent]
            })
                .overrideTemplate(EmailUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmailUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Email(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.email = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Email();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.email = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
