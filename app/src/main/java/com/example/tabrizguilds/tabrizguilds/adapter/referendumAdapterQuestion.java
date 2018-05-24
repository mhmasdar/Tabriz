package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.ReferendumModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 02/12/2017.
 */

public class referendumAdapterQuestion extends RecyclerView.Adapter<referendumAdapterQuestion.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ReferendumModel> referendumList;
    public static List<Integer> answers = new ArrayList<>();

    public referendumAdapterQuestion(Context context, List<ReferendumModel> referendumList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.referendumList = referendumList;
        answers = new ArrayList<>();
        for (int i = 0; i < referendumList.size(); i++){
            answers.add(i, 0);
        }

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_competitions_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        final ReferendumModel currentObj = referendumList.get(position);
        holder.setData(currentObj, position);

        holder.radio[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.remove(position);
                answers.add(position, 1);
            }
        });
        holder.radio[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.remove(position);
                answers.add(position, 2);
            }
        });
        holder.radio[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.remove(position);
                answers.add(position, 3);
            }
        });
        holder.radio[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.remove(position);
                answers.add(position, 4);
            }
        });

    }

    @Override
    public int getItemCount() {
        return referendumList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion;
        private TextView[] txtOption = new TextView[4];
        //        private TextView txtOption2;
//        private TextView txtOption3;
//        private TextView txtOption4;
//        private RadioGroup radioGroup;
        private RadioButton[] radio = new RadioButton[4];
//        private RadioButton radio2;
//        private RadioButton radio3;
//        private RadioButton radio4;

        int position;
        public ReferendumModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            txtOption[0] = itemView.findViewById(R.id.txtOption1);
            txtOption[1] = itemView.findViewById(R.id.txtOption2);
            txtOption[2] = itemView.findViewById(R.id.txtOption3);
            txtOption[3] = itemView.findViewById(R.id.txtOption4);
            //radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            radio[0] = (RadioButton) itemView.findViewById(R.id.radio1);
            radio[1] = (RadioButton) itemView.findViewById(R.id.radio2);
            radio[2] = (RadioButton) itemView.findViewById(R.id.radio3);
            radio[3] = (RadioButton) itemView.findViewById(R.id.radio4);
        }

        private void setData(ReferendumModel current, int position) {

            this.txtQuestion.setText(current.question);
            for (int i = 0; i < 4; i++) {
                if (current.options.get(i) != null) {
                    if (!current.options.get(i).equals("") && !current.options.get(i).equals("null")) {
                        txtOption[i].setText(current.options.get(i));
                    } else {
                        txtOption[i].setVisibility(View.GONE);
                        radio[i].setVisibility(View.GONE);
                    }
                }
                else {
                    txtOption[i].setVisibility(View.GONE);
                    radio[i].setVisibility(View.GONE);
                }
            }


            this.position = position;
            this.current = current;

        }


    }


}