package com.wealthsimple.wealthsimplecodingchallenge.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wealthsimple.wealthsimplecodingchallenge.R;
import com.wealthsimple.wealthsimplecodingchallenge.WealthsimpleCodingChallengeApplication;
import com.wealthsimple.wealthsimplecodingchallenge.component.DaggerMainComponent;
import com.wealthsimple.wealthsimplecodingchallenge.component.MainComponent;
import com.wealthsimple.wealthsimplecodingchallenge.module.ActivityModule;
import com.wealthsimple.wealthsimplecodingchallenge.module.MainModule;
import com.wealthsimple.wealthsimplecodingchallenge.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.input_command)
    EditText mCommandEditText;

    @Bind(R.id.text_response)
    TextView mResponseText;

    @Inject
    Context mContext;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final MainComponent component = DaggerMainComponent.builder()
                .applicationComponent(((WealthsimpleCodingChallengeApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this))
                .mainModule(new MainModule(this))
                .build();

        component.inject(this);
        component.inject(mPresenter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPresenter.onRestoreInstanceState(savedInstanceState);
    }

    @OnClick(R.id.button_submit)
    public void onSubmit(View v) {
        mPresenter.sendCommand(mCommandEditText.getText().toString());
    }

    @OnEditorAction(R.id.input_command)
    public boolean onSubmit(KeyEvent event) {
        mPresenter.sendCommand(mCommandEditText.getText().toString());
        return true;
    }

    @Override
    public void showMessage(String message) {
        mResponseText.setText(message);
    }

    @Override
    public void showError(String error) {
        mResponseText.setText(error);
    }
}
