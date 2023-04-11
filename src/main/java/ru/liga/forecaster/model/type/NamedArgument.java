package ru.liga.forecaster.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum NamedArgument {
    PERIOD("period"),
    OUTPUT("output"),
    ALG("alg"),
    DATE("date");

    private final String name;
    public static final Map<String, NamedArgument> argumentMap;

    static {
        argumentMap = new HashMap<>();
        for (NamedArgument arg : NamedArgument.values()) {
            argumentMap.put(arg.name , arg);
        }
    }
    public static boolean findByName (String name) {
        return argumentMap.containsKey(name);
    }

}
