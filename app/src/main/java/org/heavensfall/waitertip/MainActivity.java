package org.heavensfall.waitertip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static org.heavensfall.waitertip.R.id.Dinero;

public class MainActivity extends AppCompatActivity {

    List<Camarera> camareras = new ArrayList<>();
    List<String> resultados = new ArrayList<>();

    int camHabs = 0;
    boolean divCheck = false;
    boolean restCheck = false;
    Button boton;

    TextView dinero;
    TextView camarera1, camarera2, camarera3, camarera4, camarera5, camarera6;

    EditText hora1, hora2, hora3, hora4, hora5, hora6;
    EditText resta1, resta2, resta3, resta4, resta5, resta6;

    private FeedbackDialog feedBack;

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

        boton = (Button) findViewById(R.id.Boton);

        camarera1.setText(prefs.getString("Nombre1",getString(R.string.worker) + "1"));
        camarera2.setText(prefs.getString("Nombre2",getString(R.string.worker) + "2"));
        camarera3.setText(prefs.getString("Nombre3",getString(R.string.worker) + "3"));
        camarera4.setText(prefs.getString("Nombre4",getString(R.string.worker) + "4"));
        camarera5.setText(prefs.getString("Nombre5",getString(R.string.worker) + "5"));
        camarera6.setText(prefs.getString("Nombre6",getString(R.string.worker) + "6"));

        GrabData();
        SeleccionarCamareras();
        Tutorial();

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
            if(!divCheck) cam.SetHoras(horas.get(i).getText().toString());
            cam.activa = nombres.get(i).isEnabled();
            if(cam.activa && !cam.error && !divCheck) maxHoras += cam.GetHoras();
            if(!restCheck) cam.SetResta(restas.get(i).getText().toString());

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

