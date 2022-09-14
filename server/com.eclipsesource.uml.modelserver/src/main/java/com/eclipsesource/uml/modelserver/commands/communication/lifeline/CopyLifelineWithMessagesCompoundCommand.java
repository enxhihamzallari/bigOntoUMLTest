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
package com.eclipsesource.uml.modelserver.commands.communication.lifeline;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;

import com.eclipsesource.uml.modelserver.commands.communication.message.CopyMessageCompoundCommand;
import com.eclipsesource.uml.modelserver.commands.communication.message.MessageCopyableProperties;
import com.eclipsesource.uml.modelserver.commands.util.UmlCommandUtil;

public class CopyLifelineWithMessagesCompoundCommand extends CompoundCommand {
   private final EditingDomain domain;
   private final URI modelUri;

   public CopyLifelineWithMessagesCompoundCommand(final EditingDomain domain, final URI modelUri,
      final List<LifelineCopyableProperties> lifelineProperties,
      final List<MessageCopyableProperties> messageProperties, final Interaction parentInteraction) {
      this.domain = domain;
      this.modelUri = modelUri;

      UmlCommandUtil.safeAppend(this, copyLifelines(lifelineProperties, parentInteraction))
         .ifPresent(command -> {
            UmlCommandUtil.safeAppend(this, copyMessages(messageProperties, command.getMappings()));
         });
   }

   private CopyLifelineCompoundCommand copyLifelines(final List<LifelineCopyableProperties> properties,
      final Interaction parentInteraction) {
      return new CopyLifelineCompoundCommand(domain, modelUri, properties, parentInteraction);
   }

   private CopyMessageCompoundCommand copyMessages(final List<MessageCopyableProperties> properties,
      final Map<String, Lifeline> lifelineMappings) {
      return new CopyMessageCompoundCommand(domain, modelUri, properties, lifelineMappings);
   }
}
