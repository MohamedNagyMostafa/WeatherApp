package com.example.android.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by mohamed nagy on 9/17/2016.
 */
public class LocationDialog extends DialogFragment {

    private int selectedItem ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // set builder which builds dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose The City ..")
                .setSingleChoiceItems(getCities(), selectedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedItem = i;
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WeatherMainActivity.createNewURL(getCities()[selectedItem]);
                        Toast.makeText(getActivity(),
                                "Please Refresh The List",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return builder.create();
    }
    // Set Cities to array and return it
    private String[] getCities(){
        String[] cities = {
                "Cairo","Giza", "Alexandria",
                "Shubra El-Kheima","Port Said",
        "Suez" ,"El-Mahalla El-Kubra","Luxor",
        "Mansoura","Tanta","Asyut","Ismaïlia",
        "Faiyum","Zagazig","Damietta","Aswan",
        "Minya","Damanhur","Beni Suef","Hurghada",
        "Qena","Sohag","Shibin Al Kawm, Al Minufiyah",
        "Banha","Arish"
        };
        return cities;
    }
}
