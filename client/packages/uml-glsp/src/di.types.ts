/*********************************************************************************
 * Copyright (c) 2023 borkdominik and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the MIT License which is available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: MIT
 *********************************************************************************/
import { TYPES } from '@eclipse-glsp/client';

export const UML_TYPES = {
    ...TYPES,
    IOnceModelInitialized: Symbol('IOnceModelInitialized')
};
