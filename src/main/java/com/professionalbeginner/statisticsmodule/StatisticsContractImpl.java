package com.professionalbeginner.statisticsmodule;

import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import org.springframework.stereotype.Component;

/**
 * @author Kempenich Florian
 */
@Component
public class StatisticsContractImpl implements StatisticsContract {

    @Override
    public double processAverage(Integer[] numbers) {
        throw new IllegalStateException("Implement with weird library");
    }
}
