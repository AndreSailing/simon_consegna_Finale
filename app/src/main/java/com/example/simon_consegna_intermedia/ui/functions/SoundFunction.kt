import android.content.Context
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sin

fun generateToneWavFile(
    context: Context,
    freqHz: Int,
    durationMs: Int,
    fileName: String
): File {

    val sampleRate = 44100
    val numSamples = durationMs * sampleRate / 1000
    val samples = DoubleArray(numSamples)
    val buffer = ByteArray(numSamples * 2)

    // Genera sinusoide
    for (i in samples.indices) {
        samples[i] = sin(2 * Math.PI * i / (sampleRate / freqHz))
    }

    // Converte in PCM 16-bit
    var idx = 0
    for (d in samples) {
        val value = (d * 32767).toInt()
        buffer[idx++] = (value and 0xFF).toByte()
        buffer[idx++] = ((value shr 8) and 0xFF).toByte()
    }

    // Salva come WAV
    val file = File(context.filesDir, "$fileName.wav")
    val output = FileOutputStream(file)

    // Header WAV
    val header = createWavHeader(buffer.size, sampleRate)
    output.write(header)
    output.write(buffer)
    output.close()

    return file
}

fun createWavHeader(dataLength: Int, sampleRate: Int): ByteArray {
    val totalDataLen = dataLength + 36
    val byteRate = sampleRate * 2

    return byteArrayOf(
        'R'.code.toByte(), 'I'.code.toByte(), 'F'.code.toByte(), 'F'.code.toByte(),
        (totalDataLen and 0xff).toByte(),
        ((totalDataLen shr 8) and 0xff).toByte(),
        ((totalDataLen shr 16) and 0xff).toByte(),
        ((totalDataLen shr 24) and 0xff).toByte(),
        'W'.code.toByte(), 'A'.code.toByte(), 'V'.code.toByte(), 'E'.code.toByte(),
        'f'.code.toByte(), 'm'.code.toByte(), 't'.code.toByte(), ' '.code.toByte(),
        16, 0, 0, 0,
        1, 0,
        1, 0,
        (sampleRate and 0xff).toByte(),
        ((sampleRate shr 8) and 0xff).toByte(),
        ((sampleRate shr 16) and 0xff).toByte(),
        ((sampleRate shr 24) and 0xff).toByte(),
        (byteRate and 0xff).toByte(),
        ((byteRate shr 8) and 0xff).toByte(),
        ((byteRate shr 16) and 0xff).toByte(),
        ((byteRate shr 24) and 0xff).toByte(),
        2, 0,
        16, 0,
        'd'.code.toByte(), 'a'.code.toByte(), 't'.code.toByte(), 'a'.code.toByte(),
        (dataLength and 0xff).toByte(),
        ((dataLength shr 8) and 0xff).toByte(),
        ((dataLength shr 16) and 0xff).toByte(),
        ((dataLength shr 24) and 0xff).toByte()
    )
}
