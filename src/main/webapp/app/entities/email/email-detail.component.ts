import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmail } from 'app/shared/model/email.model';

@Component({
    selector: 'jhi-email-detail',
    templateUrl: './email-detail.component.html'
})
export class EmailDetailComponent implements OnInit {
    email: IEmail;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ email }) => {
            this.email = email;
        });
    }

    previousState() {
        window.history.back();
    }
}
