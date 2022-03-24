/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.eclipsesource.uml.modelserver.commands.usecasediagram.association;

import com.eclipsesource.uml.modelserver.commands.commons.contributions.UmlCompoundCommandContribution;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;

public class AddUseCaseAssociationCommandContribution extends UmlCompoundCommandContribution {

   public static final String TYPE = "addAssociationContributuion";
   public static final String SOURCE_CLASS_URI_FRAGMENT = "sourceClassUriFragment";
   public static final String TARGET_CLASS_URI_FRAGMENT = "targetClassUriFragment";

   public static CCompoundCommand create(final String sourceClassUriFragment, final String targetClassUriFragment) {
      CCompoundCommand addAssociationCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addAssociationCommand.setType(TYPE);
      addAssociationCommand.getProperties().put(SOURCE_CLASS_URI_FRAGMENT, sourceClassUriFragment);
      addAssociationCommand.getProperties().put(TARGET_CLASS_URI_FRAGMENT, targetClassUriFragment);
      return addAssociationCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String sourceClassUriFragment = command.getProperties().get(SOURCE_CLASS_URI_FRAGMENT);
      String targetClassUriFragment = command.getProperties().get(TARGET_CLASS_URI_FRAGMENT);

      return new AddUseCaseAssociationCompoundCommand(domain, modelUri, sourceClassUriFragment, targetClassUriFragment);
   }

}
