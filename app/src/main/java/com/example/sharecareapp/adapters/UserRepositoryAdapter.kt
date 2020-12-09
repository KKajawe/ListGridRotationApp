package com.example.sharecareapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sharecareapp.R
import com.example.sharecareapp.model.RepositoryList
import com.example.sharecareapp.model.RepositoryListItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repository_list_item.view.*

class UserRepositoryAdapter(private val repositoryList: RepositoryList) :
    RecyclerView.Adapter<UserRepositoryAdapter.DataViewHolder>() {

    class DataViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(repositoryListItem: RepositoryListItem) {
            itemView.apply {
                repo_name.text = repositoryListItem.fullName
                repo_desc.text = repositoryListItem.description
            }
        }


    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(repositoryList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_list_item, parent, false),
        )

    override fun getItemCount(): Int = repositoryList.size


    fun updateRepoList(new_repositoryList: RepositoryList) {
        this.repositoryList.apply {
            clear()
            addAll(new_repositoryList)
        }

    }
}
