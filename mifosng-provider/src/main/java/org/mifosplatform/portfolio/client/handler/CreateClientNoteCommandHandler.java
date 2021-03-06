package org.mifosplatform.portfolio.client.handler;

import org.mifosplatform.commands.handler.NewCommandSourceHandler;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.EntityIdentifier;
import org.mifosplatform.portfolio.client.service.ClientWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClientNoteCommandHandler implements NewCommandSourceHandler {

    private final ClientWritePlatformService clientWritePlatformService;

    @Autowired
    public CreateClientNoteCommandHandler(final ClientWritePlatformService clientWritePlatformService) {
        this.clientWritePlatformService = clientWritePlatformService;
    }

    @Transactional
    @Override
    public EntityIdentifier processCommand(final JsonCommand command) {

        return this.clientWritePlatformService.addClientNote(command.resourceId(), command);
    }
}