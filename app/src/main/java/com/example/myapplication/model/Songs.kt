package com.example.myapplication.model

import org.simpleframework.xml.*

@Root(name = "entry")
class Songs {
    @set:Element
    @get:Element
    var title: String? = null

    @set:Element
    @get:Element
    var id: ID? = null

    @set:Element(name = "name", required = false)
    @get:Element(name = "name", required = false)
    @Namespace(prefix = "im")
    var name: String? = null

    @set:ElementList(inline = true, required = false)
    @get:ElementList(inline = true, required = false)
    var link: List<Link> = arrayListOf()

    @set:ElementList(name = "image", inline = true, required = false)
    @get:ElementList(name = "image", inline = true, required = false)
    @Namespace(prefix = "im")
    var images: List<Image> = arrayListOf()

    @set:Element
    @get:Element
    var category: Category? = null

    @set:Element
    @get:Element
    @Namespace(prefix = "im")
    var artist: String? = null

    @set:Element(required = false)
    @get:Element(required = false)
    @Namespace(prefix = "im")
    var releaseDate: General? = null

}