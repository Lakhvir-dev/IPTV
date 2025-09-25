package com.dhiman.iptv.dialog.filePickerDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.dhiman.iptv.R
import java.io.File
import java.util.ArrayList

class FilePickerCustomDialogBox(private val mContext: Context) : Dialog(mContext),
    View.OnClickListener,
    AdapterView.OnItemClickListener {
    private var textViewFilePath: TextView? = null
    private var listView: ListView? = null
    private var buttonBack: Button? = null
    private var buttonCancel: Button? = null
    private var dir: ArrayList<Option>? = null
    private var adapter: FileAdapter? = null
    private var currentDir: File? = null
    private var mItemListener: DialogItemListener? = null
    private var initialPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_file_chooser)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setCanceledOnTouchOutside(false)
        initView()
        initList()
        initialPath = "/storage"
        currentDir = File(initialPath.toString())
        fillList()
        setListener()
    }

    private fun fillList() {
        val path = currentDir?.absolutePath
        textViewFilePath!!.text = path
        val fls = ArrayList<Option>()
        try {
            dir!!.clear()
            if (path == initialPath) {
                dir!!.add(
                    Option(
                        context.getString(R.string.text_internal_storage),
                        "Folder",
                        "/storage/emulated/0"
                    )
                )
            } else {
                for (ff in currentDir!!.listFiles()!!) {
                    if (ff.isDirectory)
                        dir!!.add(Option(ff.name, "Folder", ff.absolutePath))
                    else {
                        if (ff.name.contains(".m3u") || ff.name.contains(".M3U"))
                            fls.add(Option(ff.name, "File Size: " + ff.length(), ff.absolutePath))
                    }
                }
                dir!!.add(0, Option("..", "Parent Directory", currentDir!!.parent))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dir!!.sort()
        fls.sort()
        dir!!.addAll(fls)
        adapter!!.notifyDataSetChanged()
    }

    private fun initList() {
        adapter = FileAdapter(mContext, R.layout.file_view, dir!!)
        listView!!.adapter = adapter
        listView!!.itemsCanFocus = true
    }

    private fun setListener() {
        buttonBack!!.setOnClickListener(this)
        buttonCancel!!.setOnClickListener(this)
        listView!!.onItemClickListener = this
    }

    private fun initView() {
        dir = ArrayList()
        textViewFilePath = findViewById(R.id.text_view_file_path)
        buttonCancel = findViewById(R.id.button_cancel)
        buttonBack = findViewById(R.id.button_back)
        listView = findViewById(R.id.list_view)
    }

    override fun onClick(v: View) {
        if (v.id == buttonBack!!.id) {
            dismiss()
        } else if (v.id == buttonCancel!!.id) {
            dismiss()
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val option = adapter!!.getItem(position)
        if (option.data.equals("folder", ignoreCase = true) || option.data.equals(
                "parent directory",
                ignoreCase = true
            )
        ) {
            when (option.path) {
                "/storage/emulated" -> {
                    //                    buttonBack.setVisibility(View.GONE);
                    currentDir = initialPath?.let { File(it) }
                    fillList()
                }
                else -> {
                    //                    buttonBack.setVisibility(View.VISIBLE);
                    currentDir = File(option.path)
                    fillList()
                }
            }
        } else {
            onFileClick(option)
        }
    }

    private fun onFileClick(option: Option) {
        if (mItemListener != null) {
            mItemListener!!.onItemClick(option)
        }
        dismiss()
    }

    fun setItemListener(itemListener: DialogItemListener) {
        mItemListener = itemListener
    }

    interface DialogItemListener {
        fun onItemClick(option: Option)
    }

}