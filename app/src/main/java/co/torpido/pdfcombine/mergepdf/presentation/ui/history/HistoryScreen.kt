package co.torpido.pdfcombine.mergepdf.presentation.ui.history

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HistoryScreen(addPDF: () -> Unit) {
    HistoryScreenList(addPDF = addPDF)
}

@Composable
fun HistoryScreenList(
    modifier: Modifier = Modifier,
    addPDF: () -> Unit
) {
    val annotatedTopBarText = buildAnnotatedString {
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
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF4F2))
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 0.dp)
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = annotatedTopBarText,
                    modifier = modifier
                        .weight(1f)
                        .padding(start = 16.dp, top = 0.dp)
                )

                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_medium),
                        contentDescription = stringResource(
                            id = R.string.add_button_content_description
                        ),
                        contentScale = ContentScale.None,
                        modifier = modifier
                            .padding(top = 32.dp, end = 16.dp)
                            .clickable(onClick = addPDF)
                            .wrapContentSize()
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "History",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFD18C84),
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(15) {
            HistoryScreenItem(Modifier.padding(start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HistoryScreenItem(modifier: Modifier = Modifier) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.pdf_icon_small),
                contentDescription = stringResource(
                    id = R.string.pdf_icon_content_description
                ),
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFA40D0D),
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .width(50.dp)
                    .height(50.dp),
                contentScale = ContentScale.None
            )
            Column(
                modifier = modifier
                    .padding(start = 5.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.brief_task),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFA40D0D),
                    ),
                    modifier = modifier.height(17.dp)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = stringResource(id = R.string.eleven_pages_placeholder, 11),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA40D0D),
                    ),
                    modifier = modifier.height(17.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryScreenItemPreview() {
    MaterialTheme {
        HistoryScreenItem()
    }
}

@Preview
@Composable
fun HistoryScreenListPreview() {
    MaterialTheme {
        HistoryScreenList(addPDF = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen(addPDF = {})
    }
}
