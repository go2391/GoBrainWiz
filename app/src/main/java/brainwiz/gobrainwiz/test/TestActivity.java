package brainwiz.gobrainwiz.test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import brainwiz.gobrainwiz.BaseActivity;
import brainwiz.gobrainwiz.BaseFragment;
import brainwiz.gobrainwiz.R;
import brainwiz.gobrainwiz.onlinetest.InstructionsFragment;

public class TestActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean isCompanyTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragmentById != null) {
                    if (fragmentById instanceof InstructionsFragment) {

                    } else if (fragmentById instanceof TestQuestionFragment) {
                        ((TestQuestionFragment) fragmentById).submitTest();
                    }
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        isCompanyTest = extras.getBoolean(BaseFragment.IS_COMPANY_TEST);

        if (isCompanyTest) {
            fragmentTransaction(InstructionsFragment.getInstance(extras), R.id.content_frame, false);
        } else {
            fragmentTransaction(TestQuestionFragment.getInstance(extras.getString(BaseFragment.ID), extras.getBoolean(BaseFragment.IS_REVIEW)), R.id.content_frame, false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                break;

        }


        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
