import android.content.Context
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sin
/**
 * Genera un file WAV contenente una sinusoide a frequenza fissa.
 *
 * @param context  Context Android, usato per accedere alla cartella interna dell'app.
 * @param freqHz   Frequenza del tono da generare, in Hertz.
 * @param durationMs Durata del suono in millisecondi.
 * @param fileName Nome del file (senza estensione) da salvare nella memoria interna.
 *
 * @return File WAV generato e salvato in context.filesDir.
 *
 * Il file contiene:
 * - campioni PCM 16-bit little-endian
 * - frequenza di campionamento 44100 Hz
 * - forma d'onda sinusoidale
 */
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
/**
 * Crea l'header WAV (formato RIFF) per un file PCM 16-bit mono.
 *
 * @param dataLength Lunghezza dei dati audio (solo parte PCM), in byte.
 * @param sampleRate Frequenza di campionamento, in Hertz.
 *
 * @return Array di byte contenente l'header WAV completo (44 byte).
 *
 * L'header generato segue il formato:
 * - ChunkID: "RIFF"
 * - Format: "WAVE"
 * - Subchunk1ID: "fmt "
 * - AudioFormat: PCM (1)
 * - NumChannels: 1 (mono)
 * - BitsPerSample: 16
 * - Subchunk2ID: "data"
 */
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
