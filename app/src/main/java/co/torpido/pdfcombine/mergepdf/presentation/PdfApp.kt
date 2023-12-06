package co.torpido.pdfcombine.mergepdf.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.torpido.pdfcombine.mergepdf.presentation.navigation.BottomNavigationBar
import co.torpido.pdfcombine.mergepdf.presentation.navigation.Navigation
import co.torpido.pdfcombine.mergepdf.presentation.navigation.NavigationItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PdfApp(addPDF: () -> Unit,navController: NavHostController,onNavItemClicked: (NavigationItem) -> Unit, listSize : Int) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController,onNavItemClicked, listSize = listSize) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController, addPdf = addPDF)
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

        },navController,onNavItemClicked,0)
    }
}