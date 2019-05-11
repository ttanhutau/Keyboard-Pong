package com.example.pongkeyboard;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;


class PongView extends View implements  View.OnTouchListener{
        private int xB;
        private int yB;
        private int width;
        private int height;
        private int vBx = 10;
        private int vBy = 10;

        private int xP;
        private int yP;

        private int xp_save;

        private int xP1;
        private int yP1;

        private int[] x_letters;
        private int[] y_letters;

        private String result = "";
        private InputConnection in;

        final private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int rayon_ball;
        final int bar_size;

    PongKeyboard keyboard = new PongKeyboard();



    private int memory_count = 0;
        private int count = 0;





        private Paint paint =  new Paint();
        private Paint paint_letters =  new Paint();
        private Paint paint_result =  new Paint();


        public PongView(Context context, InputConnection ic) {
            super(context);
            this.in = ic;
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(Color.RED);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            this.xB = size.x / 2;
            this.yB = size.y / 3;
            this.width = size.x;
            this.height = size.y;



            rayon_ball= this.width / 10;
            bar_size = this.width / 3;

            this.xP= size.x /2 ;
            this.yP = size.y - 300;


            this.xP1 = this.xP + bar_size;

            this.xp_save = this.xP;

            this.yP1 = this.yP + 30;
            int height_dix = this.height / 10;
            int width_dix = this.width / 10;

            this.x_letters = new int[] {/*right*/ this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8, this.width / 8,
                    /*top*/ this.width - width_dix * 7, this.width - width_dix * 6, this.width - width_dix * 5, this.width - width_dix * 4, this.width - width_dix * 3, this.width - width_dix * 2,
                    /*left*/ this.width - width_dix,  this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix, this.width -width_dix};
            this.y_letters = new int[] {/*right*/this.height, this.height - height_dix * 2, this.height - height_dix * 3, this.height - height_dix * 4, this.height - height_dix * 5, this.height - height_dix * 6, this.height - height_dix * 7, this.height - height_dix * 8, this.height - height_dix * 9, this.height - height_dix * 10,
                    /*top*/ height_dix , height_dix , height_dix , height_dix ,height_dix , height_dix ,
                    /*left*/ this.height, this.height - height_dix * 2, this.height - height_dix * 3, this.height - height_dix * 4, this.height - height_dix * 5, this.height - height_dix * 6, this.height - height_dix * 7, this.height - height_dix * 8, this.height - height_dix * 9, this.height - height_dix * 10 - 50  };




            this.paint_letters.setStyle(Paint.Style.FILL);
            this.paint_letters.setColor(Color.BLACK);

            this.paint_letters.setTextSize(50);

            this.paint_result.setStyle(Paint.Style.FILL);
            this.paint_result.setColor(Color.GREEN);
            this.paint_result.setTextSize(50);





            this.setOnTouchListener(this);

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getX() > 0 && event.getX() + bar_size < this.width){
                this.xP= (int) event.getX();
                this.xP1 = (int) event.getX() + bar_size;
                if(event.getAction() ==  MotionEvent.ACTION_UP){
                    this.xp_save = this.xP;

                }
                if(event.getAction() ==  MotionEvent.ACTION_BUTTON_PRESS){
                    if(  this.xP < event.getX() && event.getX() < this.xP1 && this.yP < event.getY() && event.getY() < this.yP1){
                        this.in.sendKeyEvent(new KeyEvent(KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_SPACE));
                        Toast.makeText(getContext(), "you ve lost in "+ this.count + " coups ", Toast.LENGTH_SHORT).show();


                    }
                }


            }


            return true;
        }


        @Override
        public void onDraw(Canvas canvas) {


            canvas.drawCircle(this.xB, this.yB, (float) 30.0, this.paint);
            canvas.drawRect(this.xP - 30, this.yP+rayon_ball,   this.xP1 ,this.yP1, this.paint);
            for(int i = 0; i < this.alphabet.length(); i++) {
                canvas.drawText(Character.toString(alphabet.charAt(i)), (float) this.x_letters[i],  (float) this.y_letters[i], this.paint_letters);
            }

            canvas.drawText(this.result, (float) this.width / 2- 200, (float) this.height / 2 - 200,this.paint_result );
            changePosition(canvas);

        }




        public void changePosition(Canvas canvas){
            // recalculer position

            this.xB += this.vBx;
            this.yB += this.vBy;
            if(xB > this.width - 30  ||  xB < 0 + 30){
                this.vBx *= -1;


            }

            if(yB > this.height - 30  || yB < 0 + 30){
                this.vBy *= -1;

            }
            if(yB > this.height - 30){
                Toast.makeText(getContext(), "you ve lost in "+ this.count + " coups ", Toast.LENGTH_SHORT).show();
                this.in.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                keyboard.onCreateInputView();
                return;



            }
            if(this.xB + 30 > this.xP && this.xB - 30 < this.xP1){
                if(this.yB > this.yP && this.yB < this.yP1) {
                    this.count++;
                    this.vBy = - Math.abs(vBy);
                    if(this.xp_save != this.xP){

                        this.vBx = (this.xP - this.xp_save) / 10;



                    }


                }

            }
            for(int i = 0; i < this.alphabet.length(); i++) {

                if (this.xB + 30 > this.x_letters[i] && this.xB - 30 < this.x_letters[i]){
                    if (this.yB + 30 > this.y_letters[i] && this.yB - 30 < this.y_letters[i]) {
                        if(this.vBy < 0){
                            if(this.count > this.memory_count){

                                this.result += this.alphabet.charAt(i);
                                this.memory_count = this.count;

                                //each time it select a letter it write it down in the textfield
                                   canvas.drawText(Character.toString(alphabet.charAt(i)), (float) this.x_letters[i],  (float) this.y_letters[i], this.paint_letters);

                                in.commitText(String.valueOf(this.alphabet.charAt(i)),1);
                            }
                        }


                    }

                }
            }





            invalidate();



        }







    }


