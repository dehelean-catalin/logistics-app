package app.logisctics.dao.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderDto {
    @Min(0)
    @NotNull
    private Long deliveryDate;
    @NotNull
    private Long destinationId;
}
