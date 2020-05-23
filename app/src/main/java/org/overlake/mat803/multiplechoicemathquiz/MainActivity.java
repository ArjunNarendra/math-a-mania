package org.overlake.mat803.multiplechoicemathquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.overlake.mat803.multiplechoicemathquiz.MiniGame.MiniGameActivity;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.Category;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.Question;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.QuizActivity;
import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.QuizDbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    private static final int REQUEST_CODE_MINI_GAME = 2;
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

    private int highScore;
    private int gameHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewHighscore = findViewById(R.id.text_view_highscore);
        textViewGameHighscore = findViewById(R.id.text_view_minigamehighscore);
        spinnerCategory = findViewById(R.id.spinner_category);
        loadCategories();
        loadDifficultyLevels();
        loadHighscore();


        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        Button buttonStartGame = findViewById(R.id.button_start_minigame);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highScore) {
                    updateHighscore(score, requestCode);
                }
            }
        }
        if (requestCode == REQUEST_CODE_MINI_GAME) {
            if (resultCode == RESULT_OK) {
                int gameScore = data.getIntExtra(MiniGameActivity.EXTRA_GAME_SCORE, 0);
                if (gameScore > gameHighscore) {
                    updateHighscore(gameScore, requestCode);
                }

            }
        }
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
        highScore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Quiz Highscore: " + highScore);
        gameHighscore = prefs.getInt(KEY_GAME_HIGHSCORE, 0);
        textViewGameHighscore.setText("Minigame Highscore: " + gameHighscore);
    }

    private void updateHighscore(int highScoreNew, int requestCode) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (requestCode == REQUEST_CODE_QUIZ) {
            highScore = highScoreNew;
            textViewHighscore.setText("Quiz Highscore: " + highScore);
            editor.putInt(KEY_HIGHSCORE, highScore);
        }

        if (requestCode == REQUEST_CODE_MINI_GAME) {
            gameHighscore = highScoreNew;
            textViewGameHighscore.setText("Minigame Highscore: " + gameHighscore);
            editor.putInt(KEY_GAME_HIGHSCORE, gameHighscore);
        }
        editor.apply();
    }
}
