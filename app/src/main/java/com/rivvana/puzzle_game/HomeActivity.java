package com.rivvana.puzzle_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private int emptyX = 2;
    private int emptyY = 2;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadViews();
        loadNomor();
        generateChars();
        loadDatatoViews();
    }

    private void loadDatatoViews(){
        emptyX=2;
        emptyY=2;
        for (int i=0;i<group.getChildCount()-1;i++){
            buttons[i/3][i%3].setText(String.valueOf(tiles[i]));
            buttons[i/3][i%3].setBackgroundResource(android.R.drawable.btn_default);
        }

        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this,R.color.colorFreeButton));
    }

    private char generateChars(){
        int n=9;
        Random random = new Random();
        while (n>1){
            int randomChar = random.nextInt(52);
            char c = (char) (random.nextInt(9)+'a');
            int temp = tiles[c];
            tiles[randomChar]=tiles[n];
            tiles[n] = temp;
        }
        if (!isSolvable())
            generateChars();

    }

    private boolean isSolvable(){
        int countInversions=0;
        for (int i=0; i<9; i++){
            for (int j=0; j<i; j++){
                if (tiles[j]>tiles[i])
                    countInversions++;
            }
        }
        return countInversions%2 ==0;
    }

    private void loadNomor(){
        tiles = new int[9];
        for (int i = 0; i<group.getChildCount()-1; i++){
            tiles[i] = i+1;
        }
    }

    private void loadViews(){
        group=findViewById(R.id.group);
        buttons = new Button[3][3];

        for (int i = 0; i<group.getChildCount(); i++){
            buttons[i/3][i%3] = (Button) group.getChildAt(i);
        }
    }

    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if ((Math.abs(emptyX-x)==1&&emptyY==y)||(Math.abs(emptyY-y)==1&&emptyX==x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFreeButton));
            emptyX=x;
            emptyY=y;
            checkWin();

        }

    }

    private void checkWin(){
        boolean isWIn = false;
        if (emptyX==2&&emptyY==2){
            for (int i = 0; i < group.getChildCount()-1; i++) {
                if (buttons[i/3][i%3].getText().toString().equals(String.valueOf(i+1))){
                    isWIn =true;
                }else {
                    isWIn = false;
                    break;
                }

            }
        }

        if (isWIn){
            Toast.makeText(this, "Selamat Kamu menang", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i/3][i%3].setClickable(false);
            }
        }

    }

}