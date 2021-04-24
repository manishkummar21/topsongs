package com.example.myapplication.model

import org.simpleframework.xml.*


@Root(name = "feed")
@Namespace(reference = "http://itunes.apple.com/rss", prefix = "im")
class FeedResponse {
    @set:Element
    @get:Element
    var title: String? = null

    @set:ElementList(inline = true, required = false)
    @get:ElementList(inline = true, required = false)
    var entry: List<Songs> = ArrayList()
}