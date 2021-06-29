package com.staygrateful.app.todolist.external.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.staygrateful.app.todolist.R
import com.staygrateful.app.todolist.data.response.Todo
import com.staygrateful.app.todolist.external.extension.willDo
import com.staygrateful.app.todolist.external.helper.Helper
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract

class ToDoAdapter(var view: HomepageContract.View? = null) :
    ListAdapter<Todo.Data, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val EMPTY_ITEM_ID = -1
    private val DEFAULT_ITEM_ID = 1
    private val HEADER_ITEM_ID = 2
    var isCompleted = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val li = LayoutInflater.from(parent.context)
        return if (viewType == EMPTY_ITEM_ID) {
            val view = li.inflate(R.layout.item_empty, parent, false)
            EmptyHolder(view, this)
        } else if (viewType == HEADER_ITEM_ID) {
            val view = li.inflate(R.layout.item_list_header, parent, false)
            HeaderViewHolder(view, this)
        } else {
            val view = li.inflate(R.layout.item_list_todo, parent, false)
            ViewHolder(view, this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val id = getToDoItem(position)?.header.willDo(HEADER_ITEM_ID, DEFAULT_ITEM_ID)
        return currentList.isEmpty().willDo(EMPTY_ITEM_ID, id)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("Position : $position is viewholder : ${holder is ViewHolder}")
        if (holder is HeaderViewHolder) {
            holder.onBindData(position, getToDoItem(position))
        } else if (holder is ViewHolder) {
            holder.onBindData(position, getToDoItem(position))
        } else if (holder is EmptyHolder) {
            holder.onBindData(position)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size.coerceAtLeast(1)
    }

    fun getToDoItem(position: Int): Todo.Data? {
        return try {
            getItem(position)
        } catch (e: Exception) {
            null
        }
    }

    fun submit(it: List<Todo.Data>) {
        if (!isCompleted || it.isEmpty()) {
            @Suppress("notifyDataSetChanged")
            notifyDataSetChanged()
        }
        super.submitList(it)
        isCompleted = true
    }

    class HeaderViewHolder(var itemView: View, var adapter: ToDoAdapter) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val textTitle = itemView.findViewById<TextView>(R.id.text_title)

        private var model: Todo.Data? = null

        fun onBindData(position: Int, model: Todo.Data?) {
            if (model != null) {
                textTitle.text = model.headerTitle
                this.model = model
            }
        }

        override fun onClick(view: View?) {

        }
    }

    class ViewHolder(var itemView: View, var adapter: ToDoAdapter) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val textTitle = itemView.findViewById<TextView>(R.id.text_title)
        private val textStatus = itemView.findViewById<TextView>(R.id.text_status)
        private val imageUser = itemView.findViewById<ImageView>(R.id.image_user)
        private val iconStatus = itemView.findViewById<ImageView>(R.id.icon_completed)

        private var model: Todo.Data? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun onBindData(position: Int, model: Todo.Data?) {
            if (model != null) {
                val data = model.data
                if (data != null) {
                    val context = itemView.context
                    textTitle.text = data.title.replaceFirstChar(Char::uppercase)
                    textStatus.text = data.completed.willDo(
                        Helper.getStringRes(context, R.string.text_completed),
                        Helper.getStringRes(context, R.string.text_not_completed)
                    )
                    val bg = imageUser.background.mutate()
                    if (bg is GradientDrawable) {
                        bg.setColor(Helper.getUserColor(data.userId))
                    }
                    val colorStatus = Helper.getColorStatus(context, data.completed)
                    textStatus.setTextColor(colorStatus)
                    iconStatus.setColorFilter(colorStatus)
                    this.model = model
                }
            }
        }

        override fun onClick(view: View?) {
            if (view == itemView) {
                adapter.view?.onClickItemAdapter(view, model)
            }
        }
    }

    class EmptyHolder(var itemView: View, var adapter: ToDoAdapter) :
        RecyclerView.ViewHolder(itemView) {

        private val textEmpty = itemView.findViewById<TextView>(R.id.text_empty)

        fun onBindData(position: Int) {
            if (adapter.isCompleted) {
                textEmpty.text = itemView.context.getString(R.string.message_no_item)
            } else {
                textEmpty.text = itemView.context.getString(R.string.message_progress)
            }
        }
    }

    companion object {
        private val DIFF_UTIL: DiffUtil.ItemCallback<Todo.Data> =
            object : DiffUtil.ItemCallback<Todo.Data>() {
                override fun areItemsTheSame(oldItem: Todo.Data, newItem: Todo.Data): Boolean {
                    return oldItem.sameItem(newItem)
                }

                override fun areContentsTheSame(oldItem: Todo.Data, newItem: Todo.Data): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }
}
