package bn.nadamattar.libraryprojectnada.views.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.FragmentAddBookDialogBinding;

public class AddBookDialog extends DialogFragment {
    FragmentAddBookDialogBinding binding;
    Uri image ;
    FirebaseStorage firebaseStorage ;
    FirebaseUser currentUser ;
    FirebaseFirestore db ;


    public AddBookDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentAddBookDialogBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        //الآن بدي لما المستخدم يضغط عإضافة صورة يروح عالمعارض المتاحة ويختار المستخدم صورة وأرجعله اياها في التطبيق
        ActivityResultLauncher<String> gallery = registerForActivityResult
                (new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                            //ال GetContent تستخدم لأجيب الملفات الصور الفيديوهات يلي عالجهاز
                            @Override
                            public void onActivityResult(Uri result) {
                                image = result;
                                Glide.with(getActivity())
                                        .load(image)
                                        .error(R.drawable.ic_person_add )
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .transform(new FitCenter(),new RoundedCorners(30))
                                        .into(binding.imgAddBook);

                            }
                        }
                );
        binding.imgAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery.launch("image/*");
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = binding.bookName.getText().toString();
                String bookAuthor = binding.authorName.getText().toString();
                String categoryName = binding.bookCategory.getText().toString();

                if ((bookName!=null&&!bookName.isEmpty()&&
                        bookAuthor!=null&&!bookAuthor.isEmpty()&&
                        categoryName!=null&&!categoryName.isEmpty())&&
                        (image!=null) )
                {//كلهم لا يساوي نل وليسوا فارغين{
                    uploadImageToFirebase();
//                    addUserBookData(bookName,bookAuthor,categoryName);
                    Toast.makeText(getActivity(), "سأعمل على تطويرها و هنا تتم الإضافة بنجاح", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "فشلت عملية الإضافة", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
            }
        });


    }

    // المفروض هاي الصورة يلي راجعة آخذ رابطها والبيانات التانية يلي هي الدولة والعمر وأضيفهن بهاشماب عالفيرستور ، وأضيفهن لاحقا عقائمة كتبي
    private void uploadImageToFirebase() {
//       StorageReference reference =
               firebaseStorage.getReference().child("images/user_image/"+image.getLastPathSegment())
                       .putFile(image);
//               UploadTask uploadTask = reference.putFile(image);

    }


//    private void addUserBookData (String bookName , String authorBook , String categoryName ){
//        Map<String, Object> UserBookData = new HashMap<>();
//        UserBookData.put("bookName",bookName);
//        UserBookData.put("authorBook",authorBook);
//        UserBookData.put("categoryName",categoryName);
//
//        db.collection("users_data").document(currentUser.getUid())
//                .set(UserBookData)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getActivity()," successfully " , Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getActivity(),"Error" , Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams)layoutParams);

    }


}