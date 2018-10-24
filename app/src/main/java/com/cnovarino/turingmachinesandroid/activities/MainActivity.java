package com.cnovarino.turingmachinesandroid.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cnovarino.turingmachinesandroid.R;
import com.cnovarino.turingmachinesandroid.fragments.QRFragment;
import com.cnovarino.turingmachinesandroid.fragments.TuringMachineFragment;
import com.cnovarino.turingmachinesandroid.listener.OnFragmentInteractionListener;
import com.cnovarino.turingmachinesandroid.managers.TuringMachineManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    FragmentManager fm;
    TuringMachineFragment turingMachineFragment;
    QRFragment qrFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        turingMachineFragment = (TuringMachineFragment) fm.findFragmentByTag("TuringMachineFragment");
        qrFragment = (QRFragment) fm.findFragmentByTag("QRFragment");


        if(turingMachineFragment == null){
            turingMachineFragment = new TuringMachineFragment();
            turingMachineFragment.setRetainInstance(true);

            fm.beginTransaction()
                    .replace(R.id.fragment_layout,turingMachineFragment,"TuringMachineFragment")
                    .addToBackStack("TuringMachineFragment")
                    .commit();
        }

        if(qrFragment == null){

            qrFragment = new QRFragment();
            qrFragment.setRetainInstance(true);

        }
    }

    @Override
    public void onBackPressed() {
        if(fm.getBackStackEntryCount() > 1){
            super.onBackPressed();
        }
    }

    @Override
    public void onReadQR() {

        Dexter.withActivity(MainActivity.this)
                .withPermissions(Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(!report.areAllPermissionsGranted())
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Permisos")
                                    .setMessage("Debe dar el permiso de la camara para poder escanear un codigo QR.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create().show();

                        else{
                            fm.beginTransaction()
                                    .replace(R.id.fragment_layout,qrFragment,"QRFragment")
                                    .addToBackStack("QRFragment")
                                    .commit();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();

    }

    @Override
    public void onCodeScanned(String code) {

        int result = TuringMachineManager.deserialize(code);

        if(result == 1){
            fm.popBackStackImmediate("TuringMachineFragment",0);
            turingMachineFragment.loadCurrentMachine();

        }else{

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Error")
                    .setMessage("El codigo escaneado no contiene una maquina de turing valida")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qrFragment.resetCamera();
                            dialog.dismiss();
                        }
                    }).create().show();

        }

    }
}
