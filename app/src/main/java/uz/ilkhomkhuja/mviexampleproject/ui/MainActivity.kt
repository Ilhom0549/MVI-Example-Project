package uz.ilkhomkhuja.mviexampleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.ilkhomkhuja.mviexampleproject.adapters.MainAdapter
import uz.ilkhomkhuja.mviexampleproject.databinding.ActivityMainBinding
import uz.ilkhomkhuja.mviexampleproject.models.User
import uz.ilkhomkhuja.mviexampleproject.network.ApiClient
import uz.ilkhomkhuja.mviexampleproject.repository.MainIntent
import uz.ilkhomkhuja.mviexampleproject.repository.MainState
import uz.ilkhomkhuja.mviexampleproject.viewmodels.MainViewModel
import uz.ilkhomkhuja.mviexampleproject.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(ApiClient.apiService)
        )[MainViewModel::class.java]
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.GONE
                        renderList(it.users)
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyItemRangeChanged(0, users.size)
    }

    private fun setupClicks() {
        binding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }
}