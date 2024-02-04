package app.logisctics.sevice;

import app.logisctics.dao.converter.DestinationConverter;
import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.exception.DestinationNotFoundException;
import app.logisctics.exception.IllegalDestinationNameException;
import app.logisctics.repository.DestinationRepository;
import app.logisctics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService{

    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<DestinationDto> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();

        return DestinationConverter.modelListToDtoList(destinations);
    }

    @Override
    public DestinationDto getDestinationById(Long id) throws DestinationNotFoundException {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(()-> new DestinationNotFoundException(id));

        return DestinationConverter.modelToDto(destination);
    }

    @Override
    public Long createDestination(DestinationDto destinationDto) throws BadRequestException {
        if(destinationDto.getId() != null){
            throw new BadRequestException("Id should not be provided");
        }

        Optional<Destination> optionalDestination = destinationRepository.findByName(destinationDto.getName());

        if(optionalDestination.isPresent()){
            Destination destination = optionalDestination.get();

            throw new IllegalDestinationNameException(destination.getId(), destination.getName());
        }

        Destination destination = DestinationConverter.dtoToModel(destinationDto);

        return destinationRepository.save(destination).getId();
    }

    @Override
    public void updateDestination(DestinationDto destinationDto) throws BadRequestException {
        if(destinationDto.getId() != null){
            throw new BadRequestException("Id should not be provided");
        }

        Optional<Destination> optionalDestination = destinationRepository.findByName(destinationDto.getName());

        if(optionalDestination.isPresent()){
            Destination destination = optionalDestination.get();

            throw new IllegalDestinationNameException(destination.getId(), destination.getName());
        }

        Destination destination = DestinationConverter.dtoToModel(destinationDto);

        destinationRepository.save(destination);
    }

    @Override
    public void deleteDestination(Long id) throws DestinationNotFoundException {

        Destination destination = destinationRepository.findById(id)
                .orElseThrow(()-> new DestinationNotFoundException(id));

        orderRepository.findAllByDestinationId(id).forEach(orderRepository::archiveOrder);

        destinationRepository.delete(destination);
    }
}
