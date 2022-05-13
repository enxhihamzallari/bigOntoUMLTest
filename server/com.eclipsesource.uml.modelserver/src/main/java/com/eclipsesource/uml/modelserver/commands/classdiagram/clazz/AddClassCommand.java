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
package com.eclipsesource.uml.modelserver.commands.classdiagram.clazz;

import com.eclipsesource.uml.modelserver.commands.commons.semantic.UmlSemanticElementCommand;
import com.eclipsesource.uml.modelserver.commands.util.UmlSemanticCommandUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLFactory;

public class AddClassCommand extends UmlSemanticElementCommand {

   protected final Class newClass;
   protected final Boolean isAbstract;

   public AddClassCommand(final EditingDomain domain, final URI modelUri, final Boolean isAbstract) {
      super(domain, modelUri);
      this.newClass = UMLFactory.eINSTANCE.createClass();
      this.isAbstract = isAbstract;
   }

   @Override
   protected void doExecute() {
      newClass.setIsAbstract(isAbstract);
      newClass.setName(UmlSemanticCommandUtil.getNewClassName(umlModel));
      umlModel.getPackagedElements().add(newClass);
   }

   public Class getNewClass() {
      return newClass;
   }

}
