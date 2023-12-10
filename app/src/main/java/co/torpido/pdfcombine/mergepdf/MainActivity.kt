package co.torpido.pdfcombine.mergepdf

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.torpido.pdfcombine.mergepdf.databinding.ActivityMainBinding
import co.torpido.pdfcombine.mergepdf.presentation.PdfApp
import co.torpido.pdfcombine.mergepdf.presentation.base.PdfPickerActivity
import co.torpido.pdfcombine.mergepdf.presentation.navigation.NavigationItem
import co.torpido.pdfcombine.mergepdf.utils.PdfMergeTool
import com.wada811.databindingktx.dataBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : PdfPickerActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by dataBinding()
    private lateinit var navController: NavHostController
    private var uriList: MutableList<Uri> = mutableListOf()
    private var newSize by mutableStateOf(0)
    private var isLoading by mutableStateOf(false)

    @Inject
    lateinit var pdfMergeTool: PdfMergeTool

    private val onNavItemClicked: (NavigationItem) -> Unit = { item ->

        when (item) {
            is NavigationItem.Home -> {

            }
            is NavigationItem.Merge -> if(uriList.size>1) {
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    pdfMergeTool.mergePDFs(uriList)
                }
            }
            is NavigationItem.History -> {

            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListener()

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        setContent {
            PdfAppContent()
            PdfApp(
                addPDF = {
                    openFilePicker()
                },
                navController,
                onNavItemClicked,
                newSize,
                isLoading
            )
        }
    }

    @Composable
    fun PdfAppContent() {
        navController = rememberNavController()
    }


    private fun initListener() = with(binding) {
        setSuccessListener { _, uris ->
            uriList = uris
            newSize = uris.size
            navController.navigate("${NavigationItem.Merge.route}?pdfList=$uris")
        }
        pdfMergeTool.setSuccessListener { isSuccess, errorMessage ->
            if (isSuccess) {
                Toast.makeText(
                    this@MainActivity,
                    "Download dizinine kayıt edilerek birleştirme tamamlandı",
                    Toast.LENGTH_SHORT
                ).show()
                isLoading = false
                navController.navigate(NavigationItem.History.route)
            } else {
                Toast.makeText(this@MainActivity, "Error: $errorMessage", Toast.LENGTH_SHORT).show()

            }

            uriList.clear()

        }

    }

}



