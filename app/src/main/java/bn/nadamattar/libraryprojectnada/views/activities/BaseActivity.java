package bn.nadamattar.libraryprojectnada.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.adapter.AdapterBooks;
import bn.nadamattar.libraryprojectnada.listener.ActionItemBook;
import bn.nadamattar.libraryprojectnada.model.Book;
import bn.nadamattar.libraryprojectnada.storag.sharedpreferences.UserPreference;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth ;
    FirebaseUser currentUser ;
    UserPreference userPreference;

    public final String EMAIL_KEY = "email";
    public final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        userPreference = new UserPreference(this);
//        FirebaseAuth.getInstance().signOut();

    }

}
