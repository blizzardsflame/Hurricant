package com.blizzard.hurricant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blizzard.hurricant.R;
import com.blizzard.hurricant.weather.Hourly;

public class Houradapter extends RecyclerView.Adapter <Houradapter.HourViewHolder> {

    //variable to hold the array of hours
    private Hourly[] hHours;
    private Context mContext;

    //constructor to create the adapter in the activity and then set the data
    public Houradapter(Context context, Hourly[] hours){
        mContext=context;
        hHours = hours;
    }

    //methode will be called whenever a new viewholder is needed
    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hourly_list_item,viewGroup,false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder hourViewHolder, int i) {
    hourViewHolder.bindHour(hHours[i]);
    }

    @Override
    public int getItemCount() {
        return hHours.length;
    }

    class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         TextView hTimeLabel;
         TextView hTemperatureLabel;
         TextView hSummaryLabel;
         ImageView hIconImageView;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            hTimeLabel = itemView.findViewById(R.id.hTimeLabel);
            hTemperatureLabel = itemView.findViewById(R.id.hTemperatureLabel);
            hSummaryLabel = itemView.findViewById(R.id.hSummaryLabel);
            hIconImageView = itemView.findViewById(R.id.hIconImageView);

            itemView.setOnClickListener(this);
        }
         void bindHour(Hourly hour){
            hTimeLabel.setText(hour.getHour());
            hSummaryLabel.setText(hour.getSummary());
            hTemperatureLabel.setText(hour.getTemperature() + "");
            hIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View v) {
            String time = hTimeLabel.getText().toString();
            String temperature = hTemperatureLabel.getText().toString();
            String summary = hSummaryLabel.getText().toString();
            String message = String.format("At %s it'll be %s and %s",time,temperature,summary);
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }


}
