package com.biodata.apps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.biodata.apps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        setupUI()
    }

    private fun setupUI() {
        userPreference = UserPreference(context = this)
        userPreference.user.asLiveData().observe(this, {
            if (it == null) {
                binding.btnTambahUser.text = resources.getString(R.string.tambah_user)
            } else {
                binding.btnTambahUser.text = resources.getString(R.string.edit_user)
                binding.txtNama.text = it.nama
                binding.txtGender.text = it.gender
                binding.txtAlamat.text = it.alamat
                binding.txtTempat.text = it.tempat
                binding.txtTanggal.text = it.tanggal
            }
        })
    }

    private fun setupAction() {
        binding.btnTambahUser.setOnClickListener {
            startActivity(Intent(this, TambahUserActivity::class.java))
        }
    }
}