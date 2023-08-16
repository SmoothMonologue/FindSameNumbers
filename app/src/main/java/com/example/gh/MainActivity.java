package com.example.gh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random random = new Random();
    NumberButton[][] buttons = new NumberButton[4][4];
    int count[] = new int[12];
    int targetNumber[] = new int[12];
    Character units[] = {'+', '-', 'x', '^'};
    Character[] covers = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};
    //int impossibleNumbers[] = {31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, };
    int openCount = 0, goal = 0;
    int firstValue, secondValue;
    Character calUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = (TableLayout)findViewById(R.id.tablelayout);
        TableRow[] tableRows = new TableRow[5];

        for (int i = 0; i < 12; i++) {
            firstValue = random.nextInt(12)+1;
            secondValue = random.nextInt(12)+1;
            calUnit = units[random.nextInt(4)];
            targetNumber[i] = playerChoice();

            count[i] = 0;
        }
        firstValue = 0; secondValue = 0;

        for (int i = 0; i < 5; i++) {
            tableRows[i] = new TableRow(this);
            table.addView(tableRows[i]);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f);
            layoutParams.setMargins(5, 10, 5, 10);

            if (i == 3) {
                for (int j =0; j < 4; j++) {
                    NumberButton button = new NumberButton(this);
                    button.set(units[j]); button.setLayoutParams(layoutParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calUnit = button.getCover();
                            Toast.makeText(MainActivity.this, firstValue + String.valueOf(calUnit) + secondValue + "=" + playerChoice(), Toast.LENGTH_SHORT).show();
                            openCount = 0;
                            for (int x = 0; x < 3; x++) {
                                for (int y = 0; y < 4; y++) {
                                    buttons[x][y].closeCover();
                                }
                            }
                            firstValue = 0; secondValue = 0;
                        }
                    });
                    buttons[i][j]=button;
                    tableRows[i].addView(buttons[i][j]);
                }
            }

            else if (i == 4) {
                NumberButton requst = new NumberButton(this);
                layoutParams.setMargins(5, 50, 5, 10);
                requst.set(targetNumber[goal]); requst.setLayoutParams(layoutParams);
                requst.openCover(); requst.textView.setTextSize(70);
                //requst.set
                tableRows[i].addView(requst);
            }

            else {
                for (int j = 0; j < 4; j++) {
                    int ranValue = random.nextInt(12);
                    if(count[ranValue] > 0) {
                        j -= 1;
                        continue;
                    }
                    NumberButton button = new NumberButton(this);
                    button.set(ranValue+1); button.set(covers[i*4+j]); button.setLayoutParams(layoutParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openCount++;
                            if (openCount == 1) {
                                firstValue = button.getValue();
                                button.openCover();
                            }
                            if (openCount == 2) {
                                secondValue = button.getValue();
                                button.openCover();
                            }
                        }
                    });
                    buttons[i][j]=button;
                    count[ranValue]++;
                    tableRows[i].addView(buttons[i][j]);
                }
            }
        }
    }

    void countMatch() {

    }

    int playerChoice() {
        int result = 0;
        switch (calUnit) {
            case ('+'):
                result = firstValue + secondValue;
                break;
            case ('-'):
                result = firstValue - secondValue;
                break;
            case ('x'):
                result = firstValue * secondValue;
                break;
            case ('^'):
                result = (int)Math.pow(firstValue, 2) + (int)Math.pow(secondValue, 2);
                break;
        }
        return result;
    }
}