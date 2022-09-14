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
package com.eclipsesource.uml.modelserver.commands.communication.message;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Message;

import com.eclipsesource.uml.modelserver.commands.commons.semantic.UmlSemanticElementCommand;
import com.eclipsesource.uml.modelserver.commands.util.UmlSemanticCommandUtil;

public class SetMessageNameCommand extends UmlSemanticElementCommand {

   protected String semanticUriFragment;
   protected String newName;

   public SetMessageNameCommand(final EditingDomain domain, final URI modelUri, final String semanticUriFragment,
      final String newName) {
      super(domain, modelUri);
      this.semanticUriFragment = semanticUriFragment;
      this.newName = newName;
   }

   @Override
   protected void doExecute() {
      Message messageToRename = UmlSemanticCommandUtil.getElement(umlModel, semanticUriFragment, Message.class);
      messageToRename.setName(newName);
      messageToRename.getSendEvent().setName(messageToRename.getName() + " - SendEvent");
      messageToRename.getReceiveEvent().setName(messageToRename.getName() + " - ReceiveEvent");
   }

}
