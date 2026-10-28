package com.example.micro1;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class StudentGradeAdapter extends RecyclerView.Adapter<StudentGradeAdapter.ViewHolder> {

    private final Context context;
    private List<StudentGrade> gradeList;
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(StudentGrade grade, int position);
    }

    public StudentGradeAdapter(Context context, List<StudentGrade> gradeList, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.gradeList = gradeList;
        this.deleteClickListener = deleteClickListener;
    }

    public void updateList(List<StudentGrade> newList) {
        this.gradeList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentGrade item = gradeList.get(position);

        holder.tvName.setText(item.getStudentName());
        String info = "Roll: " + item.getRollNumber() + " • " + item.getSubject();
        holder.tvSubjectRoll.setText(info);
        holder.tvBadgeGrade.setText(item.getLetterGrade());

        String totalStr = String.format(Locale.US, "%.1f / 100", item.getTotalMarks());
        holder.tvTotalMarks.setText(totalStr);

        String percStr = String.format(Locale.US, "%.1f%%", item.getPercentage());
        holder.tvPercentage.setText(percStr);

        String gpaStr = String.format(Locale.US, "%.2f", item.getGpa());
        holder.tvGPA.setText(gpaStr);

        int colorRes = getGradeColor(item.getLetterGrade());
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(ContextCompat.getColor(context, colorRes));
        holder.tvBadgeGrade.setBackground(shape);

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (deleteClickListener != null && pos != RecyclerView.NO_POSITION) {
                deleteClickListener.onDeleteClick(item, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gradeList != null ? gradeList.size() : 0;
    }

    private int getGradeColor(String grade) {
        if (grade == null) return R.color.primary;
        switch (grade.toUpperCase()) {
            case "A+":
            case "A":
                return R.color.grade_a;
            case "B":
                return R.color.grade_b;
            case "C":
                return R.color.grade_c;
            case "D":
                return R.color.grade_d;
            case "F":
                return R.color.grade_f;
            default:
                return R.color.primary;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBadgeGrade, tvName, tvSubjectRoll, tvTotalMarks, tvPercentage, tvGPA;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBadgeGrade = itemView.findViewById(R.id.tvBadgeGrade);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvSubjectRoll = itemView.findViewById(R.id.tvItemSubjectRoll);
            tvTotalMarks = itemView.findViewById(R.id.tvItemTotalMarks);
            tvPercentage = itemView.findViewById(R.id.tvItemPercentage);
            tvGPA = itemView.findViewById(R.id.tvItemGPA);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}