/********************************************************************************
 * Copyright (c) 2024 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package com.borkdominik.big.glsp.uml.core;

import com.borkdominik.big.glsp.uml.core.pattern.PhasePatternOperationHandler;
import com.borkdominik.big.glsp.uml.uml.representation.onto_uml_class.OntoUmlValidator;
import com.borkdominik.big.glsp.uml.uml.representation.onto_uml_class.ModelValidationHandler;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.features.core.model.GModelFactory;

import com.borkdominik.big.glsp.server.core.BGEMFDiagramModule;
import com.borkdominik.big.glsp.server.core.model.BGModelRepresentation;
import com.borkdominik.big.glsp.server.core.model.integrations.BGEMFSourceModelStorage;
import com.borkdominik.big.glsp.uml.core.gmodel.UMLGModelFactory;
import com.borkdominik.big.glsp.uml.core.model.UMLModelMigrator;
import com.borkdominik.big.glsp.uml.core.model.UMLModelRepresentation;
import com.borkdominik.big.glsp.uml.core.model.UMLSourceModelStorage;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.glsp.server.features.validation.RequestMarkersHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import com.google.inject.Singleton;

public class UMLDiagramModule extends BGEMFDiagramModule {

   @Override
   protected void configureAdditionals() {
      super.configureAdditionals();

      bind(UMLModelMigrator.class).in(Singleton.class);
   }

   @Override
   protected Class<? extends BGModelRepresentation> bindBGModelStateRepresentation() {
      return UMLModelRepresentation.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return UMLGModelFactory.class;
   }

   @Override
   protected Class<? extends BGEMFSourceModelStorage> bindSourceModelStorage() {
      return UMLSourceModelStorage.class;
   }


   @Override
   protected void configure() {
      super.configure();

      // Bind custom RequestMarkersHandler
      bind(RequestMarkersHandler.class).to(ModelValidationHandler.class);
   }

   @Override
   protected Class<? extends ModelValidator> bindModelValidator() {
      return OntoUmlValidator.class;
   }

   @Override
   protected void configureActionHandlers(MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.add(ImportModelActionHandler.class);
      bindings.add(ExportModelActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(MultiBinding<OperationHandler<?>> bindings) {
      super.configureOperationHandlers(bindings);
      bindings.add(PhasePatternOperationHandler.class);
   }

   @Override
   public String getDiagramType() {
      return "umldiagram";
   }

}
