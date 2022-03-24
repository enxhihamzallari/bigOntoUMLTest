package com.eclipsesource.uml.modelserver.commands.usecasediagram.extendedge;

import com.eclipsesource.uml.modelserver.commands.commons.contributions.UmlCompoundCommandContribution;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class AddExtendCommandContribution extends UmlCompoundCommandContribution {

    public static final String TYPE = "addExtendContribution";
    public static final String EXTENDING_USECASE_URI_FRAGMENT = "extendingUriUseCaseUriFragment";
    public static final String EXTENDED_USECASE_URI_FRAGMENT = "extendedUriUseCaseUriFragment";

    public static CCompoundCommand create(final String extendingUseCaseUri, final String extendedUseCaseUri) {
        CCompoundCommand extendedUseCaseCompoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
        extendedUseCaseCompoundCommand.setType(TYPE);
        extendedUseCaseCompoundCommand.getProperties().put(EXTENDING_USECASE_URI_FRAGMENT, extendingUseCaseUri);
        extendedUseCaseCompoundCommand.getProperties().put(EXTENDED_USECASE_URI_FRAGMENT, extendedUseCaseUri);
        return extendedUseCaseCompoundCommand;
    }

    @Override
    protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
        throws DecodingException {
        String extendingUseCaseUri = command.getProperties().get(EXTENDING_USECASE_URI_FRAGMENT);
        String extendedUseCaseUri = command.getProperties().get(EXTENDED_USECASE_URI_FRAGMENT);
        return new AddExtendCompoundCommand(domain, modelUri, extendingUseCaseUri, extendedUseCaseUri);
    }
}
