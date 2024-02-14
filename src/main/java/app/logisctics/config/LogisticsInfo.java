package app.logisctics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LogisticsInfo implements InfoContributor {

    private final CompanyInfo companyInfo;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> userDetails = new HashMap<>();

        userDetails.put("Profit", companyInfo.getProfit().toString());
        userDetails.put("Current Date", companyInfo.getCurrentDate().toString());

        builder.withDetail("company-info", userDetails);
    }
}
