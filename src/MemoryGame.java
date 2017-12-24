/*
    Christopher Will
    CS335 Program 0
    9/12/2017
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

public class MemoryGame {
    private int clicks = 0; //the number of clicks for the current game
    private final int NUM_BUTTONS = 24;
    private final int GUESSES_TO_WIN = NUM_BUTTONS / 2; //user needs 12 correct guesses to win
    private int correctGuesses = 0;
    private int numGuesses = 0;
    private JButton buttons[] = new JButton[NUM_BUTTONS];
    private ImageIcon images[] = new ImageIcon[NUM_BUTTONS];
    private int firstPickIndex; //index of the the 1st button they clicked
    private int secondPickIndex; //index of the 2nd button they clicked
    private boolean wrongGuess = false; //keep track of whether there last guess was correct
    private Board gameBoard;

    private void resetButtons(){ //remove the old action listeners, add new ones, and set the button's icons to default
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setIcon(new ImageIcon());
            ActionListener[] listeners = buttons[i].getActionListeners();
            for(int j = 0; j < listeners.length; j++){
                buttons[i].removeActionListener(listeners[j]); //remove all the action listeners on each button
            }
            buttons[i].addActionListener(new ButtonListener());
        }
    }
    private void restartGame(){//user selected Restart Game so reset all the necessary variables to 0 or false
        correctGuesses = 0;
        numGuesses = 0;
        clicks = 0;
        wrongGuess = false;
        Collections.shuffle(Arrays.asList(images)); //shuffle the images again
        gameBoard.resetBoard(); //reset the board
        resetButtons(); // reset the buttons
    }
    private void activateStart(Board gameBoard){ //put an action listener on the start button
        gameBoard.getStartGameButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < buttons.length; i++){
                    buttons[i].addActionListener(new ButtonListener()); //add action listeners to the 24 buttons
                }
                gameBoard.getStartGameButton().removeActionListener(this); //user may only press the start label once
                gameBoard.addRestartButton(); //now that the user has started they have the option to restart, so add that button
            }
        });
    }
    private MemoryGame(){
        gameBoard = new Board(buttons); //create a board using the buttons array
        gameBoard.setImages(NUM_BUTTONS, images); //set the 24 images
        gameBoard.getRestartButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        }); //add an action listener to the restart button that calls a function to restart the game
        activateStart(gameBoard);
    } /*End MemoryGame*/
    private void resetIcons(){//users last guess was wrong so set the image of those 2 buttons to the default image icon
        if(wrongGuess && (clicks % 2 == 1)){
            buttons[firstPickIndex].setIcon(new ImageIcon());
            buttons[secondPickIndex].setIcon(new ImageIcon());
        }
    }
    private void removeListeners(int index){ //remove the action listeners from the button at index
        ActionListener[] listeners = buttons[index].getActionListeners();
        for(int i = 0; i < listeners.length; i++){
            buttons[index].removeActionListener(listeners[i]);
        }
    }
    private void imagesMatch(){ //images were a match so remove their action listeners
        removeListeners(firstPickIndex);
        removeListeners(secondPickIndex);
        wrongGuess = false;
        correctGuesses++;
        if(correctGuesses == GUESSES_TO_WIN){ //user finally won the game so display a nice congratulations message to them
            gameBoard.userWon();
        }
    }
    private void imagesDontMatch(){ //images didn't match so add the action listeners back to those 2 buttons
        buttons[firstPickIndex].addActionListener(new ButtonListener());
        buttons[secondPickIndex].addActionListener(new ButtonListener());
        wrongGuess = true;
    }
    private class ButtonListener implements ActionListener{ //event handler for the JButtons
        private void findPick(ActionEvent e){//find which button the user clicked and reveal its image
            for(int i = 0; i < NUM_BUTTONS; i++){
                if(e.getSource() == buttons[i]){
                    buttons[i].setIcon(images[i]);
                    removeListeners(i); //button can't be clicked again while we're waiting for the user to click another button
                    if(clicks % 2 == 1){
                        firstPickIndex = i; //save the index of the image the user clicked for their 1st guess
                    }else{
                        secondPickIndex = i; //save the index of the image the user clicked for their 2nd guess
                    }
                }
            }
        }
        public void actionPerformed(ActionEvent e){ //when the user clicks a button
            clicks++;
            gameBoard.updateClicks(clicks); //increment the number of clicks and update the board
            resetIcons();
            findPick(e); //reset the icons and find which button was clicked
            if(clicks % 2 == 0){ //this was the user's 2nd click
                numGuesses++;
                gameBoard.updateGuesses(numGuesses); //increment the number of guesses and update this on the board
                if(images[firstPickIndex].getImage() == images[secondPickIndex].getImage()){
                    imagesMatch(); // the images matched
                }else{
                    imagesDontMatch(); //the images didn't match
                }
            }
        }
    }/* end ButtonListener */
    public static void main(String[] args) {
        new MemoryGame(); //this plays the game
    }
}

