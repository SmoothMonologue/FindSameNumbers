package com.example.gh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
    int[] count = new int[12];
    int[] targetNumber = new int[12];
    Character[] units = {'+', '-', 'x', '^'};
    Character[] covers = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};
    int openCount = 0, goal = 0, tryCount = 0;
    int firstValue, secondValue;
    Character calUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = (TableLayout)findViewById(R.id.tablelayout);
        TableRow[] tableRows = new TableRow[6];

        NumberButton requst = new NumberButton(this);
        TextView numOfTry = new TextView(this);
        TextView numOfGoal = new TextView(this);

        for (int i = 0; i < 12; i++) {
            firstValue = random.nextInt(12)+1;
            secondValue = random.nextInt(12)+1;
            if (secondValue == firstValue) {
                if (secondValue != 1) secondValue--;
                else secondValue++;
            }
            calUnit = units[random.nextInt(4)];
            for (int j = 0; j < i; j++) {
                if (playerChoice() == targetNumber[j]) {
                    i--;
                    continue;
                }
            }
            targetNumber[i] = playerChoice();

            count[i] = 0;
        }
        firstValue = 0; secondValue = 0;

        for (int i = 0; i < 6; i++) {
            tableRows[i] = new TableRow(this);
            table.addView(tableRows[i]);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f);
            layoutParams.setMargins(5, 5, 5, 5);

            if (i == 3) {
                for (int j =0; j < 4; j++) {
                    NumberButton button = new NumberButton(this);
                    button.set(units[j]); button.setLayoutParams(layoutParams);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (firstValue == 0 || secondValue == 0) {
                                Toast.makeText(MainActivity.this, "숫자 2개를 고른 뒤에 기호를 선택하세요.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (goal == 12) {
                                    Toast.makeText(MainActivity.this, "문제를 모두 푸셨습니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    calUnit = button.getCover();
                                    Toast.makeText(MainActivity.this, firstValue + String.valueOf(calUnit) + secondValue + "=" + playerChoice(), Toast.LENGTH_SHORT).show();
                                    refresh(buttons);
                                    tryCount++;
                                    numOfTry.setText("시도: " + tryCount);

                                    if (playerChoice() == targetNumber[goal]) {
                                        requst.correct();
                                        goal++;
                                        if (goal == 12) {
                                            requst.textView.setText("게임 종료");
                                            gameOver(buttons);
                                        } else {
                                            requst.set(targetNumber[goal]);
                                            requst.openCover();
                                            numOfGoal.setText("문제: " + String.valueOf(goal + 1) + "/12");
                                        }
                                    } else requst.wrong();
                                    firstValue = 0;
                                    secondValue = 0;
                                    openCount = 0;
                                }
                            }
                        }
                    });
                    buttons[i][j]=button;
                    tableRows[i].addView(buttons[i][j]);
                }
            }

            else if (i == 4) {
                layoutParams.setMargins(5, 5, 5, 5);
                requst.set(targetNumber[goal]); requst.setLayoutParams(layoutParams);
                requst.openCover(); requst.textView.setTextSize(70);
                tableRows[i].addView(requst);
            }

            else if (i == 5) {
                numOfTry.setTextSize(20);
                numOfTry.setText("시도: 0");
                numOfTry.setLayoutParams(layoutParams);
                tableRows[i].addView(numOfTry);

                numOfGoal.setTextSize(20);
                numOfGoal.setText("문제: 1/12");
                numOfTry.setLayoutParams(layoutParams);
                tableRows[i].addView(numOfGoal);
            }

            else {
                for (int j = 0; j < 4; j++) {
                    int ranValue = random.nextInt(12);
                    if(count[ranValue] > 0) {
                        j--;
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
                                if (button.getValue() == firstValue) {
                                    Toast.makeText(MainActivity.this, "첫번째와 다른 수를 골라주세요.", Toast.LENGTH_SHORT).show();
                                    openCount = 1;
                                }
                                else {
                                    secondValue = button.getValue();
                                    button.openCover();
                                }
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

    void refresh(NumberButton[][] question) {
        int getRow = question.length - 1;
        int getCol = question[0].length;
        for (int i = 0; i < getRow; i++) {
            for (int j = 0; j < getCol; j++) {
                question[i][j].closeCover();
            }
        }
    }

    void gameOver(NumberButton[][] question) {
        int getRow = question.length - 1;
        int getCol = question[0].length;
        for (int i = 0; i < getRow; i++) {
            for (int j = 0; j < getCol; j++) {
                question[i][j].openCover();
            }
        }
    }
}