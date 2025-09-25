package com.dhiman.iptv.activity.login_type

import android.view.View
import androidx.databinding.ObservableField
import com.dhiman.iptv.util.base.BaseViewModel

class LoginTypeViewModel : BaseViewModel() {

    val isFocusOnXtream = ObservableField(false)
    val isFocusOnPlayList = ObservableField(false)
    val isFocusOnBack = ObservableField(false)

    fun onFocusChange(view: View, value:Boolean) {
//        when (view.id) {
//            R.id.btnXtreamCode -> {
//                isFocusOnXtream.set(value)
//            }
//            R.id.btnPlayList -> {
//                isFocusOnPlayList.set(value)
//            }
//
//            R.id.ivBack -> {
//                isFocusOnBack.set(value)
//            }
//        }

    }

    fun onClick(view: View) {
//        when (view.id) {
//            R.id.ivBack -> {
//                (view.context as AppCompatActivity).finish()
//            }
//            R.id.btnXtreamCode -> {
//                view.context.apply {
//                    isFocusOnXtream.set(true)
//                    isFocusOnPlayList.set(false)
//
//                    val xtreamCodeDialog = XtreamCodeDialog()
//                    xtreamCodeDialog.show((this as AppCompatActivity).supportFragmentManager, "")
//                    xtreamCodeDialog.callBack = {
//                        val loadingDialog = LoadingDialog()
//                        loadingDialog.show((this).supportFragmentManager, "")
//                        loadingDialog.isCancelable  = false
//                        loadingDialog.callBack = {
//                            startActivity(Intent(this, HomeActivity::class.java))
//                            finishAffinity()
//                        }
//                    }
//                }
//            }
//
//            R.id.btnPlayList -> {
//                view.context.apply {
//                    isFocusOnXtream.set(false)
//                    isFocusOnPlayList.set(true)
////                    val addPlayListDialog = AddPlayListDialog()
////                    addPlayListDialog.show((this as AppCompatActivity).supportFragmentManager, "")
////                    addPlayListDialog.callBack = {
////                        startActivity(Intent(this, UserListActivity::class.java))
////                    }
//                    startActivity(Intent(this, UserListActivity::class.java))
//                }
//            }
//        }
    }
}