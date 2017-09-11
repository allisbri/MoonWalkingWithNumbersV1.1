package net.disconventional.moonwalkingwithnumbers1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import net.disconventional.moonwalkingwithnumbers1.R;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    //Handler handler;
    public static EditText editDigits;
    EditText answer;
    TextView instructions;

    Random generator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearlayout);

        //adds instructions textview to this activity
        TextView instructTextView = new TextView(this);
        instructTextView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //sets text to instruct string from strings.xml
        instructTextView.setText(getString(R.string.instruct));

        linearLayout.addView(instructTextView, 0);

        editDigits = (EditText) findViewById(R.id.digits);  //number of digits user wants to memorize

        Button start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context = getApplicationContext();
                //getDigits returns true if editDigits contains anything other than an int
                boolean error = NumbersActivity.getDigits(view, context);
                String digitsString;
                if (!error) {
                    for (int i = 0; i < 4; i++) {
                        digitsString = "";
                        for (int j = 0; j < NumbersActivity.digits; j++) {
                            try {
                                int addDigit = generator.nextInt(9);
                                digitsString += addDigit;
                            } catch (Exception e) {
                                return;
                            }

                            NumbersActivity.randArray[i] = digitsString;
                        }
                    }
                    Intent intent = new Intent(MainActivity.this, NumbersActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}