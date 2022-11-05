package io.captyup.service

import com.google.cloud.storage.Storage
import java.nio.channels.Channels
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.StreamingOutput

@ApplicationScoped
class FilesService {
    @Inject
    lateinit var storage: Storage
    fun getZipOutputStream(): StreamingOutput {

        return StreamingOutput { os ->
            val zipOut = ZipOutputStream(os)

            val path1= "1855d5f6-0250-4b80-a369-2246f97108a9-health.txt"
            val blob1 = storage.get("myBucket", path1)
            zipOut.putNextEntry(ZipEntry(path1))
            Channels.newInputStream(blob1.reader()).transferTo(zipOut)
            zipOut.closeEntry()

            val path2= "asset/a7e41c02-a703-400b-8739-661819a06ed8.mp3"
            val blob2 = storage.get("myBucket", path2)
            zipOut.putNextEntry(ZipEntry(path2))
            Channels.newInputStream(blob2.reader()).transferTo(zipOut)
            zipOut.closeEntry()

            zipOut.close()
            os.flush()
            os.close()
        }
    }
}
