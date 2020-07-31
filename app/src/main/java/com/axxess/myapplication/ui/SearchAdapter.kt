package com.axxess.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.axxess.myapplication.R
import com.axxess.myapplication.databinding.SearchItemBinding
import com.axxess.myapplication.model.SearchModel

class SearchAdapter(val callback: ImageClick) : RecyclerView.Adapter<SearchViewHolder>() {

    /**
     * The videos that our Adapter will show
     */
    var searchList: List<SearchModel> = emptyList()
        set(value) {
            field = value

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val withDataBinding: SearchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SearchViewHolder.LAYOUT,
            parent,
            false)
        return SearchViewHolder(withDataBinding)
    }

    override fun getItemCount() = searchList.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.searchModel = searchList[position]
            it.imageCallBack = callback
        }
    }

}

/**
 * ViewHolder for items. All work is done by data binding.
 */
class SearchViewHolder(val viewDataBinding: SearchItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.search_item
    }
}


/**
 * Click listener for images. By giving the block a name it helps a reader understand what it does.
 *
 */
class ImageClick(val block: (SearchModel) -> Unit) {
    /**
     * Called when a image is clicked
     *
     * @param image the image that was clicked
     */
    fun onClick(image: SearchModel) = block(image)
}
