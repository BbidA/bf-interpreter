package util;

import javafx.scene.control.TextArea;

import java.util.LinkedList;

/**
 * Created by liao on 2017/6/11.
 */
public class RedoUndo {

    LinkedList<String> redoList;
    LinkedList<String> undoList;
    TextArea targetArea;

    public RedoUndo(TextArea targetArea) {
        this.targetArea = targetArea;

        redoList = new LinkedList<>();
        undoList = new LinkedList<>();
        targetArea.setOnKeyPressed(event -> {
            undoList.add(targetArea.getText());
            redoList.clear();
        });
    }

    public void redo() {
        if (canRedo()) {
            undoList.add(targetArea.getText());
            targetArea.setText(redoList.removeLast());
        }
    }

    public void undo() {
        if (canUndo()) {
            redoList.add(targetArea.getText());
            targetArea.setText(undoList.removeLast());
        }
    }

    private boolean canRedo() {
        return !redoList.isEmpty();
    }

    private boolean canUndo() {
        return !undoList.isEmpty();
    }
}
