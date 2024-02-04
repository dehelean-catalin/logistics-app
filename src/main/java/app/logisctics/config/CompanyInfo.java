package app.logisctics.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
@Getter
public class CompanyInfo {

    private LocalDate currentDate = LocalDate.of(2021, 12, 16);
    private Long profit;

    public Long getCurrentDateAsLong(){
        return this.currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000;
    }
}
