package ui;

import javafx.scene.control.TextArea;
import util.RedoUndo;


/**
 * Created by liao on 2017/6/12.
 */
public class CodeEditArea extends TextArea {

    private RedoUndo redoUndo;

    public CodeEditArea(String initString) {
        super(initString);
        redoUndo = new RedoUndo(this);
        setWrapText(true);
        setStyle("-fx-control-inner-background:#2b2b2b; " +
                "-fx-font-family: Consolas; -fx-highlight-fill: #3af2ff; -fx-font-size: 25px;" +
                "-fx-highlight-text-fill: #323232; -fx-text-fill: #3af2ff;");
    }

    public void myRedo() {
        this.redo();
        redoUndo.redo();
    }

    public void myUndo() {
        redoUndo.undo();
    }
}
