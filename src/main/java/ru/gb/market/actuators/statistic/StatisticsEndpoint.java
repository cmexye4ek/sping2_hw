package ru.gb.market.actuators.statistic;

import lombok.Data;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.gb.market.aspects.StatisticsAspect;

import java.util.*;

@Component
@Data
@Endpoint(id = "statistic")
public class StatisticsEndpoint {

    private final StatisticsAspect statisticsAspect;

    @ReadOperation
    public HashMap<String, Long> getStatistic() {
        return statisticsAspect.getStatList();
    }
}
