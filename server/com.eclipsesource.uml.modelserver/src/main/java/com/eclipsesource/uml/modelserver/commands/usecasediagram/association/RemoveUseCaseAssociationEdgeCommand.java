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

import com.eclipsesource.uml.modelserver.commands.commons.notation.UmlNotationElementCommand;
import com.eclipsesource.uml.modelserver.commands.util.UmlNotationCommandUtil;
import com.eclipsesource.uml.modelserver.unotation.Edge;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;

public class RemoveUseCaseAssociationEdgeCommand extends UmlNotationElementCommand {

   protected final Edge edgeToRemove;

   public RemoveUseCaseAssociationEdgeCommand(final EditingDomain domain, final URI modelUri, final String semanticProxyUri) {
      super(domain, modelUri);
      this.edgeToRemove = UmlNotationCommandUtil.getNotationElement(modelUri, domain, semanticProxyUri, Edge.class);
   }

   @Override
   protected void doExecute() {
      umlDiagram.getElements().remove(edgeToRemove);
   }
}
