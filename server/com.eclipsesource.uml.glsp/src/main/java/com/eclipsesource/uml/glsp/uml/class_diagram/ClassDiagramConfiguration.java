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
package com.eclipsesource.uml.glsp.uml.class_diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.diagram.BaseDiagramConfiguration;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

import com.eclipsesource.uml.glsp.uml.class_diagram.constants.ClassTypes;
import com.google.common.collect.Lists;

public class ClassDiagramConfiguration extends BaseDiagramConfiguration {

   @Override
   public String getDiagramType() { return "umldiagram"; }

   @Override
   public List<EdgeTypeHint> getEdgeTypeHints() {
      return Lists.newArrayList(
         // CLASS DIAGRAM
         createDefaultEdgeTypeHint(ClassTypes.ASSOCIATION),
         createDefaultEdgeTypeHint(ClassTypes.AGGREGATION),
         createDefaultEdgeTypeHint(ClassTypes.COMPOSITION),
         createDefaultEdgeTypeHint(ClassTypes.CLASS_GENERALIZATION));
   }

   @Override
   public EdgeTypeHint createDefaultEdgeTypeHint(final String elementId) {
      List<String> allowed;

      ArrayList<String> from;
      ArrayList<String> to;

      switch (elementId) {
         // CLASS DIAGRAM
         case ClassTypes.ASSOCIATION:
         case ClassTypes.CLASS_GENERALIZATION:
            allowed = Lists.newArrayList(ClassTypes.CLASS, ClassTypes.INTERFACE);
            return new EdgeTypeHint(elementId, true, true, true, allowed, allowed);
         case ClassTypes.COMPOSITION:
         case ClassTypes.AGGREGATION:
            allowed = Lists.newArrayList(ClassTypes.CLASS);
            return new EdgeTypeHint(elementId, true, true, true, allowed, allowed);
         default:
            break;
      }
      return new EdgeTypeHint(elementId, true, true, true, List.of(), List.of());
   }

   @Override
   public List<ShapeTypeHint> getShapeTypeHints() {
      List<ShapeTypeHint> hints = new ArrayList<>();
      // GRAPH
      hints.add(new ShapeTypeHint(DefaultTypes.GRAPH, false, false, false, false,
         List.of(ClassTypes.CLASS,
            ClassTypes.ENUMERATION,
            ClassTypes.INTERFACE,
            ClassTypes.ABSTRACT_CLASS)));

      // CLASS DIAGRAM
      hints.add(new ShapeTypeHint(ClassTypes.CLASS, true, true, false, false,
         List.of(ClassTypes.PROPERTY)));
      hints.add(new ShapeTypeHint(ClassTypes.ABSTRACT_CLASS, true, true, false, false,
         List.of(ClassTypes.PROPERTY)));
      hints.add(new ShapeTypeHint(ClassTypes.INTERFACE, true, true, false, false,
         List.of(ClassTypes.PROPERTY)));
      hints.add(new ShapeTypeHint(ClassTypes.PROPERTY, false, true, false, true,
         List.of()));
      hints.add(new ShapeTypeHint(ClassTypes.PROPERTY, true, true, false, false));

      return hints;
   }

   @Override
   public Map<String, EClass> getTypeMappings() {
      Map<String, EClass> mappings = DefaultTypes.getDefaultTypeMappings();

      // CLASS DIAGRAM
      mappings.put(ClassTypes.ICON_CLASS, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(ClassTypes.CLASS, GraphPackage.Literals.GNODE);
      mappings.put(ClassTypes.ICON_ENUMERATION, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(ClassTypes.ENUMERATION, GraphPackage.Literals.GNODE);
      mappings.put(ClassTypes.INTERFACE, GraphPackage.Literals.GNODE);
      mappings.put(ClassTypes.PROPERTY, GraphPackage.Literals.GLABEL);
      mappings.put(ClassTypes.ASSOCIATION, GraphPackage.Literals.GEDGE);
      mappings.put(ClassTypes.AGGREGATION, GraphPackage.Literals.GEDGE);
      mappings.put(ClassTypes.COMPOSITION, GraphPackage.Literals.GEDGE);
      mappings.put(ClassTypes.CLASS_GENERALIZATION, GraphPackage.Literals.GEDGE);

      // SHARED WITH DEPLOYMENT AND USECASE
      mappings.put(ClassTypes.LABEL_EDGE_MULTIPLICITY, GraphPackage.Literals.GLABEL);

      // SHARED WITH OBJECT DIAGRAM
      mappings.put(ClassTypes.ATTRIBUTE, GraphPackage.Literals.GLABEL);
      mappings.put(ClassTypes.PROPERTY, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(ClassTypes.ICON_PROPERTY, GraphPackage.Literals.GCOMPARTMENT);
      mappings.put(ClassTypes.LABEL_PROPERTY_NAME, GraphPackage.Literals.GLABEL);
      mappings.put(ClassTypes.LABEL_PROPERTY_TYPE, GraphPackage.Literals.GLABEL);
      mappings.put(ClassTypes.LABEL_PROPERTY_MULTIPLICITY, GraphPackage.Literals.GLABEL);

      return mappings;
   }

   @Override
   public ServerLayoutKind getLayoutKind() { return ServerLayoutKind.MANUAL; }

}
