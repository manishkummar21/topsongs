package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root
class Image {
    @set:Attribute
    @get:Attribute
    var height: Int = 0

    @set:Text
    @get:Text
    var text: String? = null
}