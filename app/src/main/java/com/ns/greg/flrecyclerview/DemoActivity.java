package com.ns.greg.flrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ns.greg.library.fastlightrecyclerview.base.BaseRecyclerViewHolder;
import com.ns.greg.library.fastlightrecyclerview.base.SingleVHAdapter;
import com.ns.greg.library.fastlightrecyclerview.decoration.EasyDecoration;
import com.ns.greg.library.fastlightrecyclerview.listener.LoadMoreScrollListener;
import com.ns.greg.library.fastlightrecyclerview.listener.OnItemClickListener;
import com.ns.greg.library.fastlightrecyclerview.utils.ViewUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory
 * @since 2017/7/26
 */
public class DemoActivity extends AppCompatActivity implements OnItemClickListener {

  private static final int LIST_SIZE = 200;

  private final List<Integer> demoList = new ArrayList<>(LIST_SIZE);

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    for (int i = 0; i < LIST_SIZE; i++) {
      demoList.add(i);
    }

    verticalDemo();
    horizontalDemo();
  }

  private void verticalDemo() {
    RecyclerView recyclerView = findViewById(R.id.vertical_rv);
    VerticalAdapter autoLoadAdapter = new VerticalAdapter(this, getApplicationContext(), demoList);
    autoLoadAdapter.setAutoLoad(true);
    recyclerView.setAdapter(autoLoadAdapter);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnScrollListener(new LoadMoreScrollListener());
    recyclerView.addItemDecoration(new EasyDecoration.Builder().setMargin(10, 10, 10, 10)
        .setDividerOrientation(layoutManager)
        .setDividerSize(ViewUtils.convertDp2Pixel(2, getApplicationContext()))
        .build());
  }

  private void horizontalDemo() {
    RecyclerView recyclerView = findViewById(R.id.horizontal_rv);
    HorizontalAdapter adapter = new HorizontalAdapter(this, getApplicationContext(), demoList);
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnScrollListener(new LoadMoreScrollListener());
    // Don't know why the bottom margin not working correctly at HORIZONTAL orientation
    recyclerView.addItemDecoration(new EasyDecoration.Builder().setMargin(10, 10, 10, 0)
        .setDividerOrientation(layoutManager)
        .setDividerSize(ViewUtils.convertDp2Pixel(3, getApplicationContext()))
        .build());
  }

  /**
   * Root click callback from {@link BaseRecyclerViewHolder}
   *
   * @param position position of data set
   */
  @Override public void onRootViewClick(int position) {
    Toast.makeText(getApplicationContext(), "Position: " + position + ", parent view clicked.",
        Toast.LENGTH_SHORT).show();
  }

  /**
   * Specific child view callback from {@link BaseRecyclerViewHolder}
   *
   * @param id child view id
   * @param position position of data set
   */
  @Override public void onSpecificViewClick(int id, int position) {
    Toast.makeText(getApplicationContext(),
        "Position: " + position + ", child view id: " + id + " clicked.", Toast.LENGTH_SHORT)
        .show();
  }

  private static class VerticalAdapter extends SingleVHAdapter<BaseRecyclerViewHolder, Integer> {

    private final DemoActivity instance;

    VerticalAdapter(DemoActivity reference, Context context, @NonNull List<Integer> list) {
      super(context, list);
      WeakReference<DemoActivity> weakReference = new WeakReference<>(reference);
      instance = weakReference.get();
    }

    @Override protected BaseRecyclerViewHolder onCreateViewHolderImp(ViewGroup parent) {
      BaseRecyclerViewHolder recyclerViewHolder = new BaseRecyclerViewHolder(
          LayoutInflater.from(getContext()).inflate(R.layout.list_content, parent, false));
      // Register click listener of root view
      recyclerViewHolder.setOnItemClickListener(instance);
      return recyclerViewHolder;
    }

    @Override protected void onBindViewHolderImp(BaseRecyclerViewHolder holder, int position,
        Integer listItem) {
      // Register click listener of specific view
      holder.getView(R.id.item_tv).setOnClickListener(holder);
      holder.getView(R.id.item_tv).setBackgroundColor(Color.RED);
      if (isLoading() && position == getItemCount() - 1) {
        holder.getView(R.id.item_tv).setVisibility(View.GONE);
        holder.getView(R.id.loading).setVisibility(View.VISIBLE);
      } else {
        holder.getView(R.id.loading).setVisibility(View.GONE);
        holder.getTextView(R.id.item_tv).setVisibility(View.VISIBLE);
        holder.getTextView(R.id.item_tv).setText(String.valueOf(listItem));
      }
    }

    @Override protected int getInitItemCount() {
      return 0;
    }
  }

  private static class HorizontalAdapter extends SingleVHAdapter<BaseRecyclerViewHolder, Integer> {

    private final DemoActivity instance;

    HorizontalAdapter(DemoActivity reference, Context context, @NonNull List<Integer> list) {
      super(context, list);
      WeakReference<DemoActivity> weakReference = new WeakReference<>(reference);
      instance = weakReference.get();
    }

    @Override protected BaseRecyclerViewHolder onCreateViewHolderImp(ViewGroup parent) {
      BaseRecyclerViewHolder recyclerViewHolder = new BaseRecyclerViewHolder(
          LayoutInflater.from(getContext()).inflate(R.layout.list_content, parent, false));
      // Register click listener of root view
      recyclerViewHolder.setOnItemClickListener(instance);
      return recyclerViewHolder;
    }

    @Override protected void onBindViewHolderImp(BaseRecyclerViewHolder holder, int position,
        Integer listItem) {
      // Register click listener of specific view
      holder.getView(R.id.item_tv).setOnClickListener(holder);
      holder.getView(R.id.item_tv).setBackgroundColor(Color.BLUE);
      if (isLoading() && position == getItemCount() - 1) {
        holder.getView(R.id.item_tv).setVisibility(View.GONE);
        holder.getView(R.id.loading).setVisibility(View.VISIBLE);
      } else {
        holder.getView(R.id.loading).setVisibility(View.GONE);
        holder.getTextView(R.id.item_tv).setVisibility(View.VISIBLE);
        holder.getTextView(R.id.item_tv).setText(String.valueOf(listItem));
      }
    }

    @Override protected int getInitItemCount() {
      return 0;
    }
  }
}
