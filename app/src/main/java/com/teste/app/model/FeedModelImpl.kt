package com.teste.app.model

abstract class FeedModelImpl : FeedModel {

    override var publicationDescription: String
        get() = throw NotImplementedError("")
        set(publicationDescription) = throw NotImplementedError("")

    override var linkUrl: String
        get() = throw NotImplementedError("")
        set(linkUrl) = throw NotImplementedError("")

    override var publicationCountLike: Int
        get() = throw NotImplementedError("")
        set(publicationCountLike) = throw NotImplementedError("")

    override var community: Community
        get() = throw NotImplementedError("")
        set(community) = throw NotImplementedError("")

    override var master: Master
        get() = throw NotImplementedError("")
        set(master) = throw NotImplementedError("")

}


