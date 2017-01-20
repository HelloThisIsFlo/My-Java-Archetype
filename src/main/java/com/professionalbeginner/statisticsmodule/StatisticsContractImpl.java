package com.professionalbeginner.statisticsmodule;

import com.professionalbeginner.domain.interfacelayer.statistics.StatisticsContract;
import org.boris.expr.Expr;
import org.boris.expr.ExprEvaluatable;
import org.boris.expr.parser.ExprParser;
import org.boris.expr.util.Exprs;
import org.boris.expr.util.SimpleEvaluationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * This whole class is intentionally obfuscated, cumbersome and ... completely useless.
 *
 * Its only purpose it to showcase how easy it is to integrate a 3rd-party tool, as cumbersome as it is, when
 * abstracting it away.
 *
 * @author Kempenich Florian
 */
@Component
public class StatisticsContractImpl implements StatisticsContract {

    @Override
    public double processAverage(Integer[] numbers) {
        String expression = buildExpression(numbers);
        return evaluate(expression);
    }

    private String buildExpression(Integer[] numbers) {
        ExpressionBuilder expression = new ExpressionBuilder();
        expression.setMethod("average");

        for (Integer number : numbers) {
            expression.addParameter(number.toString());
        }

        return expression.build();
    }

    private double evaluate(String expression) {
        SimpleEvaluationContext context = new SimpleEvaluationContext();
        try {
            Expr e = ExprParser.parse(expression);
            Exprs.toUpperCase(e);
            if (e instanceof ExprEvaluatable) {
                e = ((ExprEvaluatable) e).evaluate(context);
                return Double.parseDouble(e.toString());
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
