package bn.nadamattar.libraryprojectnada.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity {
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.regBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register (){
        String email = binding.regEtEmailAddress.getText().toString();
        String password = binding.regEtPassword.getText().toString();
        if (!email.isEmpty()&& email!=null && !password.isEmpty() && password!=null){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(); // انتنت فارغ لأعبي البيانات وهناك أستقبلهن في آكتيفتي ريزلت لآنشر
                                intent.putExtra(EMAIL_KEY,email);
                                intent.putExtra(PASSWORD_KEY,password);
                                userPreference.setEdit(false);
                                userPreference.setEditUserData(false);
                                setResult(RESULT_OK,intent);
                                FirebaseAuth.getInstance().signOut();
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else if (email.isEmpty() || email==null){
            binding.regEtEmailAddress.setError(getResources().getString(R.string.error_enter_email));
        }else if (password.isEmpty() || password==null){
            binding.regEtPassword.setError(getResources().getString(R.string.error_enter_password));
        }
    }

}