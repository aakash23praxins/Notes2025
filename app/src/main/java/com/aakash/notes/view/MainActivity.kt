package com.aakash.notes.view

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.aakash.notes.databinding.ActivityMainBinding
import com.aakash.notes.model.data.Notes
import com.aakash.notes.view.adapter.NotesAdapter
import com.aakash.notes.viewmodel.MyApplication
import com.aakash.notes.viewmodel.NotesViewModel
import com.aakash.notes.viewmodel.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter
    private var dataList = listOf<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val factory = NotesViewModelFactory((application as MyApplication).repository)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        viewModel.getAllData.observe(this) { notesList ->
            adapter.setData(notesList)
            dataList = notesList
        }

        viewModel.getCountData.observe(this) { data ->
            if (data == 0) {
                binding.imgNoData.visibility = View.VISIBLE
            } else {
                binding.imgNoData.visibility = View.GONE
            }
        }



        binding.edtSearchNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println(p0)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
                filterData(p0.toString())
            }
        })

        binding.addDatFab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        adapter = NotesAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 0)
            }
        })

        ItemTouchHelper(object : SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteNotes(adapter.getPositionDataDelete(viewHolder.adapterPosition))
                Toast.makeText(applicationContext, "Deleted!!!", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(binding.recyclerView)

    }

    private fun filterData(data: String) {
        val filteredList = mutableListOf<Notes>()
        for (i in dataList) {
            if (i.nTitle.contains(data ?: "", ignoreCase = true) || i.nDesc.contains(
                    data ?: "",
                    true
                )
            ) {
                filteredList.add(i)
                binding.imgNoData.visibility = View.GONE
            } else {
                binding.imgNoData.visibility = View.VISIBLE
            }
        }
        adapter.filteredItem(filteredList)
    }

}