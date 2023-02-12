package project.example.rvse

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model(var id: Int, val title: String, val text: String): Parcelable
