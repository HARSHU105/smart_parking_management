package com.example.loginactivity.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginactivity.BaseFragment;
import com.example.loginactivity.HomeActivity;
import com.example.loginactivity.PrefManager;
import com.example.loginactivity.R;
import com.example.loginactivity.utility.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    EditText etoldpswd  , etnewpswd, etconfirmpswd;
    Button btnSubmit;
    PrefManager prefManager;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        prefManager =new PrefManager(this.getActivity());
        initialize(view);

        return view;
    }

    private void initialize(View view) {

        etoldpswd = (EditText) view.findViewById(R.id.etoldpswd);
        etnewpswd = (EditText) view.findViewById(R.id.etnewpswd);
        etconfirmpswd = (EditText) view.findViewById(R.id.etconfirmpswd);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);


    }

    private void dbUpdatePWD(){
        showDialog("Please wait..\n Updating the data.");
        String strNewPassword = etnewpswd.getText().toString().trim();
        String strOLDPassword = etoldpswd.getText().toString().trim();
        String strMob = prefManager.geMobile();



        DatabaseReference rootDataBase = FirebaseDatabase.getInstance().getReference().child("users");

        rootDataBase.child(strMob).child("password").setValue(strNewPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                cancelDialog();
                showAlert("Password Updated Successfully......!");
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                cancelDialog();
                Snackbar.make(etoldpswd, "password is incorrect", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, this.getActivity());
        if (view.getId() == R.id.btnSubmit)
        {

            if (etoldpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
                etoldpswd.requestFocus();
                //   etoldpswd.setError("Enter Password");
                return;
            }
            if (etnewpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Enter New Password", Toast.LENGTH_SHORT).show();
                etnewpswd.requestFocus();
//                etnewpswd.setError("Enter New Password");
                return;
            }
            if (etconfirmpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "RE-Enter Password", Toast.LENGTH_SHORT).show();
                etconfirmpswd.requestFocus();
//                etconfirmpswd.setError("RE-Enter Password");
                return;
            }
            if (!etconfirmpswd.getText().toString().matches(etnewpswd.getText().toString())) {

                Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
                etconfirmpswd.requestFocus();
//                etconfirmpswd.setError("Password Not Matched");
                return;
            }

            dbUpdatePWD();

        }


    }
}