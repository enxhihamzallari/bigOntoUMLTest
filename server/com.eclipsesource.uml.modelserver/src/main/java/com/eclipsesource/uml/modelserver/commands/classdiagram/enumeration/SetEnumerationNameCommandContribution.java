package com.eclipsesource.uml.modelserver.commands.classdiagram.enumeration;

import com.eclipsesource.uml.modelserver.commands.commons.contributions.UmlSemanticCommandContribution;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class SetEnumerationNameCommandContribution extends UmlSemanticCommandContribution {

   public static final String TYPE = "setEnumerationName";
   public static final String NEW_NAME = "newName";

   public static CCommand create(final String semanticUri, final String newName) {
      CCommand setEnumerationNameCommand = CCommandFactory.eINSTANCE.createCommand();
      setEnumerationNameCommand.setType(TYPE);
      setEnumerationNameCommand.getProperties().put(SEMANTIC_URI_FRAGMENT, semanticUri);
      setEnumerationNameCommand.getProperties().put(NEW_NAME, newName);
      return setEnumerationNameCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
         throws DecodingException {
      String semanticUriFragment = command.getProperties().get(SEMANTIC_URI_FRAGMENT);
      String newName = command.getProperties().get(NEW_NAME);
      return new SetEnumerationNameCommand(domain, modelUri, semanticUriFragment, newName);
   }
}
