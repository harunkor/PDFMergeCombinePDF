package co.torpido.pdfcombine.mergepdf.extension

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.IOException


fun Uri.getFileNameAndExtension(): Pair<String, String>? {
    val path = this.path
    if (path != null) {
        val fileName = path.substringAfterLast("/").replace("]","")
        val extension = fileName.substringAfterLast(".", "")
        if (extension.isNotEmpty()) {
            return Pair(fileName, extension)
        }
    }
    return null
}

fun Uri.getDocumentFile(context: Context): DocumentFile? {
    return DocumentFile.fromSingleUri(context, this)
}

fun Uri.getPdfPageCount(context: Context): Int {
    try {
        val parcelFileDescriptor: ParcelFileDescriptor? =
       context.applicationContext.contentResolver.openFileDescriptor(this, "r")

        parcelFileDescriptor?.let {
            val pdfRenderer = PdfRenderer(it)
            val pageCount = pdfRenderer.pageCount
            pdfRenderer.close()
            it.close()

            return pageCount
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return -1
}



