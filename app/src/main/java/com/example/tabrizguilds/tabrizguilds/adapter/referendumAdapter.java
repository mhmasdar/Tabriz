package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.ReferendumModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mohamadHasan on 23/12/2017.
 */

public class referendumAdapter extends RecyclerView.Adapter<referendumAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ReferendumModel> referendumList;
//    public static List<Integer> answers = new ArrayList<>();


    public referendumAdapter(Context context, List<ReferendumModel> referendumList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.referendumList = referendumList;
//        answers = new ArrayList<>();
//        for (int i = 0; i < referendumList.size(); i++){
//            answers.add(i, 0);
//        }

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_referendum_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        final ReferendumModel currentObj = referendumList.get(position);
        holder.setData(currentObj, position);

    }

    @Override
    public int getItemCount() {
        return referendumList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion;
        private LinearLayout[] lytOption = new LinearLayout[4];
        //        private TextView txtOption1;
//        private TextView txtOption2;
//        private TextView txtOption3;
//        private TextView txtOption4;
        private TextView[] txtOption = new TextView[4];
        private TextView[] txtPercent = new TextView[4];
//        private TextView txtPercent2;
//        private TextView txtPercent3;
//        private TextView txtPercent4;
        private ProgressBar[] progress = new ProgressBar[4];
//        private ProgressBar progress2;
//        private ProgressBar progress3;
//        private ProgressBar progress4;

        int position;
        public ReferendumModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            lytOption[0] = (LinearLayout) itemView.findViewById(R.id.lytOption1);
            lytOption[1] = (LinearLayout) itemView.findViewById(R.id.lytOption2);
            lytOption[2] = (LinearLayout) itemView.findViewById(R.id.lytOption3);
            lytOption[3] = (LinearLayout) itemView.findViewById(R.id.lytOption4);
            txtOption[0] = (TextView) itemView.findViewById(R.id.txtOption1);
            txtOption[1] = (TextView) itemView.findViewById(R.id.txtOption2);
            txtOption[2] = (TextView) itemView.findViewById(R.id.txtOption3);
            txtOption[3] = (TextView) itemView.findViewById(R.id.txtOption4);
            txtPercent[0] = (TextView) itemView.findViewById(R.id.txtPercent1);
            txtPercent[1] = (TextView) itemView.findViewById(R.id.txtPercent2);
            txtPercent[2] = (TextView) itemView.findViewById(R.id.txtPercent3);
            txtPercent[3] = (TextView) itemView.findViewById(R.id.txtPercent4);
            progress[0] = (ProgressBar) itemView.findViewById(R.id.progress1);
            progress[1] = (ProgressBar) itemView.findViewById(R.id.progress2);
            progress[2] = (ProgressBar) itemView.findViewById(R.id.progress3);
            progress[3] = (ProgressBar) itemView.findViewById(R.id.progress4);

            //progress.setProgress(65);
            progress[0].setRotation(180);
            progress[1].setRotation(180);
            progress[2].setRotation(180);
            progress[3].setRotation(180);
        }

        private void setData(ReferendumModel current, int position) {

            this.txtQuestion.setText(current.question);
            for (int i = 0; i < 4; i++) {
                if (current.options.get(i) != null) {
                    if (!current.options.get(i).equals("") && !current.options.get(i).equals("null")) {
                        txtOption[i].setText(current.options.get(i));
                        if (i == 0) {
                            txtPercent[i].setText(current.res1 + "%");
                            progress[i].setProgress((int)current.res1);
                        }else if (i == 1) {
                            txtPercent[i].setText(current.res2 + "%");
                            progress[i].setProgress((int)current.res2);
                        }
                        if (i == 2) {
                            txtPercent[i].setText(current.res3 + "%");
                            progress[i].setProgress((int)current.res3);
                        }
                        if (i == 3) {
                            txtPercent[i].setText(current.res4 + "%");
                            progress[i].setProgress((int)current.res4);
                        }
                    } else {
                        lytOption[i].setVisibility(View.GONE);

                    }
                }
                else {
                    lytOption[i].setVisibility(View.GONE);

                }
            }


            this.position = position;
            this.current = current;


        }

    }
}