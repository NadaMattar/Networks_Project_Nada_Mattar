package bn.nadamattar.libraryprojectnada.views.fragments;

import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

import bn.nadamattar.libraryprojectnada.storag.sharedpreferences.UserPreference;

public class BaseFragment extends Fragment {

    UserPreference userPreference;
    FirebaseUser currentUser ;
    String userName ;
    Uri personImage ;
    FirebaseFirestore db ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userPreference = new UserPreference(getActivity());
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userName= currentUser.getDisplayName();
        personImage = currentUser.getPhotoUrl();
        db = FirebaseFirestore.getInstance();
//

    }



}
