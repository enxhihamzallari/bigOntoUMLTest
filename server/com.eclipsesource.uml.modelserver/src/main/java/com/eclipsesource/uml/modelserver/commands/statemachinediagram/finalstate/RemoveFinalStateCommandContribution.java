package com.eclipsesource.uml.modelserver.commands.statemachinediagram.finalstate;

import com.eclipsesource.uml.modelserver.commands.commons.contributions.UmlCompoundCommandContribution;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class RemoveFinalStateCommandContribution extends UmlCompoundCommandContribution {

    public static final String TYPE = "removeFinalState";

    public static CCompoundCommand create(final String parentSemanticUri, final String semanticUri) {
        CCompoundCommand removeFinalStateCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
        removeFinalStateCommand.setType(TYPE);
        removeFinalStateCommand.getProperties().put(PARENT_SEMANTIC_URI_FRAGMENT, parentSemanticUri);
        removeFinalStateCommand.getProperties().put(SEMANTIC_URI_FRAGMENT, semanticUri);

        return removeFinalStateCommand;
    }

    @Override
    protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
            throws DecodingException {
        String parentSemanticUri = command.getProperties().get(PARENT_SEMANTIC_URI_FRAGMENT);
        String semanticUriFragment = command.getProperties().get(SEMANTIC_URI_FRAGMENT);

        return new RemoveFinalStateCompoundCommand(domain, modelUri, parentSemanticUri, semanticUriFragment);
    }
}
