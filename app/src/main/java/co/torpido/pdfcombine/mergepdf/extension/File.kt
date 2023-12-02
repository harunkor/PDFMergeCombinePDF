package co.torpido.pdfcombine.mergepdf.extension


import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



fun Uri.getFileNameFromUri(context: Context): String {
    val documentFile = DocumentFile.fromSingleUri(context, this)
    return documentFile?.takeIf { it.exists() }?.name ?: this.lastPathSegment.orEmpty()
}
fun Uri.getPdfPageCount(context: Context): Int {
    try {
        val parcelFileDescriptor: ParcelFileDescriptor? = context.contentResolver.openFileDescriptor(this, "r")
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

@SuppressLint("Range")
fun Uri.getFilePathFromContentUri( context: Context): String? {
    var filePath: String? = null
    if (ContentResolver.SCHEME_CONTENT == this.scheme) {
        try {
            val cursor = context.contentResolver.query(this, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    val inputStream = context.contentResolver.openInputStream(this)
                    val file = File(context.cacheDir, displayName)
                    val outputStream = FileOutputStream(file)
                    inputStream?.copyTo(outputStream)
                    filePath = file.absolutePath
                    inputStream?.close()
                    outputStream.close()
                }
            }
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PdfFilePath", "Hata", e)
        }
    }
    return filePath
}



