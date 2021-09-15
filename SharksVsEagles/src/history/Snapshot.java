package history;

import commands.Command;
import model.interfaces.Board;
import model.interfaces.GameEngine;

import java.io.*;
import java.util.Base64;

public class Snapshot {

    private History history;
    private GameEngine gameEngine;

    public Snapshot(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        history = new History();
    }

    public void execute(Command command){
        history.push(command, new Memento(this));
    }

    public void undo(){
        if(history.undo()){
            gameEngine.undoMoveCallback();
        }
    }

    public String backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(gameEngine.getBoard());
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch(IOException e) {
            System.err.println("IOException occurred.");
            return "";
        }
    }

    public void restore(String state){
        try {
            byte[] data = Base64.getDecoder().decode(state);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Board boardBackup = (Board) ois.readObject();
            gameEngine.setBoard(boardBackup);
            ois.close();
        } catch(ClassNotFoundException e) {
            System.err.println("ClassNotFoundException occurred.");
        } catch(IOException e) {
            System.err.println("IOException occurred.");
        }
    }
}
