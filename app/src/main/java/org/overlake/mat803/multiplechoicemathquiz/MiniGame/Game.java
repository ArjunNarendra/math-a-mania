package org.overlake.mat803.multiplechoicemathquiz.MiniGame;

import android.media.SoundPool;

import org.overlake.mat803.multiplechoicemathquiz.MiniGame.QuestionMiniGame;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private ArrayList<QuestionMiniGame> questions;
    private int numberCorrect;
    private int numberIncorrect;
    private int totalQuestions;
    private int score;
    private QuestionMiniGame currentQuestion;


    public Game() {
        numberCorrect = 0;
        numberIncorrect = 0;
        totalQuestions = 0;
        score = 0;
        currentQuestion = new QuestionMiniGame(1, 10);
        questions = new ArrayList<QuestionMiniGame>();
    }

    public void makeNewQuestion() {
        Random randomNumberMaker = new Random();
        int questionType = randomNumberMaker.nextInt(4);
        currentQuestion = new QuestionMiniGame(questionType, totalQuestions * 2 + 5);
        totalQuestions++;
        questions.add(currentQuestion);
    }

    public boolean checkAnswer(int submittedAnswer) {
        boolean isCorrect;
        if (currentQuestion.getAnswer() == submittedAnswer) {
            numberCorrect++;
            isCorrect = true;
        } else {
            numberIncorrect++;
            isCorrect = false;
        }
        score = numberCorrect * 10  - numberIncorrect * 30;
        return isCorrect;
    }

    public ArrayList<QuestionMiniGame> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionMiniGame> questions) {
        this.questions = questions;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberIncorrect() {
        return numberIncorrect;
    }

    public void setNumberIncorrect(int numberIncorrect) {
        this.numberIncorrect = numberIncorrect;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public QuestionMiniGame getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionMiniGame currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}
