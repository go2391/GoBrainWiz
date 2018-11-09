package brainwiz.gobrainwiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LoginActivity extends BaseActivity {
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


//        if (getActionBar() != null) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//            getActionBar().setHomeButtonEnabled(true);
//        }


        fragmentTransaction(new LoginFragment(), R.id.login_frame);
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
