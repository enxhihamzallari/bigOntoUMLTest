/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.eclipsesource.uml.modelserver.uml.diagram.class_diagram.commands.interface_realization;

import java.util.Optional;

import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.VisibilityKind;

import com.eclipsesource.uml.modelserver.shared.codec.codecs.EmbeddedCodec;

public final class UpdateInterfaceRealizationArgument implements EmbeddedCodec.JsonEncodable {
   private String name;
   private String label;
   private VisibilityKind visibilityKind;
   private String implementingClassifierId;
   private String contractId;

   public Optional<String> name() {
      return Optional.ofNullable(name);
   }

   public Optional<String> label() {
      return Optional.ofNullable(label);
   }

   public Optional<VisibilityKind> visibilityKind() {
      return Optional.ofNullable(visibilityKind);
   }

   public Optional<String> implementingClassifierId() {
      return Optional.ofNullable(implementingClassifierId);
   }

   public Optional<String> contractId() {
      return Optional.ofNullable(contractId);
   }

   public static final class Builder {
      private final UpdateInterfaceRealizationArgument argument = new UpdateInterfaceRealizationArgument();

      public Builder name(final String value) {
         argument.name = value;
         return this;
      }

      public Builder label(final String value) {
         argument.label = value;
         return this;
      }

      public Builder visibilityKind(final VisibilityKind value) {
         argument.visibilityKind = value;
         return this;
      }

      public Builder implementingClassifier(final BehavioredClassifier value, final EMFIdGenerator id) {
         argument.implementingClassifierId = id.getOrCreateId(value);
         return this;
      }

      public Builder contract(final Interface value, final EMFIdGenerator id) {
         argument.contractId = id.getOrCreateId(value);
         return this;
      }

      public UpdateInterfaceRealizationArgument get() {
         return argument;
      }
   }
}
