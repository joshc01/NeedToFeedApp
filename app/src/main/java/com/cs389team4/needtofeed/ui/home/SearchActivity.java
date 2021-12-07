package com.cs389team4.needtofeed.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentSearchBinding;

import org.bouncycastle.util.test.Test;

public class SearchActivity extends AppCompatActivity {
    Bundle extras = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        extras = getIntent().getExtras();

        Log.d("Test", "onCreate: ");
        //goTest(extras);
    }

    private void goTest (Bundle info){
        TextView view = findViewById(R.id.testText);
        view.setText(info.getString("Test"));
    }

}