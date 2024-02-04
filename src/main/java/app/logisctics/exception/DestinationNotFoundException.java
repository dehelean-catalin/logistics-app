package app.logisctics.exception;

import org.apache.coyote.BadRequestException;

import java.io.IOException;

public class DestinationNotFoundException extends BadRequestException {

    public DestinationNotFoundException(Long id){
        super(String.format("Not found destination with id: %d", id));
    }
}
