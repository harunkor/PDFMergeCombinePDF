package co.torpido.pdfcombine.mergepdf.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import co.torpido.pdfcombine.mergepdf.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PdfMergeTool(private val context: Context) {

    private var successListener: ((Boolean, String) -> Unit)? = null

    fun setSuccessListener(successListener: (Boolean, String) -> Unit) {
        this.successListener = successListener
    }

    fun mergePDFs(selectedPdfUriList: MutableList<Uri>) {
        if (selectedPdfUriList.isNotEmpty()) {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val pdfDocument = PdfDocument()
                    for (i in selectedPdfUriList.indices) {

                        withContext(Dispatchers.IO) {

                            val parcelFileDescriptor: ParcelFileDescriptor? =
                                context.contentResolver.openFileDescriptor(selectedPdfUriList[i], "r")
                            if (parcelFileDescriptor != null) {
                                val renderer = PdfRenderer(parcelFileDescriptor)
                                val pageCount = renderer.pageCount
                                for (pageIndex in 0 until pageCount) {

                                    //GETTING IMAGES FROM PDF
                                    val page = renderer.openPage(pageIndex)
                                    val bitmap = Bitmap.createBitmap(
                                        page.width,
                                        page.height,
                                        Bitmap.Config.ARGB_8888
                                    )
                                    val canvas = Canvas(bitmap)
                                    canvas.drawColor(Color.WHITE)
                                    page.render(
                                        bitmap,
                                        null,
                                        null,
                                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                                    )

                                    //ADDING IMAGE TO PDF
                                    val pageInfo = PdfDocument.PageInfo.Builder(A4_WIDTH, A4_HEIGHT, 1).create()
                                    val pdfPage = pdfDocument.startPage(pageInfo)
                                    val pdfCanvas = pdfPage.canvas
                                    pdfCanvas.drawBitmap(bitmap, 0f, 0f, null)
                                    pdfDocument.finishPage(pdfPage)
                                    page.close()
                                }
                                renderer.close()
                            }
                        }
                    }
                    savePdfDocument(pdfDocument)
                    pdfDocument.close()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { successListener?.invoke(false, it) }
            }
        } else {
            successListener?.invoke(false, context.getString(R.string.not_empty))
        }
    }


    private fun savePdfDocument(pdfDocument: PdfDocument) {

        val fileName = context.getString(R.string.pdfmerge) + SimpleDateFormat(context.getString(R.string.date_format_text), Locale.UK).format(Calendar.getInstance().time)
        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadDirectory,"/$fileName.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            successListener?.invoke(true,"")
        } catch (e: IOException) {
            e.localizedMessage?.let { successListener?.invoke(false, it) }
        }
        pdfDocument.close()
    }

    companion object {
        private const val A4_WIDTH = 595
        private const val A4_HEIGHT = 842
    }

}