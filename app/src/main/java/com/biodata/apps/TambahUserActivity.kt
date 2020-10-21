package com.biodata.apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.lifecycleScope
import com.biodata.apps.databinding.ActivityTambahUserBinding
import kotlinx.coroutines.launch

class TambahUserActivity : AppCompatActivity() {
    private val binding: ActivityTambahUserBinding by lazy {
        ActivityTambahUserBinding.inflate(layoutInflater)
    }
    private lateinit var userPreference: UserPreference
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userPreference = UserPreference(context = this)

        user = intent?.getParcelableExtra("USER")
        setupUI()
        setupAction()
    }

    private fun setupUI() {
        binding.etTanggalLahir.openCalender(context = this)
        binding.nama.markRequiredInRed()
        binding.tanggalLahir.markRequiredInRed()
        binding.tempatLahir.markRequiredInRed()
        if(user != null){
            binding.nameToolbar.text = resources.getString(R.string.edit_user)
            binding.etNama.text = user?.nama?.toEditable()
            if(user?.gender?.equals("Laki-Laki")!!){
                binding.radioMale.isChecked = true
            }else{
                binding.radioFemale.isChecked = true
            }
            binding.etTempatLahir.text = user?.tempat?.toEditable()
            binding.etTanggalLahir.text = user?.tanggal?.toEditable()
            binding.etAlamat.text = user?.alamat?.toEditable()
        }
    }

    private fun setupAction() {
        binding.btClose.setOnClickListener {
            onBackPressed()
        }

        binding.iconAction.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val gender = findViewById<RadioButton>(checkedRadioButtonId).text.toString().trim()
            val tanggal = binding.etTanggalLahir.text.toString().trim()
            val tempat = binding.etTempatLahir.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()

            val validation = arrayOfNulls<Boolean>(3)

            validation[0] = binding.nama.inputError(
                nama, resources.getString(
                    R.string.empty_fields,
                    resources.getString(R.string.nama)
                )
            )
            validation[1] = binding.tanggalLahir.inputError(
                nama, resources.getString(
                    R.string.empty_fields,
                    resources.getString(R.string.tanggal_lahir)
                )
            )
            validation[2] = binding.tempatLahir.inputError(
                nama, resources.getString(
                    R.string.empty_fields,
                    resources.getString(R.string.tempat_lahir)
                )
            )

            if (!validation.contains(false)) {
                lifecycleScope.launch {
                    userPreference.saveUser(
                        User(
                            nama = nama,
                            gender = gender,
                            tempat = tempat,
                            tanggal = tanggal,
                            alamat = alamat
                        )
                    )
                    binding.root.snackBar("Data berhasil ditambahkan")
                    onBackPressed()
                }
            }
        }
    }
}