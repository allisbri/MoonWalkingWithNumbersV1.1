package net.disconventional.moonwalkingwithnumbers1;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.disconventional.moonwalkingwithnumbers1.R;

public class NumbersActivity extends AppCompatActivity {
    EditText answer; //for users final answer
    TextView memorize; //for number the user will have to memorize
    Button submitButton; //submits answer
    TextView timerView;
    static int answersArrayCount = 0;
    int numberCorrect = 0;
    int timeLeft; //for timer
    public static int digits = 0; //number of digits the user wants
    public static String[] randArray = new String[4];
    public static String[] answersArray = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        memorize = (TextView) findViewById(R.id.memorize);
        answer = (EditText) findViewById(R.id.answer);
        timerView = (TextView) findViewById(R.id.timerText);
        submitButton = (Button) findViewById(R.id.submitButton);

        memorize.setText("Memorize this number: " + randArray[answersArrayCount]);
        memorize.setVisibility(EditText.VISIBLE);
        timeLeft = 60000;

        new CountDownTimer(timeLeft, 1000) {
            //count down from 60 seconds
            public void onTick(long timeLeft) {
                timerView.setText("Timer:  " + (timeLeft/1000));
            }
            //allow user to enter answer without seeing the number they were told to memorize
            public void onFinish() {
                timerView.setText("Times up!");
                memorize.setVisibility(EditText.INVISIBLE);
                answer.setVisibility(EditText.VISIBLE);
                submitButton.setVisibility(Button.VISIBLE);
            }
        }.start();
    }

    /*
        called when user submits answer
        validates that answer is an int
        adds answer to array previous answers
        gives number correct on last answer and resets game
     */
    public void submit(View view) {
        answer = (EditText) findViewById(R.id.answer);
        boolean answerError = false;

        try {
            int answerInt = Integer.parseInt(answer.getText().toString());
            String answerString = Integer.toString(answerInt);
            answersArray[answersArrayCount] = answerString;
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            answerError = true;
        }
        if (!answerError) {
            answersArrayCount++;

            answer.setText("");
            if ((answersArray.length) == (answersArrayCount)) {
                for (int z = 0; z < 4; z++) {
                    if (answersArray[z].equals(randArray[z])) {
                        numberCorrect++;
                    }

                }
                Toast.makeText(this, "You got " + numberCorrect + " out of 4 correct.", Toast.LENGTH_LONG).show();
                memorize.setVisibility(view.INVISIBLE);
                reset();

            } else {
                finish();
                //restarts this intent
                startActivity(getIntent());
            }

        }
    }

    //called when the start button is clicked for the first time. Controls for situations where no values are entered
    public static boolean getDigits(View view, Context context) {
        boolean startError = false;
        try {
            String editDigitsString = MainActivity.editDigits.getText().toString();
            int d = Integer.parseInt(editDigitsString);
            digits = d;
        } catch (Exception e) {
            Toast toast = Toast.makeText(context, "Please enter a valid integer", Toast.LENGTH_LONG);
            toast.show();
            startError = true;
        }
        return startError;
    }

    //resets game
    public void reset(){
        digits = 0;
        answersArrayCount = 0;
        numberCorrect = 0;
        Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
