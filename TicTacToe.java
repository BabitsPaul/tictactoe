import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe
{
    public static void main(String[] args)
    {
        new TicTacToe(3);
    }

    //ui
    public JButton[] buttons;

    public JFrame frame;

    //model
    public int dimension;

    public String[] state;

    public int move = 0;

    public TicTacToe(int dimension)
    {
        this.dimension = dimension;

        createView();
        createController();
        createModel();
    }

    //view

    public void createView()
    {
        frame = new JFrame("TicTacToe");

        frame.setLayout(new GridLayout(dimension, dimension));

        buttons = new JButton[dimension * dimension];
        for(int i = 0; i < dimension * dimension; i++)
        {
            buttons[i] = new JButton();
            frame.add(buttons[i]);
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    public void updateUI()
    {
        for(int i = 0; i < buttons.length; i++)
            buttons[i].setText(state[i]);

        frame.revalidate();
        frame.repaint();
    }

    public void resetUI()
    {
        for(int i = 0; i < buttons.length; i++)
            buttons[i].setText("");

        frame.revalidate();
        frame.repaint();
    }

    //model

    public void createModel()
    {
        state = new String[dimension * dimension];
        for(int i = 0; i < state.length; i++)
            state[i] = "";
    }

    public void resetModel()
    {
        //just recreate the model
        createModel();

        //reset move counter
        move = 0;
    }

    public void update(int index)
    {
        //dont update used slots
        if(!state[index].equals(""))
            return;

        state[index] = getPlayerToken();

        //update the UI
        updateUI();

        checkWin();
    }

    public String getPlayerToken()
    {
        String token;

        if(move % 2 == 0)
            token = "X";
        else
            token = "Y";

        move++;

        return token;
    }

    public void checkWin()
    {
        boolean won = false;

        //check for rows
        for(int i = 0; i < dimension && !won; i++)
        {
            int xCount = 0,
                    yCount = 0;

            for(int j = 0; j < dimension; j++)
                if(state[i * dimension + j].equals("X"))
                    xCount++;
                else if(state[i * dimension + j].equals("Y"))
                    yCount++;

            if(yCount == dimension || xCount == dimension)
                won = true;
        }

        //check columns
        for(int i = 0; i < dimension && !won; i++)
        {
            int xCount = 0,
                    yCount = 0;

            for(int j = 0; j < dimension;  j++)
                if(state[i + j * dimension].equals("X"))
                    xCount++;
                else if(state[i + j * dimension].equals("Y"))
                    yCount++;

            if(yCount == 3 || xCount == dimension)
                won = true;
        }

        //check diagonals
        int xCountDiag1 = 0,
                yCountDiag1 = 0,
                xCountDiag2 = 0,
                yCountDiag2 = 0;

        for(int i = 0; i < dimension && !won; i++)
        {
            if(state[i + i * dimension].equals("X"))
                xCountDiag1++;
            else if(state[i + i * dimension].equals("X"))
                yCountDiag1++;

            if(state[(dimension - i - 1) + i * dimension].equals("X"))
                xCountDiag2++;
            else if(state[(dimension - i - 1) + i * dimension].equals("Y"))
                yCountDiag2++;

            if(yCountDiag1 == 3 || xCountDiag1 == dimension)
                won = true;
            if(yCountDiag2 == 3 || xCountDiag2 == dimension)
                won = true;
        }

        if(won)
        {
            resetModel();
            resetUI();
        }
    }

    //controller

    public void createController()
    {
        for(int i = 0; i < buttons.length; i++)
            buttons[i].addActionListener(new Controller(i));
    }

    class Controller
        implements ActionListener
    {
        public int index;

        public Controller(int index)
        {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //interface to model, update the value in the state
            update(index);
        }
    }
}