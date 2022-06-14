package com.example.kotlin_filemanager.image.imageplayer

class ImagePlayerPresenter(private var view: IImagePlayerView?) : IImagePlayerPresenter {

    override fun init() {
        view!!.displayImage()
        view!!.onClick()
    }


}