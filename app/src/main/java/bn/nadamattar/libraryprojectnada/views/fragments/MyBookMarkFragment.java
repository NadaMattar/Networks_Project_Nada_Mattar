package bn.nadamattar.libraryprojectnada.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

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
import bn.nadamattar.libraryprojectnada.databinding.FragmentMyBookMarkBinding;
import bn.nadamattar.libraryprojectnada.listener.ActionItemBook;
import bn.nadamattar.libraryprojectnada.model.Book;

public class MyBookMarkFragment extends BaseFragment {
    FragmentMyBookMarkBinding binding;


    public MyBookMarkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentMyBookMarkBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

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


        if (!userPreference.isBooksSaved()) return;
        getMyBooksSaved();


        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = binding.etSearch.getText().toString();
                if (bookName!=null&&!bookName.isEmpty()) getBookByName(bookName);
            }
        });

    }



    private void getMyBooksSaved(){
        DocumentReference reference =  db.collection("users_data").document(currentUser.getUid());
        reference.collection("saved")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        requestType(task);
                    }
                });
    }

//    استخدام الفلتر بناءًا ع اسم الكتاب يرجعلي كتاب من  الكتب المحفوظة
    private void getBookByName(String bookName) {
        db.collection("users_data").document(currentUser.getUid())
                .collection("saved")
                .whereEqualTo("bookName",bookName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        requestType(task);
                    }
                });

    }


    private void requestType(Task<QuerySnapshot> task) {
        if (task.isSuccessful()){
            ArrayList<Book> bookArrayList = new ArrayList<>();
            for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()) {
                Log.d("bookObject",queryDocumentSnapshot.getData().toString());
                Book book = queryDocumentSnapshot.toObject(Book.class);
                Log.d("book",book.getBookName());
                book.setDocumentId(queryDocumentSnapshot.getId());
                bookArrayList.add(book);
            } binding.rvSavedBook.setLayoutManager(new GridLayoutManager(getActivity(),2));
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
            binding.rvSavedBook.setAdapter(adapterBooks);
        }else {
            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}