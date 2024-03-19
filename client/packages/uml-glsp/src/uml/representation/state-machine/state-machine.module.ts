/**
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 **/
import { UMLDiagramType } from '@borkdominik-biguml/uml-protocol';
import { ContainerModule } from 'inversify';
import { registerFinalStateElement } from '../../elements/final-state/final-state.element';
import { registerPseudoStateElement } from '../../elements/pseudo-state/pseudo-state.element';
import { registerRegionElement } from '../../elements/region/region.element';
import { registerStateMachineElement } from '../../elements/state-machine/state-machine.element';
import { registerStateElement } from '../../elements/state/state.element';
import { registerTransitionElement } from '../../elements/transition/transition.element';

export const umlStateMachineDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    const context = { bind, unbind, isBound, rebind };
    registerFinalStateElement(context, UMLDiagramType.STATE_MACHINE);
    registerRegionElement(context, UMLDiagramType.STATE_MACHINE);
    registerStateElement(context, UMLDiagramType.STATE_MACHINE);
    registerStateMachineElement(context, UMLDiagramType.STATE_MACHINE);
    registerPseudoStateElement(context, UMLDiagramType.STATE_MACHINE);
    registerTransitionElement(context, UMLDiagramType.STATE_MACHINE);
});
