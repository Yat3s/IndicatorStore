package com.yat3s.indicatorstore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yat3s.chopin.ChopinLayout;
import com.yat3s.chopin.indicator.LottieIndicator;

public class MainActivity extends AppCompatActivity {
    private static final int COMPLETE_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ChopinLayout chopinLayout = (ChopinLayout) findViewById(R.id.chopin_layout);
        LottieIndicator headerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_CIRCLE_ORANGE, 0.2f);
        LottieIndicator footerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_CIRCLE_ORANGE, 0.2f);
        headerIndicator.setBackgroundColor(Color.GREEN);
        footerIndicator.setBackgroundColor(Color.GREEN);

        chopinLayout.setRefreshHeaderIndicator(headerIndicator);
        chopinLayout.setLoadingFooterIndicator(footerIndicator);
        chopinLayout.setOnRefreshListener(new ChopinLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chopinLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chopinLayout.refreshComplete();
                    }
                }, COMPLETE_DELAY);
            }
        });
        chopinLayout.setOnLoadMoreListener(new ChopinLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                chopinLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chopinLayout.refreshComplete();
                    }
                }, COMPLETE_DELAY);
            }
        });
    }
}
