package swd.affiliate_marketing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import swd.affiliate_marketing.global.GlobalVariable;
import swd.affiliate_marketing.model.Publisher;

public class LoginActivity extends AppCompatActivity {

    private EditText tvUsername, tvPassword;
    private TextView tvLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvUsername = findViewById(R.id.txtLoginUsername);
        tvPassword = findViewById(R.id.txtLoginPassword);
        tvLoginError = findViewById(R.id.txtLoginError);
    }

    public void clickToLogin(View view) {
        final String username = tvUsername.getText().toString();
        final String password = tvPassword.getText().toString();
        tvLoginError.setText("");
        OkHttpClient okHttpClient = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(Publisher.class);
        final JsonAdapter<Publisher> jsonAdapter = moshi.adapter(type);

        String domain = getResources().getString(R.string.virtual_api);

        String url = domain + "api/Publishers/Login";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Get data API Error: ", e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvLoginError.setText("Invalid username or password");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                if (!json.isEmpty()) {
                    final Publisher publisher = jsonAdapter.fromJson(json);
                    if (publisher != null) {
                        ((GlobalVariable) getApplication()).publisher = publisher;
                        SharedPreferences sharedPreferences = getSharedPreferences("swd.affiliate_marketing_preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvLoginError.setText("Invalid username or password");
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLoginError.setText("Invalid username or password");
                        }
                    });
                }
            }
        });

    }
}
