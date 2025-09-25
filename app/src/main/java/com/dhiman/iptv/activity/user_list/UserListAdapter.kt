package com.dhiman.iptv.activity.user_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.databinding.ListItemEpgProgramMenuBinding
import com.dhiman.iptv.databinding.ListItemUserBinding
import com.dhiman.iptv.util.OnFocusChangeListenerInterface
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.onFocusChange

class UserListAdapter(
    val dataList: List<UserDataModel>,
    val clickListener: RecyclerViewClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var deleteUserCallback: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserListHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as UserListHolder

        val model = dataList[position]
        holder.apply {
            binding.apply {
                tvUserName.text = model.userModel.playListName
                tvFolderName.text = model.userModel.url
            }

            itemView.setOnClickListener {
                clickListener.onClick(itemView, position, model, position)
            }

            itemView.setOnLongClickListener {
                deleteUserPopMenu(it, holder.absoluteAdapterPosition)
                false
            }

            itemView.onFocusChange(object : OnFocusChangeListenerInterface {
                override fun onFocus(view: View, value: Boolean) {
                    if (value) {
                        holder.itemView.setBackgroundResource(R.color.white_alpha)
                    } else {
                        holder.itemView.setBackgroundResource(R.color.transparent)
                    }
                }
            })
        }
    }

    /**
     * Delete User Pop Menu
     */
    private fun deleteUserPopMenu(view: View, selPos: Int) {
        val popupWindow = PopupWindow()
        popupWindow.apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            isFocusable = true

            val binding = DataBindingUtil.inflate<ListItemEpgProgramMenuBinding>(
                LayoutInflater.from(view.context),
                R.layout.list_item_epg_program_menu,
                null,
                false
            )

            contentView = binding.root
            showAsDropDown(view)

            binding.apply {
                llAddToFavourite.gone()
                ivPlay.setImageResource(R.drawable.red_cross_small)
                tvPlay.setText(R.string.delete)

                llPlay.setOnClickListener {
                    dismiss()
                    deleteUserCallback?.invoke(selPos)
                }

                llClose.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class UserListHolder(val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}