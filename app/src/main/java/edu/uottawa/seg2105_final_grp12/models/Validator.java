package edu.uottawa.seg2105_final_grp12.models;

import java.util.function.Function;

@FunctionalInterface
public interface Validator extends Function<String, Boolean> {
    Boolean apply(String input);
}
