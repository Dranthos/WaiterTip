package org.heavensfall.waitertip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Tabla extends AppCompatActivity {

    TextView camarera1, camarera2, camarera3, camarera4, camarera5, camarera6;
    List<TextView> nombres;
    List<String> lista;

    int camarerasHab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);

        camarera1 = (TextView) findViewById(R.id.Lista1);
        camarera2 = (TextView) findViewById(R.id.Lista2);
        camarera3 = (TextView) findViewById(R.id.Lista3);
        camarera4 = (TextView) findViewById(R.id.Lista4);
        camarera5 = (TextView) findViewById(R.id.Lista5);
        camarera6 = (TextView) findViewById(R.id.Lista6);

        nombres = Arrays.asList(camarera1, camarera2, camarera3, camarera4, camarera5, camarera6);

        for (int i = 0; i < nombres.size(); i++) {
            nombres.get(i).setEnabled(false);
            nombres.get(i).setText(" ");
        }
        if (lista != null) lista.clear();
        camarerasHab = 0;

        lista = getIntent().getStringArrayListExtra("lista");
        camarerasHab = getIntent().getIntExtra("habs", 0);

        for (int i = 1; i <= camarerasHab; i++) {
            nombres.get(i - 1).setEnabled(true);
            nombres.get(i - 1).setText(lista.get(i - 1));
            nombres.get(i - 1).startAnimation(animation1);
        }

        camarerasHab = 0;
    }

    @Override
    public void onResume ()
    {  // After a pause OR at startup
        super.onResume();
        camarerasHab = 0;
    }
}



