package co.torpido.pdfcombine.mergepdf.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.torpido.pdfcombine.mergepdf.R
import co.torpido.pdfcombine.mergepdf.presentation.ui.advertise.Advertise


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    addPDF: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color(0xFFA42516),
                fontSize = 58.24.sp,
                fontFamily = FontFamily(Font(R.font.abril)),
                fontWeight = FontWeight(400)
            )
        ) {
            append(stringResource(id = R.string.pdf))
        }
        withStyle(
            style = SpanStyle(
                color = Color(0xFFA42516),
                fontSize = 24.sp,
                fontWeight = FontWeight(300)
            )
        ) {
            append(stringResource(id = R.string.merge))
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF4F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 0.dp)
                )
        ) {
            Text(
                text = annotatedString,
                modifier = modifier
                    .weight(1f)
                    .padding(start = 16.dp, top = 0.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_add_medium),
                contentDescription = stringResource(
                    id = R.string.add_button_content_description
                ),
                contentScale = ContentScale.None,
                modifier = modifier
                    .padding(top = 32.dp, end = 16.dp)
                    .clickable(onClick = addPDF)

            )

        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Advertise().BannerAdView()
        }
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(
                id = R.drawable.pdf_icon
            ),
            contentDescription = stringResource(
                id = R.string.pdf_icon_content_description
            ),
            modifier = modifier
                .width(109.dp)
                .height(109.dp),
            contentScale = ContentScale.None
        )
        Text(
            text = "You don’t have any PDF Documents",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFFA42516),
            ),
            modifier = modifier.padding(top = 1.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.underline),
            contentDescription = "image description",
            modifier = modifier
                .padding(top = 5.dp, end = 31.dp)
                .width(134.dp)
                .height(9.dp)
                .align(Alignment.End),
            contentScale = ContentScale.None
        )
        Image(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "image description",
            modifier = modifier
                .padding(top = 5.dp, end = 50.dp)
                .width(112.32.dp)
                .align(Alignment.End),
            contentScale = ContentScale.None
        )
        Box(
            modifier = modifier
                .width(65.dp)
                .height(65.dp)
                .border(width = 5.dp, color = Color(0xE5FFFFFF), shape = CircleShape)
                .background(color = Color(0xFFA40D0D), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "add button",
                modifier = modifier
                    .clickable(onClick = addPDF),
                contentScale = ContentScale.None
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(addPDF = {})
    }
}