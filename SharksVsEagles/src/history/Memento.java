package history;

public class Memento {

    private String backup;
    private Snapshot snapshot;

    public Memento(Snapshot snapshot){
        this.snapshot = snapshot;
        this.backup = snapshot.backup();
    }

    public void restore() {
        snapshot.restore(backup);
    }
}
