package com.example.mypc.stores.ui.main.usermanager;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Location;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.utils.BitmapUtils;
import com.example.mypc.stores.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserManagerFragment extends BaseFragment implements UserManagerView, OnMapReadyCallback {
    @Inject
    SharedPreferences mPreferences;
    @Inject
    UserManagerPresenter mPresenter;
    @BindView(R.id.imv_avatar)
    CircleImageView imvAvatar;
    @BindView(R.id.tv_acc_name)
    TextView tvAccName;
    @BindView(R.id.edt_edit_acc_number)
    EditText edtEditAccNumber;
    @BindView(R.id.btn_edit_number)
    Button btnEditNumber;
    @BindView(R.id.edt_edit_acc_name)
    EditText edtEditAccName;
    @BindView(R.id.btn_edit_acc_name)
    Button btnEditAccName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.btn_edit_acc_location)
    Button btnEditAccLocation;
    @BindView(R.id.layout_store)
    LinearLayout layoutStore;
    @BindView(R.id.btn_store_folow)
    Button btnStoreFolow;
    @BindView(R.id.btn_lich_su)
    Button btnLichSu;
    @BindView(R.id.layout_user)
    LinearLayout layoutUser;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;
    Unbinder unbinder;
    //map

    private GoogleMap mMap;
    double lat;
    double lng;

    private SupportMapFragment mapFragment;
    private String accFullname;
    private Bitmap bitmap;
    private String accAvatar;
    private String accType;
    private long accId;
    private String accName;
    private String accNumber;
    @Override
    protected void initView(View view) {
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.store_map);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        LatLng latLng = new LatLng(lat, lng);

        Glide.with(getActivity().getApplicationContext())
                .load(accAvatar)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(40, 40) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = BitmapUtils.getRoundedCornerBitmap(resource, 150);
                        mMap.addMarker(new MarkerOptions().position(latLng)
                                .title(accFullname)
                                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))).showInfoWindow();
                    }
                });
        CameraPosition mCameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).bearing(40).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));

    }


    private void setView(String accType) {
        if (accType.equals("user")) {
            layoutStore.setVisibility(View.VISIBLE);

        } else layoutUser.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        getAcc();
        setView(accType);
        Glide.with(this).load(accAvatar).into(imvAvatar);
        edtEditAccNumber.setText(accNumber);
        tvAccName.setText(accName);
        edtEditAccName.setText(accFullname);

        mPresenter.getLocationStore(accId);


    }

    private void getAcc() {
        accType = mPreferences.getString(Constants.PREF_ACC_TYPE, "");
        accId = mPreferences.getLong(Constants.PREF_ACC_ID, 0);
        accAvatar = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        accName = mPreferences.getString(Constants.PREF_ACC_NAME, "");
        accFullname = mPreferences.getString(Constants.PREF_ACC_FULLNAME, "");
        accNumber = mPreferences.getString(Constants.PREF_ACC_NUMBER, "");
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


    @OnClick({R.id.btn_edit_number, R.id.btn_edit_acc_name,
            R.id.btn_edit_acc_location, R.id.btn_store_folow,
            R.id.btn_lich_su, R.id.btn_setting, R.id.btn_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_number:
                edtEditAccNumber.setEnabled(true);
                edtEditAccNumber.setClickable(true);

                break;
            case R.id.btn_edit_acc_name:
                edtEditAccName.setEnabled(true);
                edtEditAccNumber.setClickable(true);
                break;
            case R.id.btn_edit_acc_location:

                break;
            case R.id.btn_store_folow:
                break;
            case R.id.btn_lich_su:
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_sign_out:
                mPresenter.signOut(accId);
                break;
        }
    }


    @Override
    public void onLoadLocationSuccess(Location location) {
        lat = location.getLocationLat();
        lng = location.getLocationLng();
        mapFragment.getMapAsync(this);
    }


}
