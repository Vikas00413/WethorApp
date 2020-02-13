package com.bookmylibrary.librarian.viewmodel

class ErrorModel {
    var isHasError: Boolean = false

    var error: String? = null



    constructor(hasError: Boolean, error: String) {
        this.isHasError = hasError
        this.error = error
    }
}
