package com.professionalbeginner.statisticsmodule;

import static com.google.common.base.Strings.nullToEmpty;

/**
 * @author Kempenich Florian
 */
public class ExpressionBuilder {

    private String method;
    private String parameters;

    public ExpressionBuilder() {
        method = "";
        parameters = "";
    }

    public ExpressionBuilder setMethod(String method) {
        this.method = nullToEmpty(method);
        return this;
    }

    public ExpressionBuilder addParameter(String parameter) {
        if (fistParameter()) {
            appendFirstParameter(parameter);
        } else {
            appendExtraParameters(parameter);
        }
        return this;
    }

    public String build() {
        return method + "(" + parameters + ")";
    }

    private void appendExtraParameters(String parameter) {
        this.parameters = parameters + ", " + parameter;
    }

    private void appendFirstParameter(String parameter) {
        this.parameters = parameter;
    }

    private boolean fistParameter() {
        return parameters.isEmpty();
    }
}
