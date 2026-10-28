package com.example.micro1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentGrades.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "student_grades";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "student_name";
    public static final String COL_ROLL = "roll_number";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_ASSESSMENT = "assessment_marks";
    public static final String COL_MIDTERM = "midterm_marks";
    public static final String COL_FINAL = "final_marks";
    public static final String COL_TOTAL = "total_marks";
    public static final String COL_PERCENTAGE = "percentage";
    public static final String COL_GRADE = "letter_grade";
    public static final String COL_GPA = "gpa";
    public static final String COL_STATUS = "performance_status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_ROLL + " TEXT, " +
                COL_SUBJECT + " TEXT, " +
                COL_ASSESSMENT + " REAL, " +
                COL_MIDTERM + " REAL, " +
                COL_FINAL + " REAL, " +
                COL_TOTAL + " REAL, " +
                COL_PERCENTAGE + " REAL, " +
                COL_GRADE + " TEXT, " +
                COL_GPA + " REAL, " +
                COL_STATUS + " TEXT" + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertGrade(StudentGrade grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, grade.getStudentName());
        values.put(COL_ROLL, grade.getRollNumber());
        values.put(COL_SUBJECT, grade.getSubject());
        values.put(COL_ASSESSMENT, grade.getAssessmentMarks());
        values.put(COL_MIDTERM, grade.getMidtermMarks());
        values.put(COL_FINAL, grade.getFinalMarks());
        values.put(COL_TOTAL, grade.getTotalMarks());
        values.put(COL_PERCENTAGE, grade.getPercentage());
        values.put(COL_GRADE, grade.getLetterGrade());
        values.put(COL_GPA, grade.getGpa());
        values.put(COL_STATUS, grade.getPerformanceStatus());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public List<StudentGrade> getAllGrades() {
        List<StudentGrade> gradeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StudentGrade grade = parseCursor(cursor);
                gradeList.add(grade);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gradeList;
    }

    public List<StudentGrade> searchGrades(String keyword) {
        List<StudentGrade> gradeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String searchQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_NAME + " LIKE ? OR " +
                COL_ROLL + " LIKE ? OR " +
                COL_SUBJECT + " LIKE ? ORDER BY " + COL_ID + " DESC";

        String searchPattern = "%" + keyword + "%";
        Cursor cursor = db.rawQuery(searchQuery, new String[]{searchPattern, searchPattern, searchPattern});

        if (cursor.moveToFirst()) {
            do {
                StudentGrade grade = parseCursor(cursor);
                gradeList.add(grade);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gradeList;
    }

    public boolean deleteGrade(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    public int getTotalRecordCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public double getAverageGPA() {
        SQLiteDatabase db = this.getReadableDatabase();
        String avgQuery = "SELECT AVG(" + COL_GPA + ") FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(avgQuery, null);
        double avg = 0.0;
        if (cursor.moveToFirst()) {
            avg = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return avg;
    }

    private StudentGrade parseCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndexOrThrow(COL_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(COL_NAME);
        int rollIndex = cursor.getColumnIndexOrThrow(COL_ROLL);
        int subIndex = cursor.getColumnIndexOrThrow(COL_SUBJECT);
        int assessIndex = cursor.getColumnIndexOrThrow(COL_ASSESSMENT);
        int midIndex = cursor.getColumnIndexOrThrow(COL_MIDTERM);
        int finalIndex = cursor.getColumnIndexOrThrow(COL_FINAL);
        int totalIndex = cursor.getColumnIndexOrThrow(COL_TOTAL);
        int percIndex = cursor.getColumnIndexOrThrow(COL_PERCENTAGE);
        int gradeIndex = cursor.getColumnIndexOrThrow(COL_GRADE);
        int gpaIndex = cursor.getColumnIndexOrThrow(COL_GPA);
        int statusIndex = cursor.getColumnIndexOrThrow(COL_STATUS);

        return new StudentGrade(
                cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getString(rollIndex),
                cursor.getString(subIndex),
                cursor.getDouble(assessIndex),
                cursor.getDouble(midIndex),
                cursor.getDouble(finalIndex),
                cursor.getDouble(totalIndex),
                cursor.getDouble(percIndex),
                cursor.getString(gradeIndex),
                cursor.getDouble(gpaIndex),
                cursor.getString(statusIndex)
        );
    }
}