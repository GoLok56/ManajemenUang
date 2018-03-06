package io.github.golok56.manajemenuang.ui.user.newuser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.ui.base.BaseDialog;

/**
 * @author Satria Adi Putra
 */

public class NewUserDialog extends BaseDialog {
    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";

    @BindView(R.id.et_user_name_dialog_create_user)
    EditText mEtName;
    @BindView(R.id.et_user_wallet_dialog_create_user)
    EditText mEtWallet;

    private NewUserViewModel mViewModel;

    private Communicator mCommunicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewUserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Communicator) {
            mCommunicator = (Communicator) context;
        } else {
            throw new RuntimeException("Error! Activity must implement Communicator interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_user, null);
        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(OK, (dialogInterface, i) -> {
                    mViewModel.createUser(
                            mEtName.getText().toString(),
                            mEtWallet.getText().toString()
                    );
                    mCommunicator.onPositive();
                })
                .setNegativeButton(CANCEL, (dialogInterface, i) -> {
                    showToast(mViewModel.getValidationText());
                    getActivity().finish();
                })
                .create();
    }

    public interface Communicator {
        void onPositive();
    }

}
