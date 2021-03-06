package org.mifosplatform.useradministration.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.EntityIdentifier;
import org.mifosplatform.useradministration.service.PermissionWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMakerCheckerPermissionsCommandHandler implements NewCommandSourceHandler {

    private final PermissionWritePlatformService writePlatformService;

    @Autowired
    public UpdateMakerCheckerPermissionsCommandHandler(final PermissionWritePlatformService writePlatformService) {
        this.writePlatformService = writePlatformService;
    }

    @Transactional
    @Override
    public EntityIdentifier processCommand(final JsonCommand command) {

        return this.writePlatformService.updateMakerCheckerPermissions(command);
    }
}