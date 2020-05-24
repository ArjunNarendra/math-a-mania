package org.overlake.mat803.multiplechoicemathquiz.MiniGame;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import org.overlake.mat803.multiplechoicemathquiz.R;

import java.util.Random;

public class QuestionMiniGame implements Parcelable {

    private int firstNumber;
    private int secondNumber;
    private int answer;


    // there are four possible choices for the user to pick from
    private int[] answerArray;

    // which of the four positions is correct: 0, 1, 2, or 3.
    private int answerPosition;

    // the maximum value of first number or second number
    private int upperLimit;

    // string output of the problem. e.g. "4 + 9 = "
    private String questionPhrase;

    // generate a new random question
    public QuestionMiniGame (int upperLimit) {
        this.upperLimit = upperLimit;
        Random randomNumberMaker = new Random();
        this.firstNumber = randomNumberMaker.nextInt(upperLimit);
        this.secondNumber = randomNumberMaker.nextInt(upperLimit);
        this.answer = this.firstNumber + this.secondNumber;
        this.questionPhrase = firstNumber + " + " + secondNumber + " = ";
        this.answerPosition = randomNumberMaker.nextInt(4);
        this.answerArray = new int[] {0, 1, 2, 3};
        this.answerArray[0] = answer + 1;
        this.answerArray[1] = answer + 10;
        this.answerArray[2] = answer - 5;
        this.answerArray[3] = answer - 2;
        this.answerArray = shuffleArray(this.answerArray);
        answerArray[answerPosition] = answer;
    }


    protected QuestionMiniGame(Parcel in) {
        firstNumber = in.readInt();
        secondNumber = in.readInt();
        answer = in.readInt();
        answerArray = in.createIntArray();
        answerPosition = in.readInt();
        upperLimit = in.readInt();
        questionPhrase = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(firstNumber);
        dest.writeInt(secondNumber);
        dest.writeInt(answer);
        dest.writeIntArray(answerArray);
        dest.writeInt(answerPosition);
        dest.writeInt(upperLimit);
        dest.writeString(questionPhrase);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionMiniGame> CREATOR = new Creator<QuestionMiniGame>() {
        @Override
        public QuestionMiniGame createFromParcel(Parcel in) {
            return new QuestionMiniGame(in);
        }

        @Override
        public QuestionMiniGame[] newArray(int size) {
            return new QuestionMiniGame[size];
        }
    };

    private int [] shuffleArray (int[] array) {
        int index, temp;
        Random randomNumberGenerator = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = randomNumberGenerator.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(int[] answerArray) {
        this.answerArray = answerArray;
    }

    public int getAnswerPosition() {
        return answerPosition;
    }

    public void setAnswerPosition(int answerPosition) {
        this.answerPosition = answerPosition;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }
}

