package io.captyup.resource

import com.google.cloud.storage.BlobId
import io.captyup.service.FilesService
import java.net.URLEncoder

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/files")
class FilesResource {
    @Inject
    lateinit var filesService: FilesService

    @GET
    fun download(): Response {
        return Response.ok(
            filesService.getZipOutputStream(),"application/zip"
        )
        .header("Content-Disposition", "attachment;filename=${URLEncoder.encode("files.zip", "UTF-8")}")
        .build()
    }
}
