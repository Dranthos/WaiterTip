package org.heavensfall.waitertip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suredigit.inappfeedback.FeedbackDialog;
import com.suredigit.inappfeedback.FeedbackSettings;

public class About extends AppCompatActivity {;

    Button boton;
    private FeedbackDialog feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        FeedBSettings();

        boton = (Button) findViewById(R.id.bSugerencias);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBack.show();
            }
        });
    }

    void FeedBSettings(){
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        feedBack.dismiss();
    }
}
