package login.softices.com.splash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import login.softices.com.splash.R;

public class Services extends AppCompatActivity implements View.OnClickListener {
    private Button buttonStart;
    private Button buttonStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);





        //getting buttons from xml
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        //attaching onclicklistener to buttons
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            //start the service here
            startService(new Intent(this,Myservice.class));
        } else if (view == buttonStop) {
            //stop the service here
            stopService(new Intent(this,Myservice.class));
        }
    }
}
