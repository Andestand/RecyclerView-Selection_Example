package project.example.rvse

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val adapter = Adapter()
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)

        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = adapter

        val tracker = SelectionTracker.Builder(
            "trackerID",
            recyclerView,
            ModelKeyProvider(adapter.array),
            MyDetailsLookup(recyclerView),
            StorageStrategy.createParcelableStorage(Model::class.java)
        ).build()
        adapter.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Model>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                Log.d("tag", "Начало работы метода")
                if (tracker.hasSelection() && actionMode == null) {
                    Log.d("tag", "Первое условие")
                    actionMode = startSupportActionMode(ActionModeController(tracker))
                    setSelectedTitle(tracker.selection.size())
                } else if(!tracker.hasSelection()) {
                    Log.d("tag", "Трекер закончил работу")
                    actionMode?.finish()
                    actionMode = null
                } else {
                    Log.d("tag", "Третье условие")
                    setSelectedTitle(tracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Выбрано: $selected"
    }

}
