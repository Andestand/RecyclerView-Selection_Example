package project.example.rvse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import project.example.rvse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val adapter = Adapter()
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {

        binding.recyclerview.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerview.adapter = adapter

        val tracker = SelectionTracker.Builder(
            "trackerID",
            binding.recyclerview,
            ModelKeyProvider(adapter.array),
            MyDetailsLookup(binding.recyclerview),
            StorageStrategy.createParcelableStorage(Model::class.java)
        ).build()
        adapter.tracker = tracker



        /*tracker.addObserver(object : SelectionTracker.SelectionObserver<Model>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                Log.d("tracker", "Начало работы метода")
                /*if (actionMode != null) {
                    actionMode = null
                }*/
                if (tracker.hasSelection() && actionMode == null) {
                    actionMode = startSupportActionMode(ActionModeController(tracker))
                    setSelectedTitle(tracker.selection.size())
                    Log.d("tracker", "Начало работы ActionMode")
                } else if(tracker.hasSelection() || /*!tracker.hasSelection()*/ /*&&*/ !tracker.clearSelection() && actionMode != null) {
                    //actionMode?.finish()
                    setSelectedTitle(tracker.selection.size())
                    actionMode = null
                    Log.d("tracker", "Трекер закончил работу")
                } else {
                    setSelectedTitle(tracker.selection.size())
                    Log.d("tracker", "Выделение элемента или наборот")
                }

            }
        })*/

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Model>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (tracker.hasSelection() && actionMode == null) {
                    actionMode = startSupportActionMode(ActionModeController(tracker))
                    setSelectedTitle(tracker.selection.size())
                    Log.d("tracker", "Начало работы ActionMode")
                } else if (!tracker.hasSelection() && !ActionModeController(tracker).isActionMode) {
                    setSelectedTitle(tracker.selection.size())
                    actionMode = null

                    Log.d("tracker", "Трекер закончил работу")
                } else {
                    setSelectedTitle(tracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Выбрано: $selected"
        Log.d("setSelectedTitle", "Выбрано: $selected")
    }
}
