package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root
class Link() {

    constructor(type: String?) : this() {
        this.type = type
    }

    @set:Attribute
    @get:Attribute
    var type: String? = null

    @set:Attribute
    @get:Attribute
    var href: String? = null

    @set:Element(name = "duration", required = false)
    @get:Element(name = "duration", required = false)
    @Namespace(prefix = "im")
    var duration: Long = 0


    override fun equals(other: Any?): Boolean {
        if (other is Link)
            return this.type.equals(other.type,false)
        return false
    }
}