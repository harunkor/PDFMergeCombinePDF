package co.torpido.pdfcombine.mergepdf.di


import android.content.Context
import co.torpido.pdfcombine.mergepdf.presentation.base.PdfPickerActivity
import co.torpido.pdfcombine.mergepdf.utils.PdfMergeTool
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePdfMerge(@ApplicationContext context: Context) = PdfMergeTool(context)






}