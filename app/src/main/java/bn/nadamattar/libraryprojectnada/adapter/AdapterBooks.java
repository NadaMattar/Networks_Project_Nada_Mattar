package bn.nadamattar.libraryprojectnada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ItemBookBinding;
import bn.nadamattar.libraryprojectnada.listener.ActionItemBook;
import bn.nadamattar.libraryprojectnada.model.Book;

public class AdapterBooks extends RecyclerView.Adapter<AdapterBooks.MyViewHolderBooks> {
    Context context ;
    ArrayList<Book> bookArrayList ;
    ActionItemBook actionItemBook ;

    public AdapterBooks(Context context, ArrayList<Book> bookArrayList ,ActionItemBook actionItemBook ) {
        this.context = context;
        this.bookArrayList = bookArrayList;
        this.actionItemBook = actionItemBook;
    }

    @NonNull
    @Override
    public MyViewHolderBooks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolderBooks(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderBooks holder, int position) {
        int pos = position ;
        Book book = bookArrayList.get(pos);
        if (book==null)return;
        Glide.with(context)
                .load(book.getBookImage())
                .error(R.drawable.ic_menu_book )
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new FitCenter(),new RoundedCorners(40))
                .into(holder.bookImage);
        holder.bookName.setText(book.getBookName());
        holder.bookAuthor.setText(book.getBookAuthor());

        if (book.isSave()){
            holder.bookMark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark));
        }else {
            holder.bookMark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_un_save));
        }


        holder.bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!book.isSave()){
                    holder.bookMark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark));
                    book.setSave(true);
                }else{
                    holder.bookMark.setImageDrawable(context.getDrawable(R.drawable.ic_bookmark_un_save));
                    book.setSave(false);
                }
                actionItemBook.onClickBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookArrayList!=null){
            return bookArrayList.size();
        }
        return 0;
    }

    class MyViewHolderBooks extends RecyclerView.ViewHolder {
        ImageView bookImage , bookMark;
        TextView bookName , bookAuthor ;
        public MyViewHolderBooks(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            bookImage = binding.bookImage;
            bookMark = binding.bookMark;
            bookName = binding.bookName;
            bookAuthor = binding.bookAuthor;
        }
    }
}
