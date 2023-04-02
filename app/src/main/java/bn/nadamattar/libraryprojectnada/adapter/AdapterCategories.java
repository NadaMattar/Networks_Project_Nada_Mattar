package bn.nadamattar.libraryprojectnada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ItemCategoriesBinding;
import bn.nadamattar.libraryprojectnada.listener.ActionItemCategory;
import bn.nadamattar.libraryprojectnada.model.Categories;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolderCategories> {
    Context context ;
    ArrayList<Categories> categoriesArrayList ;
    ActionItemCategory actionItemCategory ;

    public AdapterCategories(Context context, ArrayList<Categories> categoriesArrayList ,ActionItemCategory actionItemCategory) {
        this.context = context;
        this.categoriesArrayList = categoriesArrayList;
        this.actionItemCategory = actionItemCategory;
    }

    @NonNull
    @Override
    public MyViewHolderCategories onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoriesBinding binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolderCategories(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategories holder, int position) {
       Categories category = categoriesArrayList.get(position);
       if (category==null) return;
       holder.categoryName.setText(category.getCategoryName());
        Glide.with(context)
                .load(category.getCategoryImage())
                .error(R.drawable.ic_menu_book )
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CenterInside(),new RoundedCorners(10))
                .into(holder.categoryImage);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               actionItemCategory.categoryName(category.getCategoryName());
           }
       });
    }

    @Override
    public int getItemCount() {
        if (categoriesArrayList!=null){
            return categoriesArrayList.size();
        }
        return 0;
    }

    class MyViewHolderCategories extends RecyclerView.ViewHolder {
        ImageView categoryImage ;
        TextView categoryName ;
        public MyViewHolderCategories(@NonNull ItemCategoriesBinding binding) {
            super(binding.getRoot());
            categoryImage = binding.imageCategory;
            categoryName = binding.category ;
        }
    }

}
