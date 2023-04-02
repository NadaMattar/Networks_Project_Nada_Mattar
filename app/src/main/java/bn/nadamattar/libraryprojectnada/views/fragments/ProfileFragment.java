package bn.nadamattar.libraryprojectnada.views.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.FragmentProfileBinding;
import bn.nadamattar.libraryprojectnada.model.UserData;

public class ProfileFragment extends BaseFragment {
    FragmentProfileBinding binding ;
    Uri image ;
    public ProfileFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (userPreference.isDoneUserData()){
            getDataUserById();
        }else {
            return;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentProfileBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        image = currentUser.getPhotoUrl();
        putImage(image);
        binding.userName.setText(currentUser.getDisplayName());

        binding.imgUserFragment.setEnabled(false);
        binding.userName.setEnabled(false);
        binding.country.setEnabled(false);
        binding.age.setEnabled(false);

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgUserFragment.setEnabled(true);
                binding.userName.setEnabled(true);
                binding.country.setEnabled(true);
                binding.age.setEnabled(true);

                binding.done.setVisibility(View.VISIBLE);
            }
        });
        //الآن بدي لما المستخدم يضغط عإضافة صورة يروح عالمعارض المتاحة ويختار المستخدم صورة وأرجعله اياها في التطبيق
        ActivityResultLauncher<String> gallery = registerForActivityResult
                (new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                            //ال GetContent تستخدم لأجيب الملفات الصور الفيديوهات يلي عالجهاز
                            @Override
                            public void onActivityResult(Uri result) {
                                image = result;
                               putImage(image);
                            }
                        }
                );
        binding.imgUserFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                binding.done.setVisibility(View.VISIBLE);
                gallery.launch("image/*");
            }
        });
        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.userName.getText().toString();
                String country = binding.country.getText().toString();
                String age = String.valueOf(binding.age.getText());
                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .setPhotoUri(image)
                        .build();

                    if(  (image!=null||(userName!=null&&!userName.isEmpty())) && ((country!=null&&!country.isEmpty())&&(age!=null&&!age.isEmpty()))     )    {
                        updateProfile(currentUser,userProfileChangeRequest);
                        int a_age = Integer.parseInt(String.valueOf(binding.age.getText()));
                        updateUserData(country,a_age);
                    }

                binding.userName.setEnabled(false);
                binding.country.setEnabled(false);
                binding.age.setEnabled(false);
                binding.done.setVisibility(View.INVISIBLE);
                return;
            }
        });

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(getParentFragmentManager(),"update");
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog();
                dialog.show(getParentFragmentManager(),"logout");
            }
        });

    }

    private void updateProfile(FirebaseUser user , UserProfileChangeRequest userProfileChangeRequest){
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    user.reload();
//                    بعد ما أضيفهن عالفيربيز حأعمل دن في الشيرد
                        userPreference.setEdit(true);
                }else{
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    userPreference.setEdit(false);
                }
            }
        });

    }


    private void updateUserData (String country , int age){
        Map<String, Object> userData = new HashMap<>();
        userData.put("country",country);
        userData.put("age",age);

        db.collection("users_data").document(currentUser.getUid())
                .set(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity()," successfully " , Toast.LENGTH_SHORT).show();
                            userPreference.setEditUserData(true);
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            userPreference.setEditUserData(false);

                        }
                    }
                });

    }


    public void getDataUserById (){
        DocumentReference documentReference = db.collection("users_data").document(currentUser.getUid());
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                            DocumentSnapshot documentSnapshot = task.getResult();
                             UserData userData = documentSnapshot.toObject(UserData.class);
                             binding.country.setText(userData.getCountry());
                             binding.age.setText(String.valueOf(userData.getAge()));
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void putImage(Uri uri){
        Glide.with(getActivity())
                .load(uri)
                .error(R.drawable.ic_person_add )
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new FitCenter())
                .circleCrop()
                .into(binding.imgUserFragment);
    }



}