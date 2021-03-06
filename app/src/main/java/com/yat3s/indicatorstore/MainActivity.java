package com.yat3s.indicatorstore;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yat3s.chopin.ChopinLayout;
import com.yat3s.chopin.indicator.LottieIndicator;
import com.yat3s.library.adapter.BaseAdapter;
import com.yat3s.library.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int COMPLETE_DELAY = 3000;
    private static final int GRID_SPAN = 2;

    private  ChopinLayout mChopinLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChopinLayout = (ChopinLayout) findViewById(R.id.chopin_layout);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lottie_rv);
        LottieIndicator headerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_AROUND_THE_WORLD, 0.2f);
        LottieIndicator footerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_AROUND_THE_WORLD, 0.2f);
        mChopinLayout.setRefreshHeaderIndicator(headerIndicator);
        mChopinLayout.setLoadingFooterIndicator(footerIndicator);
        mChopinLayout.setOnRefreshListener(new ChopinLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mChopinLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChopinLayout.refreshComplete();
                    }
                }, COMPLETE_DELAY);
            }
        });
        mChopinLayout.setOnLoadMoreListener(new ChopinLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mChopinLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChopinLayout.loadMoreComplete();
                    }
                }, COMPLETE_DELAY);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, GRID_SPAN));
        List<LottieCase> lottieCases = new ArrayList<>();
        lottieCases.add(new LottieCase("Around The World", IndicatorStore.LOTTIE_AROUND_THE_WORLD,
                0.2f, null));
        lottieCases.add(new LottieCase("Beer Bubbles", IndicatorStore.LOTTIE_BEER_BUBBLES,
                0.2f, "#FFA80F"));
        lottieCases.add(new LottieCase("Black Dot Circle", IndicatorStore.LOTTIE_BLACK_DOT_CIRCLE,
                0.3f, null));
        lottieCases.add(new LottieCase("Four Black Ball Rotation", IndicatorStore.LOTTIE_BLACK_FOUR_BALL_ROTATION,
                0.2f, "#EA6262"));
        lottieCases.add(new LottieCase("Bounce Ball Red", IndicatorStore.LOTTIE_BOUNCE_BALL_RED,
                0.2f, null));
        lottieCases.add(new LottieCase("Circle Stroke White", IndicatorStore.LOTTIE_CIRCLE_STROKE_WHITE,
                0.2f,"#000000"));
        LottieAdapter lottieAdapter = new LottieAdapter(this, lottieCases);
        recyclerView.setAdapter(lottieAdapter);
        lottieAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<LottieCase>() {
            @Override
            public void onClick(View view, LottieCase item, int position) {
                LottieIndicator headerIndicator = new LottieIndicator(MainActivity.this, item.fileName, item.scale);
                LottieIndicator footerIndicator = new LottieIndicator(MainActivity.this, item.fileName, item.scale);
                if (null != item.backgroundColor) {
                    headerIndicator.setBackgroundColor(Color.parseColor(item.backgroundColor));
                    footerIndicator.setBackgroundColor(Color.parseColor(item.backgroundColor));
                }
                mChopinLayout.setRefreshHeaderIndicator(headerIndicator);
                mChopinLayout.setLoadingFooterIndicator(footerIndicator);
            }
        });

        configureIndicatorLocationSetting();
    }

    private void configureIndicatorLocationSetting() {
        RadioGroup indicatorLocationRg = (RadioGroup) findViewById(R.id.location_rg);
        indicatorLocationRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.location_outside_rb:
                        mChopinLayout.setHeaderIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_OUTSIDE);
                        mChopinLayout.setFooterIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_OUTSIDE);
                        break;
                    case R.id.location_behind_rb:
                        mChopinLayout.setHeaderIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_BEHIND);
                        mChopinLayout.setFooterIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_BEHIND);
                        break;
                    case R.id.location_front_rb:
                        mChopinLayout.setHeaderIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_FRONT);
                        mChopinLayout.setFooterIndicatorLocation(ChopinLayout.INDICATOR_LOCATION_FRONT);
                        break;
                }
            }
        });
        indicatorLocationRg.check(R.id.location_outside_rb);
    }

    private static class LottieAdapter extends BaseAdapter<LottieCase> {

        public LottieAdapter(Context context, List<LottieCase> data) {
            super(context, data);
        }

        @Override
        protected void bindDataToItemView(BaseViewHolder holder, LottieCase item, int position) {
            holder.setText(R.id.title_tv, item.title);

        }

        @Override
        protected int getItemViewLayoutId(int position, LottieCase lottieCase) {
            return R.layout.item_case;
        }
    }

    private static class LottieCase {
        public String title;

        public String fileName;

        public float scale;

        public String backgroundColor;

        public LottieCase(String title, String fileName, float scale, String backgroundColor) {
            this.title = title;
            this.fileName = fileName;
            this.scale = scale;
            this.backgroundColor = backgroundColor;
        }
    }
}
