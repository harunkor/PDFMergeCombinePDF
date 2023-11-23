package co.torpido.pdfcombine.mergepdf.extension

import android.content.ContentResolver
import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.tom_roush.pdfbox.pdmodel.PDDocument
import java.io.FileDescriptor
import java.io.IOException
import java.lang.Exception



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


