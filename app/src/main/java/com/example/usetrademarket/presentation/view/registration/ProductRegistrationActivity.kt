package com.example.usetrademarket.presentation.view.registration

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityProductRegistrationBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProductRegistrationActivity : BaseActivity<ActivityProductRegistrationBinding>(
    R.layout.activity_product_registration
) {

    private val viewModel: ProductRegistrationViewModel by viewModel()

    private val getContentImage1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let {
                viewModel.imageUri = it.data
                binding.image1.setImageURI(it.data)
                viewModel.uploadImage(uriToFile(it.data))
            }
        }

    private val getContentImage2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let {
                viewModel.imageUri = it.data
                binding.image2.setImageURI(it.data)
                viewModel.uploadImage(uriToFile(it.data))
            }
        }

    private val getContentImage3 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let {
                viewModel.imageUri = it.data
                binding.image3.setImageURI(it.data)
                viewModel.uploadImage(uriToFile(it.data))
            }
        }

    private val getContentImage4 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.let {
                viewModel.imageUri = it.data
                binding.image4.setImageURI(it.data)
                viewModel.uploadImage(uriToFile(it.data))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun initViewModelCallBack() = with(viewModel) {
        pickContentImage.observe(this@ProductRegistrationActivity, EventObserver {

            val intent = Intent(Intent.ACTION_PICK).apply {
                type = MediaStore.Images.Media.CONTENT_TYPE
                type = "image/*"
            }
            when(it) {
                0 -> {
                    getContentImage1.launch(intent)
                }
                1 -> {
                    getContentImage2.launch(intent)
                }
                2 -> {
                    getContentImage3.launch(intent)
                }
                3 -> {
                    getContentImage4.launch(intent)
                }
            }

        })

        error.observe(this@ProductRegistrationActivity, EventObserver {
            showToast(it)
        })

        validName.observe(this@ProductRegistrationActivity, EventObserver{
            showToast(it)
        })

        validDescription.observe(this@ProductRegistrationActivity, EventObserver{
            showToast(it)
        })

        validPrice.observe(this@ProductRegistrationActivity, EventObserver{
            showToast(it)
        })
        validCategory.observe(this@ProductRegistrationActivity, EventObserver{
            showToast(it)
        })
        validImageIds.observe(this@ProductRegistrationActivity, EventObserver{
            showToast(it)
        })
        success.observe(this@ProductRegistrationActivity, EventObserver{
            AlertDialog.Builder(this@ProductRegistrationActivity)
                .setTitle("성공")
                .setMessage("상품이 등록되었습니다.")
                .setPositiveButton("확인") { dialog, _ ->
                    finish()
                }
                .create()
                .show()
        })
    }

    private fun uriToFile(uri: Uri?): File? = uri?.let {
        contentResolver.openInputStream(uri)?.use { input ->
            val ext = if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
                MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))
            } else {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
            }
            val file = File.createTempFile("FILE_${System.currentTimeMillis()}", ".$ext", cacheDir)
            file.outputStream().use { output ->
                input.copyTo(output)
                return file
            }
        }
        return null
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ProductRegistrationActivity::class.java)
    }
}