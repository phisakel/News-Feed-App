package gr.phisakel.newsfeed.data.storage.entity

import android.support.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
@Keep
open class SourceDB : RealmObject() {

    @PrimaryKey
    var id: String = ""

    var name: String = ""

    var description: String = ""

    var url: String = ""

    var category: String = ""

    var language: String = ""

    var country: String = ""

    var isEnabled: Boolean = false
}
