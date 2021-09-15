package history;

import commands.Command;

import java.util.ArrayList;
import java.util.List;

public class History {

    private static int NUMBER_MOVES_TO_SAVE = 6;
    private List<Pair> history = new ArrayList<>();
    private int virtualSize = 0;

    private class Pair {
        Command command;
        Memento memento;
        Pair(Command command, Memento memento){
            this.command = command;
            this.memento = memento;
        }

        private Memento getMemento() {
            return memento;
        }
    }

    /**
     * Pushes a game state to a history list
     * @post.condition will only allow the history list to have at most 6 items
     * @param command
     * @param memento
     */
    public void push(Command command, Memento memento) {
        if(virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        // Save space by only storing the last 6 moves
        if(history.size() > NUMBER_MOVES_TO_SAVE){
            history.remove(0);
        }
        history.add(new Pair(command, memento));
        virtualSize = history.size();
    }

    public boolean undo() {
        Pair pair = getUndo();
        if(pair == null){
            return false;
        }
        pair.getMemento().restore();
        return true;
    }

    private Pair getUndo() {
        if(virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize -1);
        return history.get(virtualSize);
    }
}
