package com.example.sharecareapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sharecareapp.adapters.RecyclerAdapter
import com.example.sharecareapp.adapters.UserItemClickListener
import com.example.sharecareapp.util.Util.Companion.SPAN_COUNT_ONE
import com.example.sharecareapp.util.Util.Companion.SPAN_COUNT_THREE
import com.example.sharecareapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mtoolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        gridLayoutManager = if (viewModel.isListView) {
            GridLayoutManager(this, SPAN_COUNT_ONE)
        } else {
            GridLayoutManager(this, SPAN_COUNT_THREE)
        }
        recyclerAdapter = RecyclerAdapter(gridLayoutManager, UserItemClickListener {
            val intent: Intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra("userItem", it)
            startActivity(intent)
        })

        observeLiveData()
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = gridLayoutManager
    }

    private fun observeLiveData() {
        //observe live data emitted by view model
        viewModel.getUserList().observe(this, {
            recyclerAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toggleview, menu)
        if (viewModel.isListView) {
            menu?.findItem(R.id.menu_switch_layout)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_span_1
            )
        } else {
            menu?.findItem(R.id.menu_switch_layout)?.icon = ContextCompat.getDrawable(
                this,
                R.drawable.ic_span_3
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_switch_layout) {
            switchLayout()
            switchIcon(item)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = SPAN_COUNT_THREE
            viewModel.isListView = false
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
            viewModel.isListView = true
        }
        recyclerAdapter.notifyItemRangeChanged(0, recyclerAdapter.itemCount)
    }

    private fun switchIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_span_1)
        } else {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_span_3)
        }
    }
}