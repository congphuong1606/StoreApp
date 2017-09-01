package com.example.mypc.stores.ui.main.fragment.usermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
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
import com.example.mypc.stores.ui.login.LoginActivity;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.utils.BitmapUtils;
import com.example.mypc.stores.utils.Constants;
import com.example.mypc.stores.utils.RealmUtils;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class UserManagerFragment extends BaseFragment implements UserManagerView, OnMapReadyCallback {
    @Inject
    SharedPreferences mPreferences;
    @Inject
    UserManagerPresenter mPresenter;
    @Inject
    SharedPreferences.Editor editor;

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
    @BindView(R.id.btn_save_post_history)
    Button btnSavePostHistory;
    @BindView(R.id.layout_user)
    LinearLayout layoutUser;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    @BindView(R.id.btn_sign_out)
    Button btnSignOut;
    @BindView(R.id.btn_save_number)
    Button btnSaveNumber;
    @BindView(R.id.btn_save_acc_name)
    Button btnSaveAccName;


    //map

    private GoogleMap mMap;
    double lat;
    double lng;

    private SupportMapFragment mapFragment;
    private String accFullname;
    private String accAvatar;
    private String accType;
    private long accId;
    private String accName;
    private String accNumber;
    private Realm realm;


    @Override
    protected void initView(View view) {
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.store_map);
        btnSaveAccName.setVisibility(View.GONE);
        btnSaveNumber.setVisibility(View.GONE);


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
        this.realm = RealmUtils.with(this).getRealm();
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
            R.id.btn_save_post_history, R.id.btn_setting, R.id.btn_sign_out,
            R.id.btn_save_number,R.id.btn_save_acc_name
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case  R.id.btn_save_number:

                btnEditNumber.setVisibility(View.VISIBLE);
                showKeyboard(edtEditAccNumber,false);
                btnSaveNumber.setVisibility(View.GONE);
                break;
            case R.id.btn_save_acc_name:
                btnEditAccName.setVisibility(View.VISIBLE);
                btnSaveAccName.setVisibility(View.GONE);
                showKeyboard(edtEditAccName,false);
                break;
            case R.id.btn_edit_number:
                showKeyboard(edtEditAccNumber, true);
                btnEditNumber.setVisibility(View.GONE);
                btnSaveNumber.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_edit_acc_name:
                showKeyboard(edtEditAccName, true);
                btnEditAccName.setVisibility(View.GONE);
                btnSaveAccName.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_edit_acc_location:

                break;
            case R.id.btn_store_folow:
                break;
            case R.id.btn_save_post_history:
                ((MainActivity) getActivity()).showFagmentSaveHistory();
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_sign_out:
                RealmUtils.with(this).deleteBooks();
                editor.remove(Constants.PREF_TOKEN).commit();
                startActivity(new Intent(getContext(), LoginActivity.class));
                ((MainActivity) getActivity()).finish();

                break;
        }
    }


    @Override
    public void onLoadLocationSuccess(Location location) {
        lat = location.getLocationLat();
        lng = location.getLocationLng();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestFailure(String msg) {
        onShowErorr(msg);
    }


}
