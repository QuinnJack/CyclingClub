package edu.uottawa.seg2105_final_grp12.models;

import static edu.uottawa.seg2105_final_grp12.viewmodel.base.ValidatedFormViewModel.getString;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.uottawa.seg2105_final_grp12.R;

public enum Validators {
    EMPTY(R.string.error_empty, String::isEmpty),
    TOO_LONG(R.string.error_too_short, s -> s.length() < 6 && !s.equals("admin"));

    private static final Map<String, Validator> validatorMap = Stream.of(Validators.values())
            .collect(Collectors.toMap(v -> getString(v.id), v -> v.function));

    int id;
    Validator function;

    Validators(int id, Validator function) {
        this.id = id;
        this.function = function;
    }

    public static Validator get(String id) {
        return validatorMap.get(id);
    }
}
