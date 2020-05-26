package org.overlake.mat803.multiplechoicemathquiz.MultipleChoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.overlake.mat803.multiplechoicemathquiz.MultipleChoice.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MathQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Arithmetic");
        addCategory(c1);
        Category c2 = new Category("Geometry");
        addCategory(c2);
        Category c3 = new Category("Algebra");
        addCategory(c3);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        //Arithmetic Easy
        Question q1 = new Question("14 * 14",
                "196", "80", "28", 1,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q1);
        Question q2 = new Question("64 / 4",
                "16", "20", "30", 1,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q2);
        Question q3 = new Question("30 + 3 / 3",
                "31", "11", "3", 1,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q3);
        Question q4 = new Question("3 * 4 + 5",
                "17", "16", "27", 1,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q4);
        Question q5 = new Question("3/5 + 2/5",
                "1/5", "1", "1/2", 2,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q5);
        Question q6 = new Question("3 - 3 - 3 + 4",
                "0", "1", "-3", 2,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q6);
        Question q7 = new Question("6000 / 2",
                "5998", "3000", "2000", 2,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q7);
        Question q8 = new Question("400 * 0 + 1",
                "401", "0", "1", 3,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q8);
        Question q9 = new Question("60 * (30 - 10)",
                "10300", "1790", "1200", 3,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q9);
        Question q10 = new Question("2/5 - 3/5",
                "-1/10", "1/5", "-1/5", 3,
                Question.DIFFICULTY_EASY, Category.ARITHMETIC);
        addQuestion(q10);
        //Arithmetic Medium
        Question q11 = new Question("107 + 225",
                "332", "350", "330", 1,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q11);
        Question q12 = new Question("654 - 526",
                "128", "130", "120", 1,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q12);
        Question q13 = new Question("5/16 - 2/10",
                "9/80", "1/2", "7/26", 1,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q13);
        Question q14 = new Question("25 * 36",
                "900", "880", "1010", 1,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q14);
        Question q15 = new Question("2^4 - 1",
                "17", "15", "9", 2,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q15);
        Question q16 = new Question("440 / 8",
                "50", "55", "56", 2,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q16);
        Question q17 = new Question("4^100 / 4^99 ",
                "40", "4", "444", 2,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q17);
        Question q18 = new Question("3/5 * 5/4",
                "1/2", "5/6", "3/4", 3,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q18);
        Question q19 = new Question("(3/5) / (5/4)",
                "3/4", "1/2", "12/25", 3,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q19);
        Question q20 = new Question("45/8 - 1",
                "44/8", "35/8", "37/8", 3,
                Question.DIFFICULTY_MEDIUM, Category.ARITHMETIC);
        addQuestion(q20);
        //Arithmetic Hard
        Question q21 = new Question("45/8 - 3/6",
                "41/8", "22/3", "40/9", 1,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q21);
        Question q22 = new Question("5 + 8 * (9 + 6) / 5 + 4",
                "43", "40", "44", 1,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q22);
        Question q23 = new Question("3 ^ (4 * 4 / 2 - 5)",
                "27", "9", "3", 1,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q23);
        Question q24 = new Question("788 - 840",
                "-52", "50", "-50", 1,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q24);
        Question q25 = new Question("684 * 26",
                "17880", "17784", "16905", 2,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q25);
        Question q26 = new Question("20^5 / 20^3",
                "4000", "400", "40", 2,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q26);
        Question q27 = new Question("2^3 * 2^5",
                "2^15", "2^8", "4", 2,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q27);
        Question q28 = new Question("Square root of 256",
                "17", "15", "16", 3,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q28);
        Question q29 = new Question("Cube root of 64",
                "8", "5", "4", 3,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q29);
        Question q30 = new Question("5!",
                "500", "60", "120", 3,
                Question.DIFFICULTY_HARD, Category.ARITHMETIC);
        addQuestion(q30);
        // Geometry Easy
        Question q31 = new Question("What is the formula for the area of a circle",
                "πr^2", "2πr", "πr", 1,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q31);
        Question q32 = new Question("What is the formula for the circumference of a circle",
                "2πr", "360π", "πr^2", 1,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q32);
        Question q33 = new Question("What is the formula for the area of a triangle",
                "bh", "(bh) / 2", "(bh) / 4", 1,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q33);
        Question q34 = new Question("What is the formula for the area of a rectangle",
                "(bh) / 2", "bh", "h^2", 2,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q34);
        Question q35 = new Question("What is the formula for the volume of a cube",
                "6s^2", "s^3", "s^4", 2,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q35);
        Question q36 = new Question("What is the formula for the surface area of a cube",
                "s^3", "6s^2", "2πr", 2,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q36);
        Question q37 = new Question("What is the formula for the surface area of a cylinder",
                "2πr^3 + 2πrh", "2πr^2 + 2πrh", "πr^2 + πrh", 2,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q37);
        Question q38 = new Question("What is the formula for the volume of a cylinder",
                "2hπr^2", "2πr^2", "hπr^2", 3,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q38);
        Question q39 = new Question("What is the formula for the volume of a cone",
                "(s^2) / 3", "hπr^2", "(hπr^2) / 3", 3,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q39);
        Question q40 = new Question("What is the formula for the volume of a sphere",
                "4πr^2", "4πr^3", "(4πr^3) / 3", 3,
                Question.DIFFICULTY_EASY, Category.GEOMETRY);
        addQuestion(q40);
        // Geometry Medium
        Question q41 = new Question("What is the volume of a sphere with radius 2",
                "32π/3", "4π", "4π^2", 1,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q41);
        Question q42 = new Question("What is the volume of a cone with radius 3 and height 2",
                "6π", "18π", "2π", 1,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q42);
        Question q43 = new Question("What is the volume of a cylinder with radius 1 and height 4",
                "4π", "π", "6π^2", 1,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q43);
        Question q44 = new Question("What is the surface area of a cylinder with radius 2 and height 2",
                "8π", "16π", "16", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q44);
        Question q45 = new Question("What is the surface area of a cube with sides of length 4",
                "16", "96", "64", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q45);
        Question q46 = new Question("What is the volume of a cube with sides of length 5",
                "25", "125", "120", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q46);
        Question q47 = new Question("What is the area of a rectangle with base 10 and height 6",
                "50", "60", "30", 2,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q47);
        Question q48 = new Question("What is the area of a triangle with base 3 and height 2",
                "1", "6", "3", 3,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q48);
        Question q49 = new Question("What is the circumference of a circle with radius 2",
                "4", "6π", "4π", 3,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q49);
        Question q50 = new Question("What is the area of a circle with radius 6",
                "30π", "12π", "36π", 3,
                Question.DIFFICULTY_MEDIUM, Category.GEOMETRY);
        addQuestion(q50);
        // Geometry Hard
        Question q51 = new Question("A right triangle has two shorter sides of length 3 and 4. What is the length of the hypotenuse",
                "5", "2", "7", 1,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q51);
        Question q52 = new Question("A circle has a circumference of 10. It has an arc of length 1. What is the central angle of the arc, in degrees",
                "36", "1", "360", 1,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q52);
        Question q53 = new Question("A trapezoid has height of length 5 and base of length 1 and 3. What is the area",
                "10", "15", "5", 1,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q53);
        Question q54 = new Question("What is the perimeter of a regular octagon with sides of length 7",
                "49", "56", "63", 2,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q54);
        Question q55 = new Question("What angle do the sides of a regular octagon make, in degrees",
                "120", "135", "90", 2,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q55);
        Question q56 = new Question("What is the surface area of a sphere with diameter 4",
                "12π", "16π", "32π", 2,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q56);
        Question q57 = new Question("Is it possible to have a triangle with sides of length 2, 4, and 10",
                "In certain scenarios", "No", "Yes", 2,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q57);
        Question q58 = new Question("Which of these is a property of a rhombus",
                "It has five sides", "The sides are of equal length", "Opposite angles of a rhombus have equal measures", 3,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q58);
        Question q59 = new Question("Which has the greater area: A circle of radius 2, or a square with sides of length 3?",
                "They both have the same area", "Square", "Circle", 3,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q59);
        Question q60 = new Question("How many sides does a dodecagon have",
                "10", "15", "12", 3,
                Question.DIFFICULTY_HARD, Category.GEOMETRY);
        addQuestion(q60);
        // Algebra Easy
        Question q61 = new Question("x + 3 = 8: x = ",
                "5", "11", "0", 1,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q61);
        Question q62 = new Question("x / 5 = 10: x = ",
                "50", "2", "5", 1,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q62);
        Question q63 = new Question("x - 34 = 1: x = ",
                "35", "36", "33", 1,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q63);
        Question q64 = new Question("x * 8 = 64: x = ",
                "60", "8", "12", 2,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q64);
        Question q65 = new Question("5x = 10: x = ",
                "50", "2", "20", 2,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q65);
        Question q66 = new Question("2x + 5 = 9: x = ",
                "1", "2", "3", 2,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q66);
        Question q67 = new Question("40 + x = 90: x = ",
                "30", "40", "50", 3,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q67);
        Question q68 = new Question("x^2 = 64: x = ",
                "32", "4", "8", 3,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q68);
        Question q69 = new Question("2x - 4 = 8: x = ",
                "1", "12", "6", 3,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q69);
        Question q70 = new Question("3x + 3x = 12: x = ",
                "1", "3", "2", 3,
                Question.DIFFICULTY_EASY, Category.ALGEBRA);
        addQuestion(q70);
        // Algebra Medium
        Question q71 = new Question("2x - 6 = 8x: x = ",
                "-1", "1", "2", 1,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q71);
        Question q72 = new Question("3x^2 = 48: x = ",
                "4", "5", "6", 1,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q72);
        Question q73 = new Question("6x - 4 = 3x + 8: x = ",
                "4", "6", "1", 1,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q73);
        Question q74 = new Question("x/12 = 3/x: x = ",
                "5", "6", "3", 2,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q74);
        Question q75 = new Question("x^2 * x^1 = 27: x = ",
                "1", "3", "2", 2,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q75);
        Question q76 = new Question("1/(x^2) = 64: x = ",
                "1/4", "1/8", "1/2", 2,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q76);
        Question q77 = new Question("(x + 1)^2 = 25: x = ",
                "3", "5", "4", 3,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q77);
        Question q78 = new Question("x/6 + 6 = 42: x = ",
                "220", "211", "216", 3,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q78);
        Question q79 = new Question("3! + x = 10: x = ",
                "2", "7", "4", 3,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q79);
        Question q80 = new Question("x^2 + x = 30: x = ",
                "3", "4", "5", 3,
                Question.DIFFICULTY_MEDIUM, Category.ALGEBRA);
        addQuestion(q80);
        // Algebra Hard
        Question q81 = new Question("x^2 + 5x + 6 = 0: x = ",
                "-2, -3", "-2", "-1, -2", 1,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q81);
        Question q82 = new Question("2x^2 + 6x + 4 = 0: x = ",
                "-1, -2", "0, 1", "0, -1", 1,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q82);
        Question q83 = new Question("4x^2 + 4x + 1: x = ",
                "-0.5", "0.5", "-0.5, 1.5", 1,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q83);
        Question q84 = new Question("5/x + 7x = 8x: x = ",
                "2", "5", "1", 2,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q84);
        Question q85 = new Question("x^3 + 2x^2 + 4x + 3: x = ",
                "0", "-1", "2", 2,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q85);
        Question q86 = new Question("40x = 4000/x: x = ",
                "5", "10", "100", 2,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q86);
        Question q87 = new Question("x^3 / x^5 = 1/81: x = ",
                "5", "7", "9", 3,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q87);
        Question q88 = new Question("x^4 + x^2 = 20: x = ",
                "3", "1", "2", 3,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q88);
        Question q89 = new Question("20/(x-3) = 5: x = ",
                "6", "4", "7", 3,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q89);
        Question q90 = new Question("(x^2)^4 = 256",
                "4", "12", "2", 3,
                Question.DIFFICULTY_HARD, Category.ALGEBRA);
        addQuestion(q90);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
               Category category = new Category();
               category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
               category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
               categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " =? ";
        String[] selectionArgs = new String[] {String.valueOf(categoryID), difficulty};
        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}