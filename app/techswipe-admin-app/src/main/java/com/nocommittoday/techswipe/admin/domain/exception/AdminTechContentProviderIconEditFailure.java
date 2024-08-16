package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.image.ImageId;

public class AdminTechContentProviderIconEditFailure extends AbstractDomainException {

    public AdminTechContentProviderIconEditFailure(TechContentProviderId id, ImageId iconId) {
        super(AdminErrorCode.TECH_CONTENT_PROVIDER_ICON_EDIT_FAILURE, "id=" + id + ", iconId=" + iconId);
    }
}
