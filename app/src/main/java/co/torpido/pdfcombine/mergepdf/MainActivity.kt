package co.torpido.pdfcombine.mergepdf

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.torpido.pdfcombine.mergepdf.databinding.ActivityMainBinding
import co.torpido.pdfcombine.mergepdf.presentation.PdfApp
import co.torpido.pdfcombine.mergepdf.presentation.base.PdfPickerActivity
import co.torpido.pdfcombine.mergepdf.presentation.navigation.NavigationItem
import co.torpido.pdfcombine.mergepdf.utils.PdfMergeTool
import com.wada811.databindingktx.dataBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : PdfPickerActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by dataBinding()
    private lateinit var navController: NavHostController
    private var uriList: MutableList<Uri> = mutableListOf()
    val onNavItemClicked: (NavigationItem) -> Unit = { item ->

        when (item) {
            is NavigationItem.Home -> {

            }
            is NavigationItem.Merge -> {
                if(uriList.size>1) {
                    pdfMergeTool.mergePDFs(uriList)
                    navController.navigate(NavigationItem.History.route)
                    uriList.clear()
                }else {
                }

            }
            is NavigationItem.History -> {

            }
        }
    }

    @Inject
    lateinit var pdfMergeTool: PdfMergeTool

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
                onNavItemClicked
            )
        }
    }

    @Composable
    fun PdfAppContent() {
        navController = rememberNavController()
    }


    private fun initListener() = with(binding) {
        setSuccessListener { b, uris ->
            uriList = uris
            navController.navigate("${NavigationItem.Merge.route}?pdfList=$uris")
        }
        pdfMergeTool.setSuccessListener { isSuccess, errorMessage ->
            if (isSuccess) {
                Toast.makeText(
                    this@MainActivity,
                    "Download dizinine kayıt edilerek birleştirme tamamlandı",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@MainActivity, "HATA: $errorMessage", Toast.LENGTH_SHORT).show()

            }

        }

    }


}



