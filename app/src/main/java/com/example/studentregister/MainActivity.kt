package com.example.studentregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.databinding.ActivityMainBinding
import com.example.studentregister.databinding.ListItemsBinding
import com.example.studentregister.db.Student
import com.example.studentregister.db.StudentDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentRecyclerViewAdapter
    private var isListItemClicked = false

    private lateinit var selectedStudent : Student


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MYTAG", "App started!!")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val dao = StudentDatabase.getInstance(application).studentDao()
            Log.i("MYTAG", "Dao")
            val factory = StudentViewModelFactory(dao)
            viewModel = ViewModelProvider(this@MainActivity, factory).get(StudentViewModel::class.java)
            Log.i("MYTAG", "view model initialized")

            btnSave.setOnClickListener {
                Log.i("MYTAG", "Save button clicked")
                if (isListItemClicked) {
                    updateStudentData()
                    clearInput()
                } else {
                    saveStudentData()
                    Log.i("MYTAG", "Student saved")
                    clearInput()
                }
            }

            btnClear.setOnClickListener {
                if (isListItemClicked) {
                    deleteStudentData()
                } else {
                    clearInput()
                }
            }

            initRecyclerView()

        }

    }


    private fun saveStudentData() {
        binding.apply {
            viewModel.insertStudent(
                Student(
                    0,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            Log.i("MYTAG", "Student info saved!!")
        }
    }

    private fun updateStudentData() {
        binding.apply {
            viewModel.updateStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun deleteStudentData() {
        binding.apply {
            viewModel.deleteStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etEmail.text.toString()
                )
            )
            btnSave.text = "Save"
            btnClear.text = "Clear"
            isListItemClicked = false
        }
    }

    private fun clearInput() {
        binding.apply {
            etName.setText("")
            etEmail.setText("")
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvStudent.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                StudentRecyclerViewAdapter { selectedItem: Student -> listItemClicked(selectedItem) }
            rvStudent.adapter = adapter
            displayStudentList()
        }
    }

    private fun displayStudentList() {
        viewModel.students.observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(student: Student) {
        binding.apply {
            selectedStudent = student
            btnSave.text = "Update"
            btnClear.text = "Delete"
            isListItemClicked = true

            etName.setText(selectedStudent.name)
           etEmail.setText(selectedStudent.email)
        }
    }


}