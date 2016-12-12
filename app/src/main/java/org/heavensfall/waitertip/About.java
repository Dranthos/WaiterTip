package org.heavensfall.waitertip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.hockeyapp.android.FeedbackManager;

public class About extends AppCompatActivity {;

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        FeedbackManager.register(this);

        boton = (Button) findViewById(R.id.bSugerencias);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackManager.showFeedbackActivity(About.this);
            }
        });
    }
}
