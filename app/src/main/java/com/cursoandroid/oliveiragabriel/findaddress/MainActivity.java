package com.cursoandroid.oliveiragabriel.findaddress;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText cep_edit_text;
    private AppCompatButton btn_search;
    private TextView text_cep;
    private ProgressBar progressBar2;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.include);
        cep_edit_text = findViewById(R.id.cep_edit_text);
        btn_search = findViewById(R.id.btn_search);
        text_cep = findViewById(R.id.text_cep);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);
        setSupportActionBar(toolbar);



        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cep_string = cep_edit_text.getText().toString();
                validate(cep_string);

            }
        });


    }

    class FindZipCode extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar2.setVisibility(View.VISIBLE);
            text_cep.setText("");

        }

        @Override
        protected String doInBackground(String... strings) {
            String string_url = strings[0];
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer stringBuffer = null;


            try {

                URL url = new URL(string_url);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    inputStream = connection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    stringBuffer = new StringBuffer();

                    String line = "";
                    while ((line = bufferedReader.readLine()) != null){

                        stringBuffer.append(line);

                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           // text_cep.setText(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String street = jsonObject.getString("logradouro");
                String neighborhood = jsonObject.getString("bairro");
                String city = jsonObject.getString("localidade");
                String state = jsonObject.getString("uf");
                s = street + " - " + neighborhood + ", " + city + " - " + state;
                text_cep.setText(s);
                progressBar2.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void validate(String number){
        if (number.isEmpty()){

            cep_edit_text.setError("Insert a valid zip code");

        }else {
            FindZipCode findZipCode = new FindZipCode();
            findZipCode.execute("https://viacep.com.br/ws/"+number+"/json/");

        }
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
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                break;
            case R.id.exit:
                this.finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
