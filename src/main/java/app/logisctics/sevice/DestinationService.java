package app.logisctics.sevice;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.exception.DestinationNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface DestinationService {

    List<DestinationDto> getAllDestinations();

    DestinationDto getDestinationById(Long id) throws DestinationNotFoundException;

    Long createDestination(DestinationDto destinationDto) throws BadRequestException;

    DestinationDto updateDestination(DestinationDto destinationDto) throws BadRequestException;

    void deleteDestination(Long id) throws BadRequestException;
}
