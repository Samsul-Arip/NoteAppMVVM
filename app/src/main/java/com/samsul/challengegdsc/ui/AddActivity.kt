package com.samsul.challengegdsc.ui

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.databinding.ActivityAddBinding
import com.samsul.challengegdsc.utils.DateHelper
import com.samsul.challengegdsc.utils.ViewModelFactory
import com.samsul.challengegdsc.viewmodel.MainViewModel

class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
    private lateinit var note: Note
    private var toolbarTitle: String? = null
    private var idNote: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        @Suppress("DEPRECATION")
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue_primary)))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.blue_primary)

        note = Note()
        val i = intent
        toolbarTitle = i.getStringExtra("toolbar")
        idNote = i.getIntExtra("id_item", 0)

        note.id = idNote as Int

        if (toolbarTitle != null) {
            supportActionBar?.title = toolbarTitle
            binding.fabAdd.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_edit,
                    theme
                )
            )
            getViewModel()
            binding.fabAdd.setOnClickListener {
                val title = binding.edtTitle.text.toString().trim()
                val about = binding.edtAbout.text.toString().trim()
                val description = binding.edtDescription.text.toString().trim()

                setUpEdit(title, about, description)
            }
        } else {
            supportActionBar?.title = "Add Note"
            binding.fabAdd.setOnClickListener {
                val title = binding.edtTitle.text.toString().trim()
                val about = binding.edtAbout.text.toString().trim()
                val description = binding.edtDescription.text.toString().trim()

                setUpAdd(title, about, description)
            }
        }

    }

    private fun setUpAdd(title: String, about: String, description: String) {
        if (!TextUtils.isEmpty(title) || TextUtils.isEmpty(about) || TextUtils.isEmpty(description)) {
            note.title = title
            note.about = about
            note.description = description
            note.date = DateHelper.getCurrentDate()
            viewModel.insertNote(note)
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Title, About and Description cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUpEdit(title: String, about: String, description: String) {
        if (!TextUtils.isEmpty(title) || TextUtils.isEmpty(about) || TextUtils.isEmpty(description)) {
            note.title = title
            note.about = about
            note.description = description
            note.date = DateHelper.getCurrentDate()
            viewModel.updateNote(note)
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Title, About and Description cannot be empty", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getViewModel() {
        viewModel.getAllNotes().observe(this) {
            if(it!=null) {
                for(i in it) {
                    if(i.id == idNote) {
                        binding.edtTitle.setText(i.title)
                        binding.edtAbout.setText(i.about)
                        binding.edtDescription.setText(i.description)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(toolbarTitle != null) {
            menuInflater.inflate(R.menu.item_add, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Hapus")
            builder.setMessage("Apakah anda yakin ?")
            builder.setPositiveButton("Ya") { dialog, which ->
                note.id = idNote as Int
                viewModel.deleteNote(note)
                Toast.makeText(this, "Berhasil menghapus", Toast.LENGTH_SHORT).show()
                this@AddActivity.finish()
            }
            builder.setNegativeButton("Tidak") {_,_ ->
                Toast.makeText(this, "Batal menghapus", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}