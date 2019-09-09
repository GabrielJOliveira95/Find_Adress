package com.cursoandroid.oliveiragabriel.findandress;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cep_edit_text = findViewById(R.id.cep_edit_text);
        btn_search = findViewById(R.id.btn_search);
        text_cep = findViewById(R.id.text_cep);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindZipCode findZipCode = new FindZipCode();
                String cep_string = cep_edit_text.getText().toString();
                findZipCode.execute("https://viacep.com.br/ws/"+cep_string+"/json/");

            }
        });


    }

    class FindZipCode extends AsyncTask<String, Void, String>{

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
            try {
                if (s != null){

                    Toast.makeText(MainActivity.this, "\n" + "Search completed", Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject(s);
                    String street = jsonObject.getString("logradouro");
                    String neighborhood = jsonObject.getString("bairro");
                    String city = jsonObject.getString("localidade");
                    String state = jsonObject.getString("uf");
                    String complete_adress = street + " - " + neighborhood + ", " + city + " - " + state;
                    text_cep.setText(complete_adress);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
