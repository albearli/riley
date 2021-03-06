package com.example.allie.StatsPagerStuff;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.allie.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class StatsAdaptor extends PagerAdapter {
    private List<AttrStats> stats;
    private LayoutInflater layoutInflater;
    private Context context;

    public StatsAdaptor(List<AttrStats> stats, Context context) {
        this.stats = stats;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stats.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.stat_item, container, false);

        //fill in view items for specific stat
        ((TextView) view.findViewById(R.id.name)).setText(stats.get(position).getName());
        if (stats.get(position).getName().equals("Income - ")) {
            ((TextView) view.findViewById(R.id.average)).setText( "$" + stats.get(position).getAverage().toString() + ",000");

        } else if (stats.get(position).getName().equals("Money Spent - ")) {
            ((TextView) view.findViewById(R.id.average)).setText( "$" + stats.get(position).getAverage().toString());

        } else {
            ((TextView) view.findViewById(R.id.average)).setText(stats.get(position).getAverage().toString());
        }
        stats.get(position).addPieChart(context, view.findViewById(R.id.stat_detail));
        //stats.get(position).addHistogramChart(context, view.findViewById(R.id.stat_detail));
        //view.animate().translationYBy(2*view.findViewById(R.id.stat_detail).getHeight()).setDuration(0);
        //Animation animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        //Animation animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.findViewById(R.id.stat_detail).getVisibility() == View.GONE) {

                    TransitionManager.beginDelayedTransition(container, new AutoTransition());
                    view.findViewById(R.id.stat_detail).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.stat_general).animate().translationXBy(450).setDuration(200);
                    PieChart chart = stats.get(position).chart;
                    chart.spin(1000, chart.getRotationAngle(), chart.getRotationAngle()+360, Easing.EaseInOutCubic);
                    //view.animate().translationYBy(-view.findViewById(R.id.stat_detail).getHeight()).setDuration(0);
                    //view.findViewById(R.id.stat_detail).animate().alpha(255)
                    //view.animate().translationYBy(-view.findViewById(R.id.stat_detail).getHeight()).setDuration(1000);
                }else {
                    view.findViewById(R.id.stat_general).animate().translationXBy(-450).setDuration(200);
                    TransitionManager.beginDelayedTransition(container, new AutoTransition());
                    view.findViewById(R.id.stat_detail).setVisibility(View.GONE);
                    //view.animate().translationYBy(view.findViewById(R.id.stat_detail).getHeight()).setDuration(1000);
                }
            }
        });
        //CardView.LayoutParams parms = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //view.setTop(container.getBottom()+100);

        container.addView(view,  0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
