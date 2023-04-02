package bn.nadamattar.libraryprojectnada.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;

import com.google.firebase.auth.FirebaseAuth;

import bn.nadamattar.libraryprojectnada.R;
import bn.nadamattar.libraryprojectnada.databinding.ActivitySplashScreenBinding;
import bn.nadamattar.libraryprojectnada.utils.AppUtility;


@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends BaseActivity {
    ActivitySplashScreenBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation_image =AppUtility.addAnimation(SplashScreenActivity.this, R.anim.zoomin);
        binding.splashImage.setAnimation(animation_image);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (currentUser!=null){
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                }else {
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                }
                finish();
            }
        },2000);

    }


}