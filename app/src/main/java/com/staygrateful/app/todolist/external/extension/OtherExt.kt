package com.staygrateful.app.todolist.external.extension

fun <E> Boolean?.willDo(doThing : E, elseThing : E): E {
    if (this != null && this) {
        return doThing
    }
    return elseThing
}

fun <E> Boolean.justDo(doThing : E?, elseThing : E?): E? {
    if (this) {
        return doThing
    }
    return elseThing
}