package com.darkbeast0106.sqlitedemo14sl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.mindrot.jbcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {

    Button btnOlvas, btnRogzites, btnTorles, btnModosit;
    TextView textAdatok;
    DBhelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listeners();
        String password = "asd123";

        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        String hash2 = BCrypt.hashpw(password, BCrypt.gensalt());
        String hash3 = BCrypt.hashpw(password, BCrypt.gensalt());

        boolean siker1 = BCrypt.checkpw(password, hash);
        boolean siker2 = BCrypt.checkpw(password, hash2);
        boolean siker3 = BCrypt.checkpw(password, hash3);

        String s = hash+" - "+String.valueOf(siker1)+"\n";
        s+= hash2+" - "+String.valueOf(siker2)+"\n";
        s+= hash3+" - "+String.valueOf(siker3);

        textAdatok.setText(s);
    }

    private void adatLekerdezes() {
        Cursor adatok = adatbazis.adatLekerdezes();
        if (adatok == null){
            Toast.makeText(this, "Hiba történt a lekérdezés során!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (adatok.getCount() == 0){
            Toast.makeText(this, "Még nincs felvéve adat!", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder builder = new StringBuilder();
        while (adatok.moveToNext()){
            builder.append("ID: ").append(adatok.getInt(0)).append("\n");
            builder.append("Név: ").append(adatok.getString(1)).append("\n");
            builder.append("E-mail: ").append(adatok.getString(2)).append("\n");
            builder.append("Jegy: ").append(adatok.getInt(3)).append("\n\n");
        }
        textAdatok.setText(builder);
        Toast.makeText(this, "Sikeres adat lekérdezés", Toast.LENGTH_SHORT).show();
    }

    private void listeners() {
        btnOlvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adatLekerdezes();
            }
        });
        btnRogzites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rogzitesre = new Intent(MainActivity.this, RogzitActivity.class);
                startActivity(rogzitesre);
                finish();
            }
        });
        btnTorles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent torlesre = new Intent(MainActivity.this, TorolActivity.class);
                startActivity(torlesre);
                finish();
            }
        });
        btnModosit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modositasra = new Intent(MainActivity.this, ModositActivity.class);
                startActivity(modositasra);
                finish();
            }
        });

    }

    private void init() {
        btnOlvas = findViewById(R.id.btn_olvas);
        btnRogzites = findViewById(R.id.btn_rogzitesre);
        btnTorles = findViewById(R.id.btn_torlesre);
        btnModosit = findViewById(R.id.btn_modositasra);
        textAdatok = findViewById(R.id.text_adatok);

        textAdatok.setMovementMethod(new ScrollingMovementMethod());
        adatbazis = new DBhelper(MainActivity.this);
    }
}
