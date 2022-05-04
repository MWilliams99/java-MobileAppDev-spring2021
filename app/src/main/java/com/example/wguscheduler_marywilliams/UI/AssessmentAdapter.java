package com.example.wguscheduler_marywilliams.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wguscheduler_marywilliams.Entity.AssessmentEntity;
import com.example.wguscheduler_marywilliams.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);
                    intent.putExtra("courseId",current.getCourseId());
                    intent.putExtra("assessmentId", current.getAssessmentId());

                    context.startActivity(intent);
                }
            });
        }
    }

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position){
        if(mAssessments != null){
            final AssessmentEntity current = mAssessments.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());
        }
        else{
            holder.assessmentItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount(){
        if(mAssessments != null){
            return mAssessments.size();
        }
        else{
            return 0;
        }
    }

    public void setAssessments(List<AssessmentEntity> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
