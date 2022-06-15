package com.example.curiosity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

class CustomAdapterAreeInteresse(private val context: Context, private var modelArrayList: ArrayList<Model>)  : BaseAdapter() {
        override fun getViewTypeCount(): Int {
            return count
        }
        override fun getItemViewType(position: Int): Int {
            return position
        }
        override fun getCount(): Int {
            return modelArrayList.size
        }
        override fun getItem(position: Int): Any {
            return modelArrayList[position]
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView: View? = convertView
            val holder: ViewHolder
            if (convertView == null) {
                holder = ViewHolder()
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                        as LayoutInflater convertView = inflater.inflate(R.layout.list_are, null, true)
                holder.checkBox = convertView!!.findViewById(R.id.checkBox) as CheckBox holder.tvPlayer = convertView.findViewById(R.id.playerNameList) as TextView
                convertView.tag = holder
            }
            else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = convertView.tag as ViewHolder
            }
            holder.checkBox?.text = "Checkbox $position"
            holder.tvPlayer!!.text = modelArrayList[position].getPlayer()
            holder.checkBox!!.isChecked = modelArrayList[position].getSelected()
            holder.checkBox!!.setTag(R.integer.btnPlusPos, convertView)
            holder.checkBox!!.tag = position
            holder.checkBox!!.setOnClickListener {
                val pos = holder.checkBox!!.tag as Int
                Toast.makeText(
                    context, "Checkbox " + pos + "Clicked!",
                    Toast.LENGTH_SHORT
                ).show()
                if (modelArrayList[pos].getSelected()) {
                    modelArrayList[pos].setSelected(false)
                }
                else {
                    modelArrayList[pos].setSelected(true)
                }
            }
            return convertView
        }
        private inner class ViewHolder {
            var checkBox: CheckBox? = null
            var tvPlayer: TextView? = null
        }
}