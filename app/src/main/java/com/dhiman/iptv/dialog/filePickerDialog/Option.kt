package com.dhiman.iptv.dialog.filePickerDialog

import java.util.*

class Option internal constructor(val name: String?, val data: String, val path: String) :
    Comparable<Option> {

    override fun compareTo(other: Option): Int {
        return if (this.name != null)
            this.name.lowercase(Locale.getDefault()).compareTo(other.name!!.lowercase(Locale.getDefault()))
        else
            throw IllegalArgumentException()
    }
}

