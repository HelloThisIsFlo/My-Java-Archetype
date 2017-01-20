package com.professionalbeginner.statisticsmodule;

import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class StatisticsContractImplTest {

    private StatisticsContractImpl statisticsContract;

    @Before
    public void setUp() throws Exception {
        statisticsContract = new StatisticsContractImpl();
    }

    @Test
    public void testAverage2numbers() throws Exception {
        double avg = statisticsContract.processAverage(makeList(2, 3));
        assertEquals(2.5, avg, 0);
    }

    @Test
    public void testAverageMultipleNumbers() throws Exception {
        double avg = statisticsContract.processAverage(makeList(2, 3, 45, 22, 4));
        assertEquals(15.2, avg, 0);
    }

    private Integer[] makeList(Integer... numbers) {
        return numbers;
    }
}