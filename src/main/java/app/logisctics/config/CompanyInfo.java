package app.logisctics.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
@Getter
public class CompanyInfo {

    private LocalDate currentDate = LocalDate.of(2021, 12, 14);
    private Long profit;

    public Long getCurrentDateAsLong(){
        return this.currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000;
    }

    public LocalDate incrementDateByOne(){
         currentDate = currentDate.plusDays(1);
         return currentDate;
    }
}
