package bn.nadamattar.libraryprojectnada.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ActivityResultLauncher<Intent> from_register = registerForActivityResult // عند العودة من شاشة الريجستر ، طبعًا النوع انتنت لأنه من عملية انتقال وفيها داتا حاملة بيانات ، وكمان النوع بيكون متل النوع يلي حطيته في زر الريجستر تحت
                (new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    // SAFR الاختصار طبعًا هادا ، النوعية  يلي  بنحطها هان ببين شو هيَّ العملية سواء اكسبلست ولا امبلست ولا
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            binding.loginEtEmail.setText(result.getData().getStringExtra(EMAIL_KEY)); // طبعًا هان قرأت البيانات يلي رجعت من الداتا تاعت الانتنت يلي من الريجستر (يلي راجعة في ال result ) ,وحطيتها في  الايميل
                            binding.loginEtPassword.setText(result.getData().getStringExtra(PASSWORD_KEY)); // ~ ~ ~ ~

                            binding.logBtnLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    login();
                                }
                            });
                        }

                    }
                });

        binding.btnRegisterActivityLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LoginActivity.this , RegisterActivity.class); // لما أضغط هان حينقلني عالريجستر
                    from_register.launch(intent);
            }
        });

        binding.logBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });



    }



    private void login (){
        String emailLogin = binding.loginEtEmail.getText().toString(); // هان قرأت النصوص يلي الايديت
        String  passwordLogin = binding.loginEtPassword.getText().toString();//~ ~ ~ ~

        if (emailLogin!=null && !emailLogin.isEmpty() && passwordLogin!=null && !passwordLogin.isEmpty()){ // هان بفحص اذا كل الصناديق مش فارغة ومش نل ،(لأنه بفرق )
            firebaseAuth.signInWithEmailAndPassword(emailLogin,passwordLogin)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else if (emailLogin.isEmpty()|| emailLogin==null ){ // اذا اسم الايميل نل أو فارغ ، مارح يفوت وحأعرضله أحمر انه دخل الايميل ، وطبعًا ما تتم عملية الانتقال
            binding.loginEtEmail.setError(getResources().getString(R.string.error_enter_email));
        }
        else if (passwordLogin.isEmpty() || passwordLogin==null ){ // ~~~ مثل ما عملت بالايميل
            binding.loginEtPassword.setError(getResources().getString(R.string.error_enter_password));
        }


    }

}