package com.gyx.helplyfdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



	private EditText edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);






		edit = (EditText) findViewById(R.id.edit);


		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				Log.e("beforeTextChanged", "CharSequence:"+s.toString() + ",start:" + start + ",count:" + count + ",after:" + after);
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {


				Log.e("onTextChanged", "CharSequence:"+s.toString() + ",start:" + start + ",before:" + before + ",count:" + count);


			}

			@Override
			public void afterTextChanged(Editable s) {


				Log.e("afterTextChanged", "Editable:"+s.toString());

			}
		});









	}
}
