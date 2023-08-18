# ticTacToe

## Creating a simple Tic Tac Toe android game application using Java

In the following blog, we'll be working to create a basic TicTacToe game for android platform. We'll be coding the game in Java, using Android Studio IDE. The tutorial has been divided into 9 steps, each step moving progressively on top of last one. <br>

#### Note :-<br> This tutorial assumes, you have a basic working knowledge of android studio and the framework in place.
<br>

![TicTacToe](http://i.imgur.com/n88Kibx.jpg)

### Step 1:
First, I have created images to be used for the application using GNU Image Manipulation Program, popularly known as GIMP. The game board, X's and 0's were created as part of these images. If you're doing the same, make sure the images are created as '.png' or '.jpg' format only. Otherwise, you may download copyright free images from the internet, which ever way works for you.

### Step 2:
After creating the necessary images, launch the Android Studio and open a new project with an empty activity. Once the gradle build finishes, continue with setting up the layout of the application. An empty project by default has a TextView set up within a Constraint Layout. So, go ahead and delete the Text View and pull in a grid view on top of the existing constraint layout. For the grid, add the rowCount and columnCount attributes of layout, as 3 and 3 respectively. Now move forward by setting up the tic tac toe board, on the grid layout, by placing the background attribute of the layout, as the board image created in the first step.

### Step 3:
By the beginning of step3, your application would be looking something like this.
<br>
![Layout](https://github.com/TheCyberian/ticTacToe/tree/master/app/src/main/res/drawable/board.png)
<br>
Now for each of the cell, to contain the image or X or 0, we need to add 9 ImageViews. One for each cell of the grid. So, go ahead and start placing ImageViews for each of the cells. You can place any of the image (either X or 0) for now, as we'll be removing them after the view is created and be placing them back in place programmatically. 

### Step 4:
After the ImageViews are in place, we'll need to add an onClick function for all the image views. This function will do the task of animating the X or O falling into the Grid cell which is being tapped by the user. This will be done by a simple piece of code, being added to MainActivity.java file, in app/main/java:

```
  public void dropIn(View view){

    ImageView counter = (ImageView) view;
    counter.setTranslationY(-1500);
    counter.setImageResource(R.drawable.X);
    counter.animate().translationYBy(1500).rotation(3600).setDuration(500);

  }
```

The above piece of code, takes care of the animated movement for the symbol X, but doesn't do anything close to, what is required for the game to work like we want it to, yet. 


### Step 5:
To make it work like the tic tac toe game, one of fundamental things is to be able to alternate between the symbols being used. We'll handle it by creating two players and assigning them each one of the symbols. The above code snippet changes to:

```
  int activePlayer = 0;

  public void dropIn(View view){

    ImageView counter = (ImageView) view;

    counter.setTranslationY(-1500);

    if(activePlayer == 1 ){
                  counter.setImageResource(R.drawable.x);
                  activePlayer = 0;
              }
              else{
                  counter.setImageResource(R.drawable.o);
                  activePlayer = 1;
              }

    counter.animate().translationYBy(1500).rotation(3600).setDuration(500);

  }
```

The above piece of code allows the user to alternate between X's(activePlayer=1) and O's(activePlayer=0) being placed on the grid.


### Step 6:
Now the difficult part, how do we keep track of the game's current state? Since, it's one of the easy games, we needn't look much further then using arrays. We are going to use a 1-D int array and tag attribute of the ImageView to achieve this. Assign each of the Imageview value of tag; begin with 0, at the top left corner; all the way to 8, at the bottom right corner. The tags values may look like below:

```
|0|1|2|
|3|4|5|
|6|7|8|
```

After this we can start with the coding, we add a `gameState` array, initialized with value 2 in all places(Since, we are using 0, 1, to identify the player, we can use 2 as neutral value) and a int variable to capture which of the cells were tapped by user, by checking the `activePlayer` variable.
So, now our code looks like below:

```
int activePlayer = 0;
int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

public void dropIn(View view){

	ImageView counter = (ImageView) view;
	int tappedCounter = Integer.parseInt(counter.getTag().toString());

	if(gameState[tappedCounter] == 2){	
		
		gameState[tappedCounter] = activePlayer;	
		counter.setTranslationY(-1500);
	
		if(activePlayer == 1 ){
                	counter.setImageResource(R.drawable.x);
	                activePlayer = 0;
        	    }
	            else{
        	        counter.setImageResource(R.drawable.o);
                	activePlayer = 1;
	            }

	counter.animate().translationYBy(1500).rotation(3600).setDuration(500);

	}
}
```

The above code, places Zeros and ones in the gameState integer array. It does so, by getting tag value of the tapped image view and replacing that index(tag value) in `gameState` array with activePlayer value.

Now we have a somewhat functioning application. We now need to add function to check for a winner when someone gets three in a row and to reset the game when someone has won.


### Step 7:
Since, we are able to track the current game state now, we need to figure out a way to know as soon as the current game state resembles to any winning situation. We have just a few possibilities (8, precisely) for a three in a row situation in a 3X3 matrix. We can use a 2-D array to handle this situation.

```
int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
```

Now, as we have all possible winning positions in the gameState, we can loop over each of the position array every time a `gameState` change occurs.

```
for(int[] winningPosition : winningPositions) {
	if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    String winner = "";
                    if (activePlayer == 0) {
                        winner = "X's";
                    } else {
                        winner = "O's";
                    }
                    Toast.makeText(this, winner + " are a winner", Toast.LENGTH_SHORT).show();
	}
}
```
Now, our application is in a position to see if someone has won and make a toast for it. But we still lack a few more things.

### Step 8: 
In this step, we'll add a reset button and a text view displaying the winner of the game. And in the step afterwards, we'll add the functionality to the reset button.<br>
We'll need to add a button to the layout for this and change the visibility of the button to Invisibile. Same process to be repeated for the Textview which is to be added. We'll be changing the visibility of these elements in the program. So, we need them to be invisible by default.<br>
If you're using Android Studio, you can change the values directly from the Preview. Otherwise, we can go to activity_main.xml and add the following:<br>

```
 <TextView
        android:id="@+id/winnerTextView"
        android:layout_width="wrap_content"
        android:layout_height="28dp"
        android:layout_marginStart="162dp"
        android:layout_marginLeft="162dp"
        android:layout_marginTop="50dp"
        android:layout_marginBottom="70dp"
        android:text="TextView"
        android:textSize="28sp"
        android:visibility="invisible"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/gridLayout" />

    <Button
        android:id="@+id/resetButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="60dp"
        android:layout_marginLeft="60dp"
        android:layout_marginTop="50dp"
        android:layout_marginEnd="162dp"
        android:layout_marginRight="162dp"
        android:layout_marginBottom="70dp"
        android:onClick="resetGame"
        android:text="reset"
        android:visibility="invisible"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/gridLayout" />
```

And the for loop in the previous step, now changes to be like below, changing the visibility of the elements after a winner is decided. Also, we have introduced a boolean value `gameActive` at class level, to let system know when the game's to be stopped:

```
            for(int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameActive = false;
                    String winner = "";
                    if (activePlayer == 0) {
                        winner = "X's";
                    } else {
                        winner = "O's";
                    }

                    Toast.makeText(this, winner + " are a winner", Toast.LENGTH_SHORT).show();

                    Button resetButton = (Button) findViewById(R.id.resetButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " the winner ! :)");

                    resetButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }

```

### Step 9:
Now as, we have added the `resetGame` function to `onClick` attribute of resetButton, we need to add that method to `MainActivity.java`. The method does a few things for us:
1. reset the visibility of elements(Button & a TextView) back to invisible.
2. remove the images from the grid/board.
3. reset the game to be active state, i.e., `activeState` bool value is reset to true.

```
public void resetGame(View view){

        Button resetButton =  findViewById(R.id.resetButton);
        TextView winnerTextView =  findViewById(R.id.winnerTextView);

        resetButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<grid.getChildCount(); i++){
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }

        activePlayer = 0;
        gameActive = true;

        for(int i=0; i<gameState.length;i++){
            gameState[i] = 2;
        }
    }

```

We are now good to go for packaging the program into an **".apk"** file.

*Keep coding! Have fun.*

The full code of the application is available at GitHub:
[Github/The-Cyberian](https://github.com/TheCyberian)
