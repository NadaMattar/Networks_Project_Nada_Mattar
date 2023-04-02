package bn.nadamattar.libraryprojectnada.views.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ActivityMainBinding;
import bn.nadamattar.libraryprojectnada.views.fragments.AddBookDialog;
import bn.nadamattar.libraryprojectnada.views.fragments.HomeFragment;
import bn.nadamattar.libraryprojectnada.views.fragments.MyBookMarkFragment;
import bn.nadamattar.libraryprojectnada.views.fragments.MyBooksFragment;
import bn.nadamattar.libraryprojectnada.views.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {
ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// عشان أخلي الخلفية تاعت المنيو فارغة عشان ما يدخل التصميم عبعض
        binding.bottomNavigationView.setBackground(null);

// هذا السطر الاخفاء الستيتس بار
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!(userPreference.isEditDone())){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
//        وهان بدي اياه يحدد ع الأيقون يلي تحت تاعت البروفايل
            binding.bottomNavigationView.setSelectedItemId(R.id.account);
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
//        وهان بدي اياه يحدد ع الأيقون يلي تحت تاعت البروفايل
            binding.bottomNavigationView.setSelectedItemId(R.id.home);
        }



        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.books:
                        fragment = new MyBooksFragment();
                        break;
                    case R.id.book_mark:
                        fragment = new MyBookMarkFragment();
                        break;
                    case R.id.account:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                return true;
            }
        });

        binding.btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookDialog addBookDialog = new AddBookDialog();
                addBookDialog.show(getSupportFragmentManager(),"add_book");

            }
        });

//
    }

}