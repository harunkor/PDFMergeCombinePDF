package co.torpido.pdfcombine.mergepdf.extension

import android.content.ContentResolver
import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.util.Log
import androidx.documentfile.provider.DocumentFile
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

fun Uri.getFileNameFromUri(contentResolver: ContentResolver): String {
    var fileName = ""
    val cursor = contentResolver.query(this, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                fileName = it.getString(nameIndex)
            }
        }
    }
    return fileName
}

fun Uri.getPdfPageCount(context: Context): Int {
    try {
        val parcelFileDescriptor: ParcelFileDescriptor? = context.applicationContext.contentResolver.openFileDescriptor(this, "r")
        parcelFileDescriptor?.let {
            val pdfRenderer = PdfRenderer(it)
            val pageCount = pdfRenderer.pageCount
            pdfRenderer.close()
            it.close()
            return pageCount
        }
    } catch (e: IOException) {
       e.printStackTrace()
        Log.e("PdfPageCount", "Hata", e)
    }
    return -1
}

fun Uri.getDocumentFile(context: Context): DocumentFile? {
    return DocumentFile.fromSingleUri(context, this)
}


