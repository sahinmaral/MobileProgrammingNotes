package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    WeatherInformation[] weatherInformations;
    LayoutInflater inflater;

    WeakReference<Context> mainContext;

    public RecyclerAdapter(Context context, WeatherInformation[] weatherInformations) {
        inflater = LayoutInflater.from(context);
        mainContext = new WeakReference<>(context);
        this.weatherInformations = weatherInformations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.otherday_weather_condition_recycler_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherInformation weatherInformation = weatherInformations[position];

        int drawableResourceId = holder.itemView.getResources().getIdentifier("weathericon_".concat(weatherInformation.getIconName()), "drawable", mainContext.get().getPackageName());
        ImageView weatherIcon = holder.itemView.findViewById(R.id.imageViewOtherDayWeatherConditionIcon);
        weatherIcon.setImageResource(drawableResourceId);

        TextView maxTempTextView = holder.itemView.findViewById(R.id.textViewOtherDayWeatherConditionMaxTemp);
        TextView minTempTextView = holder.itemView.findViewById(R.id.textViewOtherDayWeatherConditionMinTemp);
        TextView weatherConditionNameTextView = holder.itemView.findViewById(R.id.textViewotherDayWeatherConditionName);
        TextView dayNameTextView = holder.itemView.findViewById(R.id.textViewDayName);

        String dayNames[] = new DateFormatSymbols().getWeekdays();
        int maxTemp = (int) weatherInformation.getMaxTemp();
        int minTemp = (int) weatherInformation.getMinTemp();

        maxTempTextView.setText(Integer.toString(maxTemp).concat("°"));
        minTempTextView.setText(Integer.toString(minTemp).concat("°"));
        weatherConditionNameTextView.setText(weatherInformation.getName());
        dayNameTextView.setText(dayNames[weatherInformation.getCalendar().get(Calendar.DAY_OF_WEEK)]);

    }

    @Override
    public int getItemCount() {
        return weatherInformations.length;
    }

}

