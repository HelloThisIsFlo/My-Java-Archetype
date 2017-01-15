package com.professionalbeginner.domain.interfacelayer.statistics;

/**
 * @author Kempenich Florian
 */
public interface StatisticsContract {

    /*
     * Note:
     * This contract is simply to showcase the integration of 3rd-Party in the domain.
     * Of course, if not for demonstration purposes, we would never integrate a 3rd-Party for such small thing.
     */

    /**
     * Processes the average of the numbers contained in the array
     * @param numbers numbers to average
     * @return Average
     */
    double processAverage(Integer[] numbers);
}
