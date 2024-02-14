package app.logisctics.controller;

import app.logisctics.dao.dto.DestinationDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.sevice.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get a destination by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the destination",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destination.class)) }),
            @ApiResponse(responseCode = "404", description = "Destination not found",
                    content = @Content) })
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
