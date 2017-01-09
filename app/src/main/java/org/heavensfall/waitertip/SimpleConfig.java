package org.heavensfall.waitertip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class SimpleConfig extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView camarera1, camarera2, camarera3, camarera4, camarera5, camarera6;
    Button boton;
    Spinner spinner;
    CheckBox divCheck, restCheck;

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
        divCheck = (CheckBox) findViewById(R.id.Div);
        restCheck = (CheckBox) findViewById(R.id.Rest);

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,nCamareras);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(prefs.getInt("NumeroCamareras",1));

        divCheck.setChecked(prefs.getBoolean("divCheck", false));
        restCheck.setChecked(prefs.getBoolean("restCheck", false));

        camarera1.setText(prefs.getString("Nombre1",getString(R.string.trabajador) + "1"));
        camarera2.setText(prefs.getString("Nombre2",getString(R.string.trabajador) + "2"));
        camarera3.setText(prefs.getString("Nombre3",getString(R.string.trabajador) + "3"));
        camarera4.setText(prefs.getString("Nombre4",getString(R.string.trabajador) + "4"));
        camarera5.setText(prefs.getString("Nombre5",getString(R.string.trabajador) + "5"));
        camarera6.setText(prefs.getString("Nombre6",getString(R.string.trabajador) + "6"));

        SeleccionarCamareras(0);
        Tutorial();

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Guardar();
            }
        });
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
        editor.putBoolean("divCheck", divCheck.isChecked());
        editor.putBoolean("restCheck",restCheck.isChecked());

        editor.apply();

        Toast.makeText(getApplicationContext(),"Datos guardados con éxito", Toast.LENGTH_LONG).show();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    void Tutorial(){

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Tuto2");

        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(spinner)
                        .setDelay(500)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Puedes configurar el número de empleados entre los que repartir el bote aquí.")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(camarera1)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Pulsa en cada uno de sus nombres para editarlos.")
                        .withRectangleShape(true)
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(restCheck)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Si no vas a necesitar restarle dinero a los empleados, marca esta casilla.")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(divCheck)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Si todos tus empleados trabajan las mismas horas, marcando esta casilla harás que el dinero se divida igualitariamente")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(boton)
                        .withRectangleShape()
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Pulsa este boton para guardar los cambios y volver a la pantalla principal")
                        .build()
        );

        sequence.start();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        camarerasActivas = position;

        if(position == 0) Toast.makeText(parent.getContext(), "ATENCIÓN: No has elegido camareras", Toast.LENGTH_LONG).show();
        else SeleccionarCamareras(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset_tutorial) {
            MaterialShowcaseView.resetAll(getBaseContext());
            Toast.makeText(getApplicationContext(),"Tutorial reiniciado", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
