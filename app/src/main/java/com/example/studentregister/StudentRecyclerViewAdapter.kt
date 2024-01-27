package com.example.studentregister

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentregister.db.Student

class StudentRecyclerViewAdapter(
  private val clickListener: (Student) -> Unit
): RecyclerView.Adapter<StudentViewHolder>() {

    private val studentsList = ArrayList<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_items, parent, false)
        return StudentViewHolder(listItem)
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


class StudentViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun bind(student: Student, clickListener: (Student) -> Unit){
        val nameTextView = view.findViewById<TextView>(R.id.tvName)
        val emailTextView = view.findViewById<TextView>(R.id.tvEmail)
        nameTextView.text = student.name
        emailTextView.text = student.email
        view.setOnClickListener {
            clickListener(student)
        }
    }
}