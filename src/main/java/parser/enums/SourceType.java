package parser.enums;

import lombok.extern.log4j.Log4j2;
import parser.errors.NoEnumWithSuchValueError;

@Log4j2
public enum SourceType implements EnumValue {
    AUDIENCE("аудитория"),
    SUBJECT("предмет"),
    TEACHER("преподаватель"),
    GROUP("группа");

    private final String value;

    SourceType(String value) {
        this.value = value;
    }


    /**
     * Returns a SourceType enum object with a value equal to {@param value}
     *
     * @param value the value
     * @return the enum object by value
     */
    public static SourceType getEnumByValue(String value) {
        SourceType[] values = values();
        for (SourceType enumObj : values) {
            if (enumObj.getValue().equals(value)) {
                return enumObj;
            }
        }
        String errorMessage = "There is no enum with value '" + value + "'";
        log.error(errorMessage);
        throw new NoEnumWithSuchValueError(errorMessage);
    }

    @Override
    public String getValue() {
        return value;
    }
}
