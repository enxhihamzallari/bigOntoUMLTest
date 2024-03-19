/*********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 *********************************************************************************/

import { UMLDiagramType } from '@borkdominik-biguml/uml-protocol';
import { configureModelElement, GEdge, GEdgeView } from '@eclipse-glsp/client';
import { interfaces } from 'inversify';
import { QualifiedUtil } from '../../qualified.utils';

export function registerAssociationElement(
    context: { bind: interfaces.Bind; isBound: interfaces.IsBound },
    representation: UMLDiagramType
): void {
    configureModelElement(context, QualifiedUtil.typeId(representation, 'Association'), GEdge, GEdgeView);
    configureModelElement(context, QualifiedUtil.typeId(representation, 'Aggregation'), GEdge, GEdgeView);
    configureModelElement(context, QualifiedUtil.typeId(representation, 'Composition'), GEdge, GEdgeView);
}
