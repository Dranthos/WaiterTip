package org.heavensfall.waitertip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SimpleConfig extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView camarera1, camarera2, camarera3, camarera4, camarera5, camarera6;
    Button boton;
    Spinner spinner;

    int camarerasActivas = 0;
    private static final String[]nCamareras = {"Numero de camareras","1","2","3","4","5","6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_config);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        camarera1 = (TextView) findViewById(R.id.Nombre1);
        camarera2 = (TextView) findViewById(R.id.Nombre2);
        camarera3 = (TextView) findViewById(R.id.Nombre3);
        camarera4 = (TextView) findViewById(R.id.Nombre4);
        camarera5 = (TextView) findViewById(R.id.Nombre5);
        camarera6 = (TextView) findViewById(R.id.Nombre6);

        boton = (Button) findViewById(R.id.botonconfig);
        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,nCamareras);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(prefs.getInt("NumeroCamareras",0));

        camarera1.setText(prefs.getString("Nombre1",getString(R.string.trabajador) + "1"));
        camarera2.setText(prefs.getString("Nombre2",getString(R.string.trabajador) + "2"));
        camarera3.setText(prefs.getString("Nombre3",getString(R.string.trabajador) + "3"));
        camarera4.setText(prefs.getString("Nombre4",getString(R.string.trabajador) + "4"));
        camarera5.setText(prefs.getString("Nombre5",getString(R.string.trabajador) + "5"));
        camarera6.setText(prefs.getString("Nombre6",getString(R.string.trabajador) + "6"));

        SeleccionarCamareras(0);

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Guardar();
            }
        });
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        camarerasActivas = position;

        if(position == 0) Toast.makeText(parent.getContext(), "ATENCIÓN: ¡No has elegido camareras!", Toast.LENGTH_LONG).show();
        else SeleccionarCamareras(position);
        //Toast.makeText(parent.getContext(), "Camareras seleccionadas: " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void SeleccionarCamareras(int cantidad){
        List<TextView> nombres = Arrays.asList(camarera1, camarera2, camarera3, camarera4, camarera5, camarera6);

        for (int i=0;i<=nombres.size() - 1;i++){
            nombres.get(i).setEnabled(false);
        }
        for (int i=0;i<=cantidad - 1;i++){
            nombres.get(i).setEnabled(true);
        }
    }

    void Guardar(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("Nombre1",camarera1.getText().toString());
        editor.putString("Nombre2",camarera2.getText().toString());
        editor.putString("Nombre3",camarera3.getText().toString());
        editor.putString("Nombre4",camarera4.getText().toString());
        editor.putString("Nombre5",camarera5.getText().toString());
        editor.putString("Nombre6",camarera6.getText().toString());
        editor.putInt("NumeroCamareras", camarerasActivas);

        editor.apply();

        Toast.makeText(getApplicationContext(),"¡Nombres guardados!", Toast.LENGTH_LONG).show();

        Intent returnIntent = new Intent();
        //returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
