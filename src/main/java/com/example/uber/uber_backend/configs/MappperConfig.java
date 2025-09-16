package com.example.uber.uber_backend.configs;


import com.example.uber.uber_backend.dtos.PointDto;
import com.example.uber.uber_backend.utilis.GeometryUtiliz;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper =new ModelMapper();

        modelMapper.typeMap(PointDto.class, Point.class).setConverter(pointDto -> {
            PointDto pointerDto=pointDto.getSource();
            return GeometryUtiliz.createPoint(pointerDto);
        });

        modelMapper.typeMap(Point.class,PointDto.class).setConverter(pointer -> {
            Point point=pointer.getSource();
            double[] coordinates={
                    point.getX(),
                    point.getY()
            };
            return  new PointDto(coordinates);
        });

        return modelMapper;
    }
}
