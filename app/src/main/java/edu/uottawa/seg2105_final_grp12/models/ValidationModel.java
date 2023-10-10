package edu.uottawa.seg2105_final_grp12.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import edu.uottawa.seg2105_final_grp12.models.repository.AuthRepository;

public interface ValidationModel {
    Task<Boolean> validate(String input);

    ValidationModel EMPTY = input -> Tasks.forResult(!input.isEmpty());

    ValidationModel EMAIL_IN_USE = input -> AuthRepository.getInstance().emailExists(input);

    // any other validation checks can be added here, returning a boolean task (true if passed, false if failed)
}
