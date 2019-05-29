import { Component } from '@angular/core';

import { faFacebook, faTwitter, faInstagram } from '@fortawesome/free-brands-svg-icons';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['footer.scss']
})
export class FooterComponent {
    facebook = faFacebook;
    twitter = faTwitter;
    instagram = faInstagram;
}
