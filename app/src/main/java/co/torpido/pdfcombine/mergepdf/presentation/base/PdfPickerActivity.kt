package co.torpido.pdfcombine.mergepdf.presentation.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import co.torpido.pdfcombine.mergepdf.extension.getFilePathFromContentUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


abstract class PdfPickerActivity(res: Int): AppCompatActivity(res) {

    private var successListener: ((Boolean,  MutableList<Uri>) -> Unit)? = null
    private lateinit var pdfSelectionLauncher: ActivityResultLauncher<Intent>
    private val selectedPdfUris = mutableListOf<Uri>()
    private val PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        pdfSelectionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    handleSelectedPdfFiles(data)
                }
            }
        }
    }

    private fun handleSelectedPdfFiles(data: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            val clipData = data.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val filePath = uri.getFilePathFromContentUri(applicationContext)
                    val file = File(filePath)
                    val uriNew = FileProvider.getUriForFile(applicationContext, applicationContext.packageName+ ".provider",file)
                    selectedPdfUris.add(uriNew)
                }
            } else {
                val uri = data.data
                val filePath = uri?.getFilePathFromContentUri(applicationContext)
                val file = File(filePath)
                val uriNew = FileProvider.getUriForFile(applicationContext, applicationContext.packageName+ ".provider",file)

                uriNew?.let { selectedPdfUris.add(it) }
            }

            withContext(Dispatchers.Main) {
                successListener?.invoke(true,selectedPdfUris)
            }
        }

    }


    fun openFilePicker() {
        checkPermissions()
    }

    fun setSuccessListener(successListener: (Boolean, MutableList<Uri>) -> Unit) {
        this.successListener = successListener
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
            val writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

            val readPermissionGranted =
                ContextCompat.checkSelfPermission(this, readPermission) == PackageManager.PERMISSION_GRANTED

            val writePermissionGranted =
                ContextCompat.checkSelfPermission(this, writePermission) == PackageManager.PERMISSION_GRANTED

            if (readPermissionGranted && writePermissionGranted) {
                openPicker()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(readPermission, writePermission),
                    PERMISSION_REQUEST_CODE
                )
            }
        } else {
            openPicker()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                openPicker()
            } else {
                Toast.makeText(this, "Dosya izinleri reddedildi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun  openPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pdfSelectionLauncher.launch(intent)
    }
}
