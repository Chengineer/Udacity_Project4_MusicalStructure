package com.example.android.p4musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the view that shows the samba genre
        TextView sambaDeGafieira = (TextView) findViewById(R.id.samba_de_gafieira);
        // Set a click listener on that View
        sambaDeGafieira.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Samba de Gafieira View is clicked on.
            @Override
            public void onClick(View view) {
                Intent sambaDeGafieiraIntent = new Intent(MainActivity.this, SambaDeGafieiraActivity.class);
                startActivity(sambaDeGafieiraIntent);
            }

        });
        // Find the view that shows the sertanejo genre
        TextView sertanejo = (TextView) findViewById(R.id.sertanejo);
        // Set a click listener on that View
        sertanejo.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Sertanejo View is clicked on.
            @Override
            public void onClick(View view) {
                Intent sertanejoIntent = new Intent(MainActivity.this, SertanejoActivity.class);
                startActivity(sertanejoIntent);
            }
        });
        // Find the view that shows the forro genre
        TextView forro = (TextView) findViewById(R.id.forro);
        // Set a click listener on that View
        forro.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Forro View is clicked on.
            @Override
            public void onClick(View view) {
                Intent forroIntent = new Intent(MainActivity.this, ForroActivity.class);
                startActivity(forroIntent);
            }

        });
        // Find the view that shows the capoeira genre
        TextView capoeira = (TextView) findViewById(R.id.capoeira);
        // Set a click listener on that View
        capoeira.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Capoeira View is clicked on.
            @Override
            public void onClick(View view) {
                Intent capoeiraIntent = new Intent(MainActivity.this, CapoeiraActivity.class);
                startActivity(capoeiraIntent);
            }

        });
    }
}
