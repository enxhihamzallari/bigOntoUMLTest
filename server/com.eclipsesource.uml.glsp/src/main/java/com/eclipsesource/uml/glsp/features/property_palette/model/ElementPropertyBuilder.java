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
package com.eclipsesource.uml.glsp.features.property_palette.model;

import java.util.ArrayList;
import java.util.List;

public class ElementPropertyBuilder<TPropertyType extends Enum<TPropertyType>> {
   private final String elementId;
   private final List<ElementPropertyItem> items = new ArrayList<>();

   public ElementPropertyBuilder(final String elementId) {
      this.elementId = elementId;
   }

   public ElementPropertyBuilder<TPropertyType> text(final TPropertyType property, final String label,
      final String value) {
      return text(elementId, property, label, value);
   }

   public ElementPropertyBuilder<TPropertyType> text(final String elementId, final TPropertyType property,
      final String label,
      final String value) {
      items.add(new ElementTextPropertyItem(elementId, property.name(), label, value));
      return this;
   }

   public ElementPropertyBuilder<TPropertyType> bool(final TPropertyType property, final String label,
      final boolean value) {
      return bool(elementId, property, label, value);
   }

   public ElementPropertyBuilder<TPropertyType> bool(final String elementId, final TPropertyType property,
      final String label,
      final boolean value) {
      items.add(new ElementBoolPropertyItem(elementId, property.name(), label, value));
      return this;
   }

   public ElementPropertyBuilder<TPropertyType> choice(final TPropertyType property, final String label,
      final List<ElementChoicePropertyItem.Choice> choices,
      final String choice) {
      return choice(elementId, property, label, choices, choice);
   }

   public ElementPropertyBuilder<TPropertyType> choice(final String elementId, final TPropertyType property,
      final String label,
      final List<ElementChoicePropertyItem.Choice> choices, final String choice) {
      items.add(new ElementChoicePropertyItem.Builder(elementId, property.name()).label(label).choices(choices)
         .choice(choice).build());
      return this;
   }

   public ElementPropertyBuilder<TPropertyType> reference(final TPropertyType property, final String label,
      final List<ElementReferencePropertyItem.Reference> references) {
      return reference(elementId, property, label, references, List.of(), false, false);
   }

   public ElementPropertyBuilder<TPropertyType> reference(final TPropertyType property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates) {
      return reference(elementId, property, label, references, creates, false, false);
   }

   public ElementPropertyBuilder<TPropertyType> reference(final TPropertyType property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable) {
      return reference(elementId, property, label, references, creates, isOrderable, false);
   }

   public ElementPropertyBuilder<TPropertyType> reference(final TPropertyType property, final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable, final Boolean isAutocomplete) {
      return reference(elementId, property, label, references, creates, isOrderable, isAutocomplete);
   }

   public ElementPropertyBuilder<TPropertyType> reference(final String elementId, final TPropertyType property,
      final String label,
      final List<ElementReferencePropertyItem.Reference> references,
      final List<ElementReferencePropertyItem.CreateReference> creates,
      final Boolean isOrderable, final Boolean isAutocomplete) {
      items.add(
         new ElementReferencePropertyItem.Builder(elementId, property.name()).label(label).references(references)
            .creates(creates).isOrderable(isOrderable)
            .isAutocomplete(isAutocomplete).build());
      return this;
   }

   public ElementPropertyBuilder<TPropertyType> choice(final ElementChoicePropertyItem item) {
      items.add(item);
      return this;
   }

   public ElementPropertyBuilder<TPropertyType> reference(final ElementReferencePropertyItem item) {
      items.add(item);
      return this;
   }

   public List<ElementPropertyItem> items() {
      return items;
   }

}
