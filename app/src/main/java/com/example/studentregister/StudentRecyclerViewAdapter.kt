package com.example.studentregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.databinding.ListItemsBinding
import com.example.studentregister.db.Student

class StudentRecyclerViewAdapter(
  private val clickListener: (Student) -> Unit
): RecyclerView.Adapter<StudentViewHolder>() {

    private val studentsList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentsList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentsList[position], clickListener)
    }

    fun setList(student: List<Student>){
        studentsList.clear()
        studentsList.addAll(student)

    }


}


class StudentViewHolder(private val binding: ListItemsBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(student: Student, clickListener: (Student) -> Unit) {
        binding.apply {
            tvName.text = student.name
            tvEmail.text = student.email
            root.setOnClickListener {
                clickListener(student)
            }
        }
    }
}