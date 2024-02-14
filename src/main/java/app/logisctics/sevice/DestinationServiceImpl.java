package app.logisctics.sevice;

import app.logisctics.cache.DestinationCache;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService{

    private final DestinationRepository destinationRepository;
    private final DestinationCache destinationCache;
    private final OrderRepository orderRepository;

    @Override
    public List<DestinationDto> getAllDestinations() {
        List<Destination> destinations = destinationCache.findAll();

        return DestinationConverter.modelListToDtoList(destinations);
    }

//    @Cacheable("destinations")
    @Override
    public DestinationDto getDestinationById(Long id) throws DestinationNotFoundException {
        Destination destination = destinationCache.findById(id)
                .orElseThrow(()-> new DestinationNotFoundException(id));

        return DestinationConverter.modelToDto(destination);
    }

    @Override
    public Long createDestination(DestinationDto destinationDto) throws BadRequestException {
        if(destinationDto.getId() != null){
            throw new BadRequestException("Id should not be provided");
        }

        Optional<Destination> optionalDestination =
                destinationCache.findByName(destinationDto.getName());

        if(optionalDestination.isPresent()){
            Destination destination = optionalDestination.get();

            throw new IllegalDestinationNameException(destination.getId(), destination.getName());
        }

        Destination destination = DestinationConverter.dtoToModel(destinationDto);

        Destination savedDestination = destinationRepository.save(destination);
        destinationCache.save(savedDestination);

        return savedDestination.getId();
    }

//    @CachePut(value = "destinations", key = "#destinationDto.id")
    @Override
    public DestinationDto updateDestination(DestinationDto destinationDto) throws BadRequestException {
        if(destinationDto.getId() != null){
            throw new BadRequestException("Id should not be provided");
        }

        Optional<Destination> optionalDestination =
                destinationCache.findByName(destinationDto.getName());

        if(optionalDestination.isPresent()){
            Destination destination = optionalDestination.get();

            throw new IllegalDestinationNameException(destination.getId(), destination.getName());
        }

        Destination destination = DestinationConverter.dtoToModel(destinationDto);

        Destination savedDestination = destinationRepository.save(destination);
        destinationCache.save(savedDestination);

        return DestinationConverter.modelToDto(savedDestination);
    }

//    @CacheEvict("destinations")
    @Override
    public void deleteDestination(Long id) throws DestinationNotFoundException {

        Destination destination = destinationCache.findById(id)
                .orElseThrow(()-> new DestinationNotFoundException(id));

        orderRepository.findAllByDestinationId(id).forEach(orderRepository::archiveOrder);

        destinationRepository.delete(destination);
        destinationCache.delete(destination.getId());

    }
}
