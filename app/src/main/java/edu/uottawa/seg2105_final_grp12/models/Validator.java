package edu.uottawa.seg2105_final_grp12.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Validator {

    private List<ValidationModel> validationModels = new ArrayList<>();

    public Validator(ValidationModel... models) {
        validationModels = Arrays.stream(models).collect(Collectors.toList());
    }

    public Task<HashSet<ValidationModel>> validate(String input) {
        TaskCompletionSource<HashSet<ValidationModel>> completionSource = new TaskCompletionSource<>();
        HashSet<ValidationModel> failedModels = new HashSet<>();

        Tasks.whenAllComplete(validationModels.stream()
                        .map(m -> m.validate(input).addOnSuccessListener(valid -> {
                            if (!valid)
                                failedModels.add(m);
                        })).collect(Collectors.toList())).addOnSuccessListener(
                                objects -> completionSource.setResult(failedModels));

        return completionSource.getTask();
    }

    public Validator addModel(ValidationModel model) {
        validationModels.add(model);
        return this;
    }
}
