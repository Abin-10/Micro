package com.example.micro1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ViewRecordsActivity extends AppCompatActivity {

    private RecyclerView rvStudentGrades;
    private LinearLayout layoutEmpty;
    private TextInputEditText etSearch;

    private DatabaseHelper dbHelper;
    private StudentGradeAdapter adapter;
    private List<StudentGrade> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        dbHelper = new DatabaseHelper(this);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupSearch();
        loadRecords();
    }

    private void initViews() {
        rvStudentGrades = findViewById(R.id.rvStudentGrades);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        etSearch = findViewById(R.id.etSearch);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        rvStudentGrades.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentGradeAdapter(this, gradeList, (grade, position) -> showDeleteConfirmation(grade));
        rvStudentGrades.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecords(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadRecords() {
        gradeList = dbHelper.getAllGrades();
        adapter.updateList(gradeList);
        updateEmptyState(gradeList.isEmpty());
    }

    private void filterRecords(String keyword) {
        if (keyword.isEmpty()) {
            gradeList = dbHelper.getAllGrades();
        } else {
            gradeList = dbHelper.searchGrades(keyword);
        }
        adapter.updateList(gradeList);
        updateEmptyState(gradeList.isEmpty());
    }

    private void showDeleteConfirmation(StudentGrade grade) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Grade Record")
                .setMessage("Are you sure you want to delete the grade record for " + grade.getStudentName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteGrade(grade.getId());
                    if (deleted) {
                        Toast.makeText(this, "Record deleted successfully", Toast.LENGTH_SHORT).show();
                        loadRecords();
                    } else {
                        Toast.makeText(this, "Failed to delete record", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateEmptyState(boolean isEmpty) {
        if (isEmpty) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvStudentGrades.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvStudentGrades.setVisibility(View.VISIBLE);
        }
    }
}