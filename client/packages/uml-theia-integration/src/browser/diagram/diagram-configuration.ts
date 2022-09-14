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

import {
    configureDiagramServer,
    GLSPDiagramConfiguration,
    TheiaDiagramServer
} from "@eclipse-glsp/theia-integration/lib/browser";
import { createUmlDiagramContainer } from "@eclipsesource/uml-sprotty/lib";
import { Container, injectable,inject } from "inversify";
import { connectTheiaDiagramOutlineView, TheiaDiagramOutlineFactory } from "../theia-diagram-outline-view";
import { UmlLanguage } from "../../common/uml-language";
import { UmlGLSPTheiaDiagramServer } from "./diagram-server";
import { DiagramOutlineViewService } from "@eclipsesource/uml-sprotty/lib/features/diagram-outline-view/diagram-outline-view-service";

@injectable()
export class UmlDiagramConfiguration extends GLSPDiagramConfiguration {
    @inject(TheiaDiagramOutlineFactory) protected readonly theiaDiagramOutlineFactory: () => DiagramOutlineViewService;

    diagramType: string = UmlLanguage.diagramType;

    doCreateContainer(widgetId: string): Container {
        const container = createUmlDiagramContainer(widgetId);
        configureDiagramServer(container, UmlGLSPTheiaDiagramServer);
        container.bind(TheiaDiagramServer).toService(UmlGLSPTheiaDiagramServer);

        connectTheiaDiagramOutlineView(container, this.theiaDiagramOutlineFactory);
        return container;
    }
}
