/*
 * Enjun
 *
 * @version     1.0 Beta 1
 * @author      Rocking Stars
 * @copyright   2018, Enjun
 *
 * Copyright 2018 RockingStars

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rockingstar.main;

import com.rockingstar.engine.controllers.Engine;
import com.rockingstar.engine.io.models.Util;

import static javafx.application.Application.launch;

public class Main {

    public static final boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        Util.displayStatus("Starting pre-launch checks");
        Util.displayStatus("Debug mode is on");
        launch(Engine.class, args);
    }
}