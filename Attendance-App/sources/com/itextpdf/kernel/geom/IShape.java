package com.itextpdf.kernel.geom;

import java.io.Serializable;
import java.util.List;

public interface IShape extends Serializable {
    List<Point> getBasePoints();
}
