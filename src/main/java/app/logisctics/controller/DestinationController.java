package app.logisctics.controller;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.sevice.DestinationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
@CrossOrigin
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;
    @GetMapping
    public List<DestinationDto> getDestinations(){
        return destinationService.getAllDestinations();
    }
    @GetMapping("/{id}")
    public DestinationDto getDestinationById(@RequestParam Long id) throws BadRequestException {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public Long postDestination(@Valid @RequestBody DestinationDto destinationDto) throws BadRequestException {
        return destinationService.createDestination(destinationDto);
    }

    @PutMapping
    public void updateDestination(@Valid @RequestBody DestinationDto destinationDto) throws BadRequestException {
        destinationService.updateDestination(destinationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@RequestParam Long id) throws BadRequestException {
        destinationService.deleteDestination(id);
    }

}