                if (!divCheck) resultado = ((cam.GetHoras() * Dinero) / maxHoras) - cam.GetResta();
                else resultado = (Dinero /camareras.size()) - cam.GetResta();
                resultados.add(cam.GetNombre() + " se lleva " + resultado + "€");
            }
        }

        Intent intent = new Intent(MainActivity.this, Tabla.class);
        intent.putExtra("lista", (ArrayList<String>) resultados);
        intent.putExtra("habs", camHabs);
        startActivity(intent);

    }

    void SeleccionarCamareras(){
        List<TextView> nombres = Arrays.asList(camarera1, camarera2, camarera3, camarera4, camarera5, camarera6);
        List<EditText> horas = Arrays.asList(hora1, hora2, hora3, hora4, hora5, hora6);
        List<EditText> restas = Arrays.asList(resta1, resta2, resta3, resta4, resta5, resta6);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int cantidad = prefs.getInt("NumeroCamareras",1);

        camarera1.setText(prefs.getString("Nombre1",getString(R.string.worker) + "1"));
        camarera2.setText(prefs.getString("Nombre2",getString(R.string.worker) + "2"));
        camarera3.setText(prefs.getString("Nombre3",getString(R.string.worker) + "3"));
        camarera4.setText(prefs.getString("Nombre4",getString(R.string.worker) + "4"));
        camarera5.setText(prefs.getString("Nombre5",getString(R.string.worker) + "5"));
        camarera6.setText(prefs.getString("Nombre6",getString(R.string.worker) + "6"));
        divCheck = prefs.getBoolean("divCheck", false);
        restCheck = prefs.getBoolean("restCheck", false);

        for (int i=0;i<=nombres.size() - 1;i++){
            nombres.get(i).setVisibility(View.INVISIBLE);
            nombres.get(i).setEnabled(false);
            horas.get(i).setEnabled(false);
            horas.get(i).setHint("");
            horas.get(i).setText("");
            restas.get(i).setEnabled(false);
            restas.get(i).setHint("");
        }
        for (int i=0;i<=cantidad - 1;i++){
            nombres.get(i).setVisibility(View.VISIBLE);
            nombres.get(i).setEnabled(true);
            if(!divCheck) {                         //Compruebo si esta habilitada la division igualitaria
                horas.get(i).setEnabled(true);
                horas.get(i).setHint(getString(R.string.hours));
            }
            if (!restCheck) {                       //Compruebo si estan habilitadas las restas
                restas.get(i).setEnabled(true);
                restas.get(i).setHint(getString(R.string.substraction));
            }
        }
        //Si hay 0 camareras, muestro el aviso
        if(cantidad == 0){

            boton.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(this)
                    .setTitle("No hay Empleados activos")
                    .setMessage("Para que esto funcione debe haber empleados activos")
                    .setPositiveButton("Ir a configuración", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(((Dialog) dialog).getContext(), Config.class);
                            startActivityForResult(intent, 1);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else{
            boton.setVisibility(View.VISIBLE);
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
                SeleccionarCamareras();
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
            Intent intent = new Intent(this, Config.class);
            startActivityForResult(intent, 1);
        }
        if(id == R.id.action_about){
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }
        if(id == R.id.action_feedback){
            FeedbackSettings feedbackSettings = new FeedbackSettings();

            //SUBMIT-CANCEL BUTTONS
            feedbackSettings.setCancelButtonText("Cancelar");
            feedbackSettings.setSendButtonText("Enviar");

            //DIALOG TEXT
            feedbackSettings.setText("Hola, ¿Crees que podriamos mejorar en algo?");
            feedbackSettings.setYourComments("Escribe lo que quieras aquí...");
            feedbackSettings.setTitle("Enviar comentario");

            //TOAST MESSAGE
            feedbackSettings.setToast("Gracias por el comentario");
            feedbackSettings.setToastDuration(Toast.LENGTH_SHORT);  // Default
            //feedbackSettings.setToastDuration(Toast.LENGTH_LONG);

            //RADIO BUTTONS
            feedbackSettings.setRadioButtons(true); // Disables radio buttons
            feedbackSettings.setBugLabel("Fallo");
            feedbackSettings.setIdeaLabel("Idea");
            feedbackSettings.setQuestionLabel("Pregunta");

            //RADIO BUTTONS ORIENTATION AND GRAVITY
            feedbackSettings.setOrientation(LinearLayout.HORIZONTAL); // Default
            feedbackSettings.setOrientation(LinearLayout.VERTICAL);
            feedbackSettings.setGravity(Gravity.RIGHT); // Default
            feedbackSettings.setGravity(Gravity.LEFT);
            feedbackSettings.setGravity(Gravity.CENTER);

            //SET DIALOG MODAL
            feedbackSettings.setModal(true); //Default is false

            //DEVELOPER REPLIES
            feedbackSettings.setReplyTitle("Mensaje del desarrollador");
            feedbackSettings.setReplyCloseButtonText("Cerrar");
            feedbackSettings.setReplyRateButtonText("¡Calificanos!");

            //DEVELOPER CUSTOM MESSAGE (NOT SEEN BY THE END USER)
            feedbackSettings.setDeveloperMessage("Ver. 1.1.2");

            feedBack = new FeedbackDialog(this, "AF-CD88C8314001-31", feedbackSettings);
            feedBack.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(feedBack != null)feedBack.dismiss();
    }

    void Tutorial(){

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Tuto1");

        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(dinero)
                        .setDelay(500)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Bienvenido a WaiterTip, con este breve tutorial te enseñaremos a utilizar la app de forma sencilla y en muy pocos pasos. \n\n" +
                                "En el recuadro seleccionado mas arriba tienes que introducir el dinero a repartir.")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(camarera1)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("A cada empleado le corresponde una fila.")
                        .withRectangleShape(true)
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(hora1)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Introduce aquí las horas que ha trabajado el empleado desde la ultima distribución del bote.")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(resta1)
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Si el empleado ha hecho uso de su dinero asignado antes de la distribución, puedes restarselo introduciendo la cifra aquí.")
                        .withRectangleShape()
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.toolbar))
                        .withRectangleShape()
                        .setDismissText("Siguiente")
                        .setDismissTextColor(getResources().getColor(R.color.green))
                        .setContentText("Desde aquí puedes acceder al menú que contiene el apartado opciones entre otros.")
                        .build()
        );

        sequence.start();
    }
}
