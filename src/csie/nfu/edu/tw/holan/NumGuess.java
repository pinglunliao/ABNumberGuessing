package csie.nfu.edu.tw.holan;

import java.util.Arrays;
import java.util.Collections;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NumGuess extends Activity {

	private Button guessBtn, newGameBtn;
	private TextView resultTxt, answerTxt;
	private EditText guessEdit;
	private String answerStr, playerGuessStr;
	private Integer[] answer = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private int maxDigits = 2;
	private Spinner maxDigitSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);
		
		guessEdit = (EditText)findViewById(R.id.numGuessEdit);
		guessBtn = (Button)findViewById(R.id.guessBtn);
		resultTxt = (TextView)findViewById(R.id.guessResultTxt);
		answerTxt = (TextView)findViewById(R.id.answerTxt);

		guessBtn.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkGuess();
			}
		});
		
		newGameBtn = (Button)findViewById(R.id.newGameBtn);
		newAnswer();
		newGameBtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newAnswer();
			}
		});
		
		
		maxDigitSpinner = (Spinner)findViewById(R.id.digitSpin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.max_digits, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		maxDigitSpinner.setAdapter(adapter);
		
		maxDigitSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				maxDigits = Integer.parseInt(parent.getItemAtPosition(pos).toString());
				newAnswer();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private void newAnswer() {
		shuffle();
		StringBuffer tmpAnswer = new StringBuffer();
		for(int i = 0; i < maxDigits; i++)
			tmpAnswer.append(answer[i]);
		answerTxt.setText(tmpAnswer);
		answerStr = answerTxt.getText().toString();
	}

	private void shuffle() {
		// Shuffle the elements in the array
		Collections.shuffle(Arrays.asList(answer));
	}
	
	private void checkGuess() {	
		playerGuessStr = guessEdit.getText().toString();
		
		// select all guess digits
		guessEdit.setSelection(0, playerGuessStr.length());

		if(playerGuessStr.length() != maxDigits) {
			resultTxt.setText("Guess number error");
			resultTxt.setTextColor(Color.RED);
		} else {
			// Check the answer
			resultTxt.setText("Check...");
			resultTxt.setTextColor(Color.BLACK);

			int a = positionMatch();
			int b = appearMatch();
			
			resultTxt.setText(a + "A" + b + "B");
		}
	}

	private int positionMatch() {
		int sameCount = 0;
		for(int i = 0; i < maxDigits; i++) {
			if( playerGuessStr.charAt(i) == answerStr.charAt(i) )
				sameCount++;
		}

		return sameCount;
	}
	
	private int appearMatch() {
		int appearCount = 0;
		for(int i = 0; i < maxDigits; i++) {
			for(int j = 0; j < maxDigits; j++) {
				if( i != j && playerGuessStr.charAt(i) == answerStr.charAt(j) )
				appearCount++;
			}
		}
		
		return appearCount;
	}
}
