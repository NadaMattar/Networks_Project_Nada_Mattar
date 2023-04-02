package bn.nadamattar.libraryprojectnada.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

import bn.nadamattar.libraryprojectnada.databinding.FragmentDialogLogoutBinding;
import bn.nadamattar.libraryprojectnada.storag.sharedpreferences.UserPreference;
import bn.nadamattar.libraryprojectnada.views.activities.LoginActivity;


public class Dialog extends DialogFragment {
    FragmentDialogLogoutBinding binding ;

    public Dialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDialogLogoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            binding.yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    dismiss();
           //   عشان يسكر المين آكتيفيتي من الديالوج (يصل للأكتفيتي من الديالوج من خلال الكونتيكست)
                    getActivity().finish();
                }

            });

            binding.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    علشا خلص ينهي الديالوج
                    dismiss();
                }
            });

    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams)layoutParams);

    }
}