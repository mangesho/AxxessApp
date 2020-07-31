package com.axxess.myapplication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.axxess.myapplication.R;
import com.axxess.myapplication.databinding.ActivityImageDetailBinding;
import com.axxess.myapplication.model.SearchModel;
import com.axxess.myapplication.viewmodels.ImageDetailViewModel;
import com.bumptech.glide.Glide;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_DATA = "imageData";
    private ImageDetailViewModel imageDetailViewModel;

    private SearchModel searchModel;
    private ActivityImageDetailBinding activityImageDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityImageDetailBinding =  DataBindingUtil.setContentView(this,R.layout.activity_image_detail);

        searchModel = getIntent().getParcelableExtra(IMAGE_DATA);
        imageDetailViewModel = ViewModelProviders.of(this, new ImageDetailViewModel.Factory(getApplication(), searchModel.getId()))
                .get(ImageDetailViewModel.class);

        // Set the lifecycleOwner so DataBinding can observe LiveData
        activityImageDetailBinding.setLifecycleOwner(this);
        activityImageDetailBinding.setViewModel(imageDetailViewModel);

        setActiobarTitle(getResources().getString(R.string.title_activity_image_detail));

        Glide.with(this)
                .load(searchModel.getCover())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(activityImageDetailBinding.imgvDetail);

        activityImageDetailBinding.btnSubmit.setOnClickListener(view -> {
            saveComment();
        });

    }

    private void setActiobarTitle(String title)
    {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.tv_title);
        titleTxtView.setText(title);
    }

    private void saveComment(){
        String comment = activityImageDetailBinding.edtCommentBox.getText().toString();
        if(!TextUtils.isEmpty(comment)){
            imageDetailViewModel.setComment(searchModel.getId(), comment);
            Toast.makeText(this, "Comment added succesfully.", Toast.LENGTH_SHORT).show();
        }
    }
}