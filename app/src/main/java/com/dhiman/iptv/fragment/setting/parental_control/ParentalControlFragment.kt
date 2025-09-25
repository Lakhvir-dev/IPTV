package com.dhiman.iptv.fragment.setting.parental_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentParentalControlBinding
import com.dhiman.iptv.dialog.parental_control.ParentalControlDialog
import kotlin.getValue

class ParentalControlFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentParentalControlBinding
    private val viewModel: ParentalControlViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentParentalControlBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        listeners()
    }

    private fun listeners() {
        binding.setupPinLayout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.setupPinLayout -> {
                view?.context.apply {
                    val parentalControlDialog = ParentalControlDialog()
                    parentalControlDialog.show(
                        (this as AppCompatActivity).supportFragmentManager,
                        ""
                    )
                    parentalControlDialog.callBack = {
                        // startActivity(Intent(this, UserListActivity::class.java))
                    }
                }
//                startActivity(Intent(requireActivity(), SetupPinActivity::class.java))
            }
        }

    }


}