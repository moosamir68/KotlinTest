package com.example.moosamir.myapplicationkotlin.Adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moosamir.myapplicationkotlin.R
import kotlinx.android.synthetic.main.song_item_view.view.*

class AlbumsAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.

    class ViewHolder(val cell: View) : RecyclerView.ViewHolder(cell){
        var song_title = cell.song_name_textview.text
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AlbumsAdapter.ViewHolder {
        // create a new view

        val layoutInflater = LayoutInflater.from(parent.context)

        val cellForRow = layoutInflater.inflate(R.layout.song_item_view, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ViewHolder(cellForRow)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.textView.text = myDataset[position]
        holder.song_title = myDataset[position]
        holder.cell.song_name_textview.text = myDataset[position]
        holder.cell.song_image_imageview.setImageResource(R.drawable.ic_icons8_hamburger)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}