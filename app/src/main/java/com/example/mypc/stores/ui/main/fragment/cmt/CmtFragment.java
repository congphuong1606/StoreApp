package com.example.mypc.stores.ui.main.fragment.cmt;


import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Comment;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.adapter.CmtAdapter;
import com.example.mypc.stores.ui.base.BaseFragment;
import com.example.mypc.stores.ui.main.MainActivity;
import com.example.mypc.stores.ui.main.fragment.listpost.ListPostFragment;
import com.example.mypc.stores.utils.Constants;
import com.example.mypc.stores.utils.TimeControler;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CmtFragment extends BaseFragment implements CmtFragmentView {
    ArrayList<Comment> comments;
    @Inject
    CmtFragmentPresenter mPresenter;
    @Inject
    SharedPreferences mPreferences;
    //
    @BindView(R.id.rcv_cmt)
    RecyclerView rcvCmt;
    @BindView(R.id.edt_new_cmt)
    EditText edtNewCmt;
    @BindView(R.id.btn_sent_cmt)
    Button btnSentCmt;
    private long cmtPostId;
    private long cmtAccId;
    private String cmtAccAvatar;
    private String cmtTime;
    private String cmtContent;
    private long cmtId;
    private CmtAdapter mAdapter;
    private int mPostPosition;


    @Override
    protected void initView(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        rcvCmt.setLayoutManager(manager);
        rcvCmt.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        comments = new ArrayList<>();
        mAdapter = new CmtAdapter(comments);
        rcvCmt.setAdapter(mAdapter);
        cmtPostId = getArguments().getLong("postId");
        mPostPosition = getArguments().getInt("postPosition");
        mPresenter.onLoadPostCmts(cmtPostId);


    }
    @Override
    public void onUploadNewCmtSuccess(Comment comment) {
        comments.add(comment);
        mAdapter.notifyDataSetChanged();
        mPresenter.onUpdatePost(cmtPostId);
    }

    @Override
    public void onUpdateCountPostCmtSuccess(Integer countPostCmt) {
        ListPostFragment.updateCountPostCmt(countPostCmt,cmtPostId,mPostPosition);
    }




    @Override
    protected int getLayoutID() {
        return R.layout.fragment_cmt;
    }

    @Override
    protected void onDestroyComposi() {
        mPresenter.onDestroy();
    }

    @Override
    protected void injectDependence(View view) {
        MyApplication.get().getAppComponent()
                .plus(new ViewModule(this)).injectTo(this);
    }


    @Override
    public void onLoadCmtSuccess(ArrayList<Comment> cmts) {
        comments.addAll(cmts);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFail(String s) {

    }




    @OnClick(R.id.btn_sent_cmt)
    public void onViewClicked() {
        initNewCmt();
        Comment cmt = new Comment(cmtId, cmtAccId, cmtAccAvatar, cmtPostId,
                cmtContent, cmtTime);
        mPresenter.onUploadNewCmt(cmt);
        edtNewCmt.setText("");
        hideKeyboard();
    }

    private void initNewCmt() {
        TimeControler time = new TimeControler();
        cmtAccId = mPreferences.getLong(Constants.PREF_ACC_ID, 0);
        cmtAccAvatar = mPreferences.getString(Constants.PREF_ACC_AVATAR, "");
        cmtContent = edtNewCmt.getText().toString().trim();
        cmtTime = time.getCurentTime() + "";
        cmtId = cmtAccId + cmtPostId + time.getLongCurentTime();
    }
}