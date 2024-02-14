package app.logisctics.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationDto {
    private Long id;

    @NotBlank
    @Schema(example = "Alba Iulia")
    private String name;

    @Min(0)
    @NotNull
    @Schema(example = "20", type = "number")
    private int distance;

//    @AssertTrue
}
