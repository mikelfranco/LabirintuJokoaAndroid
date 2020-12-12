package com.unieibar.uni_58_labirintua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unieibar.uni_58_labirintua.dialog.IMezuaDialogListener;
import com.unieibar.uni_58_labirintua.dialog.MezuaDialog;
import com.unieibar.uni_58_labirintua.logika.GeziakViewListener;
import com.unieibar.uni_58_labirintua.logika.ILabirintuaListener;
import com.unieibar.uni_58_labirintua.logika.Norabidea;
import com.unieibar.uni_58_labirintua.logika.ZailtasunMota;
import com.unieibar.uni_58_labirintua.view.GeziakView;
import com.unieibar.uni_58_labirintua.view.LabirintuaView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener,
                                                                View.OnClickListener,
                                                                ILabirintuaListener,
                                                                GeziakViewListener,
                                                                IMezuaDialogListener {

    // Propietate estatikoak
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 2;
    public static boolean BAIMENAK = false ;
    public static String [] zailtasuna = {  "Erraza",       // 0
                                            "Arrunta",      // 1
                                            "Zaila",        // 2
                                            "Oso zaila",    // 3
                                            "Zoratzekoa",   // 4
                                            "Eman tiro bat :'("};  // 5


    // Barne propietateak
    Spinner sp_zailtasuna;
    FloatingActionButton btn_berria, btn_ebatzi, btn_gorde;
    LabirintuaView labirintua;
    GeziakView gv_kontrola;
    boolean labirintua_ebatzita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_labirintua_logo_foreground); //also displays wide logo
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(true); //optional

        zailtasuna = new String[]{getString(R.string.zailtasun_erraza),       // 0
                                getString(R.string.zailtasun_arrunta),      // 1
                                getString(R.string.zailtasun_zaila),        // 2
                                getString(R.string.zailtasun_oso_zaila),    // 3
                                getString(R.string.zailtasun_zoratzekoa),   // 4
                                getString(R.string.zailtasun_emantirobat)};  // 5

        sp_zailtasuna = (Spinner)findViewById(R.id.sp_zailtasuna);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, zailtasuna);
        sp_zailtasuna.setAdapter(adapter);
        sp_zailtasuna.setOnItemSelectedListener(this);

        btn_berria = (FloatingActionButton)findViewById(R.id.btn_berria);
        btn_berria.setOnClickListener(this);
        btn_ebatzi = (FloatingActionButton)findViewById(R.id.btn_ebatzi);
        btn_ebatzi.setOnClickListener(this);
        btn_gorde = (FloatingActionButton)findViewById(R.id.btn_gorde);
        btn_gorde.setOnClickListener(this);

        labirintua = (LabirintuaView)findViewById(R.id.labirintua);
        labirintua.setZailtasuna(ZailtasunMota.ERRAZA);
        labirintua.setILabirintuaListener(this);

        gv_kontrola = (GeziakView)findViewById(R.id.gv_kontrola);
        gv_kontrola.setGeziakViewListener(this);

        labirintua_ebatzita = false;

        baimenak_eskatu();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        labirintu_berria();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //lbl_aukeratutakoa.setText("Ez duzu ezer aukeratu");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_berria:
                labirintu_berria();
                break;
            case R.id.btn_ebatzi:
                labirintua_ebatzi();
                break;
            case R.id.btn_gorde:
                labirintua_gorde();
                break;
        }
    }

    private void labirintu_berria()
    {
        if (sp_zailtasuna.getSelectedItemPosition() == 0)
        {
            labirintua.setZailtasuna(ZailtasunMota.ERRAZA);
        }
        else if (sp_zailtasuna.getSelectedItemPosition() == 1)
        {
            labirintua.setZailtasuna(ZailtasunMota.ARRUNTA);
        }
        else if (sp_zailtasuna.getSelectedItemPosition() == 2)
        {
            labirintua.setZailtasuna(ZailtasunMota.ZAILA);
        }
        else if (sp_zailtasuna.getSelectedItemPosition() == 3)
        {
            labirintua.setZailtasuna(ZailtasunMota.OSO_ZAILA);
        }
        else if (sp_zailtasuna.getSelectedItemPosition() == 4)
        {
            labirintua.setZailtasuna(ZailtasunMota.ZORATZEKOA);
        }
        else if (sp_zailtasuna.getSelectedItemPosition() == 5)
        {
            labirintua.setZailtasuna(ZailtasunMota.EMANTIROBAT);
        }

        labirintua.labirintu_berria();
    }

    private void labirintua_ebatzi()
    {
        if (!labirintua_ebatzita) {
            labirintua.bidea_bilatu();
            labirintua_ebatzita = true;
        } else {
            labirintua.labirintua_garbitu();
            labirintua_ebatzita = false;
        }
    }

    private void labirintua_gorde()
    {
        Bitmap labirintua_bitmap = getBitmapFromView(labirintua);
        String fitxategi_izena = gordeIrudiaFitxategian(labirintua_bitmap);
        Toast.makeText(this, getString(R.string.fitxategia_gordeta) + fitxategi_izena + getString(R.string.fitxategia_bukaera), Toast.LENGTH_LONG).show();
    }

    @Override
    public void erantzunaJaso(int erantzunaId, String erantzuna) {
        switch (erantzunaId)
        {
            case 0: //BAI
                Toast.makeText(this, getString(R.string.partida_berria), Toast.LENGTH_SHORT).show();
                this.btn_berria.callOnClick();
                break;
            case 1: // EZ
                Toast.makeText(this, getString(R.string.partidarik_ez), Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void jokoa_irabazi_duzu() {
        MezuaDialog mezuaDialog = new MezuaDialog(this);
        mezuaDialog.setNoriErantzun(this);
        mezuaDialog.show();
    }

    @Override
    public void onMugitu(Norabidea norabidea) {
        boolean mugitu_da = labirintua.mugitu_jokalaria(norabidea);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (mugitu_da) {
            vibe.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            vibe.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_HEAVY_CLICK));
        }
    }


    // Baimenak eskatu erabiltzaileari
    private void baimenak_eskatu()
    {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                        getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        {
            // Baimenak falta dira, eskatu egingo dizkiogu erabiltzaileari
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {
                            Manifest.permission.VIBRATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE_PERMISSIONS);
        }
        else
        {
            // Dagoeneko baditugu behar ditugun baimen guztiak
            BAIMENAK = true;
        }
    }

    // Baimenak eskatzearen erantzuna jasotakoan metodo hau exekutatuko da
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS && grantResults.length > 0)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                // Dagoeneko baditugu behar ditugun baimen guztiak
                BAIMENAK = true;
            }
            else
            {
                Toast.makeText(this, R.string.baimenak_falta, Toast.LENGTH_LONG).show();
                // Ez dauzkagu behar ditugun baimen guztiak
                BAIMENAK = false;
            }
        }
    }


    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private String gordeIrudiaFitxategian(Bitmap irudia)
    {
        String str_data_ordua;
        String fitxategi_izena = "???";
        File file;
        FileOutputStream fOut = null;
        try {
            //String karpeta_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Labirintua";
            String karpeta_path  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Labirintua";
            File karpeta = new File(karpeta_path);
            if(!karpeta.exists()) { // Ez baldin bada karpeta existitzen, sortu
                karpeta.mkdirs();
            }
            //String str_data_ordua = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());
            str_data_ordua = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.forLanguageTag("ES")).format(new Date());
            fitxategi_izena = "LABIRINTUA_" + str_data_ordua + ".png";
            file = new File(karpeta, fitxategi_izena);
            fOut = new FileOutputStream(file);

            irudia.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fitxategi_izena;
    }
}