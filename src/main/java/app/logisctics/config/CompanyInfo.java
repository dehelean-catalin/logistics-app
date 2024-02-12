package app.logisctics.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Getter
public class CompanyInfo {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate currentDate = LocalDate.of(2021, 12, 14);
    private final AtomicLong profit = new AtomicLong(0L);

    public Long getCurrentDateAsLong(){
        return this.currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000;
    }

    public LocalDate incrementDateByOne(){
         currentDate = currentDate.plusDays(1);
         return currentDate;
    }
    public long getLocalDateStringAsLong(String date){
        if(date.isBlank()){
            return LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        }

        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public long updateProfit(Long delta){
        return profit.addAndGet(delta);
    }
}
