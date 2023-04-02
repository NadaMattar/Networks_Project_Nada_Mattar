package bn.nadamattar.libraryprojectnada.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.FragmentMyBooksBinding;
import bn.nadamattar.libraryprojectnada.model.UserData;


public class MyBooksFragment extends BaseFragment {
    FragmentMyBooksBinding binding;


    public MyBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBooksBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.userNameFragment.setText(userName);
        Glide.with(getActivity())
                .load(personImage)
                .error(R.drawable.icon_account)
                .placeholder(R.drawable.icon_account)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new FitCenter())
                .circleCrop()
                .into(binding.imageUserFragment);




    }


    public void getUserBookData (){
        DocumentReference documentReference = db.collection("users_data").document(currentUser.getUid());
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                            DocumentSnapshot documentSnapshot = task.getResult();
                            UserData userData = documentSnapshot.toObject(UserData.class);

                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }



}