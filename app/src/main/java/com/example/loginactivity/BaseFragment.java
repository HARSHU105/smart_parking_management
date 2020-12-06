package com.example.loginactivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BaseFragment extends Fragment {

    ProgressDialog dialog;

   CustomPopUpListener customPopUpListener;
    public BaseFragment() {

    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    protected void showDialog() {
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(getActivity(), "", msg, true);
    }

    public static boolean isValidePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        return !(phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern));
    }

    public static boolean isValideEmailID(EditText editText) {
        String emailEntered = editText.getText().toString().trim();
        return !(emailEntered.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches());
    }

    public static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return !(text.isEmpty());
    }

    public static boolean validatePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        if (phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern)) {
            return false;
        }
        return true;
    }

    public static boolean isValidPan(String Pan) {
//        String rx = "/[A-Z]{5}[0-9]{4}[A-Z]{1}$/";
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(Pan);
        if (matcher.matches()) {
            return true;

        } else {
            return false;
        }
    }


    // region CustomPopup

    public interface CustomPopUpListener {

        void onPositiveButtonClick(Dialog dialog, View view);

        void onCancelButtonClick(Dialog dialog, View view);

    }

    public void registerCustomPopUp(CustomPopUpListener customPopUpListener) {
        if (customPopUpListener != null)
            this.customPopUpListener = customPopUpListener;
    }

    //endregion



    public void showAlert(String strBody) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Elite");

            builder.setMessage(strBody);
            String positiveText = "Ok";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Please try again..", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCustomToast(String strMessage) {


        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast,
                (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.txtMessage);
        text.setText("" + strMessage);

        Toast toast = new Toast(getActivity().getApplicationContext());
        // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    public void DownloadFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static boolean isValidPan(EditText editText) {
        String panNo = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        String panNoAlter = editText.getText().toString().toUpperCase();
        return !(panNoAlter.isEmpty() || !panNoAlter.matches(panNo));
    }


    public boolean validateMake (AutoCompleteTextView acMake , boolean IsMakeValid)
    {

        if (!isEmpty(acMake)){
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        } else if (IsMakeValid == false) {
            acMake.requestFocus();
            acMake.setError("Invalid Make");
            return false;

        }
        else{
            return true;
        }
    }

    public boolean validateInsCompName (EditText etInsComName)
    {

        if (!isEmpty(etInsComName)){
            etInsComName.requestFocus();
            etInsComName.setError("Enter Insurer Company Name");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateProposerName (EditText etName)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            etName.setError("Enter Proposer Name");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateNominee (EditText etName)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            etName.setError("Enter Nominee Name");
            return false;
        }else{
            return  true;
        }
    }
    public boolean validateNomineeRel (EditText etName)
    {

        if (!isEmpty(etName)){
            etName.requestFocus();
            etName.setError("Enter Relation with Nominee");
            return false;
        }else{
            return  true;
        }
    }
    public boolean validateCity (EditText etCity)
    {

        if (!isEmpty(etCity)){
        etCity.requestFocus();
        etCity.setError("Enter City");
        return false;
        }else{
            return  true;
        }
    }

    public boolean validateRTO (EditText etRTO)
    {

        if (!isEmpty(etRTO)){
            etRTO.requestFocus();
            etRTO.setError("Enter Nearest RTO");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validatePinCode (EditText etPincode) {
        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }else{
            return  true;
        }
    }

    public boolean validateVehicle (EditText etVehicle)
    {
        if (!isEmpty(etVehicle)) {
            etVehicle.requestFocus();
            etVehicle.setError("Enter Vehicle Number");
            return false;
        } else{
            return  true;
        }
    }
    public boolean validateProposer (EditText etNameOfProposer)
    {
        if (!isEmpty(etNameOfProposer)) {
            etNameOfProposer.requestFocus();
            etNameOfProposer.setError("Enter Name Of Proposer");
            return false;
        } else{
            return  true;
        }
    }

    public boolean validateSumAssured (EditText etSumAssured)
    {
        if (!isEmpty(etSumAssured)) {
            etSumAssured.requestFocus();
            etSumAssured.setError("Enter Sum Assured");
            return false;
        } else{
            return  true;
        }
    }

}
