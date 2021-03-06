/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package jhlabs.map.proj;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * A projection which does nothing. Use this for drawing non-geographical overlays.
 */
public class NullProjection extends Projection {

    public Point2D.Double transform(Point2D.Double src, Point2D.Double dst) {
        dst.x = src.x;
        dst.y = src.y;
        return dst;
    }

    public Shape projectPath(Shape path, AffineTransform t, boolean filled) {
        if (t != null)
            t.createTransformedShape(path);
        return path;
    }

    public Shape getBoundingShape() {
        return null;
    }

    public boolean isRectilinear() {
        return true;
    }

    public String toString() {
        return "Null";
    }

}
