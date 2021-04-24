package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Namespace

class Category : General() {

    @set:Attribute
    @get:Attribute
    @Namespace(prefix = "im")
    var id: String? = null

}