package com.example.sharecareapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sharecareapp.R
import com.example.sharecareapp.model.UserItem
import com.example.sharecareapp.util.DiffUtilCallback
import com.example.sharecareapp.util.Util.Companion.SPAN_COUNT_ONE
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.gridview_item.view.*
import kotlinx.android.synthetic.main.listview_item.*


class RecyclerAdapter(layoutManager: GridLayoutManager, val itemClickListnr : UserItemClickListener) : PagedListAdapter<UserItem, RecyclerAdapter.RecyclrViewHolder>(DiffUtilCallback()) {


    private var mLayoutManager: GridLayoutManager = layoutManager

    private val VIEW_TYPE_SMALL = 1
    private val VIEW_TYPE_BIG = 2

    open class RecyclrViewHolder(override val containerView: View, private val viewType: Int) : RecyclerView.ViewHolder(
            containerView
    ), LayoutContainer {

        fun bind(userItem: UserItem, clickListener: (UserItem) -> Unit) {
            itemView.apply {
                if (viewType == 2) {
                    title_big.text = userItem.login
//                description.text = product.description
                    Glide.with(image_big.context)
                            .load(userItem.avatarUrl)
                            .into(image_big)
                } else {
                    title_small.text = userItem.login
//                description.text = product.description
                    Glide.with(image_small.context)
                            .load(userItem.avatarUrl)
                            .into(image_small)
                }
                itemView.setOnClickListener{clickListener(userItem)}
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclrViewHolder {


        val view: View = if (viewType == this.VIEW_TYPE_BIG) {
            LayoutInflater.from(parent.context).inflate(
                R.layout.listview_item,
                    parent,
                    false
            )
        } else {
            LayoutInflater.from(parent.context).inflate(
                R.layout.gridview_item,
                    parent,
                    false
            )
        }
        return RecyclrViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: RecyclrViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it,itemClickListnr.clickListener) }
    }

    /*   override fun getItemCount(): Int =User.size*/

    override fun getItemViewType(position: Int): Int {
        return if (mLayoutManager.spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

}
data class UserItemClickListener(val clickListener: (userItem: UserItem) -> Unit)