package app.logisctics.dao.converter;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.dao.model.Destination;

import java.util.List;

public class DestinationConverter {

    public static Destination dtoToModel(DestinationDto destinationDto) {
        return Destination.builder()
                .distance(destinationDto.getDistance())
                .name(destinationDto.getName())
                .build();

    }
    public static DestinationDto modelToDto(Destination destination){
        return DestinationDto.builder()
                .id(destination.getId())
                .distance(destination.getDistance())
                .name(destination.getName())
                .build();
    }
    public static List<DestinationDto> modelListToDtoList(List<Destination> destinations){
        return destinations.stream()
                .map(DestinationConverter::modelToDto)
                .toList();
    }

}
