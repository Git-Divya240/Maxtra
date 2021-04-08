package com.example.maxtra

import android.content.Intent
import android.net.Uri

fun Any.getUriWhenPickedFromGallery(intent: Intent?): Uri? = intent?.data