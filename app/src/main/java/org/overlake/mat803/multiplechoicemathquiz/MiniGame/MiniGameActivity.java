package org.overlake.mat803.multiplechoicemathquiz.MiniGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.overlake.mat803.multiplechoicemathquiz.R;

public class MiniGameActivity extends AppCompatActivity {
    public static final String EXTRA_GAME_SCORE = "extraGameScore";

    private static final String KEY_SECONDS_LEFT = "keySecondsLeft";
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_NUMBER_CORRECT = "keyNumberCorrect";
    private static final String KEY_NUMBER_INCORRECT = "keyNumberIncorrect";
    private static final String KEY_CURRENT_QUESTION = "keyCurrentQuestion";


    private CountDownTimer countDownTimer;

    Button btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_questions, tv_timer, tv_bottomMessage;
    ProgressBar prog_timer;
    Game g;
    int secondsRemaining;
    long timeLeftInMillis;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_game);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_bottomMessage = findViewById(R.id.tv_bottommessage);
        tv_questions = findViewById(R.id.tv_questions);
        tv_timer = findViewById(R.id.tv_timer);
        prog_timer = findViewById(R.id.prog_timer);
        timeLeftInMillis = 30000;
        g = new Game();

        if (savedInstanceState == null) {
            tv_timer.setText("0sec");
            tv_questions.setText("");
            tv_bottomMessage.setText("Press Go");
            tv_score.setText("0pts");
            nextTurn();
        } else {
            timeLeftInMillis = savedInstanceState.getLong(KEY_SECONDS_LEFT);
            g.setScore(savedInstanceState.getInt(KEY_SCORE));
            g.setTotalQuestions(savedInstanceState.getInt(KEY_QUESTION_COUNT));
            g.setNumberCorrect(savedInstanceState.getInt(KEY_NUMBER_CORRECT));
            g.setNumberIncorrect(savedInstanceState.getInt(KEY_NUMBER_INCORRECT));
            g.setCurrentQuestion((QuestionMiniGame) savedInstanceState.getParcelable(KEY_CURRENT_QUESTION));
        }
        startCountDown();

        View.OnClickListener answerButtonClickListener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;
                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()) + "pts");
                nextTurn();
            }
        };
        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                secondsRemaining = (int) (timeLeftInMillis / 1000) % 60;
                tv_timer.setText(Integer.toString(secondsRemaining) + "sec");
                prog_timer.setProgress(30 - secondsRemaining);
            }

            @Override
            public void onFinish() {
                tv_timer.setText(0 + "sec");
                prog_timer.setProgress(30);
                btn_answer0.setEnabled(false);
                btn_answer1.setEnabled(false);
                btn_answer2.setEnabled(false);
                btn_answer3.setEnabled(false);
                tv_bottomMessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finishQuiz();
                    }
                }, 5000);
            }
        }.start();
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_GAME_SCORE, g.getScore());
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void nextTurn() {
        // create a new question
        // set text on answer button
        // enable answer buttons
        // start the timer
        g.makeNewQuestion();
        int[] answers = g.getCurrentQuestion().getAnswerArray();
        btn_answer0.setText(Integer.toString(answers[0]));
        btn_answer1.setText(Integer.toString(answers[1]));
        btn_answer2.setText(Integer.toString(answers[2]));
        btn_answer3.setText(Integer.toString(answers[3]));
        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);
        tv_questions.setText(g.getCurrentQuestion().getQuestionPhrase());
        tv_bottomMessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_SECONDS_LEFT, timeLeftInMillis);
        outState.putInt(KEY_SCORE, g.getScore());
        outState.putInt(KEY_QUESTION_COUNT, g.getTotalQuestions());
        outState.putInt(KEY_NUMBER_CORRECT, g.getNumberCorrect());
        outState.putInt(KEY_NUMBER_INCORRECT, g.getNumberIncorrect());
        outState.putParcelable(KEY_CURRENT_QUESTION, g.getCurrentQuestion());
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }
}
