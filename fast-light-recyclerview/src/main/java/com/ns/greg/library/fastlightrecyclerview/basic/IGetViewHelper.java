package com.ns.greg.library.fastlightrecyclerview.basic;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Gregory on 2016/4/28.
 */
public interface IGetViewHelper {

  View getView(int id);

  Button getButton(int id);

  ImageButton getImageButton(int id);

  ImageView getImageView(int id);

  TextView getTextView(int id);

  LinearLayout getLinearLayout(int id);

  RelativeLayout getRelativeLayout(int id);
}
