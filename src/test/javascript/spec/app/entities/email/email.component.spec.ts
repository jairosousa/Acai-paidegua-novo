/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EmailComponent } from 'app/entities/email/email.component';
import { EmailService } from 'app/entities/email/email.service';
import { Email } from 'app/shared/model/email.model';

describe('Component Tests', () => {
    describe('Email Management Component', () => {
        let comp: EmailComponent;
        let fixture: ComponentFixture<EmailComponent>;
        let service: EmailService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EmailComponent],
                providers: []
            })
                .overrideTemplate(EmailComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EmailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Email(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.emails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
