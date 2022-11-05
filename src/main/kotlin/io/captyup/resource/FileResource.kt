package io.captyup.resource

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.Storage
import java.net.URLEncoder
import java.nio.channels.Channels
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

@Path("/file")
class FileResource {
    @Inject
    lateinit var storage: Storage

    @GET
    fun download(@QueryParam("path") path: String): Response {
        val blobId = BlobId.of("myBucket", path)
        val blob = storage.get(blobId)
        return Response.ok(
            Channels.newInputStream(blob.reader()),
            blob.contentType
        )
            .header("Content-Disposition", "attachment;filename=${URLEncoder.encode(blob.name, "UTF-8")}")
            .build()
    }
}
