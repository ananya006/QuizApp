package com.example.myquiz;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView scoreTextView, timeTextView, questionTextView;
    private Button button1, button2, button3, button4;
    private TextView resultTextView;
    private Button playButton;

    private LinearLayout linearlayout;
    private GridLayout gridlayout;

    private int correctQuestion;
    private int totalQuestions;
    private int locationOfCorrectAnswer;

    ArrayList<Integer> answer=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        scoreTextView=findViewById(R.id.score_text_view);
        timeTextView=findViewById(R.id.time_text_view);
        questionTextView=findViewById(R.id.question_text_view);

        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);
        button3=findViewById(R.id.button_3);
        button4=findViewById(R.id.button_4);

        resultTextView=findViewById(R.id.answer_text_view);
        playButton=findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        linearlayout=findViewById(R.id.linear_layout);
        gridlayout=findViewById(R.id.grid_layout);

    }

    private void startQuiz(){
        timeTextView.setText("30s");
        correctQuestion=0;
        totalQuestions=0;
        gridlayout.setVisibility(View.VISIBLE);
        linearlayout.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.INVISIBLE);
        scoreTextView.setText("0/0");

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeTextView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                timeTextView.setText("0s");
                gridlayout.setVisibility(View.INVISIBLE);
                linearlayout.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);
                playButton.setText("Play Again");
                showScore();
            }
        }.start();

        createQuestion();
    }

    private void createQuestion(){

        answer.clear();
        Random random=new Random();

        int a=random.nextInt(21);
        int b=random.nextInt(21);

        locationOfCorrectAnswer=random.nextInt(4);

        for (int i=0;i<4;i++){
            if (i==locationOfCorrectAnswer){
                answer.add(a+b);
            }
            else{
                int wrongAnswer=random.nextInt(41);
                while(wrongAnswer==a+b){
                    wrongAnswer=random.nextInt(41);
                }
                answer.add(wrongAnswer);
            }
        }

        String question=String.valueOf(a)+"+"+String.valueOf(b);
        questionTextView.setText(question);

        button1.setText(String.valueOf(answer.get(0)));
        button2.setText(String.valueOf(answer.get(1)));
        button3.setText(String.valueOf(answer.get(2)));
        button4.setText(String.valueOf(answer.get(3)));

    }

    public void checkAnswer(View view){
        if (view.getTag().toString().equals(String.valueOf(locationOfCorrectAnswer))){
            correctQuestion++;
            resultTextView.setText("Correct Answer");
        }
        else {
            resultTextView.setText("Wrong Answer");
        }
        totalQuestions++;
        scoreTextView.setText(String.valueOf(correctQuestion)+"/"+String.valueOf(totalQuestions));

        createQuestion();
    }

    private void showScore(){
        resultTextView.setText("Score = "+String.valueOf(correctQuestion)+"/"+String.valueOf(totalQuestions));
    }
}
