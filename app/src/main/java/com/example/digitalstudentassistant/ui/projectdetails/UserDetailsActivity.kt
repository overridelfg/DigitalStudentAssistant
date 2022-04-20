package com.example.digitalstudentassistant.ui.projectdetails

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.ActivityUserDetailsBinding
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.cv.CVActivity
import com.example.digitalstudentassistant.ui.cv.CVViewModel
import com.example.digitalstudentassistant.ui.profile.CVListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserDetailsBinding
    private lateinit var cVListAdapter: CVListAdapter
    private var userId : String = ""
    private val cVViewModel by viewModels<CVViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userNameTextView.text = intent.extras!!.getString("firstName") + " " + intent.extras!!.getString("lastName")
        binding.emailTextView.text = intent.extras!!.getString("email")
        binding.telegramNameTextView.text = intent.extras!!.getString("telegram")
        userId = intent.extras!!.getString("userId")!!
        setUpAdapter()
        subscribeGetUserCV()
        cVViewModel.getCV(intent.extras!!.getString("userId")!!)
    }

    private fun setUpAdapter(){
        cVListAdapter = CVListAdapter{
            val intent = Intent(this, CVActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("cvId", it.id)
            intent.putExtra("nameCV", it.nameCV)
            intent.putExtra("aboutInfo", it.aboutInfo)
            intent.putExtra("school", it.school)
            intent.putExtra("university", it.university)
            intent.putExtra("workStatus", it.workStatus)
            intent.putExtra("citizenship", it.citizenship)
            intent.putExtra("language", it.language)
            intent.putExtra("workSchedule", it.workSchedule)
            intent.putExtra("skill", it.skill)
            intent.putExtra("busyness", it.busyness)
            startActivity(intent)
        }

        binding.cvListAdapter.adapter = cVListAdapter
        binding.cvListAdapter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeGetUserCV(){
        cVViewModel.publicGetCVStateFlow.onEach {
            when(it){
                is UIState.Loading ->{

                }
                is UIState.Success -> {
                    cVListAdapter.cVList.clear()
                    for (element in it.data) {
                        cVListAdapter.cVList.add(
                            element
                        )
                    }
                    cVListAdapter.notifyDataSetChanged()
                }
                is UIState.Error ->{

                }
            }
        }.launchIn(lifecycleScope)
    }
}