package com.packtub.mathgamechapter2;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool soundPool;
    int correctSample = -1;
    int incorrectSample = -1;
    String[] operatorArray = {"+", "-", "*"};

    int correctAnswer;
    Button buttonObjectChoice1;
    Button buttonObjectChoice2;
    Button buttonObjectChoice3;
    TextView textObjectPartA;
    TextView textObjectPartB;
    TextView textObjectOperator;
    TextView textObjectScore;
    TextView textObjectLevel;

    int currentScore = 0;
    int currentLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        textObjectPartA = (TextView) findViewById(R.id.textPartA);

        textObjectOperator = (TextView) findViewById(R.id.textOperator);

        textObjectPartB = (TextView) findViewById(R.id.textPartB);

        textObjectScore = (TextView) findViewById(R.id.textScore);

        textObjectLevel = (TextView) findViewById(R.id.textLevel);

        buttonObjectChoice1 = (Button) findViewById(R.id.buttonChoice1);

        buttonObjectChoice2 = (Button) findViewById(R.id.buttonChoice2);

        buttonObjectChoice3 = (Button) findViewById(R.id.buttonChoice3);


        buttonObjectChoice1.setOnClickListener(this);
        buttonObjectChoice2.setOnClickListener(this);
        buttonObjectChoice3.setOnClickListener(this);

        setQuestion();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("correct.ogg");
            correctSample = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("incorrect.ogg");
            incorrectSample = soundPool.load(descriptor, 0);

        } catch (IOException e) {
        }
    }

    public void setQuestion() {
        int numberRange = currentLevel * 3;
        Random randInt = new Random();

        int partA = randInt.nextInt(numberRange);
        partA++;


        int partB = randInt.nextInt(numberRange);
        partB++;

        int partOperate = randInt.nextInt(3);
        String op = operatorArray[partOperate];

        switch (partOperate) {
            case 0:
                correctAnswer = partA + partB;
                break;
            case 1:
                correctAnswer = partA - partB;
                break;
            case 2:
                correctAnswer = partA * partB;
                break;
        }


        int wrongAnswer1 = correctAnswer - 2;
        int wrongAnswer2 = correctAnswer + 2;

        textObjectPartA.setText("" + partA);
        textObjectPartB.setText("" + partB);
        textObjectOperator.setText(op);



        int buttonLayout = randInt.nextInt(3);
        switch (buttonLayout) {

            case 0:
                buttonObjectChoice1.setText("" + correctAnswer);
                buttonObjectChoice2.setText("" + wrongAnswer1);
                buttonObjectChoice3.setText("" + wrongAnswer2);
                break;

            case 1:
                buttonObjectChoice1.setText("" + wrongAnswer1);
                buttonObjectChoice2.setText("" + correctAnswer);
                buttonObjectChoice3.setText("" + wrongAnswer2);
                break;

            case 2:
                buttonObjectChoice1.setText("" + wrongAnswer2);
                buttonObjectChoice2.setText("" + wrongAnswer1);
                buttonObjectChoice3.setText("" + correctAnswer);
                break;
        }
    }

    void updateScoreAndLevel(int answerGiven) {

        if (isCorrect(answerGiven)) {
            for (int i = 1; i <= currentLevel; i++) {
                currentScore = currentLevel + i;
            }

            currentLevel++;
        } else {

            currentScore = 0;
            currentLevel = 1;
        }

        textObjectScore.setText("Score: " + currentScore);
        textObjectLevel.setText("Level: " + currentLevel);
        setQuestion();
    }

    boolean isCorrect(int answerGiven) {
        boolean correctTrueOrFalse;

        if (answerGiven == correctAnswer) {
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            correctTrueOrFalse = true;
            soundPool.play(correctSample, 1, 1, 0, 0, 1);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            correctTrueOrFalse = false;
            soundPool.play(incorrectSample, 1, 1, 0, 0, 1);
        }
        return correctTrueOrFalse;
    }

    @Override
    public void onClick(View v) {
        int answerGiven = 0;

        switch (v.getId()) {

            case R.id.buttonChoice1:
                answerGiven = Integer.parseInt("" + buttonObjectChoice1.getText());

                break;

            case R.id.buttonChoice2:
                answerGiven = Integer.parseInt("" + buttonObjectChoice2.getText());

                break;

            case R.id.buttonChoice3:
                answerGiven = Integer.parseInt("" + buttonObjectChoice3.getText());

                break;
        }

        updateScoreAndLevel(answerGiven);
    }
}
