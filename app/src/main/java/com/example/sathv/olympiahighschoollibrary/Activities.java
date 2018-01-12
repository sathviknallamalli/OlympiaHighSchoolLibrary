package com.example.sathv.olympiahighschoollibrary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class Activities extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar;
    Login l = new Login();
    TextView studentName;
    TextView email;

    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        //set which page should first display once acitivities is launched after login
        CatalogFragment fragment = new CatalogFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        //set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //set the navigation view
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);

        //set the values for textviews in navheader
        studentName = (TextView) v.findViewById(R.id.name);
        studentName.setText(l.getFullName());

        email = (TextView) v.findViewById(R.id.email);
        email.setText(l.getEmail());


    }

    //when back is pressed on android phone
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //inflate the navigationview activities view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_catalog) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new CatalogFragment()).commit();
        } else if (id == R.id.nav_checkedbooks) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new CheckedFragment()).commit();
        } else if (id == R.id.nav_reservedbooks) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new ReservedFragment()).commit();
        } else if (id == R.id.nav_map) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new MapFragment()).commit();
        } else if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
        }  else if (id == R.id.nav_reminders) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new RemindersFragment()).commit();
        } else if (id == R.id.nav_account) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new AccountFragment()).commit();
        } else if (id == R.id.nav_information) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new InformationFragment()).commit();
        } else if (id == R.id.nav_wishlist) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new WishlistFragment()).commit();
        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new ShareFragment()).commit();
        } else if (id == R.id.nav_contact) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new ContactFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
