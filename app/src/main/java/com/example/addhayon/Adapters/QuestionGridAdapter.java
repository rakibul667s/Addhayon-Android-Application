package com.example.addhayon.Adapters;

import static com.example.addhayon.DBQurey.ANSWERED;
import static com.example.addhayon.DBQurey.NOT_VIDITED;
import static com.example.addhayon.DBQurey.REVIEW;
import static com.example.addhayon.DBQurey.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.addhayon.DBQurey;
import com.example.addhayon.QuestionsActivity;
import com.example.addhayon.R;

public class QuestionGridAdapter extends BaseAdapter {
    private int numOfQues;
    private Context context;

    public QuestionGridAdapter(Context context, int numOfQues) {
        this.numOfQues = numOfQues;
        this.context =context;
    }

    @Override
    public int getCount() {
        return numOfQues;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myview;

        if(view == null){
            myview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item,viewGroup, false);
        }else {
            myview = view;
        }
        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof QuestionsActivity)
                    ((QuestionsActivity)context).goToQuestion(i);

            }
        });

        TextView quesTv = myview.findViewById(R.id.ques_num);
        quesTv.setText(String.valueOf(i+1));
        switch (DBQurey.g_quesList.get(i).getStatus()){
            case NOT_VIDITED :
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.Blank)));
                break;
            case UNANSWERED:
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.gradient2EndColor)));
                break;
            case ANSWERED:
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.Answer)));
                break;

            case REVIEW:
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.Review)));
                break;

            default:
                break;

        }
        return myview;
    }
}
