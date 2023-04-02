package bn.nadamattar.libraryprojectnada.utils;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AppUtility {

    public static Animation addAnimation (Context context , int anim){
        Animation animation ;
        animation = AnimationUtils.loadAnimation(context, anim);
        return animation;
    }

}
