package com.dhiman.iptv.activity.user_list

import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.databinding.ActivityUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListActivity : BaseActivity<ActivityUserListBinding>(ActivityUserListBinding::inflate) {

    override fun onActivityReady() {

    }

//    @Inject
//    lateinit var sharedPrefs: SharedPrefs
//    private val viewModel: UserListViewModel by viewModels()
//    lateinit var binding: ActivityUserListBinding
//    lateinit var adapter: UserListAdapter

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        binding = DataBindingUtil.setContentView(
////            this,
////            R.layout.activity_user_list
////        )
////        binding.viewModel = viewModel
////
////        /** Observer Listeners */
////        setupObserver()
////
////        /** Set up onFocus Change Listeners */
////        setUpFocusChangeListeners()
//    }


//    /**
//     * Set up onFocus Change Listeners
//     */
//    private fun setUpFocusChangeListeners() {
//        binding.ivBack.requestFocus()
//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.ivBack.requestFocus()
//        }, 500)
//    }

//    /**
//     * Observer Listeners
//     */
//    @SuppressLint("NotifyDataSetChanged")
//    private fun setupObserver() {
//        viewModel.allUserList.observe(this) {
//            it?.let {
//                //   if (it.isNotEmpty()) {
//                val dataList = ArrayList<UserDataModel>()
//                for (element in it) {
//                    if (element.userModel.playListType.equals(ConstantUtil.FILE, true)) {
//                        dataList.add(element)
//                    }
//                }
//
//                adapter = UserListAdapter(dataList, this)
//                binding.rvUserList.adapter = adapter
//                adapter.deleteUserCallback = {
//                    viewModel.deleteUser(dataList[it])
//                }
//                // }
//            }
//        }
//    }

//    override fun onClick(view: View, position: Int, selectedModel: Any, childPosition: Int) {
//        if (selectedModel is UserDataModel) {
//            sharedPrefs.saveUser(selectedModel.userModel)
//            val intent = Intent(this, HomeActivity::class.java)
//            intent.putExtra(ConstantUtil.INTENT_ID, ConstantUtil.REFRESH_DATA_M3U)
//            intent.putExtra(ConstantUtil.DATA_MODEL, Gson().toJson(selectedModel))
//            startActivity(intent)
//            finishAffinity()
//        }
//    }

}