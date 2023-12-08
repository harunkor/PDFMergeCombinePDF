package co.torpido.pdfcombine.mergepdf.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.torpido.pdfcombine.mergepdf.R
import co.torpido.pdfcombine.mergepdf.presentation.navigation.BottomNavigationBar
import co.torpido.pdfcombine.mergepdf.presentation.navigation.Navigation
import co.torpido.pdfcombine.mergepdf.presentation.navigation.NavigationItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfApp(addPDF: () -> Unit,navController: NavHostController,onNavItemClicked: (NavigationItem) -> Unit, listSize : Int, isLoading : Boolean) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController,onNavItemClicked, listSize = listSize) },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        color = colorResource(id = R.color.pdf_document_text_color)
                    )
                } else {
                    Navigation(navController = navController, addPdf = addPDF)
                }

            }
        },
        backgroundColor = Color.Transparent
    )

}

@Preview
@Composable
fun PdfAppPreview() {
    val navController = rememberNavController()
    val onNavItemClicked: (NavigationItem) -> Unit = { }
    MaterialTheme {
        PdfApp(addPDF = {

        },navController,onNavItemClicked,0, false)
    }
}