package manzil.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class SpatialUtil
{
    static GeometryFactory geoFactory = new GeometryFactory(new PrecisionModel(), 4326);

    private SpatialUtil() {}

    public static Point mapLocation(double lat, double lng)
    {
        return geoFactory.createPoint(new Coordinate(lng, lat));
    }
}
