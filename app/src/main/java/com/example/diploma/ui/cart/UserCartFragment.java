package com.example.diploma.ui.cart;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diploma.R;
import com.example.diploma.activities.PlaceOrderActivity;
import com.example.diploma.adapters.DiscountAdapter;
import com.example.diploma.adapters.HomeAdapter;
import com.example.diploma.adapters.UserCartAdapter;
import com.example.diploma.models.DiscountModel;
import com.example.diploma.models.HomeModel;
import com.example.diploma.models.UserCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserCartFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;
    private RecyclerView recyclerView;
    private UserCartAdapter userCartAdapter;
    private List<UserCartModel> userCartModelList;
    private ProgressBar progressBar;
    private TextView overTotalAmount;
    private Button buyNow;
    private String documentId;

    private RecyclerView discountRec;

    private List<DiscountModel> discountModelList;

    private DiscountAdapter discountAdapter;

    private Spinner spinner;

    private TextView totalCost;

    private static PayPalConfiguration configuration;

    private String clientId = "AZQu2eoYgxAQaHMkTZbYRdfeLPH0Qz3MI1QTRWS_JNpJ4DvW8TuRjCza7KE8-v-ziVyG6UO7alZYLBQD";

    private int PAYPAL_REQUEST_CODE = 123;

    public UserCartFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_cart, container, false);

        mDb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = root.findViewById(R.id.cart_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        buyNow = root.findViewById(R.id.cart_buy_now);

        overTotalAmount = root.findViewById(R.id.total_price);

        userCartModelList = new ArrayList<>();
        userCartAdapter = new UserCartAdapter(getActivity(), userCartModelList);
        recyclerView.setAdapter(userCartAdapter);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        if(mAuth.getCurrentUser() != null) {
            mDb.collection("CurrentUser").document(mAuth.getCurrentUser().getUid())
                    .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                                    documentId = documentSnapshot.getId();

                                    UserCartModel userCartModel = documentSnapshot.toObject(UserCartModel.class);

                                    userCartModel.setDocumentId(documentId);

                                    userCartModelList.add(userCartModel);
                                    userCartAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                                calculateSumList(userCartModelList);
                            }
                        }
                    });
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getContext(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.checkout_bottom_layout,
                                view.findViewById(R.id.checkout_bottom_container)
                        );

                totalCost = bottomSheetView.findViewById(R.id.total_cost);

                totalCost.setText((int) calculateSum(userCartModelList) + "₸");

                bottomSheetView.findViewById(R.id.pick_discount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View bottomSheetView2 = LayoutInflater.from(getContext())
                                .inflate(
                                        R.layout.pick_discount_layout,
                                        view.findViewById(R.id.checkout_bottom_container)
                                );
                        bottomSheetDialog.setContentView(bottomSheetView2);
                        discountRec = bottomSheetView2.findViewById(R.id.discount_rec);
                        discountRec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                        discountModelList = new ArrayList<>();
                        discountAdapter = new DiscountAdapter(getContext(), discountModelList);
                        discountRec.setAdapter(discountAdapter);

                        mDb.collection("Discount")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                DiscountModel discountModel = document.toObject(DiscountModel.class);
                                                discountModelList.add(discountModel);
                                                discountAdapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Error" + task.getException(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        bottomSheetView2.findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheetDialog.setContentView(bottomSheetView);
                            }
                        });

                    }
                });

                bottomSheetView.findViewById(R.id.place_order_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getContext(), "Place Order", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        getPayment();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        return root;
    }

    private void getPayment() {
        String amount = String.valueOf((int)calculateSum(userCartModelList));

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","Code with Arvant", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getContext(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        payPalActivityResultLauncher.launch(intent);
    }

    public double calculateSum(List<UserCartModel> list) {
        double totalSum = 0.0;
        for(UserCartModel model: list){
            totalSum += model.getTotalPrice();
        }
        return totalSum;
    }

    public void calculateSumList(List<UserCartModel> list) {
        double totalSum = 0.0;
        for(UserCartModel model: list){
            totalSum += model.getTotalPrice();
        }
        overTotalAmount.setText("Total Sum: " + (int)totalSum + "₸");
    }


    ActivityResultLauncher<Intent> payPalActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if(result.getResultCode() == PAYPAL_REQUEST_CODE && data.getData() != null) {
                        PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                        if(paymentConfirmation != null){
                            try {
                                String paymentDetails = paymentConfirmation.toJSONObject().toString();
                                JSONObject object = new JSONObject(paymentDetails);
                                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                                intent.putExtra("itemList", (Serializable) userCartModelList);
                                startActivity(intent);
                            }catch (JSONException e){
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                                intent.putExtra("itemList", (Serializable) userCartModelList);
                                startActivity(intent);
                            }
                        }
                    } else if(result.getResultCode() == Activity.RESULT_CANCELED){
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                        intent.putExtra("itemList", (Serializable) userCartModelList);
                        startActivity(intent);
                    } else if(result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                        Toast.makeText(getContext(), "Invalid payment", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                        intent.putExtra("itemList", (Serializable) userCartModelList);
                        startActivity(intent);
                    }
                }
            }
    );
}