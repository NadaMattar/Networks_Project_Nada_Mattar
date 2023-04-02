package bn.nadamattar.libraryprojectnada.model;

import android.graphics.drawable.Drawable;

public class Categories {
    private String categoryName ;
    private Integer categoryImage ;

    public Categories(String categoryName, Integer categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Integer categoryImage) {
        this.categoryImage = categoryImage;
    }
}
