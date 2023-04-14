/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
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
 ********************************************************************************/
import '../css/colors.css';

import { ModelServerConfig } from '@borkdominik-biguml/uml-modelserver/lib/config';
import * as vscode from 'vscode';
import { createContainer } from './di.config';
import { TYPES, VSCODE_TYPES } from './di.types';
import { UVGlspConnector } from './glsp/connection/uv-glsp-connector';
import { UVGlspServer } from './glsp/connection/uv-glsp-server';
import { GlspServerConfig, launchGLSPServer } from './glsp/launcher/glsp-server-launcher';
import { VSCodeSettings } from './language';
import { launchModelServer } from './modelserver/launcher/modelserver-launcher';
import { freePort } from './utils/server';
import { configureDefaultCommands } from './vscode/command/default-commands';

const modelServerRoute = '/api/v2/';

export async function activate(context: vscode.ExtensionContext): Promise<void> {
    const glspServerConfig: GlspServerConfig = {
        port: +(process.env.UML_GLSP_SERVER_PORT ?? (await freePort()))
    };

    const modelServerPort = +(process.env.UML_MODEL_SERVER_PORT ?? (await freePort()));
    const modelServerConfig: ModelServerConfig = {
        port: modelServerPort,
        route: modelServerRoute,
        url: `http://localhost:${modelServerPort}${modelServerRoute}`
    };

    const container = createContainer(context, {
        glspServerConfig,
        modelServerConfig
    });

    if (process.env.UML_MODEL_SERVER_DEBUG !== 'true') {
        await launchModelServer(container, modelServerConfig);
    }

    if (process.env.UML_GLSP_SERVER_DEBUG !== 'true') {
        await launchGLSPServer(container, glspServerConfig);
    }

    configureDefaultCommands({
        extensionContext: context,
        connector: container.get<UVGlspConnector>(TYPES.Connector),
        diagramPrefix: VSCodeSettings.commands.prefix
    });

    container.getAll<any>(VSCODE_TYPES.Watcher);
    container.get<any>(VSCODE_TYPES.EditorProvider);
    container.get<any>(VSCODE_TYPES.CommandManager);
    container.get<any>(VSCODE_TYPES.DisposableManager);
    container.get<UVGlspServer>(TYPES.GlspServer).start();
}
