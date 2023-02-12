package project.example.rvse

import android.annotation.SuppressLint
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
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("NotifyDataSetChanged")
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

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Model>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                Log.d("tracker", "Начало работы метода")
                if (tracker.hasSelection() && actionMode == null) {
                    Log.d("tracker", "Начало работы ActionMode")
                    actionMode = startSupportActionMode(ActionModeController(tracker))
                    setSelectedTitle(tracker.selection.size())
                } else if(!tracker.hasSelection()) {
                    Log.d("tracker", "Трекер закончил работу")
                    actionMode?.finish()
                    actionMode = null
                } else {
                    Log.d("tracker", "Выделение элемента или наборот")
                    setSelectedTitle(tracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Выбрано: $selected"
    }

}
