package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    private int[][] visible = new int[10][10];
    private int[][] invisible = new int[10][10];
    Scanner playerInput = new Scanner(System.in);

  public void startGame(){
    // Setting up field at game start
    System.out.println("    ");
    System.out.println("MINESWEEPER GAME ");
    setupField();

    boolean isTimeToMove = true;
    while(isTimeToMove){
      // prints if player is eligible to move based on win/lose conditions
      printVisible();
      isTimeToMove = playerMove();
      if(checkIfWon()){
        printInvisible();
        System.out.println("CONGRATULATIONS YOU WON!");
        break;
      }
    }
    
  }

  public void printInvisible() {
    
    System.out.print("\t ");

    for(int i = 0; i < 10; i++){
      System.out.print(" " + i + " ");
    }
    System.out.println(" ");
    
    for(int i = 0; i < 10; i++){
      System.out.print(i + "\t |");
      for(int j = 0; j < 10; j++){
        if(invisible[i][j] == 0) {
          System.out.print(" ");
        }
        // Prints bombs at dummy '100' locations
        else if (invisible[i][j] == 100){
          System.out.print("X");
          
        }
        else{
          System.out.print(invisible[i][j]);
        }
        System.out.print(" | ");
      }
      System.out.println("");
    }
  }

  public void printVisible() {
    System.out.print("\t ");
   for(int i = 0; i < 10; i++){
    System.out.print(" " + i + "  ");
   }
   System.out.print("\n ");
   for(int i = 0; i < 10 ; i++){
    System.out.print(i + "\t| ");
    for(int j = 0; j < 10; j++){
      // Checks if the field cell has been selected
      if(visible[i][j] == 0){
        System.out.print("?");
      }
      // checks to see if cell has been selected before, if so sets it to be empty
      else if(visible[i][j] == 50){
        System.out.print(" ");
      }
      else{
        System.out.print(visible[i][j]);
      }
      System.out.print(" | ");
    }
    System.out.print("\n");
   }
  }

  // Setup field with 2d arrays and place amount of bombs based
  // on user input
  public void setupField(){
    System.out.println("How many bombs would you like? MAX = 10");
    
    int bombAmount = playerInput.nextInt();

    if (bombAmount > 10){
      System.out.println("Can't have more than 10 bombs, setting bombs to 10");
      bombAmount = 10;
    }

    int i = 0;
    // Loops through randomly assigning bombs to grid locations based on bomb amount from user. setting to 100 as a dummy value meaning a bomb
    while (i != bombAmount) {
      Random random = new Random();
      int rowRandom = random.nextInt(10);
      int columnRandom = random.nextInt(10);
      System.out.println("Bomb located at: row: " + rowRandom + " column: " + columnRandom);
      invisible[rowRandom][columnRandom] = 100;
      i++;
    }
    createInvisible();
   
  }

  public void createInvisible() {
    // creating rows
    for (int i=0; i < 10; i++) {
      // creating columns
      for(int j=0; j < 10; j++){
        int count = 0;
        if (invisible[i][j] != 100){
          //cycles through all in the rows
          if(i != 0){
            // cycles through rows looking for dummy bomb value of 100, has limitations for far left and right rows as not to check outside of array.
            if(invisible[i-1][j] == 100){
              count++;
            }
            if(j != 0){
              if(invisible[i-1][j -1] == 100){
                count++;
              }
            }
          }
          
          if(i != 9){
            if(invisible[i + 1][j] == 100) {
              count++;
            }
            if(j !=9){
              if(invisible[i + 1][j + 1] == 100){
                count++;
              }
            }
          }
          // cycles through columns looking for dummy bomb value of 100, has limitations for top and bottom columns as not to check outside of array.
          if(j != 0){
            if(invisible[i][j-1] == 100){
              count++;
            }
            if(i !=9){
              if(invisible[i+1][j-1] == 100){
                count++;
              }
            }
          }
          if(j != 9){
            if(invisible[i][j + 1] == 100){
              count++;
            }
            if(i != 0){
              if(invisible[i-1][j + 1] == 100){
                count++;
              }
            }
          }
          invisible[i][j] = count;
        }
        
      }
    }
  }

  
 
  public boolean playerMove(){

    
    System.out.println("Enter row #");
    int row = playerInput.nextInt();
    
    System.out.println("Enter column #");
    int column = playerInput.nextInt();
    
    
    // checks to see if player input is within the bounds of the game grid
    if(row < 0 || row > 9 || column < 0 || column > 9 || visible[row][column]!=0){
      System.out.println("INVALID INPUT");
      
      return true;
      
    }
    // checks if player input is the dummy value of the mine
    if(invisible[row][column] == 100){
      printInvisible();
      System.out.println("GAME OVER! YOU STEPPED ON A MINE");
      return false;
    }
    // checks if the player input is in a cell without a bomb
    else if(invisible[row][column] == 0){
      updateVisible(row, column);
      
    }
    else{
      updateAdjacent(row, column);
      
    }
    
    return true;
    
  }

  public void updateVisible(int i, int j){
    visible[i][j] = 50;

    if(i != 0){
      // Changes cell values to 50 if surrounding cells also dont contain a bomb/count
      visible[i - 1][j] = invisible[i - 1][j];
      if(visible[i - 1][j] == 0){
        visible[i-1][j] = 50;
      }
      if(j != 0){
        visible[i - 1][j - 1] = invisible[i - 1][j - 1];
        if(visible[i - 1][j - 1] == 0){
          visible[i - 1][j - 1] = 50;
        }
      }
    }

    if(i != 9){
      visible[i+1][j]=invisible[i+1][j];
      if(visible[i + 1][j]==0) {
        visible[i + 1][j] = 50;
      };
      if(j != 9){
        visible[i + 1][j + 1]= invisible[i+1][j+1];
         if(visible[i + 1][j + 1]==0){
          visible[i + 1][j + 1] = 50;
         } 
      }
    }
    
    if(j != 0){
    visible[i][j - 1]=invisible[i][j-1];
    if(visible[i][j - 1]==0) visible[i][j-1] = 50;
    if(i != 9)
    {
        visible[i + 1][j - 1]=invisible[i + 1][j - 1];
        if(visible[i + 1][j - 1]==0) visible[i + 1][j - 1] = 50;
    }
    }

    if(j!=9){
      visible[i][j+1]=invisible[i][j+1];
      if(visible[i][j+1]==0) visible[i][j+1] = 50;
      if(i!=0)
      {
          visible[i-1][j+1]=invisible[i-1][j+1];
          if(visible[i-1][j+1]==0) visible[i-1][j+1] = 50;
      }
    }

  }

  public void updateAdjacent(int i, int j) {
    Random random = new Random();
    int x = random.nextInt() % 4;

    visible[i][j] = invisible[i][j];
    // Updates adjacent cells if the bomb isnt next to the current cell in check
    if(x==0){
      if(i!=0){
        if(invisible[i-1][j]!=100)
        {
            visible[i-1][j] = invisible[i-1][j];
            if(visible[i-1][j]==0)  visible[i-1][j] = 50;
        }
      }
      if(j!=0){
        if(invisible[i][j-1]!=100)
        {
            visible[i][j-1] = invisible[i][j-1];
            if(visible[i][j-1]==0)  visible[i][j-1] = 50;
        }

      }
      if(i!=0 && j!=0){
        if(invisible[i-1][j-1]!=100)
        {
            visible[i-1][j-1] = invisible[i-1][j-1];
            if(visible[i-1][j-1]==0)  visible[i-1][j-1] = 50;
        }

      }
    }
    else if(x==1){
      if(i!=0){
        if(invisible[i-1][j]!=100)
        {
            visible[i-1][j] = invisible[i-1][j];
            if(visible[i-1][j]==0)  visible[i-1][j] = 50;
        }
      }
      if(j!=9){
        if(invisible[i][j+1]!=100)
        {
            visible[i][j+1] = invisible[i][j+1];
            if(visible[i][j+1]==0)  visible[i][j+1] = 50;
        }

      }
      if(i!=0 && j!=9){
        if(invisible[i-1][j+1]!=100){
            visible[i-1][j+1] = invisible[i-1][j+1];
            if(visible[i-1][j+1]==0)  visible[i-1][j+1] = 50;
        }
      }
    }
    else if(x==2){
      if(i!=9){
        if(invisible[i+1][j]!=100)
        {
            visible[i+1][j] = invisible[i+1][j];
            if(visible[i+1][j]==0)  visible[i+1][j] = 50;
        }
      }
      if(j!=9){
        if(invisible[i][j+1]!=100)
        {
            visible[i][j+1] = invisible[i][j+1];
            if(visible[i][j+1]==0)  visible[i][j+1] = 50;
        }

      }
      if(i!=9 && j!=9){
        if(invisible[i+1][j+1]!=100){
            visible[i+1][j+1] = invisible[i+1][j+1];
            if(visible[i+1][j+1]==0)  visible[i+1][j+1] = 50;
        }
      }
    }
    else
    {
      if(i!=9){
        if(invisible[i+1][j]!=100)
        {
            visible[i+1][j] = invisible[i+1][j];
            if(visible[i+1][j]==0)  visible[i+1][j] = 50;
        }
      }
      if(j!=0){
        if(invisible[i][j-1]!=100){
            visible[i][j-1] = invisible[i][j-1];
            if(visible[i][j-1]==0)  visible[i][j-1] = 50;
        }

      }
      if(i!=9 && j!=0){
        if(invisible[i+1][j-1]!=100){
            visible[i+1][j-1] = invisible[i+1][j-1];
            if(visible[i+1][j-1]==0)  visible[i+1][j-1] = 50;
        }
      }
    }
  }

  public boolean checkIfWon(){
    for(int i = 0; i < 10; i++){

      for(int j = 0; j < 10; j++){
        // Checking to see if player has turned all the tile values meaning that the board has been cleared
        if(visible[i][j] == 0){
          if(invisible[i][j] != 100){
            return false;
          }
        }
      }

    }
    return true;
  }

  public static void main(String[] args) {
    MineSweeper game = new MineSweeper();
    game.startGame();
    
  }
  
}
