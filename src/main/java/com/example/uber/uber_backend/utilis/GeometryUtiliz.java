package com.example.uber.uber_backend.utilis;

import com.example.uber.uber_backend.dtos.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtiliz {

    public  static Point createPoint(PointDto pointDto){
// 4326 is a earth srid
        GeometryFactory geometryFactory=new GeometryFactory(new PrecisionModel(),4326);
        Coordinate coordinate=new Coordinate(pointDto.getCoordinates()[0],pointDto.getCoordinates()[1]);
        return  geometryFactory.createPoint(coordinate);

    }
}
