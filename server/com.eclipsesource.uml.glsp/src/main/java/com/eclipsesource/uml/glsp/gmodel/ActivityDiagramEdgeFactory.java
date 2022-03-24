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
package com.eclipsesource.uml.glsp.gmodel;

import java.util.ArrayList;

import com.eclipsesource.uml.glsp.gmodel.AbstractGModelFactory;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GEdgePlacementBuilder;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;
import org.eclipse.glsp.graph.util.GConstants;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.ObjectNode;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.ValueSpecification;

import com.eclipsesource.uml.glsp.model.UmlModelState;
import com.eclipsesource.uml.glsp.util.UmlConfig.CSS;
import com.eclipsesource.uml.glsp.util.UmlConfig.Types;
import com.eclipsesource.uml.glsp.util.UmlIDUtil;
import com.eclipsesource.uml.modelserver.unotation.Edge;

public class ActivityDiagramEdgeFactory extends AbstractGModelFactory<ActivityEdge, GEdge> {

   public ActivityDiagramEdgeFactory(final UmlModelState modelState) {
      super(modelState);
   }

   @Override
   public GEdge create(final ActivityEdge edge) {
      ActivityNode source = edge.getSource();
      ActivityNode target = edge.getTarget();

      String sourceId = toId(source);
      String targetId = toId(target);

      if (source instanceof Pin) {
         sourceId += "_port";
      }
      if (target instanceof Pin) {
         targetId += "_port";
      }

      GEdgeBuilder builder = new GEdgeBuilder(Types.CONTROLFLOW) //
         .id(toId(edge)) //
         .addCssClass(CSS.EDGE) //
         .sourceId(sourceId) //
         .targetId(targetId) //
         .routerKind(GConstants.RouterKind.MANHATTAN);

      if (edge.getGuard() != null) {
         builder
            .add(createGuardLabel(getLiteralStringValue(edge.getGuard()), UmlIDUtil.createGuardLabelId(toId(edge)),
               0.5d));
      }
      if (edge.getWeight() != null) {
         builder.add(
            createWeightLabel(getLiteralStringValue(edge.getWeight()), UmlIDUtil.createWeightLabelId(toId(edge)),
               0.5d));
      }

      modelState.getIndex().getNotation(edge, Edge.class).ifPresent(elem -> {
         if (elem.getBendPoints() != null) {
            ArrayList<GPoint> gPoints = new ArrayList<>();
            elem.getBendPoints().forEach(p -> gPoints.add(GraphUtil.copy(p)));
            builder.addRoutingPoints(gPoints);
         }
      });
      return builder.build();
   }

   public GEdge create(final ExceptionHandler handler) {
      ExecutableNode source = handler.getProtectedNode();
      ObjectNode target = handler.getExceptionInput();

      String sourceId = toId(source);
      String targetId = toId(target) + "_port";

      GEdgeBuilder builder = new GEdgeBuilder(Types.EXCEPTIONHANDLER) //
         .id(toId(handler)) //
         .addCssClass(CSS.EDGE) //
         .addCssClass(CSS.EDGE_DIRECTED_END_TENT) //
         .sourceId(sourceId) //
         .targetId(targetId) //
         .routerKind(GConstants.RouterKind.POLYLINE);

      return builder.build();
   }

   protected GLabel createGuardLabel(final String name, final String id, final double position) {
      return createEdgeLabel(name, position, id, Types.LABEL_GUARD, GConstants.EdgeSide.TOP);
   }

   protected GLabel createWeightLabel(final String name, final String id, final double position) {
      return createEdgeLabel(name, position, id, Types.LABEL_WEIGHT, GConstants.EdgeSide.BOTTOM);
   }

   private String getLiteralStringValue(final ValueSpecification vs) {
      if (vs instanceof LiteralString) {
         return ((LiteralString) vs).getValue();
      }
      return "no string literal";

   }

   protected GLabel createEdgeLabel(final String name, final double position, final String id, final String type,
      final String side) {
      return new GLabelBuilder(type) //
         .edgePlacement(new GEdgePlacementBuilder()//
            .side(side)//
            .position(position)//
            .offset(2d) //
            .rotate(false) //
            .build())//
         .id(id) //
         .text(name).build();
   }

}
