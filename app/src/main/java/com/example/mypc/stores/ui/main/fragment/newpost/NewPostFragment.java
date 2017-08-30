package com.example.mypc.stores.ui.main.fragment.newpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.BtnSaveClickListenner;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class NewPostFragment extends BaseFragment implements NewPostView, BtnSaveClickListenner {

    @BindView(R.id.imv_store_post_avatar)
    CircleImageView imvStorePostAvatar;
    @BindView(R.id.tv_store_post)
    TextView tvStorePost;
    @BindView(R.id.edt_new_post)
    EditText edtNewPost;
    Context mContext;

    @Inject
    SharedPreferences mPreferences;
    @Inject
    NewPostPresenter mPresenter;
    @BindView(R.id.imv_post_image)
    ImageView imvPostImage;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_garely)
    Button btnGarely;



    private String avatar;
    private String accFullName;
    private String postImage = null;
    private long accId;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_CHOOSE_PHOTO = 1;
    private byte[] picByte;


    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent()
                .plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    protected void initView(View view) {
        mContext = view.getContext();
        showKeyboard(edtNewPost,true);

    }

    @Override
    protected void initData() {
        ((MainActivity) getActivity()).setClickSaveListener(this);
        avatar = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        accFullName = mPreferences.getString(Constants.PREF_ACC_FULLNAME, "");
        accId = mPreferences.getLong(Constants.PREF_ACC_ID, 0);

        Glide.with(getContext()).load(avatar).into(imvStorePostAvatar);
        tvStorePost.setText(accFullName);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_new_post;
    }

    @Override
    protected void onDestroyComposi() {

    }


    @Override
    public void onLoadPostSuccess(Post post) {
        edtNewPost.setText("");
        getActivity().onBackPressed();
        ListPostFragment postFragment = new ListPostFragment();
        postFragment.setNewPost(post);


    }

    @Override
    public void onFail(String s) {
        Log.i("TAG onFail: ", s);

    }

    @Override
    public void onUploadPicSuccess(String picUrl) {
        postImage = picUrl;
    }


    @OnClick({R.id.btn_camera, R.id.btn_garely})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                takePicture();
                break;
            case R.id.btn_garely:
                choosePhoto();
                break;
        }
    }

    private void senPost() {
        String postContent = edtNewPost.getText().toString().trim();
        mPresenter.onUploadPost(accId, avatar, accFullName, postContent, postImage);

    }

    private void choosePhoto() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                , REQUEST_CHOOSE_PHOTO);

    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CHOOSE_PHOTO && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, imageByte);
            picByte = imageByte.toByteArray();
            uploadToFireBase(picByte);
        }
        Glide.with(mContext.getApplicationContext()).load(picByte).into(imvPostImage);


    }

    private void uploadToFireBase(byte[] picByte) {
        mPresenter.uploadPic(picByte);


    }

    @Override
    public void onclick() {
        showKeyboard(edtNewPost,false);
        if (picByte == null) {
            senPost();
        } else {
            if (postImage != null) {
                senPost();
            } else {
                Toast.makeText(getContext(), "up anh len file base chua thanh cong", Toast.LENGTH_LONG).show();
            }
        }
    }


}
