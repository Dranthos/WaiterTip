package org.heavensfall.waitertip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.heavensfall.waitertip.R.id.Dinero;

public class MainActivity extends AppCompatActivity {



    List<Camarera> camareras = new ArrayList<>();
    List<String> resultados = new ArrayList<>();

    int camHabs = 0;

    Button boton;

    TextView dinero;
    TextView camarera1, camarera2, camarera3, camarera4, camarera5, camarera6, aviso;

    ImageView flechaArriba;

    EditText hora1, hora2, hora3, hora4, hora5, hora6;
    EditText resta1, resta2, resta3, resta4, resta5, resta6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        camarera1 = (TextView) findViewById(R.id.Camarera1);
        camarera2 = (TextView) findViewById(R.id.Camarera2);
        camarera3 = (TextView) findViewById(R.id.Camarera3);
        camarera4 = (TextView) findViewById(R.id.Camarera4);
        camarera5 = (TextView) findViewById(R.id.Camarera5);
        camarera6 = (TextView) findViewById(R.id.Camarera6);
        aviso = (TextView) findViewById(R.id.Aviso);

        boton = (Button) findViewById(R.id.Boton);

        camarera1.setText(prefs.getString("Nombre1",getString(R.string.trabajador) + "1"));
        camarera2.setText(prefs.getString("Nombre2",getString(R.string.trabajador) + "2"));
        camarera3.setText(prefs.getString("Nombre3",getString(R.string.trabajador) + "3"));
        camarera4.setText(prefs.getString("Nombre4",getString(R.string.trabajador) + "4"));
        camarera5.setText(prefs.getString("Nombre5",getString(R.string.trabajador) + "5"));
        camarera6.setText(prefs.getString("Nombre6",getString(R.string.trabajador) + "6"));

        GrabData();
        SeleccionarCamareras(prefs.getInt("NumeroCamareras",0));

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calcular();
            }
        });

    }

    void Calcular(){

        GrabData();

        List<TextView> nombres = Arrays.asList(camarera1, camarera2, camarera3, camarera4, camarera5, camarera6);
        List<EditText> horas = Arrays.asList(hora1, hora2, hora3, hora4, hora5, hora6);
        List<EditText> restas = Arrays.asList(resta1, resta2, resta3, resta4, resta5, resta6);

        int resultado;
        int maxHoras = 0;
        int Dinero;

        if(dinero.getText().toString().trim().equals("null") || dinero.getText().toString().trim().length() <= 0){
            Toast.makeText(getApplicationContext(), "Introduce una cantidad de dinero válida", Toast.LENGTH_LONG).show();
            return;
        }
        else Dinero = Integer.parseInt(dinero.getText().toString());

        resultados.clear();
        camareras.clear();
        camHabs = 0;

        for (int i = 0; i < nombres.size(); i++) {          //Primero hago una lista con los empleados activos
            Camarera cam = new Camarera();

            cam.SetNombre(nombres.get(i).getText().toString());
            cam.SetHoras(horas.get(i).getText().toString());
            cam.activa = horas.get(i).isEnabled();
            if(cam.activa && !cam.error) maxHoras += cam.GetHoras();
            cam.SetResta(restas.get(i).getText().toString());

            camareras.add(cam);
            }

        for (int i = 0; i < camareras.size(); i++) {        //Calculo con las camareras que tengo
            Camarera cam = camareras.get(i);
            if (cam.activa) {
                camHabs++;
                if (cam.error) {
                    Toast.makeText(getApplicationContext(), cam.error_S, Toast.LENGTH_LONG).show();
                    return;
                }

                resultado = ((cam.GetHoras() * Dinero) / maxHoras) - cam.GetResta();
                resultados.add(cam.GetNombre() + " se lleva " + resultado + "€");
            }
        }

        Intent intent = new Intent(MainActivity.this, Tabla.class);
        intent.putExtra("lista", (ArrayList<String>) resultados);
        intent.putExtra("habs", camHabs);
        startActivity(intent);

    }

    void SeleccionarCamareras(int cantidad){
        List<TextView> nombres = Arrays.asList(camarera1, camarera2, camarera3, camarera4, camarera5, camarera6);
        List<EditText> horas = Arrays.asList(hora1, hora2, hora3, hora4, hora5, hora6);
        List<EditText> restas = Arrays.asList(resta1, resta2, resta3, resta4, resta5, resta6);

        for (int i=0;i<=nombres.size() - 1;i++){
            nombres.get(i).setVisibility(View.INVISIBLE);
            horas.get(i).setEnabled(false);
            horas.get(i).setHint("");
            restas.get(i).setEnabled(false);
            restas.get(i).setHint("");
        }
        for (int i=0;i<=cantidad - 1;i++){

            nombres.get(i).setVisibility(View.VISIBLE);
            horas.get(i).setEnabled(true);
            horas.get(i).setHint(getString(R.string.horas));
            restas.get(i).setEnabled(true);
            restas.get(i).setHint(getString(R.string.resta));
        }
        //Si hay 0 camareras, muestro el aviso
        if(cantidad == 0){
            boton.setVisibility(View.GONE);
            aviso.setVisibility(View.VISIBLE);
        }
        else{
            boton.setVisibility(View.VISIBLE);
            aviso.setVisibility(View.GONE);
        }
    }

    void GrabData(){
        dinero = (TextView) findViewById(Dinero);

        camarera1 = (TextView) findViewById(R.id.Camarera1);
        camarera2 = (TextView) findViewById(R.id.Camarera2);
        camarera3 = (TextView) findViewById(R.id.Camarera3);
        camarera4 = (TextView) findViewById(R.id.Camarera4);
        camarera5 = (TextView) findViewById(R.id.Camarera5);
        camarera6 = (TextView) findViewById(R.id.Camarera6);

        hora1 = (EditText) findViewById(R.id.C_Horas1);
        hora2 = (EditText) findViewById(R.id.C_Horas2);
        hora3 = (EditText) findViewById(R.id.C_Horas3);
        hora4 = (EditText) findViewById(R.id.C_Horas4);
        hora5 = (EditText) findViewById(R.id.C_Horas5);
        hora6 = (EditText) findViewById(R.id.C_Horas6);

        resta1 = (EditText) findViewById(R.id.C_Resta1);
        resta2 = (EditText) findViewById(R.id.C_Resta2);
        resta3 = (EditText) findViewById(R.id.C_Resta3);
        resta4 = (EditText) findViewById(R.id.C_Resta4);
        resta5 = (EditText) findViewById(R.id.C_Resta5);
        resta6 = (EditText) findViewById(R.id.C_Resta6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

                camarera1.setText(prefs.getString("Nombre1",getString(R.string.trabajador) + "1"));
                camarera2.setText(prefs.getString("Nombre2",getString(R.string.trabajador) + "2"));
                camarera3.setText(prefs.getString("Nombre3",getString(R.string.trabajador) + "3"));
                camarera4.setText(prefs.getString("Nombre4",getString(R.string.trabajador) + "4"));
                camarera5.setText(prefs.getString("Nombre5",getString(R.string.trabajador) + "5"));
                camarera6.setText(prefs.getString("Nombre6",getString(R.string.trabajador) + "6"));
                SeleccionarCamareras(prefs.getInt("NumeroCamareras",0));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onA

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SimpleConfig.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }
}
