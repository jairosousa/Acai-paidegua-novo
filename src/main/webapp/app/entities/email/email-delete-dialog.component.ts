import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from './email.service';

@Component({
    selector: 'jhi-email-delete-dialog',
    templateUrl: './email-delete-dialog.component.html'
})
export class EmailDeleteDialogComponent {
    email: IEmail;

    constructor(protected emailService: EmailService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'emailListModification',
                content: 'Deleted an email'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-delete-popup',
    template: ''
})
export class EmailDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ email }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.email = email;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/email', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/email', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
