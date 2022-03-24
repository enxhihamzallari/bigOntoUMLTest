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

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Association;

import java.util.function.Supplier;

public class AddUseCaseAssociationCompoundCommand extends CompoundCommand {

   public AddUseCaseAssociationCompoundCommand(final EditingDomain domain, final URI modelUri,
                                               final String sourceClassUriFragment, final String targetClassUriFragment) {

      // Chain semantic and notation command
      AddUseCaseAssociationCommand command = new AddUseCaseAssociationCommand(domain, modelUri, sourceClassUriFragment,
         targetClassUriFragment);
      this.append(command);
      Supplier<Association> semanticResultSupplier = command::getNewAssociation;
      this.append(new AddUseCaseAssociationEdgeCommand(domain, modelUri, semanticResultSupplier));
   }

}
