package com.ns.greg.flrecyclerview;

import android.content.Context;
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
import com.ns.greg.library.fastlightrecyclerview.BaseSingleVHAdapter;
import com.ns.greg.library.fastlightrecyclerview.basic.BaseRecyclerViewHolder;
import com.ns.greg.library.fastlightrecyclerview.module.LoadMoreScrollListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory
 * @since 2017/7/26
 */
public class DemoActivity extends AppCompatActivity
    implements BaseRecyclerViewHolder.OnItemClickListener {

  private List<Integer> demoList;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    demoList = new ArrayList<>();
    for (int i = 0; i < 300; i++) {
      demoList.add(i);
    }
  }

  @Override protected void onResume() {
    super.onResume();

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.demo_rv);
    DemoAdapter adapter = new DemoAdapter(this, getApplicationContext(), demoList);
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerView.addOnScrollListener(new LoadMoreScrollListener());
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

  private static class DemoAdapter extends BaseSingleVHAdapter<BaseRecyclerViewHolder, Integer> {

    private final DemoActivity instance;

    DemoAdapter(DemoActivity reference, Context context, @NonNull List<Integer> list) {
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
      if (isLoading() && position == getItemCount() - 1) {
        holder.getView(R.id.item_tv).setVisibility(View.GONE);
        holder.getView(R.id.loading).setVisibility(View.VISIBLE);
      } else {
        holder.getView(R.id.loading).setVisibility(View.GONE);
        holder.getTextView(R.id.item_tv).setVisibility(View.VISIBLE);
        holder.getTextView(R.id.item_tv).setText(String.valueOf(listItem));
      }
    }

    @Override protected boolean isAutoLoadEnable() {
      return true;
    }

    @Override protected int getInitItemCount() {
      return 0;
    }
  }
}
