package com.qiu.keepaccount.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.qiu.keepaccount.R;

public class BasicActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_edit_account:
                    item.setIcon(R.drawable.ic_edit_account_red);
                    mTextMessage.setText(R.string.title_edit_account);
                    return true;
                case R.id.navigation_chart:
                    item.setIcon(R.drawable.ic_chart_red);
                    mTextMessage.setText(R.string.title_chart);
                    return true;
                case R.id.navigation_me:
                    item.setIcon(R.drawable.ic_me_red);
                    mTextMessage.setText(R.string.title_me);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
