package com.samsul.challengegdsc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.databinding.ListItemBinding
import com.samsul.challengegdsc.utils.NoteDiffCallBack

class MainAdapter(val context: Context): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val listNote = arrayListOf<Note>()
    private var listNotes = listOf<Note>()
    private var onItemClickListener: ItemClickListener? = null

    fun setOnItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    fun setListAdapter(item: List<Note>) {
        this.listNotes = item
        val diffCallback = NoteDiffCallBack(listNote, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNote.clear()
        this.listNote.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

    override fun getItemCount(): Int = listNote.size

    inner class MainViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.tvTitle.text = note.title
            binding.tvDescription.text = note.description
            binding.tvTgl.text = note.date

            itemView.setOnClickListener {
                onItemClickListener?.onItemClicked(note)
            }
            binding.imgEdit.setOnClickListener {
                onItemClickListener?.onEditClicked(note)
            }
        }
    }


    interface ItemClickListener {
        fun onItemClicked(note: Note)
        fun onEditClicked(note: Note)
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                var const = constraint
//                const = const.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
//                listNote.clear()
//                if (const.isEmpty()) {
//                    listNote.addAll(listNotes)
//                } else {
//                    for (item in listNotes) {
//                        if (item.title.lowercase(Locale.getDefault()).contains(const)) {
//                            listNote.add(item)
//                        }
//                    }
//                }
//                val results = FilterResults()
//                results.values = listNote
//                return results
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                notifyDataSetChanged()
//            }
//        }
//    }
}