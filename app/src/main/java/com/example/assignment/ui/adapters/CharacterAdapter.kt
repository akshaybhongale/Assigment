package com.example.assignment.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.AdapterCharacterBinding
import com.example.assignment.models.Results
import com.example.assignment.ui.activities.CharacterDetailsActivity
import com.example.assignment.utils.DEFAULT_ZERO
import com.example.assignment.utils.KEY_CHARACTER
import com.example.assignment.utils.getColorCode

/**
 * This class is used to load list of character data in recycler view
 */
class CharacterAdapter(characterList: ArrayList<Results>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var mList = ArrayList<Results>()

    init {
        mList = characterList
    }

    inner class CharacterViewHolder(private val holder: AdapterCharacterBinding) :
        RecyclerView.ViewHolder(holder.root), View.OnClickListener {
        init {
            holder.root.setOnClickListener(this)
        }

        fun bind(results: Results) {
            holder.txtCharacterName.text = results.name
            holder.txtCharacterSpecies.text = results.species

            holder.txtStatus.setTextColor(getColorCode(results.status, holder.root.context))
            holder.txtStatus.text = results.status
        }

        override fun onClick(v: View?) {
            val data = mList[bindingAdapterPosition]
            val intent = Intent(holder.root.context, CharacterDetailsActivity::class.java)
            intent.putExtra(KEY_CHARACTER, data)
            holder.root.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val holder =
            AdapterCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return CharacterViewHolder(holder)
    }

    /**
     * This method is used to update list and populate UI
     * @param list updated list
     * @param lastSize lastsize of list
     * @param updatedSize newsize after adding new page details
     */
    fun update(list: ArrayList<Results>, lastSize: Int, updatedSize: Int) {
        mList = list
        notifyItemRangeInserted(lastSize, updatedSize)
    }

    /**
     * This method is used to clear recylcerview items list
     */
    fun clearList(){
        notifyItemRangeRemoved(DEFAULT_ZERO,itemCount)
    }
    override fun onBindViewHolder(itemView: CharacterViewHolder, position: Int) {
        itemView.bind(mList[position])
    }

    override fun getItemCount() = mList.size
}