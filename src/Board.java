import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class Board {
    //instance variables of the Board are the necessary buttons, labels, and panels
    private JButton restartButton;
    private JButton startGameButton;
    private JLabel clicksLabel;
    private JLabel guessesLabel;
    private JPanel labelView;
    private JLabel winLabel;

    public Board( JButton buttons[]){
        JFrame frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel(); //panel to hold the grid of buttons
        labelView = new JPanel(); //panel to hold the labels, and start/restart buttons
        buttonPanel.setLayout(new GridLayout(4,6, 2, 0)); //grid 4x6
        startGameButton = new JButton("Start!");
        restartButton = new JButton("Restart Game");
        clicksLabel = new JLabel("Clicks: 0");
        guessesLabel = new JLabel("Guesses: 0");
        winLabel = new JLabel("");
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton(new ImageIcon());
            buttonPanel.add(buttons[i]);
        }//instantiate each button with the default blank image and it to the panel
        labelView.setLayout(new GridLayout(1, 5, 2, 2)); //panel for the labels and start/restart buttons

        //add the buttons and labels to the panel
        labelView.add(startGameButton);
        labelView.add(clicksLabel);
        labelView.add(guessesLabel);
        labelView.add(winLabel);
        buttonPanel.setPreferredSize(new Dimension(400, 400));
        //add the panels to the frame
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(labelView, BorderLayout.NORTH);
        frame.setSize(700,700);
        frame.setVisible(true);
    }

    //set the images array to the 24 images used
    public void setImages(int numImages, ImageIcon images[]){
        int imageIndex = 0;
        for(int i = 0; i < numImages; i+=2){
            String imagePath = "src/pic" + imageIndex + ".jpg";
            images[i] = new ImageIcon(imagePath);
            images[i + 1] = new ImageIcon(imagePath);
            imageIndex++;
        }
        Collections.shuffle(Arrays.asList(images)); //shuffle the images array
        /* Found the above line to shuffle an array from the following url:
        https://www.programcreek.com/2012/02/java-method-to-shuffle-an-int-array-with-random-order/
         */
    }

    public JButton getRestartButton(){
        return restartButton; //get the restart button
    }
    public JButton getStartGameButton(){
        return startGameButton; //get the start button
    }
    public void resetBoard(){
        clicksLabel.setText("Clicks: 0");
        guessesLabel.setText("Guesses: 0"); //reset the labels on the board
        winLabel.setText("");
    }
    public void addRestartButton(){
        labelView.add(restartButton); //add the restart button to the board after the user hits the Start Game button
    }
    public void updateClicks(int clicks){
        clicksLabel.setText("Clicks: " + clicks); //update the clicks label
    }
    public void updateGuesses(int numGuesses){
        guessesLabel.setText("Guesses: " + numGuesses); //update the numGuesses label
    }
    public void userWon(){
        winLabel.setText("You won!!!");
    }
}
