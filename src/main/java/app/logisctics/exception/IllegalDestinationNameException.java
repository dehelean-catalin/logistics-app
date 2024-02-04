package app.logisctics.exception;

import org.apache.coyote.BadRequestException;

public class IllegalDestinationNameException extends BadRequestException {
    public IllegalDestinationNameException( Long id,String name){
        super(String.format("Destination with id: %d already has name: %s", id, name));
    }
}
