package io.github.golok56.manajemenuang.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.viewmodels.MainViewModel;
import io.github.golok56.manajemenuang.views.activity.BaseActivity;
import io.github.golok56.manajemenuang.views.adapter.PageAdapter;
import io.github.golok56.manajemenuang.views.fragment.dialog.NewUserDialog;

public class MainActivity extends BaseActivity implements NewUserDialog.Communicator {
    private static final int TIME_DELAY = 2000;
    private static final String DIALOG_TAG = "dialog_tag";

    @BindView(R.id.vp_main_activity)
    ViewPager mViewPager;
    @BindView(R.id.tl_main_activity)
    TabLayout mTabLayout;

    private MainViewModel mViewModel;

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get rid the shadow below ActionBar
        getSupportActionBar().setElevation(0);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (!mViewModel.hasLogin()) {
            showDialog();
        } else {
            initView();
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewModel.getBackPressedDuration() + TIME_DELAY > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
        } else {
            showToast(mViewModel.getConfirmText());
        }

        mViewModel.setBackPressedDuration(System.currentTimeMillis());
    }

    @Override
    public void onPositive() {
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void showDialog() {
        new NewUserDialog().show(getSupportFragmentManager(), DIALOG_TAG);
    }

}
