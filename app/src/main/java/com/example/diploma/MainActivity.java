package com.example.diploma;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diploma.activities.LoginActivity;
import com.example.diploma.ui.cart.UserCartFragment;
import com.example.diploma.ui.category.CategoryFragment;
import com.example.diploma.ui.favorite.FavouriteFragment;
import com.example.diploma.ui.home.HomeFragment;
import com.example.diploma.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diploma.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private ProgressBar pb;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawerLayout = binding.drawerLayout;

        NavigationView navigationView = binding.navView;

        //Hooks
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        toolbar = findViewById(R.id.toolbar);
        toolbar = binding.appBarMain.toolbarMain;
        //Toolbar
        setSupportActionBar(toolbar);

       // setSupportActionBar(binding.appBarMain.toolbar);

//        logoImg = findViewById(R.id.logoImg);
//        logoImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
//        });

        //Hide or show menu
        Menu menu = navigationView.getMenu();
        if(mAuth.getCurrentUser() == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);
        } else{
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(true);
        }

        //To make sure that fragments are clickable
        navigationView.bringToFront();
        //Navigation Drawer Menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

//        View headerView = navigationView.getHeaderView(0);
//        TextView headerName = headerView.findViewById(R.id.nav_header_name);
//        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);
//        CircleImageView headerImg = headerView.findViewById(R.id.nav_header_img);
//        ImageView navImageView = headerView.findViewById(R.id.nav_background_img);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_category,R.id.nav_profile,
//                R.id.nav_offers, R.id.nav_new_products, R.id.nav_my_orders,
//                R.id.nav_my_carts)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        if(mAuth.getCurrentUser() == null) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_favourite:
                replaceFragment(new FavouriteFragment());
                break;
            case R.id.nav_category:
                replaceFragment(new CategoryFragment());
                break;
//            case R.id.nav_orders:
//                replaceFragment(new UserOrderFragment());
//                break;
            case R.id.nav_my_carts:
                replaceFragment(new UserCartFragment());
                break;
//            case R.id.nav_admin:
//                startActivity(new Intent(getApplicationContext(), AdminPageActivity.class));
//                break;
            case R.id.nav_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_profile:
                if(mAuth.getCurrentUser() != null){
                    replaceFragment(new ProfileFragment());
                    Toast.makeText(getApplicationContext(), "Wait you are already logged in", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You are not logged in! Login first", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                break;
            case R.id.nav_rate_us:
                Toast.makeText(getApplicationContext(), R.string.rate_us, Toast.LENGTH_LONG).show();
                break;
        }
//        logoImg.setVisibility(View.GONE);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}