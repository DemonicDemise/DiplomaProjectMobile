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

import com.bumptech.glide.Glide;
import com.example.diploma.activities.LoginActivity;
import com.example.diploma.databinding.ActivityMainBinding;
import com.example.diploma.models.UserModel;
import com.example.diploma.ui.cart.UserCartFragment;
import com.example.diploma.ui.category.CategoryFragment;
import com.example.diploma.ui.favourite.FavouriteFragment;
import com.example.diploma.ui.home.HomeFragment;
import com.example.diploma.ui.order.UserOrderFragment;
import com.example.diploma.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private ProgressBar pb;
    private FirebaseDatabase mDb;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mDb = FirebaseDatabase.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        drawerLayout = binding.drawerLayout;

        NavigationView navigationView = binding.navView;

        toolbar = binding.appBarMain.toolbarMain;
        //Toolbar
        setSupportActionBar(toolbar);


        if(mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
        }

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

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);
        CircleImageView headerImg = headerView.findViewById(R.id.nav_header_img);
        ImageView navImageView = headerView.findViewById(R.id.nav_background_img);

        if(mAuth.getCurrentUser() != null) {
            mDb.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserModel userModel = snapshot.getValue(UserModel.class);

                            headerName.setText(userModel.name);
                            headerEmail.setText(userModel.email);
                            Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(headerImg);
                          //  Glide.with(getApplicationContext()).load(userModel.getNavBackgroundImg()).into(navImageView);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "You are not logged in! Login first", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
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
//                Intent intent = new Intent(getApplicationContext(), NavCategoryActivity.class);
//                startActivity(intent);
                replaceFragment(new CategoryFragment());
                break;
            case R.id.nav_user_order:
                replaceFragment(new UserOrderFragment());
                break;
            case R.id.nav_user_cart:
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
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
}