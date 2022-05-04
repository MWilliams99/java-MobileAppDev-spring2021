package com.example.wguscheduler_marywilliams.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wguscheduler_marywilliams.Entity.CourseEntity;
import com.example.wguscheduler_marywilliams.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetailActivity.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("termId", current.getTermId());

                    context.startActivity(intent);
                }
            });
        }
    }

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position){
        if(mCourses != null){
            final CourseEntity current = mCourses.get(position);
            holder.courseItemView.setText(current.getCourseName());
        }
        else{
            holder.courseItemView.setText("No Word");
        }
    }

    @Override
    public int getItemCount(){
        if(mCourses != null){
            return mCourses.size();
        }
        else{
            return 0;
        }
    }

    public void setCourses(List<CourseEntity> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }
}
