import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicTacToe extends Application
{
    private Button[][] board = new Button[3][3];
    private boolean xTurn = true;

    @Override
    public void start(Stage stage)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Create buttons
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                Button button = new Button("");
                button.setPrefSize(100, 100);

                int r = row;
                int c = col;

                button.setOnAction(e -> handleMove(r, c));

                board[row][col] = button;
                grid.add(button, col, row);
            }
        }

        // Restart button
        Button restart = new Button("Restart");

        restart.setOnAction(e -> resetBoard());

        HBox bottom = new HBox(restart);
        bottom.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(bottom);

        Scene scene = new Scene(root, 320, 350);

        stage.setScene(scene);
        stage.show();
    }

    private void handleMove(int row, int col)
    {
        Button button = board[row][col];

        // Prevent overwriting
        if(!button.getText().equals(""))
        {
            return;
        }

        if(xTurn)
        {
            button.setText("X");
        }
        else
        {
            button.setText("O");
        }

        xTurn = !xTurn;

        // Check winner
        if(checkWinner())
        {
            String winner;

            if(xTurn)
            {
                winner = "O";
            }
            else
            {
                winner = "X";
            }

            showMessage(winner + " wins!");
            resetBoard();
        }
        else if(boardFull())
        {
            showMessage("It's a tie!");
            resetBoard();
        }
    }

    private boolean checkWinner()
    {
        // Rows
        for(int row = 0; row < 3; row++)
        {
            if(same(board[row][0], board[row][1], board[row][2]))
            {
                return true;
            }
        }

        // Columns
        for(int col = 0; col < 3; col++)
        {
            if(same(board[0][col], board[1][col], board[2][col]))
            {
                return true;
            }
        }

        // Diagonals
        if(same(board[0][0], board[1][1], board[2][2]))
        {
            return true;
        }

        if(same(board[0][2], board[1][1], board[2][0]))
        {
            return true;
        }

        return false;
    }

    private boolean same(Button b1, Button b2, Button b3)
    {
        return !b1.getText().equals("") &&
               b1.getText().equals(b2.getText()) &&
               b2.getText().equals(b3.getText());
    }

    private boolean boardFull()
    {
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                if(board[row][col].getText().equals(""))
                {
                    return false;
                }
            }
        }

        return true;
    }

    private void resetBoard()
    {
        for(int row = 0; row < 3; row++)
        {
            for(int col = 0; col < 3; col++)
            {
                board[row][col].setText("");
            }
        }

        xTurn = true;
    }

    private void showMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
