package com.aakash.notes.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aakash.notes.R
import com.aakash.notes.databinding.ActivityAddBinding
import com.aakash.notes.model.data.Notes
import com.aakash.notes.viewmodel.MyApplication
import com.aakash.notes.viewmodel.NotesViewModel
import com.aakash.notes.viewmodel.NotesViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = NotesViewModelFactory((application as MyApplication).repository)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        val isEdit = intent.getBooleanExtra("isEdit", false)
        val id = intent.getIntExtra("id", -1)
        if (isEdit) {
            binding.txtAddNote.text = "Edit Note."
            binding.fabAddBtn.visibility = View.GONE
            binding.fabUpdateBtn.visibility = View.VISIBLE
            binding.imgPopUp.visibility=View.VISIBLE
            setViews(id)
        }
        binding.fabAddBtn.setOnClickListener {
            getData()
        }
        binding.fabDeleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val notes = viewModel.getIdData(id)
                deleteData(notes, id)
            }
        }
        binding.fabUpdateBtn.setOnClickListener {
            val title = binding.edtGetTitle.text.toString()
            val desc = binding.edtGetDesc.text.toString()
            if (title.isNotEmpty() && desc.isNotEmpty()) {
                updateData(title, desc, id)
            } else {
                Toast.makeText(this, "Please fill all required fields!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgPopUp.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_items, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.delete -> {
                        CoroutineScope(Dispatchers.IO).launch {
                            val notes = viewModel.getIdData(id)
                            deleteData(notes, id)
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

    }
    private fun deleteData(notes: Notes, id: Int) {
        lifecycleScope.launch {
            viewModel.deleteDataId(id)
            Toast.makeText(applicationContext, "Deleted..", Toast.LENGTH_SHORT).show()
            finishActivity(1)
        }
//        viewModel.deleteNotes(notes)
//        Toast.makeText(applicationContext, "Deleted Successfully!!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun updateData(title: String, desc: String, id: Int) {
        val notes = Notes(id, title, desc)
        viewModel.updateNotes(notes)
        Toast.makeText(this, "Updated Successfully!!!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun setViews(id: Int) {
        lifecycleScope.launch {
            val notes = viewModel.getIdData(id)
            binding.edtGetDesc.setText(notes.nDesc)
            binding.edtGetTitle.setText(notes.nTitle)
        }
    }

    private fun getData() {
        val title = binding.edtGetTitle.text.toString()
        val desc = binding.edtGetDesc.text.toString()
        if (title.isNotEmpty() && desc.isNotEmpty()) {
            saveData(title, desc)
        } else {
            Toast.makeText(this, "Please fill all required fields!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(title: String, desc: String) {
        val notes = Notes(0, title, desc)
        viewModel.insertNotes(notes)
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
        finish()
    }


}