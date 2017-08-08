package com.yat3s.indicatorstore;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yat3s.chopin.ChopinLayout;
import com.yat3s.chopin.indicator.LottieIndicator;
import com.yat3s.library.adapter.BaseAdapter;
import com.yat3s.library.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int COMPLETE_DELAY = 3000;
    private static final int GRID_SPAN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ChopinLayout chopinLayout = (ChopinLayout) findViewById(R.id.chopin_layout);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lottie_rv);
        LottieIndicator headerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_CIRCLE_STROKE_WHITE, 0.2f);
        LottieIndicator footerIndicator = new LottieIndicator(this, IndicatorStore.LOTTIE_CIRCLE_STROKE_WHITE, 0.2f);
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

        recyclerView.setLayoutManager(new GridLayoutManager(this, GRID_SPAN));
        List<LottieCase> lottieCases = new ArrayList<>();
        lottieCases.add(new LottieCase("Around The World", IndicatorStore.LOTTIE_AROUND_THE_WORLD));
        lottieCases.add(new LottieCase("Beer Bubbles", IndicatorStore.LOTTIE_BEER_BUBBLES));
        lottieCases.add(new LottieCase("Black Dot Circle", IndicatorStore.LOTTIE_BLACK_DOT_CIRCLE));
        lottieCases.add(new LottieCase("Four Black Ball Rotation", IndicatorStore.LOTTIE_BLACK_FOUR_BALL_ROTATION));
        lottieCases.add(new LottieCase("Bounce Ball Red", IndicatorStore.LOTTIE_BOUNCE_BALL_RED));
        lottieCases.add(new LottieCase("Circle Stroke White", IndicatorStore.LOTTIE_CIRCLE_STROKE_WHITE));
        LottieAdapter lottieAdapter = new LottieAdapter(this, lottieCases);
        recyclerView.setAdapter(lottieAdapter);
        lottieAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<LottieCase>() {
            @Override
            public void onClick(View view, LottieCase item, int position) {
                LottieIndicator headerIndicator = new LottieIndicator(MainActivity.this, item.fileName, 0.2f);
                LottieIndicator footerIndicator = new LottieIndicator(MainActivity.this, item.fileName, 0.2f);
                chopinLayout.setRefreshHeaderIndicator(headerIndicator);
                chopinLayout.setLoadingFooterIndicator(footerIndicator);
            }
        });
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

        public LottieCase(String title, String fileName) {
            this.title = title;
            this.fileName = fileName;
        }
    }
}
