package org.mifosplatform.organisation.monetary.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.EntityIdentifier;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.organisation.monetary.data.ConfigurationData;
import org.mifosplatform.organisation.monetary.service.OrganisationCurrencyReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/currencies")
@Component
@Scope("singleton")
public class CurrenciesApiResource {

    private final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList("selectedCurrencyOptions", "currencyOptions"));

    private final String resourceNameForPermissions = "CURRENCY";

    private final PlatformSecurityContext context;
    private final OrganisationCurrencyReadPlatformService readPlatformService;
    private final DefaultToApiJsonSerializer<ConfigurationData> toApiJsonSerializer;
    private final ApiRequestParameterHelper apiRequestParameterHelper;
    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;

    @Autowired
    public CurrenciesApiResource(final PlatformSecurityContext context, final OrganisationCurrencyReadPlatformService readPlatformService,
            final DefaultToApiJsonSerializer<ConfigurationData> toApiJsonSerializer,
            final ApiRequestParameterHelper apiRequestParameterHelper,
            final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService) {
        this.context = context;
        this.readPlatformService = readPlatformService;
        this.toApiJsonSerializer = toApiJsonSerializer;
        this.apiRequestParameterHelper = apiRequestParameterHelper;
        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String retrieveCurrencies(@Context final UriInfo uriInfo) {

        context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);

        final ConfigurationData configurationData = this.readPlatformService.retrieveCurrencyConfiguration();

        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
        return this.toApiJsonSerializer.serialize(settings, configurationData, RESPONSE_DATA_PARAMETERS);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public String updateCurrencies(final String apiRequestBodyAsJson) {

        final EntityIdentifier result = this.commandsSourceWritePlatformService.logCommandSource("UPDATE", "CURRENCY", "UPDATE", "currencies",
                null, apiRequestBodyAsJson);

        return this.toApiJsonSerializer.serialize(result);
    }
}