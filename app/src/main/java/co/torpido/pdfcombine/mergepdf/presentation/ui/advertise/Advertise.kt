package co.torpido.pdfcombine.mergepdf.presentation.ui.advertise

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import co.torpido.pdfcombine.mergepdf.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.wada811.databindingktx.BuildConfig

class Advertise {



    @Composable
    fun BannerAdView() {
        val unitId = if (BuildConfig.DEBUG)
             stringResource(id = R.string.banner_test_id)
        else stringResource(id = R.string.banner_id)

        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.FULL_BANNER)
                    adUnitId = unitId
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }


    @Composable
    @Preview
    fun BannerAdViewPreview(){
        BannerAdView()
    }

}
