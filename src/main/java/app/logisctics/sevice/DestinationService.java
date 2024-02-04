package app.logisctics.sevice;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.exception.DestinationNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface DestinationService {

    public List<DestinationDto> getAllDestinations();

    public DestinationDto getDestinationById(Long id) throws DestinationNotFoundException;

    public Long createDestination(DestinationDto destinationDto) throws BadRequestException;

    public void updateDestination(DestinationDto destinationDto) throws BadRequestException;

    public void deleteDestination(Long id) throws BadRequestException;
}
