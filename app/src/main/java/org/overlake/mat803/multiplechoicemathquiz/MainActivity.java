package org.overlake.mat803.multiplechoicemathquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.overlake.mat803.multiplechoicemathquiz.GraphPoints.GraphPointsActivity;
import org.overlake.mat803.multiplechoicemathquiz.MiniGame.MiniGameActivity;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.Category;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.Question;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.QuizActivity;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.QuizDbHelper;
import org.overlake.mat803.multiplechoicemathquiz.TakeNotes.NotesListActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Runnable {
    private static final int REQUEST_CODE_QUIZ = 1;
    private static final int REQUEST_CODE_MINI_GAME = 2;
    public static final String KEY_QUIZ_SCORE = "keyQuizScore";
    public static final String KEY_MINIGAME_SCORE = "keyMinigameScore";


    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    public static final String KEY_GAME_HIGHSCORE = "keyGameHighscore";


    private TextView textViewGameHighscore;
    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    private Spinner spinnerDifficulty;

    private int quizHighScore;
    private int minigameHighscore;
    private long resetPressedTime;

    private com.jetradarmobile.snowfall.SnowfallView snow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewHighscore = findViewById(R.id.text_view_highscore);
        textViewGameHighscore = findViewById(R.id.text_view_minigamehighscore);
        spinnerCategory = findViewById(R.id.spinner_category);
        snow = findViewById(R.id.balloon);
        RelativeLayout relativeLayout = findViewById(R.id.main_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        if (savedInstanceState != null) {
            quizHighScore = savedInstanceState.getInt(KEY_QUIZ_SCORE, quizHighScore);
            minigameHighscore = savedInstanceState.getInt(KEY_MINIGAME_SCORE, minigameHighscore);
        }

        setTitle("Math-a-Mania");
        new Thread(this).start();


    }

    private void startMiniGame() {
        Intent intent = new Intent(MainActivity.this, MiniGameActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MINI_GAME);
    }

    private void startQuiz() {
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();

        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    private void takeNotes() {
        Intent intent = new Intent(MainActivity.this, NotesListActivity.class);
        startActivity(intent);
    }

    private void graphPoints() {
        Intent intent = new Intent(MainActivity.this, GraphPointsActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > quizHighScore) {
                    updateHighscore(score, requestCode);
                    displayAnimation();
                }
            }
        }
        if (requestCode == REQUEST_CODE_MINI_GAME) {
            if (resultCode == RESULT_OK) {
                int gameScore = data.getIntExtra(MiniGameActivity.EXTRA_GAME_SCORE, 0);
                if (gameScore > minigameHighscore) {
                    updateHighscore(gameScore, requestCode);
                    displayAnimation();
                }

            }
        }
    }

    private void displayAnimation() {
        Toast.makeText(this, "New Highscore!", Toast.LENGTH_LONG).show();
        snow.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                snow.setVisibility(View.INVISIBLE);
            }
        }, 7000);
    }

    private void loadCategories() {
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();
        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }

    private void loadDifficultyLevels() {
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        String[] difficultyLevels = Question.getAllDifficultyLevels();
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        quizHighScore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Quiz Highscore: " + quizHighScore);
        minigameHighscore = prefs.getInt(KEY_GAME_HIGHSCORE, 0);
        textViewGameHighscore.setText("Minigame Highscore: " + minigameHighscore);
    }

    private void updateHighscore(int highScoreNew, int requestCode) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (requestCode == REQUEST_CODE_QUIZ) {
            quizHighScore = highScoreNew;
            textViewHighscore.setText("Quiz Highscore: " + quizHighScore);
            editor.putInt(KEY_HIGHSCORE, quizHighScore);
        }
        if (requestCode == REQUEST_CODE_MINI_GAME) {
            minigameHighscore = highScoreNew;
            textViewGameHighscore.setText("Minigame Highscore: " + minigameHighscore);
            editor.putInt(KEY_GAME_HIGHSCORE, minigameHighscore);
        }
        editor.apply();
    }

    private void resetHighscore() {
        quizHighScore = 0;
        minigameHighscore = 0;
        textViewGameHighscore.setText("Minigame Highscore: " + minigameHighscore);
        textViewHighscore.setText("Quiz Highscore: " + quizHighScore);
        Toast.makeText(this, "Highscore Reset", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, quizHighScore);
        editor.putInt(KEY_GAME_HIGHSCORE, minigameHighscore);
        editor.apply();
    }

    private void onResetPressed() {
        if (resetPressedTime + 2000 > System.currentTimeMillis()) {
            resetHighscore();
        } else {
            Toast.makeText(this, "Are you sure? Press again to reset", Toast.LENGTH_SHORT).show();
        }
        resetPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset_highscore:
                onResetPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_QUIZ_SCORE, quizHighScore);
        outState.putInt(KEY_MINIGAME_SCORE, minigameHighscore);
    }

    @Override
    public void run() {
        loadCategories();
        loadDifficultyLevels();
        loadHighscore();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        Button buttonStartGame = findViewById(R.id.button_start_minigame);
        Button buttonTakeNotes = findViewById(R.id.button_take_notes);
        Button buttonGraphPoints = findViewById(R.id.button_graph_points);
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMiniGame();
            }
        });
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
        buttonTakeNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeNotes();
            }
        });
        buttonGraphPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphPoints();
            }
        });

    }
}
