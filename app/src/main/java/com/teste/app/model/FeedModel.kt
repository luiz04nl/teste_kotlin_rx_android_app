package com.teste.app.model

interface FeedModel {

    var type: Int

    var publicationDescription: String

    var linkUrl: String

    var publicationCountLike: Int

    var title: String

    var author: Author

    var feed_status: Int

    var community: Community

    var master: Master

}
