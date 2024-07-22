package br.com.ludevsp.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CategoryEnum {
    FOOD(Arrays.asList(5411, 5412), "Food"),
    MEAL(Arrays.asList(5811, 5812), "Meal"),
    CASH(Arrays.asList(9999), "Cash");

    private final List<Integer> mccCodes;
    private final String name;

    CategoryEnum(List<Integer> mccCodes, String name) {
        this.mccCodes = mccCodes;
        this.name = name;
    }

    public static CategoryEnum getCategoryByMcc(int mcc) {
        for (CategoryEnum category : values()) {
            if (category.mccCodes.contains(mcc)) {
                return category;
            }
        }
        throw new IllegalArgumentException("MCC not found");
    }
}
