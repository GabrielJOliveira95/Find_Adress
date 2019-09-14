package com.cursoandroid.oliveiragabriel.findaddress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txt_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar = findViewById(R.id.include2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact");

        txt_link = findViewById(R.id.txt_link);
        Linkify.addLinks(txt_link, Linkify.WEB_URLS);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.about:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.exit:
                this.finish();
        }


        return super.onOptionsItemSelected(item);
    }


}