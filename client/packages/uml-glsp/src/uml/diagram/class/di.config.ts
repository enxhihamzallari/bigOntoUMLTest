/*********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 *********************************************************************************/
import { configureModelElement, GEdgeView, SCompartmentView, SEdge, SLabelView } from '@eclipse-glsp/client/lib';
import { ContainerModule } from 'inversify';

import { SEditableLabel } from '../../../graph';
import { InteractableCompartment } from '../../../graph/uml-compartment';
import { NamedElement, NamedElementView } from '../../elements';
import { UmlClassTypes } from './class.types';

export const umlClassDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    const context = { bind, unbind, isBound, rebind };

    configureModelElement(context, UmlClassTypes.CLASS, NamedElement, NamedElementView);
    configureModelElement(context, UmlClassTypes.DATA_TYPE, NamedElement, NamedElementView);
    configureModelElement(context, UmlClassTypes.ENUMERATION, NamedElement, NamedElementView);
    configureModelElement(context, UmlClassTypes.ENUMERATION_LITERAL, InteractableCompartment, SCompartmentView);
    configureModelElement(context, UmlClassTypes.INTERFACE, NamedElement, NamedElementView);
    configureModelElement(context, UmlClassTypes.OPERATION, InteractableCompartment, SCompartmentView);
    configureModelElement(context, UmlClassTypes.PACKAGE, NamedElement, NamedElementView);
    configureModelElement(context, UmlClassTypes.PROPERTY, InteractableCompartment, SCompartmentView);
    configureModelElement(context, UmlClassTypes.PROPERTY_LABEL_TYPE, SEditableLabel, SLabelView);
    configureModelElement(context, UmlClassTypes.PROPERTY_LABEL_MULTIPLICITY, SEditableLabel, SLabelView);
    configureModelElement(context, UmlClassTypes.PRIMITIVE_TYPE, NamedElement, NamedElementView);

    configureModelElement(context, UmlClassTypes.ABSTRACTION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.ASSOCIATION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.ASSOCIATION_AGGREGATION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.ASSOCIATION_COMPOSITION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.DEPENDENCY, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.INTERFACE_REALIZATION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.GENERALIZATION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.REALIZATION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.SUBSTITUTION, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.USAGE, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.PACKAGE_IMPORT, SEdge, GEdgeView);
    configureModelElement(context, UmlClassTypes.PACKAGE_MERGE, SEdge, GEdgeView);
});
