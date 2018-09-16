package com.andorid.fbgooglelogin;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.ProfileTracker;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginResultActivity extends AppCompatActivity {

    ProfileTracker profileTracker;
    Bitmap bitmap;
    CircleImageView imgProfile;
    TextView fullName;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_result);

        imgProfile = findViewById(R.id.img_pp);
        fullName = findViewById(R.id.txt_full_name);
        profile = Profile.getCurrentProfile();


        fullName.setText(profile.getFirstName() + " " + profile.getLastName());

        //Log.e("profile picture", ""+profile.getProfilePictureUri(100,100));

        //imgProfile.setImageURI(profile.getProfilePictureUri(100,100));

        //Glide.with(getApplicationContext()).load(profile.getProfilePictureUri(100,100)).into(imgProfile);

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imgProfile.setImageBitmap();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }
}
