package project.example.rvse

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker

class ActionModeController(private val tracker: SelectionTracker<Model>?): ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.action_mode_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.delete -> {

            }
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        tracker?.clearSelection()
    }
}
