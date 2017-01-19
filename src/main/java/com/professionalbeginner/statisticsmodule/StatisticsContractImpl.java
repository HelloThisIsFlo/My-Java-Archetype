package com.professionalbeginner.statisticsmodule;

import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kempenich Florian
 */
@Component
public class StatisticsContractImpl implements StatisticsContract {

    @Override
    public double processAverage(Integer[] numbers) {
        return Arrays.stream(numbers)
                .collect(Collectors.averagingDouble(num -> num));
    }
}
