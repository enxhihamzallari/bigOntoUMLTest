/*********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 *********************************************************************************/

import { ContainerModule } from 'inversify';
import { TYPES } from '../../di.types';
import { OutlineTreeProvider } from './outline-tree.provider';

export const outlineModule = new ContainerModule(bind => {
    bind(OutlineTreeProvider).toSelf().inSingletonScope();
    bind(TYPES.Outline).to(OutlineTreeProvider);
    bind(TYPES.Disposable).toService(OutlineTreeProvider);
    bind(TYPES.RootInitialization).toService(OutlineTreeProvider);
});
