package bn.nadamattar.libraryprojectnada.storag.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor; /// هان في كلاس داخلي اسمه Editor بيتم فعليًا تخزين البيانات فيه والتعديل عليها

    public UserPreference(Context context) { // الآن عملنا كونستركتور بيآخذ كونتكست
        this.context = context;
        sharedPreferences = context.getSharedPreferences("com_nadamattar_user_pref" , Context.MODE_PRIVATE);
        //  هان المود برايفت ، يعني ما حدا بيقدر يصل للشيرد وبياناتها من خارج التطبيق ،
        editor = sharedPreferences.edit(); // في الإيديتور بنخزن أو بنعدل عالبيانات يلي هو تخزين جديد
    }

    public void setEdit(boolean isEdit){
        editor.putBoolean("is_edit" , isEdit);
        editor.apply();
    }

    public boolean isEditDone(){
        return sharedPreferences.getBoolean("is_edit" ,false);
    }

    public void setEditUserData(boolean isEditUserData){
        editor.putBoolean("isEditUserData" , isEditUserData);
        editor.apply();
    }

    public boolean isDoneUserData(){
        return sharedPreferences.getBoolean("isEditUserData" ,false);
    }

    public void setBookSaved(boolean isSave){
        editor.putBoolean("isSave" , isSave);
        editor.apply();
    }

    public boolean isBooksSaved(){
        return sharedPreferences.getBoolean("isSave" ,false);
    }


}
