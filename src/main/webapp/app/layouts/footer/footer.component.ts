import { Component } from '@angular/core';

import { faFacebook, faTwitter, faInstagram, faFacebookF } from '@fortawesome/free-brands-svg-icons';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html',
    styleUrls: ['footer.scss']
})
export class FooterComponent {
    facebook = faFacebookF;
    twitter = faTwitter;
    instagram = faInstagram;
}
