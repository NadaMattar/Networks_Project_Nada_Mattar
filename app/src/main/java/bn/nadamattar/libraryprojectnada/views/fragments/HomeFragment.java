package bn.nadamattar.libraryprojectnada.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.adapter.AdapterBooks;
import bn.nadamattar.libraryprojectnada.adapter.AdapterCategories;
import bn.nadamattar.libraryprojectnada.databinding.FragmentHomeBinding;
import bn.nadamattar.libraryprojectnada.listener.ActionItemBook;
import bn.nadamattar.libraryprojectnada.listener.ActionItemCategory;
import bn.nadamattar.libraryprojectnada.model.Book;
import bn.nadamattar.libraryprojectnada.model.Categories;

public class HomeFragment extends BaseFragment {
    FragmentHomeBinding binding ;
    ArrayList<Categories> categoriesArrayList ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
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

        categoriesArrayList = new ArrayList<>();
        categoriesArrayList.add(new Categories("cultural",R.drawable.cultural_image));
        categoriesArrayList.add(new Categories("scientific",R.drawable.scientific));
        categoriesArrayList.add(new Categories("religious",R.drawable.religious_img));
        categoriesArrayList.add(new Categories("literary",R.drawable.literary_image));

        requestType("cultural");


        binding.rvHomeCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        AdapterCategories adapterCategories = new AdapterCategories(getActivity(), categoriesArrayList, new ActionItemCategory() {
            @Override
            public void categoryName(String categoryName) {
               requestType(categoryName);
               }
        });
        binding.rvHomeCategory.setAdapter(adapterCategories);



    }

    private void requestType (String categoryName){
        DocumentReference reference =  db.collection("categories").document(categoryName);
        reference.collection("info")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<Book> bookArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()) {
                                Log.d("bookObject",queryDocumentSnapshot.getData().toString());
                                Book book = queryDocumentSnapshot.toObject(Book.class);
                                Log.d("book",book.getBookName());
                                book.setDocumentId(queryDocumentSnapshot.getId());
                                bookArrayList.add(book);
                            }
                            binding.rvHomeBooks.setLayoutManager(new GridLayoutManager(getActivity(),3));
                            AdapterBooks adapterBooks = new AdapterBooks(getActivity(), bookArrayList, new ActionItemBook() {
                                @Override
                                public void onClickBook(Book book) {
                                    if (book.isSave()){
                                        db.collection("users_data").document(currentUser.getUid())
                                                .collection("saved").document(book.getDocumentId())
                                                .set(book)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            userPreference.setBookSaved(true);
                                                            Toast.makeText(getActivity(), "Saved Done", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }else{
                                        Log.d("isSave",book.isSave()+"");
                                        db.collection("users_data").document(currentUser.getUid())
                                                .collection("saved").document(book.getDocumentId())
                                                .delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(getActivity(), "Delete Done", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                });
                                    }
                                }
                            });
                            binding.rvHomeBooks.setAdapter(adapterBooks);
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
