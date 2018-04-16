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

package com.rockingstar.engine.game.models;

public class VectorXY {

    public int x;
    public int y;

    public VectorXY(int value) {
        x = y = value;
    }

    /**
     * Method that creates a vector
     * @param x
     * @param y
     */
    public VectorXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method that makes a string from the integers x and y
     * @return returns a string of the 2 integers
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
