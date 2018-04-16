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

package com.rockingstar.engine.lobby.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.GetGameListCommand;
import com.rockingstar.engine.command.client.GetPlayerListCommand;

public class LobbyModel {

    public void getPlayerList() {
        CommandExecutor.execute(new GetPlayerListCommand(ServerConnection.getInstance()));
    }

    public void getGameList() {
        ServerConnection serverConnection = ServerConnection.getInstance();
        CommandExecutor.execute(new GetGameListCommand(serverConnection));
    }

}