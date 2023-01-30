package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;


public class AIChatActivity extends AppCompatActivity {
    private EditText messageInput;
    private TextView messageOutput;
    private MeowBottomNavigation btm;

    private Button sendButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aichat);
        messageInput = findViewById(R.id.message_input);
        messageOutput = findViewById(R.id.message_output);
        sendButton = findViewById(R.id.send_button);

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString();
        messageInput.setText("");

        // send messageText to the language model and update messageOutput with the response
        String modelResponse = sendToLanguageModel(messageText);
        messageOutput.setText(modelResponse);
    }

    private String sendToLanguageModel(String input) {
        try {
            // create a URL object for the OpenAI API endpoint
            URL url = new URL("https://api.openai.com/v1/engines/davinci/completions");

            // create a new HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set the request method to POST
            connection.setRequestMethod("POST");

            // set the necessary headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer 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");

            // create a JSON object for the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("prompt", input);
            requestBody.put("max_tokens", 1024);

            // open the connection for sending data
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(requestBody.toString());
            writer.flush();

            // read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            writer.close();
            reader.close();

            // parse the response JSON and extract the generated text
            JSONObject responseJson = new JSONObject(response.toString());
            JSONArray choicesArray = responseJson.getJSONArray("choices");
            String generatedText = choicesArray.getJSONObject(0).getString("text");

            return generatedText;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Error communicating with the language model.";
        }
    }
}