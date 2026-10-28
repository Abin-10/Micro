package com.example.micro1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalRecords, tvAvgGPA;
    private MaterialCardView cardGradeCalculator, cardSavedRecords;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        tvTotalRecords = findViewById(R.id.tvTotalRecords);
        tvAvgGPA = findViewById(R.id.tvAvgGPA);
        cardGradeCalculator = findViewById(R.id.cardGradeCalculator);
        cardSavedRecords = findViewById(R.id.cardSavedRecords);
    }

    private void setupListeners() {
        cardGradeCalculator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GradeCalculatorActivity.class);
            startActivity(intent);
        });

        cardSavedRecords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewRecordsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        int totalCount = dbHelper.getTotalRecordCount();
        double avgGpa = dbHelper.getAverageGPA();

        tvTotalRecords.setText(String.valueOf(totalCount));
        tvAvgGPA.setText(String.format(Locale.US, "%.2f", avgGpa));
    }
}