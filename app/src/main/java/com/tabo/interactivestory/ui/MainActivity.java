package com.tabo.interactivestory.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tabo.interactivestory.R;


public class MainActivity extends AppCompatActivity {


    private EditText nameField;
    private Button startButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.nameText);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameField.getText().toString();
                //Toast.makeText(MainActivity.this, name ,Toast.LENGTH_LONG).show(); -- To test whether the code is working

                startStory(name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameField.setText("");
    }

    private void startStory(String name) {

        Intent intent = new Intent(this, StoryActivity.class); // Links to the other acitvity.
        String key = getString(R.string.key_name);  // Once we create some string resources in strings.xml, we need to then access them in our app using the Context.
        intent.putExtra(key, name);
        startActivity(intent); // Redirects to the new activity once the button is clicked



    }
}
