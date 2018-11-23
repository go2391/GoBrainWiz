package brainwiz.gobrainwiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class RegistrationActivity extends BaseActivity {
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.blueSecondary));



        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getIntent().getBooleanExtra(LoginFragment.KEY_ISREGISTRATION, true)) {
            fragmentTransaction(new RegistrationFragment(), R.id.login_frame);
        } else {
            toolbar.setTitle(getString(R.string.forgot_password));
            fragmentTransaction(new ForgotPasswordFragment(), R.id.login_frame);
        }
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }


    protected void showProgress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        View inflate = this.getLayoutInflater().inflate(R.layout.progress_dialog_layout, null);
        builder.setView(inflate);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        configureProgress(alertDialog);
        alertDialog.show();

    }

    public void dismissProgress() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


}
