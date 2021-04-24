package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root
class ID : General() {

    @set:Attribute(required = false)
    @get:Attribute(required = false)
    @Namespace(prefix = "im")
    var id : String? = null
}