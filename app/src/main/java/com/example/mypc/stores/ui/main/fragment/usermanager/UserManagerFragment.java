package com.example.mypc.stores.ui.main.fragment.usermanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Account;
import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.events.UserManagerclickListener;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.login.LoginActivity;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.utils.DialogUtils;
import com.example.mypc.stores.ui.main.utils.KeyBoardUtils;
import com.example.mypc.stores.utils.Constants;
import com.example.mypc.stores.utils.RealmUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

import static android.app.Activity.RESULT_OK;


public class UserManagerFragment extends BaseFragment
        implements UserManagerView, UserManagerclickListener {
    @Inject
    SharedPreferences mPreferences;
    @Inject
    UserManagerPresenter mPresenter;
    @Inject
    SharedPreferences.Editor editor;
    private UserFragmentManagerControler mControler;
    private DialogUtils dialogUtils;


    //map
    double lat;
    double lng;
    @BindView(R.id.imv_avatar)
    CircleImageView imvAvatar;
    @BindView(R.id.tv_acc_name)
    TextView tvAccName;
    @BindView(R.id.edt_edit_acc_number)
    EditText edtEditAccNumber;
    @BindView(R.id.btn_save_number)
    Button btnSaveNumber;
    @BindView(R.id.btn_edit_number)
    Button btnEditNumber;
    @BindView(R.id.edt_edit_acc_name)
    EditText edtEditAccName;
    @BindView(R.id.btn_save_acc_name)
    Button btnSaveAccName;
    @BindView(R.id.btn_edit_acc_name)
    Button btnEditAccName;
    @BindView(R.id.edt_lat)
    EditText edtLat;
    @BindView(R.id.edt_lng)
    EditText edtLng;
    @BindView(R.id.btn_save_acc_location)
    Button btnSaveAccLocation;
    @BindView(R.id.btn_edit_acc_location)
    Button btnEditAccLocation;
    @BindView(R.id.layout_store)
    RelativeLayout layoutStore;
    @BindView(R.id.btn_save_post_history)
    Button btnSavePostHistory;
    @BindView(R.id.layout_user)
    LinearLayout layoutUser;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;

    private ProgressDialog progressDialog;
    private String accFullname;
    private String accAvatar;
    private String accType;
    private long accId;
    private String accName;
    private String accNumber;
    private Realm realm;
    private byte[] picByte;
    private Account mAccount;
    private String accPass;


    @Override
    protected void initView(View view) {
        btnSaveAccName.setVisibility(View.GONE);
        btnSaveNumber.setVisibility(View.GONE);
        btnSaveAccLocation.setVisibility(View.GONE);
        getAcc();
        setView(accType);

    }


    private void setView(String accType) {
        if (accType.equals("user")) {
            layoutStore.setVisibility(View.GONE);
            layoutUser.setVisibility(View.VISIBLE);
        } else if(accType.equals("store")) {
            mPresenter.getLocationStore(accId);
            layoutUser.setVisibility(View.GONE);
            layoutStore.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void initData() {
        dialogUtils = new DialogUtils(progressDialog, getActivity());
        mControler = new UserFragmentManagerControler(getActivity());
        mControler.setViewControler(btnEditAccName,btnEditNumber,btnEditAccLocation,btnSaveNumber,
                btnSaveAccName,btnSaveAccLocation,edtEditAccName,edtEditAccNumber,edtLat);
        this.realm = RealmUtils.with(this).getRealm();
        Glide.with(this).load(accAvatar).error(R.drawable.ic_no_image).into(imvAvatar);
        edtEditAccNumber.setText(accNumber);
        tvAccName.setText(accName);
        edtEditAccName.setText(accFullname);



    }

    private void getAcc() {
        accType = mPreferences.getString(Constants.PREF_ACC_TYPE, "");
        accId = mPreferences.getLong(Constants.PREF_ACC_ID, 0);
        accAvatar = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        accName = mPreferences.getString(Constants.PREF_ACC_NAME, "");
        accFullname = mPreferences.getString(Constants.PREF_ACC_FULLNAME, "");
        accNumber = mPreferences.getString(Constants.PREF_ACC_NUMBER, "");
        accPass = mPreferences.getString(Constants.PREF_ACC_PASS, "");
        mAccount = new Account(accId, accType, accNumber, accName, accFullname, accPass, accAvatar);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_user_manager;
    }

    @Override
    protected void onDestroyComposi() {

    }

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    public void onRequestFailure(String msg) {
        DialogUtils.showErorr(getContext(), msg);
    }


    @OnClick({R.id.imv_avatar, R.id.btn_save_number, R.id.btn_edit_number, R.id.btn_save_acc_name, R.id.btn_edit_acc_name, R.id.btn_save_acc_location, R.id.btn_edit_acc_location, R.id.btn_save_post_history, R.id.btn_setting, R.id.btn_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_avatar:
                DialogUtils.showDialogGetPhotoMenu(getContext(), this);
                break;
            case R.id.btn_save_number:
                mControler.btnSaveNumberClick();
                KeyBoardUtils.hideKeyboard(getActivity());
                DialogUtils.showDialogConfim(getContext(), this, Constants.TYPE_NUMBER);
                break;
            case R.id.btn_edit_number:
                mControler.btnEditAccNumberClick();
                break;
            case R.id.btn_save_acc_name:
                mControler.btnSaveAccNameClick();
                KeyBoardUtils.hideKeyboard(getActivity());
                DialogUtils.showDialogConfim(getContext(), this, Constants.TYPE_NAME);
                break;
            case R.id.btn_edit_acc_name:
                mControler.btnEditAccNameClick();
                break;
            case R.id.btn_save_acc_location:
                mControler.btnSaveAccLocationClick();
                KeyBoardUtils.hideKeyboard(getActivity());
                DialogUtils.showDialogConfim(getContext(), this, Constants.TYPE_LOCATION);
                break;
            case R.id.btn_edit_acc_location:
                mControler.btnEditAccLocationClick();

                break;
            case R.id.btn_save_post_history:
                KeyBoardUtils.hideKeyboard(getActivity());
                ((MainActivity) getActivity()).showFagmentSaveHistory();
                ((MainActivity) getActivity()).setOpenFragmentImageView();


                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_sign_out:
                onSignOut();
                break;
        }
    }

    private void onSignOut() {
        RealmUtils.with(this).deletePosts();
        editor.remove(Constants.PREF_TOKEN).commit();
        startActivity(new Intent(getContext(), LoginActivity.class));
        ((MainActivity) getActivity()).finish();
    }

    @Override
    public void eventChoosePhoto() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                , Constants.REQUEST_CHOOSE_PHOTO);
    }

    @Override
    public void eventTakePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    public void onConfim(int typeUpdate) {
        dialogUtils.showLoading();
        switch (typeUpdate) {
            case Constants.TYPE_AVATAR:
                uploadToFireBase(picByte);
                break;
            case Constants.TYPE_NAME:
                accFullname = edtEditAccName.getText().toString().trim();
                mAccount.setAccFullName(accFullname);
                mPresenter.onUpdateAccInfo(mAccount);
                break;
            case Constants.TYPE_NUMBER:
                accNumber = edtEditAccNumber.getText().toString().trim();
                mAccount.setAccNumber(accNumber);
                mPresenter.onUpdateAccInfo(mAccount);
                break;
            case Constants.TYPE_LOCATION:
                lat = Double.parseDouble(edtLat.getText().toString().trim());
                lng = Double.parseDouble(edtLng.getText().toString().trim());
                mPresenter.updateLocation(new Location(accId, lat, lng));
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_TAKE_PHOTO) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        } else if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CHOOSE_PHOTO && data != null) {
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
            DialogUtils.showDialogConfim(getContext(), this, Constants.TYPE_AVATAR);

        }


    }

    private void uploadToFireBase(byte[] picByte) {
        mPresenter.uploadToFireBase(picByte);
    }

    @Override
    public void onLoadLocationSuccess(double locationLat, double locationLng) {
        edtLng.setText(String.valueOf(locationLng));
        edtLat.setText(String.valueOf(locationLat));

    }

    @Override
    public void onUploadPicSuccess(String avatarUrl) {
        accAvatar = avatarUrl;
        mAccount.setAccAvatar(accAvatar);
        mPresenter.onUpdateAccInfo(mAccount);
        Glide.with(getContext()).load(accAvatar).into(imvAvatar);
        editor.putString(Constants.PREF_ACC_AVATAR, "")
                .commit();
    }

    @Override
    public void onUpdateSuccess(Integer integer) {
        dialogUtils.hideLoading();
        editor.putString(Constants.PREF_ACC_AVATAR, accAvatar)
                .putString(Constants.PREF_ACC_FULLNAME, accFullname)
                .putString(Constants.PREF_ACC_NUMBER, accNumber)
                .commit();

    }
    @Override
    public void onUploadLocationSuccess() {
        dialogUtils.hideLoading();
    }
}
