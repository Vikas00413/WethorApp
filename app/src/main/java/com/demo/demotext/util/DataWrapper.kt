package app.rebel.com.bookmylibrary.viewmodel

import com.bookmylibrary.librarian.viewmodel.ErrorModel


class DataWrapper<T>(
    var data: T?, var error: ErrorModel?, var isShowLoader:Boolean//or A message String, Or whatever
)
