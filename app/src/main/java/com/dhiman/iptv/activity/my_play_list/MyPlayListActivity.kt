package com.dhiman.iptv.activity.my_play_list

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.dashboard.HomeActivity
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.ActivityMyPlayListBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPlayListActivity : AppCompatActivity(), RecyclerViewClickListener {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: MyPlayListViewModel by viewModels()
    lateinit var binding: ActivityMyPlayListBinding
    lateinit var adapter: MyPlayListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_my_play_list
        )
        binding.viewModel = viewModel


        /** Observer Listeners */
        setupObserver()

        /** Click Listeners */
        handleClickListeners()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivBack.requestFocus()
        }, 500)
    }

    /**
     * Click Listeners
     */
    private fun handleClickListeners() {
//        btnAdd.setOnClickListener {
//            if (nameEditText.text.toString().trim().isEmpty()) {
//                "Enter Name".shortToast(this)
//            } else if (usernameEditText.text.toString().trim().isEmpty()) {
//                "Enter Username".shortToast(this)
//            } else if (passwordEditText.text.toString().trim().isEmpty()) {
//                "Enter Password".shortToast(this)
//            } else if (urlEditText.text.toString().trim().isEmpty()) {
//                "Enter URL".shortToast(this)
//            } else {
//                viewModel.userAuth(
//                    urlEditText.text.toString(),
//                    usernameEditText.text.toString(),
//                    passwordEditText.text.toString()
//                )
//            }
//        }
    }

    /**
     * Observer Listeners
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        viewModel.allUserList.observe(this) {
            it?.let {
                val dataList = ArrayList<UserDataModel>()
                for (element in it) {
                    if (element.userModel.playListType.equals(ConstantUtil.XTREAM_URL, true)) {
                        dataList.add(element)
                    }
                }
                if (dataList.isNotEmpty()) {
                    adapter = MyPlayListAdapter(dataList, this)
                    binding.rvUserList.adapter = adapter
                    viewModel.selectedAdapterPosition.postValue(0)
                    viewModel.selectedAdapterUser.postValue(dataList[0])
                }
            }
        }

        viewModel.userAuth.observe(this) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    ProgressDialogPref.hideLoader()
                    it.data?.let {
                        addM3uUrl(it)
                    }
                }
                Status.LOADING -> {
                    ProgressDialogPref.showLoader(this@MyPlayListActivity)
                }
                Status.ERROR -> {
                    ProgressDialogPref.hideLoader()
                    Toast.makeText(this@MyPlayListActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.isUpdatePlayList.observe(this) {
            if (it) {
                ProgressDialogPref.showLoader(this@MyPlayListActivity)
                val selectedModel = viewModel.selectedAdapterUser.value
                selectedModel?.let { model ->
//                    urlEditText.setText(model.userModel.mainUrl)
//                    usernameEditText.setText(model.userModel.userName)
//                    passwordEditText.setText(model.userModel.password)
//                    nameEditText.setText(model.userModel.playListName)
//                    btnAdd.setText(R.string.update)
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        binding.tvHeader.setText(R.string.update_selected_playlist)
//                        ProgressDialogPref.hideLoader()
//                    }, 500)
                }
            } else {
//                urlEditText.setText("")
//                usernameEditText.setText("")
//                passwordEditText.setText("")
//                nameEditText.setText("")
//                btnAdd.setText(R.string.add)
//                binding.tvHeader.setText(R.string.add_new_play_list)
            }
        }

        viewModel.playUserList.observe(this) {
            if (it) {
                viewModel.selectedAdapterUser.value?.let { model ->
                    sharedPrefs.saveUser(model.userModel)

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra(ConstantUtil.INTENT_ID, ConstantUtil.REFRESH_DATA)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

    }

    /**
     * Add M3U8 URL
     */
    private fun addM3uUrl(userAuth: UserAuth) {
        try {
//            val userList = sharedPrefs.loadUserList()
//
//            val url = urlEditText.text.toString() +
//                    "/player_api.php?username=" +
//                    usernameEditText.text.toString() +
//                    "&password=" +
//                    passwordEditText.text.toString()
//            val userModel = UserModel()
//            userModel.mainUrl = urlEditText.text.toString()
//            userModel.userName = usernameEditText.text.toString()
//            userModel.password = passwordEditText.text.toString()
//            userModel.playListName = nameEditText.text.toString()
//            userModel.playListType = ConstantUtil.XTREAM_URL
//            userModel.url = url
//
//            userModel.id = userList.size
//            userList.add(userModel)
//            sharedPrefs.saveUserToList(userList)
//
//            if (viewModel.isUpdatePlayList.value == true) {
//                val userDataModel = viewModel.selectedAdapterUser.value
//                userDataModel?.let {
//                    it.userAuth = userAuth
//                    it.userModel = userModel
//                    viewModel.updateUserRoomDatabase(it)
//                    "Data Updated Successfully".shortToast(this@MyPlayListActivity)
//                }
//            } else {
//                val userDataModel = UserDataModel(userAuth = userAuth, userModel = userModel)
//                viewModel.addUserToRoomDatabase(userDataModel)
//                "Data Added Successfully".shortToast(this@MyPlayListActivity)
//            }
//
//            urlEditText.setText("")
//            usernameEditText.setText("")
//            passwordEditText.setText("")
//            nameEditText.setText("")
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.btnAddPlayList.requestFocus()
//            }, 500)

        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    override fun onClick(view: View, position: Int, selectedModel: Any, childPosition: Int) {
        if (selectedModel is UserDataModel) {
            viewModel.selectedAdapterPosition.postValue(position)
            viewModel.selectedAdapterUser.postValue(selectedModel)

            if (childPosition == -1) {
                sharedPrefs.saveUser(selectedModel.userModel)

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(ConstantUtil.INTENT_ID, ConstantUtil.REFRESH_DATA)
                startActivity(intent)
                (view.context as AppCompatActivity).finishAffinity()
            }
        }
    }

}