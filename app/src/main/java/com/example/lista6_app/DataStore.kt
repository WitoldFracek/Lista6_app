package com.example.lista6_app

class DataStore {

    companion object{
        val images = arrayOf(
            R.drawable.av_img1,
            R.drawable.av_img2,
            R.drawable.av_img3,
            R.drawable.av_img4
        )

        val SHARED_PREFS = "sharedPreferences"

        val INVITATION_KEY = "invitationKey"
        val INVITATION_KEY_BACK = "invitationKeyBack"
    }
}