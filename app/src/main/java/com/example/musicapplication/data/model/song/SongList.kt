import com.example.musicapplication.data.model.song.Song
import com.google.gson.annotations.SerializedName

data class SongList(
    @SerializedName("songs")
    val songs: List<Song> = emptyList()
)