package by.bivis.kbp.parser.enums;

import by.bivis.kbp.parser.errors.NoEnumWithSuchValueError;
import by.bivis.kbp.parser.functional_interfaces.TripleExpression;
import lombok.extern.log4j.Log4j2;

@Log4j2
public enum SourceType implements EnumValue {
    AUDIENCE("аудитория", "place"),
    SUBJECT("предмет", "subject"),
    TEACHER("преподаватель", "teacher"),
    GROUP("группа", "group");

    private final String value;
    private final String engValue;

    SourceType(String value, String engValue) {
        this.value = value;
        this.engValue = engValue;
    }

    /**
     * Returns a SourceType enum object with a value equal to {@param value}
     *
     * @param value      the value
     * @param expression the expression f.e. (enumObj, value1) -> enumObj.getValue().equals(value)
     * @return the enum object by value
     */
    public static SourceType getEnumBy(String value, TripleExpression<SourceType, String, Boolean> expression) {
        SourceType[] values = values();
        for (SourceType enumObj : values) {
            if (expression.evaluate(enumObj, value)) {
                return enumObj;
            }
        }
        throw new NoEnumWithSuchValueError("There is no enum with value '" + value + "'");
    }

    public static SourceType getEnumByValue(String value) {
        return getEnumBy(value, (enumObj, value1) -> enumObj.getValue().equals(value));
    }

    public static SourceType getEnumByEngValue(String value) {
        return getEnumBy(value, (enumObj, value1) -> enumObj.getEngValue().equals(value));
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getEngValue() {
        return engValue;
    }
}
