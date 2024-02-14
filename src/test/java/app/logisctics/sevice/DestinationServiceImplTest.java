package app.logisctics.sevice;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.exception.DestinationNotFoundException;
import app.logisctics.repository.DestinationRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DestinationServiceImplTest {

    @Autowired
    DestinationService destinationService;

    @Autowired
    DestinationRepository destinationRepository;

    @Test
    void deleteById_NotFound(){
        Long id = destinationService.getAllDestinations().stream()
                .mapToLong(DestinationDto::getId)
                .max()
                .orElse(0) + 1;

        DestinationNotFoundException destinationNotFoundException = assertThrows(DestinationNotFoundException.class,
                () -> destinationService.getDestinationById(id));

        assertEquals("Not found destination with id: "+id, destinationNotFoundException.getMessage());
    }

    @Test
    void deleteById_Ok() throws BadRequestException {
        long id = destinationService.getAllDestinations().stream()
                .mapToLong(DestinationDto::getId)
                .findAny()
                .orElseThrow();

        Optional<Destination> foundDestination = destinationRepository.findById(id);
        assertTrue(foundDestination.isPresent());

        destinationService.deleteDestination(id);

        foundDestination = destinationRepository.findById(id);
        assertTrue(foundDestination.isEmpty());

    }

}