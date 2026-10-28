package com.example.micro1;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class GradeCalculatorActivity extends AppCompatActivity {

    private TextInputEditText etStudentName, etRollNumber, etSubject;
    private TextInputEditText etAssessmentMarks, etMidtermMarks, etFinalMarks;
    private MaterialButton btnCalculate, btnReset, btnSave;
    private MaterialCardView cardResult;
    private TextView tvResultGradeBadge, tvResultStatus, tvResultSummary;
    private TextView tvResultPercentage, tvResultGPA, tvResultGradeText;

    private DatabaseHelper dbHelper;
    private StudentGrade currentCalculatedGrade = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_calculator);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupToolbar();
        setupListeners();
    }

    private void initViews() {
        etStudentName = findViewById(R.id.etStudentName);
        etRollNumber = findViewById(R.id.etRollNumber);
        etSubject = findViewById(R.id.etSubject);

        etAssessmentMarks = findViewById(R.id.etAssessmentMarks);
        etMidtermMarks = findViewById(R.id.etMidtermMarks);
        etFinalMarks = findViewById(R.id.etFinalMarks);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);

        cardResult = findViewById(R.id.cardResult);
        tvResultGradeBadge = findViewById(R.id.tvResultGradeBadge);
        tvResultStatus = findViewById(R.id.tvResultStatus);
        tvResultSummary = findViewById(R.id.tvResultSummary);
        tvResultPercentage = findViewById(R.id.tvResultPercentage);
        tvResultGPA = findViewById(R.id.tvResultGPA);
        tvResultGradeText = findViewById(R.id.tvResultGradeText);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupListeners() {
        btnCalculate.setOnClickListener(v -> calculateGrade());
        btnReset.setOnClickListener(v -> resetForm());
        btnSave.setOnClickListener(v -> saveGradeToDatabase());
    }

    private void calculateGrade() {
        String name = etStudentName.getText() != null ? etStudentName.getText().toString().trim() : "";
        String roll = etRollNumber.getText() != null ? etRollNumber.getText().toString().trim() : "";
        String subject = etSubject.getText() != null ? etSubject.getText().toString().trim() : "";

        String assessStr = etAssessmentMarks.getText() != null ? etAssessmentMarks.getText().toString().trim() : "";
        String midStr = etMidtermMarks.getText() != null ? etMidtermMarks.getText().toString().trim() : "";
        String finalStr = etFinalMarks.getText() != null ? etFinalMarks.getText().toString().trim() : "";

        if (TextUtils.isEmpty(name)) {
            etStudentName.setError("Student Name is required");
            etStudentName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(roll)) {
            etRollNumber.setError("Roll Number is required");
            etRollNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(subject)) {
            etSubject.setError("Subject is required");
            etSubject.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(assessStr)) {
            etAssessmentMarks.setError("Assessment marks required");
            etAssessmentMarks.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(midStr)) {
            etMidtermMarks.setError("Midterm marks required");
            etMidtermMarks.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(finalStr)) {
            etFinalMarks.setError("Final exam marks required");
            etFinalMarks.requestFocus();
            return;
        }

        double assessMarks = Double.parseDouble(assessStr);
        double midMarks = Double.parseDouble(midStr);
        double finalMarks = Double.parseDouble(finalStr);

        if (assessMarks < 0 || assessMarks > 20) {
            etAssessmentMarks.setError("Assessment marks must be between 0 and 20");
            etAssessmentMarks.requestFocus();
            return;
        }

        if (midMarks < 0 || midMarks > 30) {
            etMidtermMarks.setError("Midterm marks must be between 0 and 30");
            etMidtermMarks.requestFocus();
            return;
        }

        if (finalMarks < 0 || finalMarks > 50) {
            etFinalMarks.setError("Final exam marks must be between 0 and 50");
            etFinalMarks.requestFocus();
            return;
        }

        double totalMarks = assessMarks + midMarks + finalMarks;
        double percentage = (totalMarks / 100.0) * 100.0;

        String letterGrade;
        double gpa;
        String status;
        int statusColor;
        int badgeBgColor;

        if (totalMarks >= 90) {
            letterGrade = "A+";
            gpa = 4.00;
            status = "Pass - Distinction";
            statusColor = ContextCompat.getColor(this, R.color.grade_a);
            badgeBgColor = R.color.grade_a;
        } else if (totalMarks >= 80) {
            letterGrade = "A";
            gpa = 3.70;
            status = "Pass - First Class";
            statusColor = ContextCompat.getColor(this, R.color.grade_a);
            badgeBgColor = R.color.grade_a;
        } else if (totalMarks >= 70) {
            letterGrade = "B";
            gpa = 3.00;
            status = "Pass - Second Class";
            statusColor = ContextCompat.getColor(this, R.color.grade_b);
            badgeBgColor = R.color.grade_b;
        } else if (totalMarks >= 60) {
            letterGrade = "C";
            gpa = 2.00;
            status = "Pass - Average";
            statusColor = ContextCompat.getColor(this, R.color.grade_c);
            badgeBgColor = R.color.grade_c;
        } else if (totalMarks >= 50) {
            letterGrade = "D";
            gpa = 1.00;
            status = "Pass - Marginal";
            statusColor = ContextCompat.getColor(this, R.color.grade_d);
            badgeBgColor = R.color.grade_d;
        } else {
            letterGrade = "F";
            gpa = 0.00;
            status = "Fail - Needs Improvement";
            statusColor = ContextCompat.getColor(this, R.color.grade_f);
            badgeBgColor = R.color.grade_f;
        }

        currentCalculatedGrade = new StudentGrade(
                name, roll, subject, assessMarks, midMarks, finalMarks,
                totalMarks, percentage, letterGrade, gpa, status
        );

        tvResultGradeBadge.setText(letterGrade);
        tvResultStatus.setText(status);
        tvResultStatus.setTextColor(statusColor);
        tvResultSummary.setText(String.format(Locale.US, "Total Score: %.1f / 100", totalMarks));
        tvResultPercentage.setText(String.format(Locale.US, "%.1f%%", percentage));
        tvResultGPA.setText(String.format(Locale.US, "%.2f", gpa));
        tvResultGradeText.setText(letterGrade);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(ContextCompat.getColor(this, badgeBgColor));
        tvResultGradeBadge.setBackground(shape);

        cardResult.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Grade Calculated Successfully!", Toast.LENGTH_SHORT).show();
    }

    private void saveGradeToDatabase() {
        if (currentCalculatedGrade == null) {
            Toast.makeText(this, "Please calculate grade first", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertGrade(currentCalculatedGrade);
        if (isInserted) {
            Toast.makeText(this, "Grade record saved to SQLite database!", Toast.LENGTH_LONG).show();
            btnSave.setEnabled(false);
        } else {
            Toast.makeText(this, "Error saving record to database", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetForm() {
        etStudentName.setText("");
        etRollNumber.setText("");
        etSubject.setText("");
        etAssessmentMarks.setText("");
        etMidtermMarks.setText("");
        etFinalMarks.setText("");

        etStudentName.setError(null);
        etRollNumber.setError(null);
        etSubject.setError(null);
        etAssessmentMarks.setError(null);
        etMidtermMarks.setError(null);
        etFinalMarks.setError(null);

        cardResult.setVisibility(View.GONE);
        btnSave.setEnabled(true);
        currentCalculatedGrade = null;
        etStudentName.requestFocus();
    }
}