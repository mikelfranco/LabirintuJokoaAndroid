package com.unieibar.uni_58_labirintua.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.unieibar.uni_58_labirintua.R;

public class MezuaDialog extends Dialog implements android.view.View.OnClickListener {

    // Propietate publikoak
    public Activity activity;
    public Dialog dialog;
    public TextView lbl_titulua, lbl_galdera;
    public Button btn_bai, btn_ez;

    // Interfaze erreferentzia
    private IMezuaDialogListener noriErantzun;

    public MezuaDialog(Activity a) {
        super(a);
        this.activity = a;
    }

    public void setNoriErantzun(IMezuaDialogListener noriErantzun)
    {
        this.noriErantzun = noriErantzun;
    }

    public void setTitulua(String titulua)
    {
        lbl_titulua.setText(titulua);
    }

    public void setGaldera(String galdera)
    {
        lbl_galdera.setText(galdera);
    }

    public void setBaiTestua(String baiTestua)
    {
        btn_bai.setText(baiTestua);
    }

    public void setEzTestua(String ezTestua)
    {
        btn_ez.setText(ezTestua);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mezua_dialog);

        lbl_titulua = (TextView)findViewById(R.id.lbl_titulua);
        lbl_galdera = (TextView)findViewById(R.id.lbl_galdera);
        btn_bai = (Button) findViewById(R.id.btn_bai);
        btn_ez = (Button) findViewById(R.id.btn_ez);
        btn_bai.setOnClickListener(this);
        btn_ez.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_bai:
                // Adierazi BAI erantzun dela
                if (this.noriErantzun != null)
                {
                    this.noriErantzun.erantzunaJaso(0, getContext().getResources().getString(R.string.bai));
                }
                break;
            case R.id.btn_ez:
                // Adierazi EZ erantzun dela
                if (this.noriErantzun != null)
                {
                    this.noriErantzun.erantzunaJaso(1, getContext().getResources().getString(R.string.ez));
                }
                break;
            default:
                // Adierazi espero ez genuen zerbait erantzun dela
                this.noriErantzun.erantzunaJaso(-1,getContext().getResources().getString(R.string.ezezaguna));
                break;
        }
        // Dialog-a itxi beti erantzun ondoren
        this.dismiss();
    }
}
