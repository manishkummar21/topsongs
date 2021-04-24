package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root
open class General {

    @set:Attribute(required = false)
    @get:Attribute(required = false)
    var label: String? = null

    @set:Text(required = false)
    @get:Text(required = false)
    var text: String? = null

}